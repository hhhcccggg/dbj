package com.zwdbj.server.mobileapi.service.rankingList.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.ecwid.consul.v1.ConsulClient;
import com.zwdbj.server.mobileapi.service.rankingList.mapper.RankingListMapper;
import com.zwdbj.server.mobileapi.service.rankingList.model.RankingListInfo;
import com.zwdbj.server.mobileapi.service.rankingList.model.Recommend;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.consulLock.unit.Lock;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RankingListServiceImpl implements RankingListService {
    @Autowired
    private RankingListMapper rankingListMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private Logger logger = LoggerFactory.getLogger(RankingListServiceImpl.class);

    @Override
    public ServiceStatusInfo<List<RankingListInfo>> searchTotalRank() {
        List<RankingListInfo> result = null;
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        ConsulClient consulClient = new ConsulClient("localhost", 8500);    // 创建与Consul的连接
        Lock lock = new Lock(consulClient, "mobileapi", "totalRank");

        try {
            long userId = JWTUtil.getCurrentId();
            if (userId == 0L) {
                return new ServiceStatusInfo<>(1, "请重新登录", null);
            }
            if (!"".equals(valueOperations.get("totalRank")) && valueOperations.get("totalRank") != null) {
                logger.info("从缓存获取总榜信息");
                String str = valueOperations.get("totalRank");
                result = JSON.parseObject(str, new TypeReference<List<RankingListInfo>>() {
                });
                result.add(this.rankingListMapper.searchByUser(userId));
                return new ServiceStatusInfo<>(0, "", result);
            }

            if (lock.lock(true, 500L, 10)) {
                logger.info("从数据库中获取总榜信息");
                result = this.rankingListMapper.searchTotalRank();

                valueOperations.set("totalRank", JSONArray.toJSONString(result));
                stringRedisTemplate.expire("totalRank", 1, TimeUnit.MINUTES);
                result.add(this.rankingListMapper.searchByUser(userId));


                return new ServiceStatusInfo<>(0, "", result);
            }
            return new ServiceStatusInfo<>(1, "查询总榜失败", null);

        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询总榜失败" + e.getMessage(), null);
        } finally {
            lock.unlock();
        }

    }

    @Override
    public ServiceStatusInfo<List<RankingListInfo>> searchFriendRank() {
        List<RankingListInfo> result = null;
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        try {
            long userId = JWTUtil.getCurrentId();
            if (userId == 0L) {
                return new ServiceStatusInfo<>(1, "请重新登录", null);
            }
            if (!"".equals(valueOperations.get("friendRank" + userId)) && valueOperations.get("friendRank" + userId) != null) {
                String str = valueOperations.get("friendRank" + userId);
                result = JSON.parseObject(str, new TypeReference<List<RankingListInfo>>() {
                });
                logger.info("从缓存获取好友榜信息");
                return new ServiceStatusInfo<>(0, "", result);
            }
            result = this.rankingListMapper.searchFriendRank(userId);
            if (result != null && result.size() != 0) {
                valueOperations.set("friendRank" + userId, JSONArray.toJSONString(result));
                stringRedisTemplate.expire("friendRank" + userId, 1, TimeUnit.MINUTES);
                logger.info("从数据库中获取好友榜信息");
                return new ServiceStatusInfo<>(0, "", result);
            }
            return new ServiceStatusInfo<>(1, "您暂时没有好友", null);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "获取好友排行失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<List<Recommend>> recommend() {
        List<Recommend> result = null;
        try {
            long userId = JWTUtil.getCurrentId();
            if (userId == 0L) {
                return new ServiceStatusInfo<>(1, "请重新登录", null);
            }
            result = this.rankingListMapper.recommend(userId);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "获取推荐关注失败" + e.getMessage(), null);
        }

    }
}


