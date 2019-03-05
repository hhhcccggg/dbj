package com.zwdbj.server.adminserver.service.qiniu.service;

import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.model.FetchRet;
import com.qiniu.util.Auth;
import com.zwdbj.server.adminserver.service.category.model.AdNewCategoryInput;
import com.zwdbj.server.adminserver.service.category.service.CategoryService;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import com.zwdbj.server.config.settings.AppSettingConfigs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QiniuOperService{
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private QiniuService qiniuService;
    @Autowired
    private AppSettingConfigs appSettingConfigs;

    public ServiceStatusInfo<Integer> catOpe(){
        Configuration cfg = new Configuration(Zone.zone0());
        String catSrcUrls = "http://www.ixiupet.com/uploads/150309/2-15030920514b57.jpg,http://www.ixiupet.com/uploads/150410/3-1504101U142H1.jpg," +
                "http://www.ixiupet.com/uploads/150410/3-15041014351HZ.jpg,http://www.ixiupet.com/uploads/allimg/150403/2-1504031J035.jpg," +
                "http://www.ixiupet.com/uploads/150410/3-15041011232C39.jpg,http://www.ixiupet.com/uploads/allimg/150603/3-1506031001400-L.jpg," +
                "http://www.ixiupet.com/uploads/150309/2-1503091F253250.jpg,http://www.ixiupet.com/uploads/150409/3-150409153021a8.jpg," +
                "http://www.ixiupet.com/uploads/allimg/150403/2-1504031S512.jpg,http://www.ixiupet.com/uploads/150405/2-150405114051626.jpg," +
                "http://www.ixiupet.com/uploads/allimg/150603/3-1506031142510-L.jpg,http://www.ixiupet.com/uploads/allimg/150603/3-1506031129130-L.jpg," +
                "http://www.ixiupet.com/uploads/allimg/150404/2-150404021021.jpg,http://www.ixiupet.com/uploads/150405/2-150405111543b4.jpg," +
                "http://www.ixiupet.com/uploads/allimg/150603/3-1506030944000-L.jpg,http://www.ixiupet.com/uploads/150406/2-150406112953326.jpg," +
                "http://www.ixiupet.com/uploads/allimg/150528/3-15052Q02Q30-L.gif,http://www.ixiupet.com/uploads/allimg/150603/3-1506031FU60-L.jpg," +
                "http://www.ixiupet.com/uploads/allimg/150603/3-1506031K5000-L.jpg,http://www.ixiupet.com/uploads/150304/2-1503041F014P3.jpg," +
                "http://www.ixiupet.com/uploads/150409/3-150409145339242.jpg,http://www.ixiupet.com/uploads/allimg/150604/3-150604092J20-L.jpg," +
                "http://www.ixiupet.com/uploads/allimg/150604/3-150604091K30-L.jpg,http://www.ixiupet.com/uploads/allimg/150603/3-1506031022090-L.jpg," +
                "http://www.ixiupet.com/uploads/150410/3-150410120415456.jpg,http://www.ixiupet.com/uploads/allimg/150603/3-1506031644490-L.jpg," +
                "http://www.ixiupet.com/uploads/allimg/150603/3-1506031I5550-L.jpg,http://www.ixiupet.com/uploads/150411/3-15041109154T07.jpg," +
                "http://www.ixiupet.com/uploads/150408/2-15040R25506258.jpg,http://www.ixiupet.com/uploads/allimg/150603/3-1506031059330-L.jpg," +
                "http://www.ixiupet.com/uploads/allimg/150603/3-150603092T90-L.jpg,http://www.ixiupet.com/uploads/allimg/150603/3-150603125U60-L.jpg," +
                "http://www.ixiupet.com/uploads/allimg/150603/3-1506031033240-L.jpg,http://www.ixiupet.com/uploads/allimg/150603/3-1506031409490-L.jpg," +
                "http://www.ixiupet.com/uploads/150410/3-150410114013513.jpg,http://www.ixiupet.com/uploads/150411/3-15041111442V02.jpg," +
                "http://www.ixiupet.com/uploads/150410/3-150410110134212.jpg,http://www.ixiupet.com/uploads/150409/3-150409114445J4.jpg," +
                "http://www.ixiupet.com/uploads/150411/3-150411093S4Q8.jpg,http://www.ixiupet.com/uploads/150409/3-150409142329635.jpg," +
                "http://www.ixiupet.com/uploads/allimg/150603/3-1506031011390-L.jpg,http://www.ixiupet.com/uploads/150410/3-150410101T44B.jpg," +
                "http://www.ixiupet.com/uploads/150410/3-150410145K3L2.jpg,http://www.ixiupet.com/uploads/150410/3-15041015402aV.jpg," +
                "http://www.ixiupet.com/uploads/150410/3-150410130454251.jpg,http://www.ixiupet.com/uploads/150410/3-150410103946331.jpg," +
                "http://www.ixiupet.com/uploads/150410/3-15041014123UU.jpg,http://www.ixiupet.com/uploads/allimg/150603/3-1506031115530-L.jpg," +
                "http://www.ixiupet.com/uploads/allimg/150603/3-150603104Q10-L.jpg,http://www.ixiupet.com/uploads/150411/3-150411095926405.jpg," +
                "http://www.ixiupet.com/uploads/150411/3-150411104413500.jpg,http://www.ixiupet.com/uploads/allimg/150603/3-1506031AP00-L.jpg," +
                "http://www.ixiupet.com/uploads/150411/3-150411120956221.jpg,http://www.ixiupet.com/uploads/150410/3-15041009225H48.jpg," +
                "http://www.ixiupet.com/uploads/allimg/150603/3-1506031420510-L.jpg,http://www.ixiupet.com/uploads/150411/3-150411112140a4.jpg," +
                "http://www.ixiupet.com/uploads/150411/3-150411140315237.jpg,http://www.ixiupet.com/uploads/150410/3-150410095I1Z1.jpg," +
                "http://www.ixiupet.com/uploads/allimg/150603/3-1506031H2120-L.jpg,http://www.ixiupet.com/uploads/150411/3-150411102145K9.jpg," +
                "http://www.ixiupet.com/uploads/150410/3-150410121923217.jpg,http://www.ixiupet.com/uploads/150411/3-15041111035O30.jpg," +
                "http://www.ixiupet.com/uploads/150410/3-1504101Z6193X.jpg,http://www.ixiupet.com/uploads/allimg/150603/3-150603120A90-L.jpg," +
                "http://www.ixiupet.com/uploads/150410/3-150410155RKX.jpg,http://www.ixiupet.com/uploads/150410/3-15041015210L29.jpg," +
                "http://www.ixiupet.com/uploads/allimg/150603/3-150603162R50-L.jpg";
        String[] catUrls = catSrcUrls.split(",");
        String catTypes = "苏格兰折耳猫,沙特尔猫,褴褛猫,英国短毛猫,中国狸花猫,茶杯猫,布偶猫,加拿大无毛猫,波斯猫,异国短毛猫,喜马拉雅猫,山东狮子猫|临清狮猫,俄罗斯蓝猫,美国短毛猫," +
                "金吉拉猫,挪威森林猫,豹猫,玩具虎猫,东方短毛猫,暹罗猫,伯曼猫,澳大利亚雾猫,彼得秃猫,蒂凡尼猫,西伯利亚森林猫,卡尔特猫,加州闪亮猫,美国刚毛猫,孟买猫,英国长毛猫," +
                "虎斑猫,雪鞋猫,曼基康猫,阿舍拉猫|阿瑟拉猫,美国短尾猫,美国卷毛猫,土耳其梵猫,缅因猫,科拉特猫,埃及猫,威尔斯猫,新加坡猫,东奇尼猫,柯尼斯卷毛猫,巴厘猫,索马里猫," +
                "土耳其安哥拉猫,美国卷耳猫,内华达猫|尼比龙猫,哈瓦那猫,塞尔凯克卷毛猫,热带草原猫,东方猫,缅甸猫,塞舌尔猫,拉邦猫,欧洲缅甸猫,阿比西尼亚猫,非洲狮子猫|狮子猫," +
                "重点色短毛猫,日本短尾猫,波米拉猫,德文卷毛猫,爪哇猫|加云尼斯猫,奥西猫,马恩岛猫|曼岛猫,肯尼亚猫|非洲短毛猫";
        String[] catNames = catTypes.split(",");
        System.out.println(catUrls.length);
        System.out.println(catNames.length);
        Auth auth = Auth.create(this.appSettingConfigs.getQiniuConfigs().getAccessKey(),
                this.appSettingConfigs.getQiniuConfigs().getAccessSecrect());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        //抓取网络资源到空间
        try {
            for (int i = 0;i<67;i++){
                FetchRet fetchRet = bucketManager.fetch(catUrls[i], this.appSettingConfigs.getQiniuConfigs().getBucketName());
                System.out.println(fetchRet.hash);
                System.out.println(fetchRet.key);
                System.out.println(fetchRet.mimeType);
                System.out.println(fetchRet.fsize);
                AdNewCategoryInput input = new AdNewCategoryInput();
                input.setIconUrl(fetchRet.key);
                input.setName(catNames[i]);
                input.setParentId(252118610506551296L);
                input.setStatus(0);
                input.setType(0);
                input.setOrderIndex(0);
                this.categoryService.addCategoryAd(252118610506551296L,input);
                System.out.println(i+"次完成猫:"+catNames[i]);
            }
            return new ServiceStatusInfo<>(0,"",68);

        } catch (Exception ex){
            ex.printStackTrace();
            return new ServiceStatusInfo<>(1,"出现异常:"+ex.getMessage(),0);
        }
    }
    public ServiceStatusInfo<Integer> dogOpe(){
        Configuration cfg = new Configuration(Zone.zone0());
        String dogSrcUrls = "http://www.ixiupet.com/uploads/150411/3-150411163221948.jpg," +
                "http://www.ixiupet.com/uploads/150303/2-150303134343442.png," +
                "http://www.ixiupet.com/uploads/150413/3-1504131J945W2.jpg," +
                "http://www.ixiupet.com/uploads/allimg/150520/3-1505201125270-L.jpg," +
                "http://www.ixiupet.com/uploads/150403/2-150403152450K3.jpg," +
                "http://www.ixiupet.com/uploads/allimg/150520/3-1505201145220-L.jpg," +
                "http://www.ixiupet.com/uploads/150410/2-150410153120S7.jpg," +
                "http://www.ixiupet.com/uploads/150303/2-150303164450J5.jpg," +
                "http://www.ixiupet.com/uploads/allimg/150525/3-1505250931020-L.jpg," +
                "http://www.ixiupet.com/uploads/150304/2-150304133P1239.jpg," +
                "http://www.ixiupet.com/uploads/150413/3-15041309400X30.jpg," +
                "http://www.ixiupet.com/uploads/allimg/150617/3-15061G055460-L.jpg," +
                "http://www.ixiupet.com/uploads/150413/3-150413105534b0.jpg," +
                "http://www.ixiupet.com/uploads/150303/2-150303132505417.jpg," +
                "http://www.ixiupet.com/uploads/allimg/150523/3-1505231055560-L.jpg," +
                "http://www.ixiupet.com/uploads/150413/3-1504131JH29E.jpg," +
                "http://www.ixiupet.com/uploads/150414/3-150414121639313.jpg," +
                "http://www.ixiupet.com/uploads/150520/2-150520105K5S6.jpg," +
                "http://www.ixiupet.com/uploads/150410/2-150410144413b3.jpg," +
                "http://www.ixiupet.com/uploads/150303/2-1503031P52O09.jpg," +
                "http://www.ixiupet.com/uploads/150303/2-1503031QIY94.jpg," +
                "http://www.ixiupet.com/uploads/150303/2-15030315325O23.jpg," +
                "http://www.ixiupet.com/uploads/150413/3-150413101235436.jpg," +
                "http://www.ixiupet.com/uploads/150303/2-1503031H03JQ.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-15041609221U27.jpg," +
                "http://www.ixiupet.com/uploads/allimg/150403/2-1504031G448.jpg," +
                "http://www.ixiupet.com/uploads/150415/3-1504151A243L1.jpg," +
                "http://www.ixiupet.com/uploads/150415/3-15041511331Q15.jpg," +
                "http://www.ixiupet.com/uploads/150411/3-150411161344204.jpg," +
                "http://www.ixiupet.com/uploads/allimg/150713/3-150G31623530-L.jpg," +
                "http://www.ixiupet.com/uploads/150410/2-150410151450312.jpg," +
                "http://www.ixiupet.com/uploads/150414/3-150414161FRF.jpg," +
                "http://www.ixiupet.com/uploads/150413/3-15041309224T94.jpg," +
                "http://www.ixiupet.com/uploads/allimg/150520/3-1505201041280-L.jpg," +
                "http://www.ixiupet.com/uploads/150523/2-15052311051O44.jpg," +
                "http://www.ixiupet.com/uploads/allimg/150520/3-150520110R00-L.jpg," +
                "http://www.ixiupet.com/uploads/150414/3-150414111403111.jpg," +
                "http://www.ixiupet.com/uploads/150410/2-15041023441H33.jpg," +
                "http://www.ixiupet.com/uploads/150410/2-1504101P53Y27.jpg," +
                "http://www.ixiupet.com/uploads/150410/2-1504101R624K5.jpg," +
                "http://www.ixiupet.com/uploads/150519/2-15051915245H01.jpg," +
                "http://www.ixiupet.com/uploads/150413/3-1504131J50YI.jpg," +
                "http://www.ixiupet.com/uploads/150414/3-150414161P4X9.jpg," +
                "http://www.ixiupet.com/uploads/150403/2-15040315533K28.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-150416140U2240.jpg," +
                "http://www.ixiupet.com/uploads/allimg/150618/3-15061QGS60-L.jpg," +
                "http://www.ixiupet.com/uploads/150410/2-150410145UY38.jpg," +
                "http://www.ixiupet.com/uploads/150414/3-150414121523X1.jpg," +
                "http://www.ixiupet.com/uploads/150413/3-150413113551940.jpg," +
                "http://www.ixiupet.com/uploads/150414/3-150414111552529.jpg," +
                "http://www.ixiupet.com/uploads/allimg/150523/3-1505231116180-L.jpg," +
                "http://www.ixiupet.com/uploads/150413/3-1504131J029220.jpg," +
                "http://www.ixiupet.com/uploads/150415/3-15041511560T53.jpg," +
                "http://www.ixiupet.com/uploads/150410/2-150410121P63D.jpg," +
                "http://www.ixiupet.com/uploads/150413/3-150413095HL01.jpg," +
                "http://www.ixiupet.com/uploads/150415/3-15041512063AZ.jpg," +
                "http://www.ixiupet.com/uploads/150410/2-150410230634L1.jpg," +
                "http://www.ixiupet.com/uploads/150413/3-1504131K13VA.jpg," +
                "http://www.ixiupet.com/uploads/150413/3-150413112226195.jpg," +
                "http://www.ixiupet.com/uploads/allimg/150601/3-150601092T10-L.jpg," +
                "http://www.ixiupet.com/uploads/150414/3-150414111254W3.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-150416132T59C.jpg," +
                "http://www.ixiupet.com/uploads/150415/3-15041514252H03.jpg," +
                "http://www.ixiupet.com/uploads/150414/3-150414111640245.jpg," +
                "http://www.ixiupet.com/uploads/150413/3-1504131231031Z.jpg," +
                "http://www.ixiupet.com/uploads/150413/3-1504131K0332S.jpg," +
                "http://www.ixiupet.com/uploads/allimg/150601/3-1506011001290-L.jpg," +
                "http://www.ixiupet.com/uploads/allimg/150619/3-1506191055400-L.jpg," +
                "http://www.ixiupet.com/uploads/allimg/150417/2-15041F032090-L.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-1504161U154B1.jpg," +
                "http://www.ixiupet.com/uploads/150410/2-1504101H421128.jpg," +
                "http://www.ixiupet.com/uploads/150411/2-1504111JG42G.jpg," +
                "http://www.ixiupet.com/uploads/150430/3-150430161510139.jpg," +
                "http://www.ixiupet.com/uploads/allimg/150601/3-1506011040120-L.jpg," +
                "http://www.ixiupet.com/uploads/150413/3-15041314300WH.jpg," +
                "http://www.ixiupet.com/uploads/150411/3-1504111A051919.jpg," +
                "http://www.ixiupet.com/uploads/150414/3-15041411120NO.jpg," +
                "http://www.ixiupet.com/uploads/150415/3-150415101404452.jpg," +
                "http://www.ixiupet.com/uploads/150413/3-150413110QX43.jpg," +
                "http://www.ixiupet.com/uploads/150413/3-1504131J144A9.jpg," +
                "http://www.ixiupet.com/uploads/150413/3-1504131J301605.jpg," +
                "http://www.ixiupet.com/uploads/150415/3-150415103U3235.jpg," +
                "http://www.ixiupet.com/uploads/150415/3-150415104S14I.jpg," +
                "http://www.ixiupet.com/uploads/150414/3-150414161500G2.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-150416105049392.jpg," +
                "http://www.ixiupet.com/uploads/150414/3-150414111502943.jpg," +
                "http://www.ixiupet.com/uploads/150414/3-150414161349241.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-1504161S351U0.jpg," +
                "http://www.ixiupet.com/uploads/150415/3-150415191950L2.jpg," +
                "http://www.ixiupet.com/uploads/150413/3-1504131431005I.jpg," +
                "http://www.ixiupet.com/uploads/150413/3-150413120223H3.jpg," +
                "http://www.ixiupet.com/uploads/150413/3-1504131R045U6.jpg," +
                "http://www.ixiupet.com/uploads/150414/3-150414111025957.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-150416114022162.jpg," +
                "http://www.ixiupet.com/uploads/150413/3-1504131445505N.jpg," +
                "http://www.ixiupet.com/uploads/150414/3-150414132240B7.jpg," +
                "http://www.ixiupet.com/uploads/150415/3-1504151U129644.jpg," +
                "http://www.ixiupet.com/uploads/150414/3-150414161TS33.jpg," +
                "http://www.ixiupet.com/uploads/150415/3-1504151410463L.jpg," +
                "http://www.ixiupet.com/uploads/allimg/150713/3-150G31610540-L.jpg," +
                "http://www.ixiupet.com/uploads/allimg/150601/3-1506011110190-L.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-150416111G3424.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-1504161Z251H0.jpg," +
                "http://www.ixiupet.com/uploads/150413/3-1504131J612258.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-150416100304459.jpg," +
                "http://www.ixiupet.com/uploads/150414/3-1504141612463K.jpg," +
                "http://www.ixiupet.com/uploads/150415/3-150415135922114.jpg," +
                "http://www.ixiupet.com/uploads/150415/3-1504151R311N2.jpg," +
                "http://www.ixiupet.com/uploads/150415/3-150415114421941.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-15041615001M25.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-150416155240419.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-1504161K629591.jpg," +
                "http://www.ixiupet.com/uploads/allimg/150601/3-1506011055300-L.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-1504161T322c3.jpg," +
                "http://www.ixiupet.com/uploads/150414/3-150414121K1a9.jpg," +
                "http://www.ixiupet.com/uploads/allimg/150601/3-1506011133080-L.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-150416103223P6.jpg," +
                "http://www.ixiupet.com/uploads/150413/3-150413121U4L5.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-150416093555W6.jpg," +
                "http://www.ixiupet.com/uploads/allimg/150413/2-1504131335210-L.jpg," +
                "http://www.ixiupet.com/uploads/150414/3-150414161013V6.jpg," +
                "http://www.ixiupet.com/uploads/150414/3-1504141611114S.jpg," +
                "http://www.ixiupet.com/uploads/150414/3-1504141T92C11.jpg," +
                "http://www.ixiupet.com/uploads/150415/3-150415154302J8.jpg," +
                "http://www.ixiupet.com/uploads/150415/3-1504151F531349.jpg," +
                "http://www.ixiupet.com/uploads/150410/2-1504101430102c.jpg," +
                "http://www.ixiupet.com/uploads/150413/3-150413102915331.jpg," +
                "http://www.ixiupet.com/uploads/150414/3-150414163515613.jpg," +
                "http://www.ixiupet.com/uploads/150415/3-150415162144946.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-15041614240E18.jpg," +
                "http://www.ixiupet.com/uploads/150413/3-1504131K22H24.jpg," +
                "http://www.ixiupet.com/uploads/150415/3-15041516403SL.jpg," +
                "http://www.ixiupet.com/uploads/150415/3-1504151Z41OL.jpg," +
                "http://www.ixiupet.com/uploads/150413/3-150413104100b6.jpg," +
                "http://www.ixiupet.com/uploads/150414/3-150414121603D2.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-1504161QGQ30.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-1504161R5021K.jpg," +
                "http://www.ixiupet.com/uploads/150415/3-150415153014411.jpg," +
                "http://www.ixiupet.com/uploads/150411/3-15041114560S48.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-150416153I52X.jpg," +
                "http://www.ixiupet.com/uploads/150414/3-150414111H4921.jpg," +
                "http://www.ixiupet.com/uploads/150415/3-1504151T03XK.jpg," +
                "http://www.ixiupet.com/uploads/150415/3-150415111423964.jpg," +
                "http://www.ixiupet.com/uploads/150415/3-1504151HU4c4.jpg," +
                "http://www.ixiupet.com/uploads/allimg/150601/3-1506010944170-L.jpg," +
                "http://www.ixiupet.com/uploads/150411/3-150411151S6445.jpg," +
                "http://www.ixiupet.com/uploads/150413/3-1504131JSG96.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-150416120251947.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-15041616050Y27.jpg," +
                "http://www.ixiupet.com/uploads/allimg/150601/3-1506011015160-L.jpg," +
                "http://www.ixiupet.com/uploads/150411/3-150411155K5409.jpg," +
                "http://www.ixiupet.com/uploads/150414/3-150414121GL15.jpg," +
                "http://www.ixiupet.com/uploads/150415/3-15041509433O18.jpg," +
                "http://www.ixiupet.com/uploads/150415/3-150415095S6440.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-1504161524123I.jpg," +
                "http://www.ixiupet.com/uploads/150415/3-150415134504911.jpg," +
                "http://www.ixiupet.com/uploads/150415/3-150415155445C7.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-150416164520132.jpg," +
                "http://www.ixiupet.com/uploads/150414/3-15041416493UF.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-150416163324V8.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-1504161J121101.jpg," +
                "http://www.ixiupet.com/uploads/150414/3-150414111124130.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-15041610164O23.jpg," +
                "http://www.ixiupet.com/uploads/150413/3-1504131P5543Y.jpg," +
                "http://www.ixiupet.com/uploads/150415/3-15041510253RO.jpg," +
                "http://www.ixiupet.com/uploads/150415/3-150415105949C6.jpg," +
                "http://www.ixiupet.com/uploads/150415/3-1504151220504E.jpg," +
                "http://www.ixiupet.com/uploads/150415/3-150415151345425.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-150416143J9608.jpg," +
                "http://www.ixiupet.com/uploads/150414/3-150414161616448.jpg," +
                "http://www.ixiupet.com/uploads/150414/3-1504141K230454.jpg," +
                "http://www.ixiupet.com/uploads/150414/3-1504141PF3225.jpg," +
                "http://www.ixiupet.com/uploads/150415/3-15041514395C48.jpg," +
                "http://www.ixiupet.com/uploads/150415/3-15041515011D09.jpg," +
                "http://www.ixiupet.com/uploads/150415/3-1504151P620929.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-150416094913154.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-15041611515U13.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-150416130G0M0.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-150416134913937.jpg," +
                "http://www.ixiupet.com/uploads/150411/3-150411153441120.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-15041615105D12.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-1504161FS5S2.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-1504161HU05U.jpg," +
                "http://www.ixiupet.com/uploads/150414/3-150414161155329.jpg," +
                "http://www.ixiupet.com/uploads/150414/3-1504141S34S38.jpg," +
                "http://www.ixiupet.com/uploads/150415/3-1504151J209357.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-150416112S3G4.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-150416161A5D2.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-1504161G91c47.jpg," +
                "http://www.ixiupet.com/uploads/150411/3-150411143212N6.jpg," +
                "http://www.ixiupet.com/uploads/150415/3-1504151GI3N6.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-150416144T1F7.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-1504161JR41S.jpg," +
                "http://www.ixiupet.com/uploads/150415/3-150415092I5911.jpg," +
                "http://www.ixiupet.com/uploads/150415/3-150415145023S0.jpg," +
                "http://www.ixiupet.com/uploads/150415/3-150415160QC61.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-1504161A450161.jpg," +
                "http://www.ixiupet.com/uploads/150416/3-150416110426230.jpg";

        String[] dogUrls = dogSrcUrls.split(",");
        String dogTypes = "金毛寻回犬,哈士奇,拉布拉多寻回犬,泰迪犬,博美犬,茶杯犬,萨摩耶犬,边境牧羊犬,中华田园犬,德国牧羊犬,阿拉斯加雪橇犬,美国恶霸犬,卷毛比熊犬,藏獒,银狐犬,哈瓦那犬," +
                "小型雪纳瑞犬,贵宾犬,法国斗牛犬,秋田犬,蝴蝶犬,松狮犬,兰波格犬,吉娃娃,标准型雪纳瑞犬,柴犬,西帕基犬,灵缇,圣伯纳犬,捷克狼犬,英国斗牛犬,匈牙利牧羊犬,斗牛梗,小鹿犬," +
                "比熊犬,巨型贵宾犬,中国沙皮犬,大白熊犬,腊肠犬,巴哥犬,瑞典柯基犬,苏格兰牧羊犬,拳狮犬,杜宾犬,丝毛狗,昆明犬,罗威纳犬,古代英国牧羊犬,北京犬,卡迪根威尔士柯基犬," +
                "史宾格,惠比特犬,普罗特猎犬,大丹犬,马尔济斯犬,斗牛梗,西施犬,美国爱斯基摩犬,纽芬兰犬,高加索犬,约克夏梗,日本忡,荷兰毛狮犬,波士顿梗,伯恩山犬,大麦町犬,巴西非勒,中国细犬," +
                "波兰德斯布比野犬,捕鼠梗犬,阿富汗猎犬,威尔士跳猎犬,马犬,杜高犬,喜乐蒂牧羊犬,法老王猎犬,波利犬,奇努克犬,寻血猎犬,中国冠毛犬,贝灵顿梗,萨路基猎犬,维希拉猎犬,澳大利亚牧羊犬," +
                "骑士查理王小猎犬,拉萨犬,彭布罗克威尔士柯基犬,伊比赞猎犬,比利时马林诺斯犬,波尔多犬,猎兔犬|比格犬,波音达猎犬,刚毛猎狐梗,粗毛柯利犬,迷你杜宾,西藏猎犬,那不勒斯獒,丝毛梗," +
                "意大利灵缇犬,下司犬,西班牙加纳利犬|加那利犬,斯塔福郡梗,新斯科舍猎鸭寻猎犬,杰克罗素梗,锡利哈姆梗,法国狼犬,威玛犬,大瑞士山地犬,墨西哥无毛犬,挪威布哈德犬,平毛寻回猎犬," +
                "树丛浣熊猎犬,中亚牧羊犬,迷你贝吉格里芬凡丁犬,巴吉度犬,日本土佐犬|土佐犬,诺福克梗,爱尔兰猎狼犬,巴仙吉犬,边境梗,马士提夫獒犬,斗牛獒犬,爱尔兰雪达犬,短脚长身梗,黑俄罗斯梗," +
                "卡斯罗犬,西高地白梗,山地犬,挪威伦德猎犬,猎水獭犬,苏俄猎狼犬,挪威猎鹿犬,捷克梗,比利时牧羊犬,英国可卡犬,凯斯梗,大型黑褐色猎浣熊犬,帕尔森罗塞尔梗,博伊金猎犬,英国玩具猎鹬犬," +
                "比格猎犬,罗秦犬,澳大利亚牧牛犬,苏格兰猎鹿犬,扭玻利顿,苏塞克斯猎犬,苏格兰梗,比利时特伏丹犬,艾芬品,波尔多獒,英格兰雪达犬,美国可卡犬,德国宾莎犬,库瓦兹犬,卡南犬,斯塔福郡斗牛梗," +
                "英国跳猎犬,黑褐猎浣熊犬,凯恩梗,布鲁塞尔格里芬犬,比利牛斯牧羊犬,葡萄牙水犬,德国短毛波音达,万能梗,巨型雪纳瑞犬,威尔士梗,短毛猎狐梗,安纳托利亚牧羊犬,爱尔兰峡谷梗,英国猎狐犬," +
                "西藏梗,罗得西亚脊背犬,爱尔兰水猎犬,冰岛牧羊犬,爱尔兰红白雪达犬,红骨猎浣熊犬,玩具曼彻斯特犬,玩具猎狐梗,澳大利亚梗,田野小猎犬,卷毛寻回犬,布列塔尼犬,西班牙小猎犬,凯利蓝梗," +
                "湖畔梗,罗威士梗,切萨皮克海湾寻回犬,布雷猎犬,美国水猎犬,刚毛指示格里芬犬,波兰低地牧羊犬,爱尔兰软毛梗,史毕诺犬,芬兰拉普猎犬,美国猎狐犬,爱尔兰梗,德国硬毛波音达,葡萄牙波登可犬";
        String[] dogNames = dogTypes.split(",");
        Auth auth = Auth.create(this.appSettingConfigs.getQiniuConfigs().getAccessKey(),
                this.appSettingConfigs.getQiniuConfigs().getAccessSecrect());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        System.out.println(dogUrls.length);
        System.out.println(dogNames.length);
        //抓取网络资源到空间
        try {
            for (int i = 0;i<198;i++){
                FetchRet fetchRet = bucketManager.fetch(dogUrls[i], this.appSettingConfigs.getQiniuConfigs().getBucketName());
                System.out.println(fetchRet.hash);
                System.out.println(fetchRet.key);
                System.out.println(fetchRet.mimeType);
                System.out.println(fetchRet.fsize);
                AdNewCategoryInput input = new AdNewCategoryInput();
                input.setIconUrl(fetchRet.key);
                input.setName(dogNames[i]);
                input.setParentId(252116228808773632L);
                input.setStatus(0);
                input.setType(0);
                input.setOrderIndex(0);
                this.categoryService.addCategoryAd(252116228808773632L,input);
                System.out.println(i+"次完成狗:"+dogNames[i]);
            }
            return new ServiceStatusInfo<>(0,"",198);

        } catch (Exception ex){
            ex.printStackTrace();
            return new ServiceStatusInfo<>(1,"出现异常:"+ex.getMessage(),0);
        }
    }


}
