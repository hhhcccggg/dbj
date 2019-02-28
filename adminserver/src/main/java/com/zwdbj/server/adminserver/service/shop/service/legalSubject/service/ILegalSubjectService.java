package com.zwdbj.server.adminserver.service.shop.service.legalSubject.service;

import com.zwdbj.server.adminserver.service.shop.service.legalSubject.model.*;
import com.zwdbj.server.adminserver.service.shop.service.store.model.ReviewStoreInput;
import com.zwdbj.server.adminserver.service.userTenant.model.ModifyUserTenantInput;
import com.zwdbj.server.adminserver.service.userTenant.model.UserTenantInput;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;

import java.util.List;

public interface ILegalSubjectService {
    List<LegalSubjectModel> getLegalSubjects(LegalSubjectSearchInput input);
    List<LegalSubjectModel> getUnReviewedLegalSubjects(LegalSubjectSearchInput input);
    ServiceStatusInfo<Integer> verityUnReviewedLegalSubject(long id, ReviewStoreInput input);
    ServiceStatusInfo<LegalSubjectReviewModel> getLegalSubjectReviewById(long id);
    List<LegalSubjectReviewModel> getReviewsByLegalSubjectId(long id);
    int delLegalSubject(long id);
    int addLegalSubject(long id, UserTenantInput input);
    int modifyBasicLegalSubject(long id, ModifyUserTenantInput input);
    ShopTenantModel getDetailTenant(long legalSubjectId);
    int updateStatusById(long id,int state);
}
