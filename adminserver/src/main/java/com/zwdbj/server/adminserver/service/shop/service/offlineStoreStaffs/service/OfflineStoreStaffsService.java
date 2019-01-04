package com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.service;

import com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.model.OfflineStoreStaffs;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

import java.util.List;

public interface OfflineStoreStaffsService {
    ServiceStatusInfo<Long> create(OfflineStoreStaffs offlineStoreStaffs);

    ServiceStatusInfo<Long> update(OfflineStoreStaffs offlineStoreStaffs);

    ServiceStatusInfo<Long> deleteById(Long id);

    ServiceStatusInfo<OfflineStoreStaffs> selectById(Long id);

    ServiceStatusInfo<List<OfflineStoreStaffs>> select();


}