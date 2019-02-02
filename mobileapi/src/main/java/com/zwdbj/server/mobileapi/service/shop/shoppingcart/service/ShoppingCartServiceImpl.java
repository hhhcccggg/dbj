package com.zwdbj.server.mobileapi.service.shop.shoppingcart.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zwdbj.server.mobileapi.service.shop.shoppingcart.model.ProductInfo;
import com.zwdbj.server.mobileapi.service.shop.shoppingcart.model.ProductSKU;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    //    用户没登陆用户名和密码,添加商品,关闭浏览器再打开后 不登录用户名和密码：购物车商品还在
//
//    用户登陆了用户名密码,添加商品, 关闭浏览器再打开后 不登录用户名和密码:购物车商品不在
//    用户登陆了用户名密码,添加商品,关闭浏览器,然后再打开,登陆用户名和密码:购物车商品还在
//    用户登陆了用户名密码,添加商品, 关闭浏览器 外地老家打开浏览器  登陆用户名和密码：购物车还在
//

    public ServiceStatusInfo<Long> add(HttpServletRequest request,
                                       HttpServletResponse response,
                                       ProductInfo productInfo) {
        try {

            //判断用户是否登录
            long userId = JWTUtil.getCurrentId();
            if (userId == 0l) {
                //未登录，将购物车添加到cookie中
                if (productInfo == null) {
                    return new ServiceStatusInfo<>(1, "您未添加任何商品", null);
                }
                List<ProductInfo> shoppingCart = new ArrayList<>();
                shoppingCart.add(productInfo);
                Cookie cookie = new Cookie("shoppingcart", URLEncoder.encode(JSON.toJSONString(shoppingCart), "UTF-8"));
                cookie.setPath("/");//设置path是可以共享cookie
                cookie.setMaxAge(60 * 30);//设置cookie过期时间30分钟
                response.addCookie(cookie);
                return new ServiceStatusInfo<>(0, "", 1L);

            } else {
                //判断cooke中是否有商品，若有添加到redis，清空cookie
                Cookie[] cookies = request.getCookies();
                HashOperations<String, String, Long> hashOperations = stringRedisTemplate.opsForHash();
                if (cookies != null) {
                    List<ProductInfo> shoppingCart = null;
                    for (Cookie cookie : cookies) {

                        if ("shoppingcart".equals(cookie.getName()) && cookie.getValue() != null) {
                            shoppingCart = JSON.parseObject(URLDecoder.decode(cookie.getValue(), "UTF-8"), new TypeReference<List<ProductInfo>>() {

                            });
                            //清空cookie
                            Cookie c = new Cookie("shoppingcart", null);
                            c.setPath("/");
                            c.setMaxAge(0);
                            response.addCookie(c);
                            break;
                        }
                    }
                    for (ProductInfo p : shoppingCart) {
                        hashOperations.put("shoppingcart" + userId, JSON.toJSONString(p.getSku()), p.getCount());
                    }

                }
                //添加购物车到redis中
                if (productInfo == null) {
                    return new ServiceStatusInfo<>(1, "您未添加任何商品", 0L);
                }
                //判断是否添加了同款商品
                String key = JSON.toJSONString(productInfo.getSku());
                if (hashOperations.hasKey("shoppingcart" + userId, key)) {
                    long nums = hashOperations.get("shoppingcart" + userId, key);
                    hashOperations.put("shoppingcart" + userId, key, nums + productInfo.getCount());
                    return new ServiceStatusInfo<>(0, "", 1L);
                }
                hashOperations.put("shoppingcart" + userId, key, productInfo.getCount());
                return new ServiceStatusInfo<>(0, "", 1L);
            }

        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "添加购物车失败" + e.getMessage(), 0L);
        }
    }


    public ServiceStatusInfo<List<ProductInfo>> getShoppingCart(HttpServletRequest request) {
        try {
            long userId = JWTUtil.getCurrentId();
            if (userId == 0L) {//未登录
                //查看cookie
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if ("shoppingcart".equals(cookie.getName()) && cookie.getValue() != null) {
                            String str = URLDecoder.decode(cookie.getValue(), "UTF-8");

                            List<ProductInfo> shoppingcart = JSON.parseObject(str, new TypeReference<List<ProductInfo>>() {

                            });

                            return new ServiceStatusInfo<>(0, "", shoppingcart);
                        }
                    }
                }
                return new ServiceStatusInfo<>(1, "您没添加任何商品", null);

            } else {
                HashOperations<String, String, Long> hashOperations = stringRedisTemplate.opsForHash();
                List<ProductInfo> shoppingcart = new ArrayList<>();
                if (stringRedisTemplate.hasKey("shoppingcart" + userId)) {
                    Map<String, Long> map = hashOperations.entries("shoppingcart" + userId);
                    Set<String> set = map.keySet();
                    Iterator<String> it = set.iterator();

                    while (it.hasNext()) {
                        String str = it.next();
                        long count = map.get(str);
                        shoppingcart.add(new ProductInfo(JSON.parseObject(str, ProductSKU.class), count));
                    }
                    return new ServiceStatusInfo<>(0, "", shoppingcart);
                }
                return new ServiceStatusInfo<>(1, "您没有添加任何商品", null);

            }


        } catch (Exception e) {

            return new ServiceStatusInfo<>(1, e.getMessage(), null);
        }
    }

}
