package com.zwdbj.server.service.review.service;

import com.zwdbj.server.service.review.mapper.IResourceNeedReviewMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoReviewService {
    @Autowired
    IResourceNeedReviewMapper resourceNeedReviewMapper;
    protected Logger logger = LoggerFactory.getLogger(VideoReviewService.class);



    public void deleteResourceNeedReview(){
        this.resourceNeedReviewMapper.deleteResourceNeedReview();
    }


}
