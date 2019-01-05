package com.zwdbj.server.adminserver.service.shop.service.shopdetail.service;

import com.zwdbj.server.adminserver.service.category.model.StoreServiceCategory;
import com.zwdbj.server.adminserver.service.category.service.CategoryService;
import com.zwdbj.server.adminserver.service.qiniu.service.QiniuService;
import com.zwdbj.server.adminserver.service.shop.service.shopdetail.mapper.ShopDetailMapper;
import com.zwdbj.server.adminserver.service.shop.service.shopdetail.model.LocationInfo;
import com.zwdbj.server.adminserver.service.shop.service.shopdetail.model.OpeningHours;
import com.zwdbj.server.adminserver.service.shop.service.shopdetail.model.QualificationInput;
import com.zwdbj.server.adminserver.service.shop.service.shopdetail.model.StoreDto;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zwdbj.server.adminserver.service.shop.service.shopdetail.model.LocationInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ShopDetailServiceImpl implements ShopDetailService {

    @Autowired
    private ShopDetailMapper shopDetailMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private QiniuService qiniuService;

    @Override
    public ServiceStatusInfo<StoreDto> findStoreDetail(long storeId) {
        StoreDto result = null;
        try {
            result = this.shopDetailMapper.findStoreDetail(storeId);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询店铺基本信息失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<List<OpeningHours>> findOpeningHours(long storeId) {
        List<OpeningHours> result = null;
        try {
            result = this.shopDetailMapper.findOpeningHours(storeId);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询营业时间失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<Long> modifyOpeningHours(List<OpeningHours> list) {
        Long result = 0L;
        try {
            for (OpeningHours openingHours : list) {
                result += this.shopDetailMapper.modifyOpeningHours(openingHours);
            }
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "修改营业时间失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<Long> addOpeningHours(long storeId, List<OpeningHours> list) {
        Long result = 0L;
        try {
            for (OpeningHours openingHours : list) {
                Long id = UniqueIDCreater.generateID();
                result += this.shopDetailMapper.createOpeningHours(id, storeId, openingHours);

            }
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "增加营业时间失败" + e.getMessage(), null);
        }

    }

    @Override
    public ServiceStatusInfo<LocationInfo> showLocation(long storeId) {
        LocationInfo result = null;
        try {
            result = this.shopDetailMapper.showLocation(storeId);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "显示位置信息失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<List<StoreServiceCategory>> findExtraService(long storeId) {
        try {
            //查询店铺额外服务id
            List<Long> extraServiceIds = this.shopDetailMapper.selectExtraServiceId(storeId);
            ServiceStatusInfo<List<StoreServiceCategory>> statusInfo = this.categoryService.searchCategory(extraServiceIds);

            return statusInfo;

        } catch (Exception e) {
            return new ServiceStatusInfo(1, "显示店铺对应额外服务失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo findServiceScope(long storeId) {
        try {
            //查询店铺服务范围id
            List<Long> serviceScopeIds = this.shopDetailMapper.selectServiceScopeId(storeId);
            ServiceStatusInfo<List<StoreServiceCategory>> statusInfo = this.categoryService.searchCategory(serviceScopeIds);

            return statusInfo;

        } catch (Exception e) {
            return new ServiceStatusInfo(1, "显示店铺对应服务范围失败" + e.getMessage(), null);
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
    public ServiceStatusInfo<Long> modifylocation(LocationInfo info, long storeId) {
        Long result = 0L;
        try {
            result = this.shopDetailMapper.modifyLocation(info, storeId);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "修改位置信息失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<List<StoreServiceCategory>> findAllExtraService(long stordId) {
        return null;
    }

    @Override
    public ServiceStatusInfo<Long> modifyExtraService(long storeId, List<StoreServiceCategory> list) {
        Long result = 0L;

        try {
            //先删除原来数据
            result += this.shopDetailMapper.deleteStoreExtraService(storeId);
            //再插入新数据
            for (StoreServiceCategory e : list) {
                Long id = UniqueIDCreater.generateID();
                result += this.shopDetailMapper.createStoreExtraService(id, storeId, e.getId());
            }
            return new ServiceStatusInfo<>(0, "", result);

        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "修改店铺额外服务失败" + e.getMessage(), null);
        }

    }

    @Override
    public ServiceStatusInfo<Long> modifyServiceScopes(long storeId, List<StoreServiceCategory> list) {
        Long result = 0L;

        try {
            //先删除原来数据
            result += this.shopDetailMapper.deleteStoreServiceScopes(storeId);
            //再插入新数据
            for (StoreServiceCategory e : list) {
                Long id = UniqueIDCreater.generateID();
                result += this.shopDetailMapper.createStoreServiceScopes(id, storeId, e.getId());
            }
            return new ServiceStatusInfo<>(0, "", result);

        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "修改店铺服务范围失败" + e.getMessage(), null);
        }
    }
}