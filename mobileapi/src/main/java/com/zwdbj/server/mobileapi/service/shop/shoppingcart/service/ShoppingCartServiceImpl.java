package com.zwdbj.server.mobileapi.service.shop.shoppingcart.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zwdbj.server.mobileapi.service.shop.shoppingcart.model.ProductInfo;
import com.zwdbj.server.mobileapi.service.shop.shoppingcart.model.ProductSKU;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
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
                Cookie cookie = new Cookie("shoppingCart", URLEncoder.encode(JSON.toJSONString(shoppingCart), "UTF-8"));
                cookie.setPath("/");//设置path是可以共享cookie
                cookie.setMaxAge(60 * 30);//设置cookie过期时间30分钟
                response.addCookie(cookie);
                return new ServiceStatusInfo<>(0, "", 1L);

            } else {
                //判断cooke中是否有商品，若有添加到redis，清空cookie
                Cookie[] cookies = request.getCookies();
                HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
                if (cookies != null) {
                    List<ProductInfo> shoppingCart = null;
                    for (Cookie cookie : cookies) {

                        if ("shoppingCart".equals(cookie.getName()) && cookie.getValue() != null) {
                            shoppingCart = JSON.parseObject(URLDecoder.decode(cookie.getValue(), "UTF-8"), new TypeReference<List<ProductInfo>>() {

                            });
                            //清空cookie
                            Cookie c = new Cookie("shoppingCart", null);
                            c.setPath("/");
                            c.setMaxAge(0);
                            response.addCookie(c);
                            break;
                        }
                    }
                    for (ProductInfo p : shoppingCart) {
                        hashOperations.put("shoppingCart" + userId, JSON.toJSONString(p.getSku()), String.valueOf(p.getCount()));
                    }

                }
                //添加购物车到redis中
                if (productInfo == null) {
                    return new ServiceStatusInfo<>(1, "您未添加任何商品", 0L);
                }
                //判断是否添加了同款商品
                String key = JSON.toJSONString(productInfo.getSku());
                if (hashOperations.hasKey("shoppingCart" + userId, key)) {
                    long nums = Long.parseLong(hashOperations.get("shoppingCart" + userId, key));

                    hashOperations.put("shoppingCart" + userId, key, String.valueOf(nums + productInfo.getCount()));
                    return new ServiceStatusInfo<>(0, "", 1L);
                }
                hashOperations.put("shoppingCart" + userId, key, String.valueOf(productInfo.getCount()));
                return new ServiceStatusInfo<>(0, "", 1L);
            }

        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "添加购物车失败" + e.getMessage(), 0L);
        }
    }


    public ServiceStatusInfo<List<ProductInfo>> getShoppingCart(HttpServletRequest request, HttpServletResponse response) {
        try {
            //查看cookie
            List<ProductInfo> shoppingCart = null;
            Cookie[] cookies = request.getCookies();
            if (cookies != null && cookies.length > 0) {
                for (Cookie cookie : cookies) {
                    if ("shoppingCart".equals(cookie.getName()) && cookie.getValue() != null) {
                        String str = URLDecoder.decode(cookie.getValue(), "UTF-8");

                        shoppingCart = JSON.parseObject(str, new TypeReference<List<ProductInfo>>() {

                        });
                        break;
                    }
                }
            }
            long userId = JWTUtil.getCurrentId();
            HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();

            if (shoppingCart == null) {
                if (userId == 0L) {
                    return new ServiceStatusInfo<>(1, "您没添加任何商品", null);
                } else {

                    shoppingCart = new ArrayList<>();
                    if (stringRedisTemplate.hasKey("shoppingCart" + userId)) {
                        Map<String, String> map = hashOperations.entries("shoppingCart" + userId);
                        Set<String> set = map.keySet();
                        Iterator<String> it = set.iterator();

                        while (it.hasNext()) {
                            String str = it.next();
                            long count = Long.parseLong(map.get(str));
                            shoppingCart.add(new ProductInfo(JSON.parseObject(str, ProductSKU.class), count));
                        }
                        return new ServiceStatusInfo<>(0, "", shoppingCart);
                    }
                    return new ServiceStatusInfo<>(1, "您没有添加任何商品", null);
                }
            } else {

                if (userId == 0L) {
                    return new ServiceStatusInfo<>(0, "", shoppingCart);
                } else {
                    //存入redis中 清空cookie
                    //判断是否添加了同款商品
                    for (ProductInfo productInfo : shoppingCart) {
                        String key = JSON.toJSONString(productInfo.getSku());
                        if (hashOperations.hasKey("shoppingCart" + userId, key)) {
                            long nums = Long.parseLong(hashOperations.get("shoppingCart" + userId, key));
                            hashOperations.put("shoppingCart" + userId, key, String.valueOf(nums + productInfo.getCount()));

                        }
                        hashOperations.put("shoppingCart" + userId, key, String.valueOf(productInfo.getCount()));

                    }
                    Cookie c = new Cookie("shoppingCart", null);
                    c.setPath("/");
                    c.setMaxAge(0);
                    response.addCookie(c);

                    Map<String, String> map = hashOperations.entries("shoppingCart" + userId);
                    Set<String> set = map.keySet();
                    Iterator<String> it = set.iterator();
                    shoppingCart = new ArrayList<>();
                    while (it.hasNext()) {
                        String str = it.next();
                        long count = Long.parseLong(map.get(str));
                        shoppingCart.add(new ProductInfo(JSON.parseObject(str, ProductSKU.class), count));
                    }

                    return new ServiceStatusInfo<>(0, "", shoppingCart);
                }
            }

        } catch (Exception e) {

            return new ServiceStatusInfo<>(1, e.getMessage(), null);
        }
    }

    public ServiceStatusInfo<List<ProductInfo>> modifyShoppingCart(HttpServletRequest request, HttpServletResponse response, ProductInfo p) {
        try {
            long userId = JWTUtil.getCurrentId();
            List<ProductInfo> shoppingCart = null;
            if (userId == 0L) {
                Cookie[] cookies = request.getCookies();
                if (cookies != null && cookies.length > 0) {
                    for (Cookie cookie : cookies) {
                        if ("shoppingCart".equals(cookie.getName()) && cookie.getValue() != null) {
                            String str = URLDecoder.decode(cookie.getValue(), "UTF-8");

                            shoppingCart = JSON.parseObject(str, new TypeReference<List<ProductInfo>>() {

                            });
                            break;
                        }
                    }
                }
                if (shoppingCart == null) {
                    return new ServiceStatusInfo<>(1, "您得购物车为空", null);
                }

                for (ProductInfo productInfo : shoppingCart) {
                    if (productInfo.getSku().equals(p.getSku())) {
                        productInfo.setCount(productInfo.getCount() + p.getCount());
                    }
                }
                Cookie cookie = new Cookie("shoppingCart", URLEncoder.encode(JSON.toJSONString(shoppingCart), "UTF-8"));
                cookie.setPath("/");//设置path是可以共享cookie
                cookie.setMaxAge(60 * 30);//设置cookie过期时间30分钟
                response.addCookie(cookie);

                return new ServiceStatusInfo<>(0, "", shoppingCart);

            } else {
                HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
                String key = JSON.toJSONString(p.getSku());
                if (hashOperations.hasKey("shoppingCart" + userId, key)) {
                    long nums = Long.parseLong(hashOperations.get("shoppingCart" + userId, key));
                    hashOperations.put("shoppingCart" + userId, key, String.valueOf(nums + p.getCount()));
                    Map<String, String> map = hashOperations.entries("shoppingCart" + userId);
                    Set<String> set = map.keySet();
                    Iterator<String> it = set.iterator();
                    shoppingCart = new ArrayList<>();
                    while (it.hasNext()) {
                        String str = it.next();
                        long count = Long.parseLong(map.get(str));
                        shoppingCart.add(new ProductInfo(JSON.parseObject(str, ProductSKU.class), count));
                    }
                    return new ServiceStatusInfo<>(0, "", shoppingCart);
                }
                return new ServiceStatusInfo<>(1, "您没有添加此商品", null);

            }

        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, e.getMessage(), null);
        }

    }

    public ServiceStatusInfo<List<ProductInfo>> deleteShoppingCart(HttpServletRequest request, HttpServletResponse response,
                                                                   ProductInfo p) {
        try {
            long userId = JWTUtil.getCurrentId();
            List<ProductInfo> shoppingCart = null;
            if (userId == 0L) {
                Cookie[] cookies = request.getCookies();
                if (cookies != null && cookies.length > 0) {
                    for (Cookie cookie : cookies) {
                        if ("shoppingCart".equals(cookie.getName()) && cookie.getValue() != null) {

                            String str = URLDecoder.decode(cookie.getValue(), "UTF-8");
                            shoppingCart = JSON.parseObject(str, new TypeReference<List<ProductInfo>>() {
                            });
                            break;
                        }
                    }
                }
                for (ProductInfo productInfo : shoppingCart) {
                    if (productInfo.getSku().equals(p.getSku())) {
                        shoppingCart.remove(productInfo);

                    }
                }
                Cookie cookie = new Cookie("shoppingCart", URLEncoder.encode(JSON.toJSONString(shoppingCart), "UTF-8"));
                cookie.setPath("/");//设置path是可以共享cookie
                cookie.setMaxAge(60 * 30);//设置cookie过期时间30分钟
                response.addCookie(cookie);
                return new ServiceStatusInfo<>(0, "", shoppingCart);
            } else {
                HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
                String key = JSON.toJSONString(p.getSku());
                if (hashOperations.hasKey("shoppingCart" + userId, key)) {
                    hashOperations.delete("shoppingCart" + userId, key);
                    Map<String, String> map = hashOperations.entries("shoppingCart" + userId);
                    Set<String> set = map.keySet();
                    Iterator<String> it = set.iterator();
                    shoppingCart = new ArrayList<>();
                    while (it.hasNext()) {
                        String str = it.next();
                        long count = Long.parseLong(map.get(str));
                        shoppingCart.add(new ProductInfo(JSON.parseObject(str, ProductSKU.class), count));
                    }
                    return new ServiceStatusInfo<>(0, "", shoppingCart);
                }
                return new ServiceStatusInfo<>(1, "您没有添加此商品", null);

            }

        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, e.getMessage(), null);
        }
    }

}
