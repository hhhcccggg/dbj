package com.zwdbj.server.mobileapi.service.shop.nearbyShops.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.zwdbj.server.mobileapi.service.shop.nearbyShops.mapper.NearbyShopsMapper;
import com.zwdbj.server.mobileapi.service.shop.nearbyShops.model.*;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NearbyShopServiceImpl implements NearbyShopService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private NearbyShopsMapper nearbyShopsMapper;
    private Logger logger = LoggerFactory.getLogger(NearbyShopServiceImpl.class);
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public ServiceStatusInfo<ShopInfo> shopHomePage(long storeId) {
        try {
            //判断redis缓存中是否有当前列表信息，如果有从redis中获取。若无从数据库查询
            ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
            if (valueOperations.get("shopInfo" + storeId) != null) {
                String str = valueOperations.get("shopInfo" + storeId);
                ShopInfo shopInfo = JSON.parseObject(str, new TypeReference<ShopInfo>() {
                });
                logger.info("有缓存---shopInfo" + storeId);
                logger.info(str);

                return new ServiceStatusInfo<>(0, "", shopInfo);
            }
            ShopInfo result = nearbyShopsMapper.searchShopsById(storeId);

            List<DiscountCoupon> discountCoupons = this.nearbyShopsMapper.searchDiscountCoupon(storeId);
            result.setProducts(discountCoupons);

            List<StoreServiceCategory> serviceScopes = this.nearbyShopsMapper.searchServiceScopes(storeId);
            result.setServiceScopes(serviceScopes);

            List<StoreServiceCategory> extraServices = this.nearbyShopsMapper.searchExtraServices(storeId);
            result.setExtraServices(extraServices);

            List<OpeningHours> openingHours = this.nearbyShopsMapper.searchOpeningHours(storeId);
            result.setOpeningHours(openingHours);
            logger.info("无缓存---shopInfo" + storeId);
            logger.info(valueOperations.get("shopInfo" + storeId));
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
                logger.info("从缓存中拉取商家列表");
                return new ServiceStatusInfo<>(0, "", list);
            }
            List<NearbyShop> result = this.nearbyShopsMapper.nearbyShopList();
            if (result != null) {
                valueOperations.set("nearbyShopList---pageNo" + pageNo, JSONArray.toJSONString(result));

            }
            logger.info("从数据库中拉取商家列表");
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "拉取附近商家列表失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<List<SearchShop>> searchShop(SearchInfo info) {


        try {
            SearchRequest searchRequest = new SearchRequest("shop");//可以设置检索的索引，类型
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            QueryBuilder matchQuery = null;
            if ("all".equals(info.getFilter())) {
                if (info.getSearch() == null || "".equals(info.getSearch())) {
                    matchQuery = QueryBuilders.geoDistanceQuery("location").point(info.getLat(), info.getLon())//过滤十公里内的商家
                            .distance(50, DistanceUnit.KILOMETERS);

                } else {
                    matchQuery = QueryBuilders.boolQuery().should(new MultiMatchQueryBuilder(info.getSearch(),
                                    "name",
                                    "discountCoupons.name",
                                    "serviceScopes.name",
                                    "address")
//                            .fuzziness(Fuzziness.AUTO)//模糊匹配
                    ).filter(QueryBuilders.geoDistanceQuery("location").point(info.getLat(), info.getLon())//过滤十公里内的商家
                            .distance(50, DistanceUnit.KILOMETERS)
                    );
                }
            } else {
                if (info.getSearch() == null || "".equals(info.getSearch())) {
                    matchQuery = QueryBuilders.boolQuery()
                            .should(new MatchQueryBuilder("name", info.getFilter()))
                            .filter(QueryBuilders.geoDistanceQuery("location").point(info.getLat(), info.getLon())//过滤十公里内的商家
                                    .distance(50, DistanceUnit.KILOMETERS)
                            );
                } else {
                    matchQuery = QueryBuilders.boolQuery()
                            .should(new MatchQueryBuilder("name", info.getFilter()))
                            .should(new MultiMatchQueryBuilder(info.getSearch(),
                                    "name",
                                    "discountCoupons.name",
                                    "serviceScopes.name",
                                    "address"))
//                                    .fuzziness(Fuzziness.AUTO))//多字段查询
                            .filter(QueryBuilders.geoDistanceQuery("location").point(info.getLat(), info.getLon())//过滤十公里内的商家
                                    .distance(50, DistanceUnit.KILOMETERS)
                            );
                }
            }

            //选择排序方式
            if ("distance".equals(info.getRank())) {
                searchSourceBuilder.sort(new GeoDistanceSortBuilder("location", info.getLat(), info.getLon()).unit(DistanceUnit.KILOMETERS)
                        .order(SortOrder.ASC));//按距离排序
            } else if ("grade".equals(info.getRank())) {
                searchSourceBuilder.sort("grade");//按评分排序
            }

            searchSourceBuilder.query(matchQuery);
            searchSourceBuilder.size(info.getRows()).from((info.getPageNo() - 1) * info.getRows());//分页
            searchRequest.source(searchSourceBuilder);

            List<SearchShop> result = new ArrayList<>();

            SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
            if (searchResponse.status().getStatus() == 200) {

                SearchHit[] hits = searchResponse.getHits().getHits();
                if (hits.length == 0 || hits == null) {
                    return new ServiceStatusInfo<>(0, "没有符合条件的商家", result);
                }
                logger.info("当前是第" + info.getPageNo() + "页");
                for (SearchHit searchHit : hits) {
                    logger.info(searchHit.getId());
                    SearchShop nearbyShop = JSON.parseObject(searchHit.getSourceAsString(), new TypeReference<SearchShop>() {
                    });
                    nearbyShop.setId(Long.parseLong(searchHit.getId()));
                    result.add(nearbyShop);

                }
                return new ServiceStatusInfo<>(0, "", result);

            }
            return new ServiceStatusInfo<>(1, "搜索失败" + searchResponse.status().getStatus(), null);
        } catch (IOException e) {
            return new ServiceStatusInfo<>(1, "搜索商家失败" + e.getMessage(), null);
        }
    }


    @Override
    public List<DiscountCoupon> getNearByDiscount(double longitude, double latitude) {
        List<DiscountCoupon> discountCouponDetails = this.nearbyShopsMapper.getNearByDiscount(longitude, latitude);

        return discountCouponDetails;
    }
}
