package com.zwdbj.server.mobileapi.service.rankingList.service;

import com.zwdbj.server.mobileapi.service.rankingList.model.RankingListInfo;
import com.zwdbj.server.mobileapi.service.rankingList.model.Recommend;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;

import java.util.List;

public interface RankingListService {


    ServiceStatusInfo<List<RankingListInfo>> searchTotalRank();

    ServiceStatusInfo<List<RankingListInfo>> searchFriendRank();

    ServiceStatusInfo<List<Recommend>> recommend();
}
