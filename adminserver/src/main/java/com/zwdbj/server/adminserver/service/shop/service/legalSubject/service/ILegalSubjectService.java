package com.zwdbj.server.adminserver.service.shop.service.legalSubject.service;

import com.zwdbj.server.adminserver.service.shop.service.legalSubject.model.LegalSubjectModel;
import com.zwdbj.server.adminserver.service.shop.service.legalSubject.model.LegalSubjectReviewModel;
import com.zwdbj.server.adminserver.service.shop.service.legalSubject.model.LegalSubjectSearchInput;
import com.zwdbj.server.adminserver.service.shop.service.legalSubject.model.LegalSubjectVerityInput;
import com.zwdbj.server.probuf.middleware.mq.QueueWorkInfoModel;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

import java.util.List;

public interface ILegalSubjectService {
    boolean  handleLegalSubject(QueueWorkInfoModel.QueueWorkShopLegalSubjectData data);
    List<LegalSubjectModel> getLegalSubjects(LegalSubjectSearchInput input);
    List<LegalSubjectModel> getUnReviewedLegalSubjects(LegalSubjectSearchInput input);
    ServiceStatusInfo<Integer> verityUnReviewed(long id, LegalSubjectVerityInput input);
    List<LegalSubjectReviewModel> getReviewsByLegalSubjectId(long id);
}
