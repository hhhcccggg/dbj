package com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.service;

import com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.model.*;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;

import java.util.List;

public interface OfflineStoreStaffsService {
    ServiceStatusInfo<Long> create(StaffInput staffInput, long legalSubjectId);

    ServiceStatusInfo<Long> update(ModifyStaff modifyStaff);

    ServiceStatusInfo<Long> deleteById(long userId, long legalSubjectId, boolean isSuperStar);


    ServiceStatusInfo<Long> bulkDeleteStaffs(IsSuperStar[] isSuperStar, long legalSubjectId);

    ServiceStatusInfo<Long> bulkSetSuperStar(long[] userIds, long legalSubjectId, boolean isSuperStar);

    ServiceStatusInfo<List<OfflineStoreStaffs>> searchStaffs(SearchStaffInfo searchStaffInfo, long legalSubjectId, long storeId);

    ServiceStatusInfo<List<SuperStarInfo>> getSuperStarDetail(String search, String rank, String sort, long legalSubjectId);

    ServiceStatusInfo<SuperStarDto> videoListStaff(long userId);

    ServiceStatusInfo<Long> setSuperStar(long userId, long legalSubjectId, boolean isSuperStar);

    ServiceStatusInfo<OfflineStoreStaffs> getOfflineStoreStaffsById(long id);
}
