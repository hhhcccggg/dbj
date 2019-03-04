package com.zwdbj.server.serviceinterface.basearc.consumer;

import com.zwdbj.server.serviceinterface.basearc.provider.IVerifyRemoteService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "basearc")
public interface IVerifyRemoteConsumer extends IVerifyRemoteService {

}
