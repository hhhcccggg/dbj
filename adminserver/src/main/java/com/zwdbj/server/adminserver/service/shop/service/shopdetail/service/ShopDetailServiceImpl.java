package com.zwdbj.server.adminserver.service.shop.service.shopdetail.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zwdbj.server.adminserver.middleware.mq.QueueUtil;
import com.zwdbj.server.adminserver.service.category.mapper.ICategoryMapper;
import com.zwdbj.server.adminserver.service.category.model.StoreServiceCategory;
import com.zwdbj.server.adminserver.service.category.service.CategoryService;
import com.zwdbj.server.adminserver.service.qiniu.service.QiniuService;
import com.zwdbj.server.adminserver.service.shop.service.shopdetail.mapper.ShopDetailMapper;
import com.zwdbj.server.adminserver.service.shop.service.shopdetail.model.*;
import com.zwdbj.server.adminserver.service.shop.service.store.service.StoreService;
import com.zwdbj.server.probuf.middleware.mq.QueueWorkInfoModel;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ShopDetailServiceImpl implements ShopDetailService {
    @Autowired
    private ICategoryMapper categoryMapper;
    @Autowired
    private ShopDetailMapper shopDetailMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private QiniuService qiniuService;
    @Autowired
    private StoreService storeServiceImpl;
    private Logger logger = LoggerFactory.getLogger(ShopDetailServiceImpl.class);

    @Override
    public ServiceStatusInfo<ShopInfo> storeDetailInfo(long legalSubjectId) {
        ShopInfo result = null;
        try {
            result = this.shopDetailMapper.storeDeatilInfo(legalSubjectId);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询店铺详细信息失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<StoreDto> findStoreDetail(long legalSubjectId) {
        StoreDto result = null;
        try {
            result = this.shopDetailMapper.findStoreDetail(legalSubjectId);

            List<Long> extraServiceIds = this.shopDetailMapper.selectExtraServiceId(legalSubjectId);
            result.setExtraServices(this.categoryService.searchCategory(extraServiceIds).getData());

            result.setOpeningHours(this.shopDetailMapper.findOpeningHours(legalSubjectId));
            //查询店铺服务范围id
            List<Long> serviceScopeIds = this.shopDetailMapper.selectServiceScopeId(legalSubjectId);
            result.setServiceScopes(this.categoryService.searchCategory(serviceScopeIds).getData());
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询店铺基本信息失败" + e.getMessage(), null);
        }
    }


    @Override
    public ServiceStatusInfo<Long> modifyOpeningHours(List<OpeningHours> list, long legalSubjectId) {
        Long result = 0L;
        //先删除原有营业时间在修改
        long storeId = storeServiceImpl.selectStoreIdByLegalSubjectId(legalSubjectId);
        try {
            result = shopDetailMapper.deletedOpeningHours(storeId);
            for (OpeningHours openingHours : list) {
                long id = UniqueIDCreater.generateID();
                result += this.shopDetailMapper.modifyOpeningHours(id, openingHours);

            }
            QueueUtil.sendQueue(storeId, QueueWorkInfoModel.QueueWorkModifyShopInfo.OperationEnum.UPDATE);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "修改营业时间失败" + e.getMessage(), result);
        }
    }


    @Override
    public ServiceStatusInfo<LocationInfo> showLocation(long legalSubjectId) {
        LocationInfo result = null;
        try {
            result = this.shopDetailMapper.showLocation(legalSubjectId);
            String cityLevel = result.getCityLevel();
            String[] ids = cityLevel.split(",");
            String area = "";
            for (String id : ids) {
                area = area + categoryMapper.searchCategory(Long.parseLong(id)).getCategoryName() + ",";
            }
            area = area.substring(0, area.length() - 1);
            result.setArea(area);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "显示位置信息失败" + e.getMessage(), result);
        }
    }


//店铺资质,照片上传

    @Override
    public ServiceStatusInfo<Long> uploadCheck(QualificationInput qualificationInput, long legalSubjectId) {
        Long result = 0L;

        try {
            qualificationInput.setReviewData(qiniuService.url(qualificationInput.getReviewData()));
            long id = UniqueIDCreater.generateID();
            result = this.shopDetailMapper.uploadCheck(id, qualificationInput, legalSubjectId);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "上传失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<Long> modifylocation(LocationInfo info, long legalSubjectId) {
        Long result = 0L;
        long storeId = storeServiceImpl.selectStoreIdByLegalSubjectId(legalSubjectId);
        try {
            result = this.shopDetailMapper.modifyLocation(info, storeId);

            QueueUtil.sendQueue(storeId, QueueWorkInfoModel.QueueWorkModifyShopInfo.OperationEnum.UPDATE);

            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "修改位置信息失败" + e.getMessage(), null);
        }
    }


    @Override
    public ServiceStatusInfo<Long> modifyExtraService(long legalSubjectId, List<StoreServiceCategory> list) {
        Long result = 0L;
        long storeId = storeServiceImpl.selectStoreIdByLegalSubjectId(legalSubjectId);
        try {
            //先删除原来数据
            result += this.shopDetailMapper.deleteStoreExtraService(storeId);
            //再插入新数据
            for (StoreServiceCategory e : list) {
                Long id = UniqueIDCreater.generateID();
                result += this.shopDetailMapper.createStoreExtraService(id, legalSubjectId, e.getId());
            }

            QueueUtil.sendQueue(storeId, QueueWorkInfoModel.QueueWorkModifyShopInfo.OperationEnum.UPDATE);

            return new ServiceStatusInfo<>(0, "", result);

        } catch (Exception e) {

            return new ServiceStatusInfo<>(1, "修改店铺额外服务失败" + e.getMessage(), null);
        }

    }

    @Override
    public ServiceStatusInfo<Long> modifyServiceScopes(long legalSubjectId, List<StoreServiceCategory> list) {
        Long result = 0L;
        long storeId = storeServiceImpl.selectStoreIdByLegalSubjectId(legalSubjectId);
        try {
            //先删除原来数据
            result += this.shopDetailMapper.deleteStoreServiceScopes(storeId);
            //再插入新数据
            for (StoreServiceCategory e : list) {
                Long id = UniqueIDCreater.generateID();
                result += this.shopDetailMapper.createStoreServiceScopes(id, legalSubjectId, e.getId());
            }

            QueueUtil.sendQueue(storeId, QueueWorkInfoModel.QueueWorkModifyShopInfo.OperationEnum.UPDATE);
            return new ServiceStatusInfo<>(0, "", result);

        } catch (Exception e) {

            return new ServiceStatusInfo<>(1, "修改店铺服务范围失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<Long> modifyStoreImage(StoreImage storeImage, long legalSubjectId) {
        try {
            if ("coverImages".equals(storeImage.getType())) {
                String[] urls = storeImage.getImageUrl().split(",");
                for (String url : urls) {
                    shopDetailMapper.modifyStoreImage(storeImage.getType(), qiniuService.url(url), legalSubjectId);
                }
                return new ServiceStatusInfo<>(0, "", 1L);
            }
            shopDetailMapper.modifyStoreImage(storeImage.getType(), qiniuService.url(storeImage.getImageUrl()), legalSubjectId);
            return new ServiceStatusInfo<>(0, "", 1L);
        } catch (Exception e) {
            throw new RuntimeException("修改失败" + e.getMessage());
        }

    }
}