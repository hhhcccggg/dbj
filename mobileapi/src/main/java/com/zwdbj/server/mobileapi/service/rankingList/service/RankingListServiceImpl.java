package com.zwdbj.server.mobileapi.service.rankingList.service;


import com.zwdbj.server.mobileapi.service.rankingList.mapper.RankingListMapper;
import com.zwdbj.server.mobileapi.service.rankingList.model.RankingListInfo;
import com.zwdbj.server.mobileapi.service.rankingList.model.Recommend;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankingListServiceImpl implements RankingListService {
    @Autowired
    private RankingListMapper rankingListMapper;

    @Override
    public ServiceStatusInfo<List<RankingListInfo>> searchTotalRank() {
        List<RankingListInfo> result = null;
        try {
            result = this.rankingListMapper.searchTotalRank();
            result.add(this.rankingListMapper.searchByUser(JWTUtil.getCurrentId()));
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询总榜失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<List<RankingListInfo>> searchFriendRank() {
        List<RankingListInfo> result = null;
        try {
            long userId = JWTUtil.getCurrentId();
            result = this.rankingListMapper.searchFriendRank(userId);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "获取好友排行失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<List<Recommend>> recommend() {
        List<Recommend> result = null;
        try {
            long userId = JWTUtil.getCurrentId();
            result = this.rankingListMapper.recommend(userId);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "获取推荐关注失败" + e.getMessage(), null);
        }

    }
}


