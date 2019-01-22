package com.zwdbj.server.mobileapi.service.legalSubject.service;

import com.zwdbj.server.mobileapi.service.legalSubject.mapper.ILegalSubjectMapper;
import com.zwdbj.server.mobileapi.service.legalSubject.model.LegalSubjectModel;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LegalSubjectServiceImpl implements ILegalSubjectService {
    @Autowired
    ILegalSubjectMapper legalSubjectMapper;

    @Override
    public ServiceStatusInfo<LegalSubjectModel> getLegalSubjectById(long id) {
        try{
            LegalSubjectModel legalSubjectModel = legalSubjectMapper.getLegalSubjectById(id);
            return new ServiceStatusInfo<>(0,"",legalSubjectModel);
        }catch(Exception e){
            return new ServiceStatusInfo<>(1,"查询商家失败"+e.getMessage(),null);
        }
    }
}
