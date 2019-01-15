package com.zwdbj.server.mobileapi.controller;

import com.zwdbj.server.mobileapi.service.rankingList.model.RankingListInfo;
import com.zwdbj.server.mobileapi.service.rankingList.model.Recommend;
import com.zwdbj.server.mobileapi.service.rankingList.service.RankingListService;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/rank")
@RestController
@Api(description = "排行榜相关")
public class RankingListController {
    @Autowired
    private RankingListService rankingListServiceImpl;

    @RequestMapping(value = "/totalRank", method = RequestMethod.GET)
    @ApiOperation(value = "总榜")
    public ResponseData<List<RankingListInfo>> totalRank() {
        ServiceStatusInfo<List<RankingListInfo>> statusInfo = this.rankingListServiceImpl.searchTotalRank();
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData());
        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);

    }

    @RequestMapping(value = "friendRank", method = RequestMethod.GET)
    @ApiOperation(value = "好友榜")
    public ResponseData<List<RankingListInfo>> friendRank() {
        ServiceStatusInfo<List<RankingListInfo>> statusInfo = this.rankingListServiceImpl.searchFriendRank();
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData());
        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);
    }

    @RequestMapping(value = "/recommend", method = RequestMethod.GET)
    @ApiOperation(value = "推荐关注")
    public ResponseData<List<Recommend>> recommend() {
        ServiceStatusInfo<List<Recommend>> statusInfo = this.rankingListServiceImpl.recommend();
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData());
        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);
    }

}
