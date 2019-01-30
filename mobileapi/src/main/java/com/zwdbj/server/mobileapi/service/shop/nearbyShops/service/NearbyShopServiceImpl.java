package com.zwdbj.server.mobileapi.service.shop.nearbyShops.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.zwdbj.server.mobileapi.service.shop.nearbyShops.mapper.NearbyShopsMapper;
import com.zwdbj.server.mobileapi.service.shop.nearbyShops.model.*;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Service
public class NearbyShopServiceImpl implements NearbyShopService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private NearbyShopsMapper nearbyShopsMapper;
    private Logger logger = LoggerFactory.getLogger(NearbyShopServiceImpl.class);

    @Override
    public ServiceStatusInfo<ShopInfo> shopHomePage(long storeId) {
        try {
            //判断redis缓存中是否有当前列表信息，如果有从redis中获取。若无从数据库查询
            ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
            if (valueOperations.get("shopInfo" + storeId) != null) {
                String str = valueOperations.get("shopInfo" + storeId);
                ShopInfo shopInfo = JSON.parseObject(str, new TypeReference<ShopInfo>() {
                });
                System.out.println("有缓存---shopInfo" + storeId);
                System.out.println(str);
                System.out.println(shopInfo);

                return new ServiceStatusInfo<>(0, "", shopInfo);
            }
            ShopInfo result = nearbyShopsMapper.searchShopsById(storeId);

            List<DiscountCoupon> discountCoupons = this.nearbyShopsMapper.searchDiscountCoupon(storeId);
            result.setDiscountCoupons(discountCoupons);

            List<StoreServiceCategory> serviceScopes = this.nearbyShopsMapper.searchServiceScopes(storeId);
            result.setServiceScopes(serviceScopes);

            List<StoreServiceCategory> extraServices = this.nearbyShopsMapper.searchExtraServices(storeId);
            result.setExtraServices(extraServices);

            List<OpeningHours> openingHours = this.nearbyShopsMapper.searchOpeningHours(storeId);
            result.setOpeningHours(openingHours);
            System.out.println("无缓存---shopInfo" + storeId);
            System.out.println(valueOperations.get("shopInfo" + storeId));
//            redisTemplate.expire("shopInfo" + String.valueOf(storeId), 30, TimeUnit.MINUTES);
            if (result != null) {
                valueOperations.set("shopInfo" + storeId, JSON.toJSONString(result));

            }
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "获取商家首页信息失败" + e.getMessage(), null);
        }

    }

    @Override
    public ServiceStatusInfo<SuperStar> superStar(long storeId) {
        try {
            SuperStar result = this.nearbyShopsMapper.searchSuperStar(storeId);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询代言人失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<DiscountCouponDetail> searchDiscountCouponDetail(long discountCouponId) {
        try {
            DiscountCouponDetail result = this.nearbyShopsMapper.seachDiscountCouponDetail(discountCouponId);
            return new ServiceStatusInfo<>(0, "", result);

        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查看代金，优惠券详情失败" + e.getMessage(), null);
        }

    }

    @Override
    public ServiceStatusInfo<List<NearbyShop>> nearbyShopList(int pageNo) {
        try {
            ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
            if (valueOperations.get("nearbyShopList---pageNo" + pageNo) != null) {
                String str = valueOperations.get("nearbyShopList---pageNo" + pageNo);
                List<NearbyShop> list = JSON.parseObject(str, new TypeReference<List<NearbyShop>>() {
                });
                System.out.println("从缓存中拉取商家列表");
                return new ServiceStatusInfo<>(0, "", list);
            }
            List<NearbyShop> result = this.nearbyShopsMapper.nearbyShopList();
            if (result != null) {
                valueOperations.set("nearbyShopList---pageNo" + pageNo, JSONArray.toJSONString(result));

            }
            System.out.println("从数据库中拉取商家列表");
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "拉取附近商家列表失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<List<NearbyShop>> searchShop(String search, String rank, double lat, double lon) {
        RestClientBuilder builder = RestClient.builder(
                new HttpHost("localhost", 9200, "http"))
                .setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
                    public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder builder) {
                        return builder.setConnectTimeout(5000)//设置连接超时
                                .setSocketTimeout(60000);//设置socket超时
                    }
                }).setMaxRetryTimeoutMillis(60000);//设置最大重试超时时间}
        RestHighLevelClient client = new RestHighLevelClient(builder);
        SearchRequest searchRequest = new SearchRequest();//可以设置检索的索引，类型
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder matchQuery = QueryBuilders.boolQuery().must(new MultiMatchQueryBuilder("Chipotle")//全文检索
                .fuzziness(Fuzziness.AUTO)).filter(QueryBuilders.geoDistanceQuery("location").point(lat, lon)//过滤十公里内的商家
                .distance(10, DistanceUnit.KILOMETERS));

        searchSourceBuilder.sort("rank");//按指定排序
        searchSourceBuilder.query(matchQuery);
        searchSourceBuilder.size(10);//一次取回10条数据
        searchRequest.source(searchSourceBuilder);
        searchRequest.scroll(TimeValue.timeValueMinutes(1L));//设置sroll间隔
        List<NearbyShop> result = null;
        try {
            SearchResponse searchResponse = client.search(searchRequest);
            if (searchResponse.status().getStatus() == 200) {
                String scrollId = searchResponse.getScrollId();//返回当前的scrollId，下次查询从当前位置开始
                SearchHit[] hits = searchResponse.getHits().getHits();
                logger.info("first scroll:");
                for (SearchHit searchHit : hits) {
                    result.add(JSON.parseObject(searchHit.getSourceAsString(), new TypeReference<NearbyShop>() {
                    }));
                    logger.info(searchHit.getSourceAsString());
                }
                Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
                logger.info("loop scroll:");
                while (hits != null && hits.length > 0) {
                    SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
                    scrollRequest.scroll(scroll);
                    searchResponse = client.searchScroll(scrollRequest);
                    scrollId = searchResponse.getScrollId();
                    hits = searchResponse.getHits().getHits();
                    for (SearchHit searchHit : hits) {
                        result.add(JSON.parseObject(searchHit.getSourceAsString(), new TypeReference<NearbyShop>() {
                        }));
                        logger.info(searchHit.getSourceAsString());
                    }
                }
                ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
                clearScrollRequest.addScrollId(scrollId);
                ClearScrollResponse clearScrollResponse = client.clearScroll(clearScrollRequest);
                boolean succeeded = clearScrollResponse.isSucceeded();
                logger.info("cleared:" + succeeded);
            }
            return new ServiceStatusInfo<>(0, "", result);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            return new ServiceStatusInfo<>(1, "搜索商家失败" + e.getMessage(), null);
        }
    }
}
