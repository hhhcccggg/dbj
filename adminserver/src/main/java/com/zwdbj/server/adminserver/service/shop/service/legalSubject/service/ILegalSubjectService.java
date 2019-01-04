package com.zwdbj.server.adminserver.service.shop.service.legalSubject.service;

import com.zwdbj.server.adminserver.service.shop.service.legalSubject.model.*;
import com.zwdbj.server.adminserver.service.userTenant.model.ModifyUserTenantInput;
import com.zwdbj.server.adminserver.service.userTenant.model.UserTenantInput;
import com.zwdbj.server.probuf.middleware.mq.QueueWorkInfoModel;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

import java.util.List;

public interface ILegalSubjectService {
    List<LegalSubjectModel> getLegalSubjects(LegalSubjectSearchInput input);
    List<LegalSubjectModel> getUnReviewedLegalSubjects(LegalSubjectSearchInput input);
    ServiceStatusInfo<Integer> verityUnReviewed(long id, LegalSubjectVerityInput input);
    List<LegalSubjectReviewModel> getReviewsByLegalSubjectId(long id);
    int delLegalSubject(long id);
    int addLegalSubject(long id, UserTenantInput input);
    int modifyBasicLegalSubject(long id, ModifyUserTenantInput input);
    ShopTenantModel getDetailTenant(long legalSubjectId);
}
