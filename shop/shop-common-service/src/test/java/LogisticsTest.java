import com.zwdbj.server.utility.model.ServiceStatusInfo;
import com.zwdbj.server.shop_common_service.logistics.model.Logistics;
import com.zwdbj.server.shop_common_service.logistics.service.ILogisticsService;
import org.junit.Test;

import javax.annotation.Resource;

public class LogisticsTest {

    @Resource
    ILogisticsService logisticsService;

    @Test
    public void test01(){
        ServiceStatusInfo<Logistics> logistics = logisticsService.selectLogistics("73106644852000","zto");
        System.out.println(logistics);
    }
}
