package com.zwdbj.server.adminserver.controller;

import com.zwdbj.server.adminserver.service.es.service.EsService;
import com.zwdbj.server.adminserver.service.shop.service.store.model.StoreInfo;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/es/dbj")
public class ESController {

    @Autowired
    private EsService esService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseData<Long> index(@RequestBody StoreInfo storeInfo,
                                    @RequestParam(value = "index") String index,
                                    @RequestParam(value = "type") String type,
                                    @RequestParam(value = "id") String id) throws IOException {
        ServiceStatusInfo<Long> statusInfo = esService.index(storeInfo, index, type, id);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData(), null);

        }
        return new ResponseData<>(1, statusInfo.getMsg(), null, null);
    }
}
