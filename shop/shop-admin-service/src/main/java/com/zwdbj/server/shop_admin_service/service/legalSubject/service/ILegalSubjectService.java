package com.zwdbj.server.shop_admin_service.service.legalSubject.service;

import com.zwdbj.server.probuf.middleware.mq.QueueWorkInfoModel;

public interface ILegalSubjectService {
    boolean  handleLegalSubject(QueueWorkInfoModel.QueueWorkShopLegalSubjectData data);
}
