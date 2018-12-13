package com.zwdbj.server.pay.alipay;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

/**
 * alipay支付SDK实例
 */
public class AlipaySDKClient {

    private AlipayClient alipayClient;

    private static AlipaySDKClient ourInstance = new AlipaySDKClient();
    public static AlipaySDKClient getInstance() {
        return ourInstance;
    }

    private AlipaySDKClient() {
        alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                "2018102261777359",
                "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDFF3udmgzn47Ec\n" +
                        "K5Amp/yMXeLQMrEm01ynIHfXk3GeaRoKEEuEsLgyncCEUPTfS6DnC1591h7D/PoP\n" +
                        "ziyRFGOUIpstqivrqDZe1tPcGu5j/OVuAV36szKV+fwlR1thH8CvycXAX5aamhdp\n" +
                        "xbyQNAaXIfYnKJBretQIJ8T2uSm/PEIftRLxGW3kQEdZmBaYhLVSyg//AwLeftLa\n" +
                        "/aaYeNnw4n7i2G2mZReRSAm32NGsxyibxIBZpCBi5IwBW9SQbkOZLVWnfBOySGwx\n" +
                        "mVMJVb4W17pEZkx4tKZ2vef223xiQjUzjo2s4Di44o8g0lo7c52S1fNkCSFQ328n\n" +
                        "jnfMbs6zAgMBAAECggEBAKKDbXuTpeajmcuHpDIrvBOl6rsPwYEpMTHhlM/eFQaw\n" +
                        "1TbNHugHq6qY50BzXKauvrskZcLPBslGVI8pyxfJvx5LtrbdpZ+IMWTcMQ9B6aXq\n" +
                        "xueBGxP4JNMDsUSv5Qpd201Xy8aHSzCptHqUAJrLO0/tQgIh8YueWS1sVX23SlQT\n" +
                        "Eld0W0QBWRpIdn9TwYK++Kxol9no17f9uyWm09bCXt19JoxfGFbu5fKlpI2RQA6q\n" +
                        "4W6Q8cwgqN3F6fc/wM43rtfPmvAZA4+wpS1m0qi7HySJOYj4EBipliF6D0p+7w0c\n" +
                        "NZ1aPRNCXwnHJyhgSgHUVwa9fQw9ZIb9eIe6iGEt1fECgYEA5NM5NC1DJjrQ8537\n" +
                        "ruqZKREYwVH0AwKU/vy3sfwobuO3M/6oPMjb5kjy/opTLjXirlLgYq78Z10PTisJ\n" +
                        "H/sQDKbItmMpYebDpIBjutPZtNh2RaphVBdV+N/ZlKEVen6dCGH+/6k0G7GzUpnL\n" +
                        "zHctxjcbbHbZor2oQnyXTzdaWmkCgYEA3H9/Ky5UPXDCd9PWHtrcW6jNyDlI4bw2\n" +
                        "vy7tHnURtkkU+Fm/G5Isatt64mmFZ0grKam0tTLsB7n/vvsBZgZO4mb9li1oqb9o\n" +
                        "uyNa5Vu4cAHZp7dlvm9Mjw6eBSNi4sdwH4XkasQuPwNMi+m2GRra665YVALWdSaV\n" +
                        "hT2UmRv4JLsCgYB7mbykem5zEAIeFZAFg6MHk7yTb/kmRxbERS3UbihI/afP5hZp\n" +
                        "/vUJBw56f60+oMy47PtXUr3z90h1KLndFHgu3Ug9fIK15zh2ewNjv1sxXFtGdins\n" +
                        "ik5eCEw3BGvdptab2JMs9fC3pN0AGSMEZrvK/alKhUg5p8hjYXk5yOR9QQKBgBV3\n" +
                        "I/4kKHdCvuvDZ5jhXYpeyImxafLvy7SY8OSCKRNB0hTLR9t7Q2+zpibcduwzDmCj\n" +
                        "8L+jEW8/IDKLZPt1qcBInm88RHAh/iQZsQObBj2BMJsvM2xykVVr6t49L6c8GyI2\n" +
                        "eqIaLxZbT9xfCCcxghZnhpnHHPGSrAOolwrAukt5AoGACwSfhBwewV2tGL7RMgib\n" +
                        "GJNk/WvPA5ZqTdpTtKTUaSC8D6Y6RinKPe9CHha7yeRBsS/ajwOJQq6kys9SZp3L\n" +
                        "FchtTkPZk6KyykS8RozC5euQu/+F/pKFp4k+fk6uTW1mzqkyjUDGAfTr3JoujJf7\n" +
                        "vSBZeGGq+Obp78mIqvbj2zA=",
                "json",
                "UTF-8",
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkl34++HOjwVvxKq/LCFB9Tu5gHNF5QJ0LQrd6FjmrNklfOlyJnrZqKnlo4XrBPFMVDsmN+mI5MMvUPkDlZZ+0DIA+xd3TYeAWk/Lt2ebNrpv11CGxZiVXnoMsVFl0gBijtLGqwgeyJWXbAHAgLq8N7nFXlXbqmyOx6jSWWSvR3Tea5NIIa9AO/vLvje8QQwCezX2J/xzOpAast9h+HvgHdf06SzcoLOXLhg42CM+R3sBLVycLAHk8YZZLBRk+7nZOHz142R0pCdlA/YGtyuNd5a4B4nhVzWlbmuF85EE9P4Q20rRbrveovx1jBd+vNGBJOBPOZRQAgXGbOdTkfp66QIDAQAB",
                "RSA2");
    }

    public AlipayClient getAlipayClient() {
        return alipayClient;
    }

    public void setAlipayClient(AlipayClient alipayClient) {
        this.alipayClient = alipayClient;
    }
}
