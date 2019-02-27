package com.zwdbj.server.mobileapi.service.legalSubject.service;

import com.zwdbj.server.mobileapi.service.legalSubject.model.LegalSubjectModel;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;

public interface ILegalSubjectService {

    /**
     * 根据ID查询商家
     * @param id
     * @return
     */
    ServiceStatusInfo<LegalSubjectModel> getLegalSubjectById(long id);
}
