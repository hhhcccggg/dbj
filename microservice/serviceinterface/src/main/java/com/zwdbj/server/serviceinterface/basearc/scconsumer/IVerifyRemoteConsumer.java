package com.zwdbj.server.serviceinterface.basearc.scconsumer;

import com.zwdbj.server.serviceinterface.basearc.scprovider.IVerifyRemoteService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "basearc")
public interface IVerifyRemoteConsumer extends IVerifyRemoteService {

}
