package com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.service;

import com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.model.*;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

import java.util.List;

public interface OfflineStoreStaffsService {
    ServiceStatusInfo<Long> create(StaffInput staffInput, long legalSubjectId);

    ServiceStatusInfo<Long> update(ModifyStaff modifyStaff, long legalSubjectId);

    ServiceStatusInfo<Long> deleteById(long userId, long legalSubjectId, boolean isSuperStar);

    ServiceStatusInfo<List<OfflineStoreStaffs>> getStaffs(long legalSubjectId);

    ServiceStatusInfo<Long> bulkDeleteStaffs(long[] userIds, long legalSubjectId, boolean isSuperStar);

    ServiceStatusInfo<Long> bulkSetSuperStar(IsSuperStar[] isSuperStars, long legalSubjectId);

    ServiceStatusInfo<List<OfflineStoreStaffs>> searchStaffs(SearchStaffInfo searchStaffInfo, long legalSubjectId);

    ServiceStatusInfo<List<SuperStarInfo>> getSuperStarDetail(String search, String rank, String sort, long legalSubjectId);

    ServiceStatusInfo<SuperStarDto> videoListStaff(long userId);
}
