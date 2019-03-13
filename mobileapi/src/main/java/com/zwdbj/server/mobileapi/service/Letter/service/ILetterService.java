package com.zwdbj.server.mobileapi.service.Letter.service;

import com.zwdbj.server.basemodel.model.ServiceStatusInfo;

public interface ILetterService {

    /**
     * 判断是否还能继续私信
     * */
    ServiceStatusInfo<Integer> isSend(long receiverId);

    /**
     * 存储私信条数
     * */
    ServiceStatusInfo<Boolean> saveLetterCount(long receiverId);

}
