package com.zwdbj.server.service.review.mapper;

import org.apache.ibatis.annotations.*;

@Mapper
public interface IResourceNeedReviewMapper {

    @Delete("delete from core_resourceNeedReviews where resType=0")
    Long deleteResourceNeedReview();
}
