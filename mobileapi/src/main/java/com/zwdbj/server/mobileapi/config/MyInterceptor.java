package com.zwdbj.server.mobileapi.config;

import com.zwdbj.server.es.common.ESIndex;
import com.zwdbj.server.mobileapi.middleware.mq.ESUtil;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.text.DateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * mybatis拦截器,目前主要用于修改视频表后,发送消息队列
 */
@Intercepts(value = {
        @Signature(type = Executor.class,
                method = "update",
                args = {MappedStatement.class, Object.class}) })
public class MyInterceptor implements Interceptor {

    @Autowired
    private RedisTemplate redisTemplate;

    private static Logger logger = LoggerFactory.getLogger(MyInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        /**执行方法*/
        Object result = invocation.proceed();
        final Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }
        BoundSql boundSql = ms.getBoundSql(parameter);
        Configuration configuration = ms.getConfiguration();
        String sql = showSql(configuration,boundSql);
        String commandName = ms.getSqlCommandType().name();
        //如果修改了视频就发送消息队列发送给ES
        if(sql.indexOf(" core_videos ")>-1 &&
                (commandName.startsWith("INSERT") || commandName.startsWith("UPDATE") || commandName.startsWith("DELETE")) ){
           try{
               String action = null;
               String type=null;
               if(sql.indexOf(" core_videos ")>-1)
                   type = ESIndex.VIDEO;

               if(commandName.startsWith("INSERT"))
                   action =ESIndex.CREATE;
               else if(commandName.startsWith("UPDATE"))
                   action =ESIndex.UPDATE;
               else if(commandName.startsWith("DELETE"))
                   action =ESIndex.DELETE;
               if(action != null){
                   long id = sliptSqlGetId(action,sql);
                   if( !redisTemplate.hasKey(id)){
                       redisTemplate.opsForValue().set(id, id,60, TimeUnit.SECONDS);
                       ESUtil.QueueWorkInfoModelSend(id, type, action);
                   }
               }
           }catch (Exception e){
               logger.error("[MyInterceptor]sql:" + sql);
               logger.error("[MyInterceptor]commandName" + commandName);
               logger.error("[MyInterceptor]log:" + e.getMessage());
           }
        }
        return result;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * 切割sql获取sql中id的值
     * @param action
     * @param sql
     * @return
     */
    public static long sliptSqlGetId(String action,String sql){
        String sqlnew = sql.toLowerCase();
        if(action .equals(ESIndex.CREATE)){
            List<String> stringList = extractMessageByRegular(sqlnew);
            String insert = stringList.get(0);
            String value = stringList.get(1);
            String[] values = value.split(",");
            int index = getId(insert.split(","),"id");
            if(index>-1){
                return Long.parseLong(values[index].trim());
            }
        }else if(action .equals(ESIndex.UPDATE) || action .equals(ESIndex.DELETE)){
            String[] sqlnumber = sqlnew.split("where");
            if(sqlnumber.length != 2)
                return 0;
            String[] wheres = sqlnumber[1].split("and");
            for (int i = 0; i < wheres.length; i++) {
                //去掉空格
                String str2 = wheres[i].replaceAll(" ", "");
                if(str2.indexOf("id=")>-1 || str2.indexOf("`id`=")>-1 )
                    return Long.parseLong(str2.replaceAll("id=","")
                            .replaceAll("`id`=","").trim());
            }
        }
        return 0;
    }

    //使用循环判断
    public static int getId(String[] arr,String targetValue){
        for (int i = 0; i < arr.length; i++) {
            //去掉空格
            String str2 = arr[i].replaceAll(" ", "");
            if(str2.equals(targetValue) || str2.equals("`"+targetValue+"`") )
                return i;
        }
        return -1;
    }

    /**
     * 使用正则表达式提取中括号中的内容
     * @param msg
     * @return
     */
    public static List<String> extractMessageByRegular(String msg){
        List<String> list=new ArrayList<String>();
        Pattern p = Pattern.compile("(\\([^\\)]*\\))");
        Matcher m = p.matcher(msg);
        while(m.find()){
            list.add(m.group().substring(1, m.group().length()-1));
        }
        return list;
    }

    private String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(obj) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }
        }
        return value;
    }

    /**
     * 获取拼接过后的sql
     * @param configuration
     * @param boundSql
     * @return
     */
    public String showSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (parameterMappings.size() > 0 && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));

            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    }
                }
            }
        }
        return sql;
    }
}