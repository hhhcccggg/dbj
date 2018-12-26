package com.zwdbj.server.mobileapi.service.youzan.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.youzan.open.sdk.client.auth.Token;
import com.youzan.open.sdk.client.core.DefaultYZClient;
import com.youzan.open.sdk.client.core.YZClient;
import com.youzan.open.sdk.client.oauth.OAuth;
import com.youzan.open.sdk.client.oauth.OAuthContext;
import com.youzan.open.sdk.client.oauth.OAuthFactory;
import com.youzan.open.sdk.client.oauth.OAuthType;
import com.youzan.open.sdk.gen.v3_0_0.api.*;
import com.youzan.open.sdk.gen.v3_0_0.model.*;
import com.youzan.open.sdk.gen.v3_0_0.api.YouzanUmpPromocardBuyerSearch;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanUmpPromocardBuyerSearchParams;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanUmpPromocardBuyerSearchResult;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ResponsePageInfoData;
import com.zwdbj.server.mobileapi.config.AppConfigConstant;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import com.zwdbj.server.mobileapi.service.user.model.UserModel;
import com.zwdbj.server.mobileapi.service.user.service.UserService;
import com.zwdbj.server.mobileapi.service.youzan.model.AyouzanTradeCartAddParams;
import com.zwdbj.server.mobileapi.service.youzan.model.YZItemDto;
import com.zwdbj.server.mobileapi.service.youzan.model.YZSearchItemInput;
import com.zwdbj.server.mobileapi.service.youzan.model.YZUserLoginToken;
import okhttp3.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 有赞相关服务
 */
@Service
public class YouZanService {
    @Autowired
    UserService userService;
//    @Autowired
//    StringRedisTemplate stringRedisTemplate;
//    @Autowired
//    RedisTemplate redisTemplate;
    static org.slf4j.Logger logger = LoggerFactory.getLogger(YouZanService.class);

    protected final OkHttpClient client = new OkHttpClient();

    public ServiceStatusInfo<String> getToken() {
        String url = String.format("https://uic.youzan.com/sso/open/initToken?client_id=%s&client_secret=%s",
                AppConfigConstant.YOUZAN_CLIENT_ID,AppConfigConstant.YOUZAN_SECRECT);
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),""))
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String bodyJSON = response.body().string();
                JSONObject jsonObject = JSON.parseObject(bodyJSON);
                int code = jsonObject.getInteger("code");
                String msg = jsonObject.getString("msg");
                if (code != 0) {
                    return new ServiceStatusInfo<>(1,msg,"");
                }
                String token = jsonObject.getJSONObject("data").getString("access_token");
                return new ServiceStatusInfo<>(0,msg,token);
            } else {
                return new ServiceStatusInfo<>(1,response.message(),"");
            }
        }
        catch (Exception ex) {
            return new ServiceStatusInfo<>(1,ex.getMessage(),"");
        }
    }

    public ServiceStatusInfo<YZUserLoginToken> getUserToken(long userId) {

//        String cacheKey = AppConfigConstant.getRedisYouzanUserTokenKey(userId);
//        if (this.redisTemplate.hasKey(cacheKey)) {
//            YZUserLoginToken token= (YZUserLoginToken)this.redisTemplate.opsForValue().get(cacheKey);
//            if (token != null) {
//                return new ServiceStatusInfo<>(0,"",token);
//            }
//        }

        UserModel userModel= this.userService.findUserById(userId);
        if (userModel==null) {
            return new ServiceStatusInfo<>(1,"未找到用户",null);
        }

        int sexType = 2;
        if (userModel.getSex()==0) {
            sexType = 2;
        } else if (userModel.getSex()==1) {
            sexType =0;
        } else if (userModel.getSex()==2) {
            sexType = 1;
        }

        String openUserId = String.valueOf(userId);


        String url = String.format("https://uic.youzan.com/sso/open/login?kdt_id=%s&client_id=%s&" +
                        "client_secret=%s&open_user_id=%s&nick_name=%s&gender=%d&telephone=%s&avatar=%s",
                AppConfigConstant.YOUZAN_BIND_SHOP_ID,
                AppConfigConstant.YOUZAN_CLIENT_ID,
                AppConfigConstant.YOUZAN_SECRECT,
                openUserId,
                userModel.getNickName(),
                sexType,
                userModel.getPhone(),
                userModel.getAvatarUrl());
        logger.info(url);


        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),""))
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {

                String jsonStr = response.body().string();
                logger.info("返回》》"+jsonStr);
                JSONObject jsonObject = JSONObject.parseObject(jsonStr);
                int code = jsonObject.getInteger("code");
                String msg = jsonObject.getString("msg");
                if (code != 0) {
                    return new ServiceStatusInfo<>(1,msg,null);
                }
                YZUserLoginToken token = new YZUserLoginToken();
                token.setCookieKey(jsonObject.getJSONObject("data").getString("cookie_key"));
                token.setCookieValue(jsonObject.getJSONObject("data").getString("cookie_value"));
                token.setAccessToken(jsonObject.getJSONObject("data").getString("access_token"));
                //写入缓存
//                this.redisTemplate.opsForValue().set(cacheKey,token,7*23*3600-600,TimeUnit.SECONDS);
                return new ServiceStatusInfo<>(0,msg,token);

            } else  {
                return new ServiceStatusInfo<>(1,response.message(),null);
            }
        } catch (Exception ex) {
            return new ServiceStatusInfo<>(1,ex.getMessage(),null);
        }
    }

    public ServiceStatusInfo<Integer> logOut(long userId) {
        String url = String.format("https://uic.youzan.com/sso/open/logout?client_id=%s&" +
                        "client_secret=%s&open_user_id=%s",
                AppConfigConstant.YOUZAN_CLIENT_ID,
                AppConfigConstant.YOUZAN_SECRECT,
                String.valueOf(userId));
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),""))
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
//                this.redisTemplate.delete(AppConfigConstant.getRedisYouzanUserTokenKey(userId));
                String jsonStr = response.body().string();
                JSONObject jsonObject = JSONObject.parseObject(jsonStr);
                int code = jsonObject.getInteger("code");
                String msg = jsonObject.getString("msg");
                if (code != 0) {
                    return new ServiceStatusInfo<>(1,msg,code);
                }
                return new ServiceStatusInfo<>(0,msg,code);

            } else  {
                return new ServiceStatusInfo<>(1,response.message(),-1);
            }
        } catch (Exception ex) {
            return new ServiceStatusInfo<>(1,ex.getMessage(),-1);
        }
    }

    public ServiceStatusInfo<String> getServerToken() {
        String token=null;
//        if (this.stringRedisTemplate.hasKey(AppConfigConstant.REDIS_YOUZAN_SERVER_TOKEN_KEY)) {
//            token = this.stringRedisTemplate.opsForValue().get(AppConfigConstant.REDIS_YOUZAN_SERVER_TOKEN_KEY);
//        }
        if (token == null) {
            OAuth oAuth = OAuthFactory.create(OAuthType.SELF,new OAuthContext(AppConfigConstant.YOUZAN_CLIENT_ID,AppConfigConstant.YOUZAN_SECRECT,
                    Long.parseLong(AppConfigConstant.YOUZAN_BIND_SHOP_ID)));
            token = oAuth.getToken().getAccessToken();
//            this.stringRedisTemplate.opsForValue().set(AppConfigConstant.REDIS_YOUZAN_SERVER_TOKEN_KEY,token,oAuth.getToken().getExpiresIn()-60,TimeUnit.SECONDS);
            logger.info("获取有赞SERVER TOKEN:"+token);
        }
        return new ServiceStatusInfo<>(0,"",token);
    }


    //## 相关api请求
    /**
     * 搜索有赞的商城商品
     * @param input
     * @return
     */
    public ResponsePageInfoData<List<YZItemDto>> searchGoods(YZSearchItemInput input) {
        ServiceStatusInfo<String> token = getServerToken();
        YZClient client = new DefaultYZClient(new Token(token.getData()));
        YouzanItemSearchParams youzanItemSearchParams = new YouzanItemSearchParams();

        youzanItemSearchParams.setQ(input.getQ());
        youzanItemSearchParams.setPageNo((long)input.getPageNo());
        youzanItemSearchParams.setPageSize((long)input.getRows());
        if (input.getIds()!=null&&input.getIds().length()>0) {
            youzanItemSearchParams.setItemIds(input.getIds());
        }

        YouzanItemSearch youzanItemSearch = new YouzanItemSearch();
        youzanItemSearch.setAPIParams(youzanItemSearchParams);
        YouzanItemSearchResult result = client.invoke(youzanItemSearch);
        if (result==null) {
            return new ResponsePageInfoData<>(ResponseDataCode.STATUS_ERROR,"内部发生错误",null,0);
        }
        List<YZItemDto> dtos = new ArrayList<>();
        for (YouzanItemSearchResult.ItemListOpenModel itemListOpenModel:result.getItems()) {
            YZItemDto dto = new YZItemDto();
            dto.setId(itemListOpenModel.getItemId());
            dto.setAlias(itemListOpenModel.getAlias());
            dto.setTitle(itemListOpenModel.getTitle());
            dto.setPrice(itemListOpenModel.getPrice().intValue());
            dto.setNo(itemListOpenModel.getItemNo());
            dto.setQuantity(itemListOpenModel.getQuantity().intValue());
            dto.setPostType(itemListOpenModel.getPostType().intValue());
            dto.setPostFee(itemListOpenModel.getPostFee());
            dto.setDetailUrl(itemListOpenModel.getDetailUrl());
            dto.setOrigin(itemListOpenModel.getOrigin());
            dto.setImageUrl(itemListOpenModel.getImage());
            dtos.add(dto);
        }
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL,"",dtos,result.getCount());
    }

    public ServiceStatusInfo<Integer> lotteryTicketCount(long userId) {
        ServiceStatusInfo<String> token = getServerToken();
        YZClient client = new DefaultYZClient(new Token(token.getData()));
        YouzanUmpPromocardBuyerSearchParams youzanUmpPromocardBuyerSearchParams = new YouzanUmpPromocardBuyerSearchParams();

        youzanUmpPromocardBuyerSearchParams.setStatus("VALID");
//        youzanUmpPromocardBuyerSearchParams.setFansId(userId);
        String phone = this.userService.getPhone(userId);
        phone = "18161279360";
        if (phone!=null && !phone.isEmpty()) {
            youzanUmpPromocardBuyerSearchParams.setMobile(Long.valueOf(phone));
        } else {
            youzanUmpPromocardBuyerSearchParams.setOpenUserId(userId);
        }

        YouzanUmpPromocardBuyerSearch youzanUmpPromocardBuyerSearch = new YouzanUmpPromocardBuyerSearch();
        youzanUmpPromocardBuyerSearch.setAPIParams(youzanUmpPromocardBuyerSearchParams);
        try {
            YouzanUmpPromocardBuyerSearchResult result = client.invoke(youzanUmpPromocardBuyerSearch);
            if (result == null) {
                logger.info("未找到用户{" + userId + "}的优惠券信息");
            }
            return new ServiceStatusInfo<>(0, "", result == null ? 0 : result.getCards().length);
        } catch ( Exception ex ) {
            logger.warn(ex.getMessage());
            return new ServiceStatusInfo<>(0, "", 0);
        }
    }

    public ServiceStatusInfo<Integer> cartNum(long userId) {
        ServiceStatusInfo<YZUserLoginToken> tokenServiceStatusInfo = this.getUserToken(userId);
        if(!tokenServiceStatusInfo.isSuccess()) {
            return new ServiceStatusInfo<Integer>(1,"获取token失败",null);
        }
        YZClient client = new DefaultYZClient(new Token(tokenServiceStatusInfo.getData().getAccessToken()));
        YouzanTradeCartCountParams youzanTradeCartCountParams = new YouzanTradeCartCountParams();
        youzanTradeCartCountParams.setKdtId(Long.parseLong(AppConfigConstant.YOUZAN_BIND_SHOP_ID));
        YouzanTradeCartCount youzanTradeCartCount = new YouzanTradeCartCount();
        youzanTradeCartCount.setAPIParams(youzanTradeCartCountParams);
        YouzanTradeCartCountResult result = client.invoke(youzanTradeCartCount);
        if (result!=null&&result.getIsSuccess()) {
            return new ServiceStatusInfo<>(0,"",result.getData().intValue());
        }
        return new ServiceStatusInfo<Integer>(1,"获取失败",null);
    }

    public ServiceStatusInfo<Integer> oerderNum(long userId)  {
        YZClient client = new DefaultYZClient(new Token(getServerToken().getData()));
        YouzanTradesSoldOuterGetParams youzanTradesSoldOuterGetParams = new YouzanTradesSoldOuterGetParams();

        youzanTradesSoldOuterGetParams.setOuterUserId(String.valueOf(userId));
        youzanTradesSoldOuterGetParams.setOuterType("");
        youzanTradesSoldOuterGetParams.setPageSize(1L);
        youzanTradesSoldOuterGetParams.setPageNo(10L);
        youzanTradesSoldOuterGetParams.setStatus("");

        YouzanTradesSoldOuterGet youzanTradesSoldOuterGet = new YouzanTradesSoldOuterGet();
        youzanTradesSoldOuterGet.setAPIParams(youzanTradesSoldOuterGetParams);
        YouzanTradesSoldOuterGetResult result = client.invoke(youzanTradesSoldOuterGet);
        if (result!=null) {
            return new ServiceStatusInfo<Integer>(0,"",result.getTotalResults().intValue());
        }
        return new ServiceStatusInfo<>(0,"",0);
    }

    public ServiceStatusInfo<Integer> addCartGoods(Long userId, Long storeId, AyouzanTradeCartAddParams cartAddParams){
        ServiceStatusInfo<YZUserLoginToken> tokenServiceStatusInfo = this.getUserToken(userId);
        if(!tokenServiceStatusInfo.isSuccess()) {
            return new ServiceStatusInfo<Integer>(1,"获取token失败",null);
        }
        YZClient client = new DefaultYZClient(new Token(tokenServiceStatusInfo.getData().getAccessToken()));
        //YouzanTradeCartAddParams youzanTradeCartAddParams = new YouzanTradeCartAddParams();
        //YouzanMultistoreGoodsSkuGetResult.GoodsDetail goodsDetail= cartAddParams.getGoodsDetail();
        YouzanMultistoreGoodsSkuGetParams youzanMultistoreGoodsSkuGetParams = new YouzanMultistoreGoodsSkuGetParams();
        youzanMultistoreGoodsSkuGetParams.setNumIid(cartAddParams.getItemId());
        youzanMultistoreGoodsSkuGetParams.setOfflineId(storeId);
        YouzanMultistoreGoodsSkuGet youzanMultistoreGoodsSkuGet = new YouzanMultistoreGoodsSkuGet();
        youzanMultistoreGoodsSkuGet.setAPIParams(youzanMultistoreGoodsSkuGetParams);
        YouzanMultistoreGoodsSkuGetResult result1 = client.invoke(youzanMultistoreGoodsSkuGet);
        cartAddParams.setGoodsDetail(result1.getItem());
        YouzanTradeCartAdd youzanTradeCartAdd = new YouzanTradeCartAdd();
        youzanTradeCartAdd.setAPIParams(cartAddParams);
        YouzanTradeCartAddResult result = client.invoke(youzanTradeCartAdd);
        if (result!=null&&result.getIsSuccess()) {
            return new ServiceStatusInfo<>(0,"添加成功",1);
        }
        return new ServiceStatusInfo<Integer>(1,"添加失败",null);
    }

    /**
     * 获取网点商品SKU
     */
    //public YouzanMultistoreGoodsSkuGetResult.GoodsDetail
}
