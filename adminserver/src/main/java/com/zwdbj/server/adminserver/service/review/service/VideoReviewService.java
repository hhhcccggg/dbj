package com.zwdbj.server.adminserver.service.review.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zwdbj.server.probuf.middleware.mq.QueueWorkInfoModel;
import com.zwdbj.server.adminserver.service.pet.service.PetService;
import com.zwdbj.server.adminserver.service.review.mapper.IResourceNeedReviewMapper;
import com.zwdbj.server.adminserver.service.review.model.ReviewModel;
import com.zwdbj.server.adminserver.service.user.service.UserService;
import com.zwdbj.server.adminserver.service.video.service.VideoService;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoReviewService {
    @Autowired
    IResourceNeedReviewMapper resourceNeedReviewMapper;
    @Autowired
    VideoService videoService;
    @Autowired
    UserService userService;
    @Autowired
    PetService petService;
    protected Logger logger = LoggerFactory.getLogger(VideoReviewService.class);

    public boolean reviewMQ(QueueWorkInfoModel.QueueWorkQiniuReviewResult reviewResult){
        try {
            if (reviewResult==null) return true;
            String dataResponse = reviewResult.getResultContent();
            JSONObject dataObject= JSON.parseObject(dataResponse);
            String inputKey = dataObject.getString("inputKey");
            JSONObject resultObject = dataObject.getJSONArray("items").getJSONObject(0).getJSONObject("result").getJSONObject("result");
            String suggestion = resultObject.getString("suggestion");
            logger.info("++++++++++++++++++++++++++++++++++"+suggestion);
            int count = this.resourceNeedReviewMapper.findByResContent(inputKey);
            if (count==0){
                this.newReview(inputKey,suggestion);
            }else if(count==1){
                Long id = this.resourceNeedReviewMapper.getIdByKey(inputKey);
                this.updateReview(id,suggestion);
                ReviewModel model = this.selectReviewById(id);
                if (model.getResType()==1 && model.getDataId()!=0 && model.getDataType()==2){
                    this.videoService.updateReview(model.getDataId(),model.getReviewResultType());
                    logger.info("++++++++++++++++++++++++++++++++++"+"ResType"+model.getResType()+",DataId"+model.getDataId()+",ReviewResultType"+model.getReviewResultType());
                    this.deleteReview(id);
                /*}else if (model.getResType()==0  && model.getDataType()==2 && model.getDataId()!=0){
                    this.deleteReview(id);
                    logger.info("我是视频图片删除1111");
                }else if (model.getResType()==0  && model.getDataType()==0 && model.getDataId()==0){
                    this.deleteReview(id);
                    logger.info("我是不知道图片删除1111");*/
                }else if (model.getResType()==0 && model.getDataId()!=0 && model.getDataType()==0){
                    this.userService.updateReview(model.getDataId(),model.getReviewResultType());
                    this.deleteReview(id);
                }else if (model.getResType()==0 && model.getDataId()!=0 && model.getDataType()==1){
                    this.petService.updateReview(model.getDataId(),model.getReviewResultType());
                    this.deleteReview(id);
                }else {
                    //this.deleteReview(id);
                    logger.info("没有数据类型了....");
                }
            }else {
                this.resourceNeedReviewMapper.deleteByInputKey(inputKey);
                logger.info("我是视频的封面和第一帧图片,我是无用的,所以删除了1");
            }
            return true;
        }catch (Exception e){
            logger.error("图片,视频错误++++++"+e.getMessage());
            return true;
        }

    }

    public boolean reviewMQ2(QueueWorkInfoModel.QueueWorkQiniuWaitReviewResData reviewResult){
        try {
            if (reviewResult==null) return true;
            String inputKey = reviewResult.getResContent();
            Long dataId = reviewResult.getDataId();
            int dataType = reviewResult.getDataType();
            int resType = reviewResult.getResType();
            int count = this.resourceNeedReviewMapper.findByResContent(inputKey);
            if (count==0){
                this.newReviewResData(inputKey,dataId,dataType,resType);
            }else if (count==1){
                Long id = this.resourceNeedReviewMapper.getIdByKey(inputKey);
                this.updateReviewResData(id,dataId,dataType,resType);
                ReviewModel model = this.selectReviewById(id);
                if (model.getResType()==1 && model.getDataId()!=0 && model.getDataType()==2 && model.getReviewResultType()!=null){
                    this.videoService.updateReview(model.getDataId(),model.getReviewResultType());
                    this.deleteReview(id);
                    logger.info("++++++++++++++++++++++++++++++++++"+"ResType"+model.getResType()+",DataId"+model.getDataId()+",ReviewResultType"+model.getReviewResultType());
                /*}else if (model.getResType()==0  && model.getDataType()==2 && model.getReviewResultType()!=null){
                    this.deleteReview(id);
                    logger.info("我是视频图片删除2222");
                }else if (model.getResType()==0  && model.getDataType()==0 && model.getDataId()==0){
                    this.deleteReview(id);
                    logger.info("我是不知道图片删除2222");*/
                }else if (model.getResType()==0 && model.getDataId()!=0 && model.getDataType()==0){
                    this.userService.updateReview(model.getDataId(),model.getReviewResultType());
                    this.deleteReview(id);
                }else if(model.getResType()==0 && model.getDataId()!=0 && model.getDataType()==1){
                    this.petService.updateReview(model.getDataId(),model.getReviewResultType());
                    this.deleteReview(id);
                }else {
                    //this.deleteReview(id);
                    logger.info("没有数据类型处理了222.....");
                }
            }else {
                this.resourceNeedReviewMapper.deleteByInputKey(inputKey);
                logger.info("+++++我是视频的封面和第一帧图片,我是无用的,所以删除了++++++++");
            }
            return true;
        }catch (Exception e){
            logger.error("图片,视频错误2++++++"+e.getMessage());
            return true;
        }


    }

    public void newReviewResData(String inputKey,Long dataId,int dataType,int resType){
        Long id = UniqueIDCreater.generateID();
        this.resourceNeedReviewMapper.newReviewResData(id,inputKey,dataId,dataType,resType);
    }
    public void updateReviewResData(Long id,Long dataId,int dataType,int resType){
        this.resourceNeedReviewMapper.updateReviewResData(id,dataId,dataType,resType);
    }




    public void  newReview(String inputKey,String suggestion){
        Long id = UniqueIDCreater.generateID();
        this.resourceNeedReviewMapper.newReview(id,inputKey,suggestion);
    }

    public void updateReview(Long id,String suggestion){
        this.resourceNeedReviewMapper.updateReview(id,suggestion);
    }

    public ReviewModel selectReviewById(Long id){
        ReviewModel model = this.resourceNeedReviewMapper.selectReviewById(id);
        return model;
    }
    public void deleteReview(Long id){
        this.resourceNeedReviewMapper.deleteReview(id);

    }

    public void deleteResourceNeedReview(){
        this.resourceNeedReviewMapper.deleteResourceNeedReview();
    }


}
