package com.zwdbj.server.utility.common.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

public class AESUtil {
    //private static Prop prop = DataDictionary.getProp();

    /**
     * 密钥算法
     */
    private static final String ALGORITHM = "AES";
    /**
     * 加解密算法/工作模式/填充方式
     */
    private static final String ALGORITHM_MODE_PADDING = "AES/ECB/PKCS7Padding";
    /**
     * 生成key
     */
    //微信支付API密钥设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
    private static String paySign = "JpP6T3AqRCNXJVKKbLai391yWd6tXsPD";
    //对商户key做md5，得到32位小写key*
    private static SecretKeySpec key = new SecretKeySpec(MD5Util.MD5Encode(paySign, "UTF-8").toLowerCase().getBytes(), ALGORITHM);

    static {

    }

    /**
     * AES加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptData(String data) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        // 创建密码器
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING, "BC");
        // 初始化
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64Util.encode(cipher.doFinal(data.getBytes()));
    }

    /**
     * AES解密
     *
     *（1）对加密串A做base64解码，得到加密串B
     *（2）用key*对加密串B做AES-256-ECB解密（PKCS7Padding）
     * @param base64Data
     * @return
     * @throws Exception
     */
    public static String decryptData(String base64Data) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING, "BC");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(Base64Util.decode(base64Data)));
    }

    /*public static void main(String[] args) throws Exception {
        String A = "I03pTAqdCMfyVR2V+tBUhRdox2WYmJsFiGN3yZwmXgH4cwlFT2WqBLlzGMz7n2mnjRq/SkojW9V5aETSWnylrZ8lfYZX+PlOuazP0Qbr03wVI7LenE44ISDKBaZQoy7F+ce5FPTyFil5t/BYKOeBRfW01UIFYkRVpC9XQoSDxkqnRnUT8uL0DzP7Tu7kBcjt96aPezRga2/KxrjjgCRoZrxFDfoLsp+Lp5iwnx6vm7bM36f6ISBxNd/aUgS9wK/WkbPbO9184jwq3HsVFfzzX+n16XluMjxz2b5vHuDakEtFiti41N6VjwNFycatgSx7yfhlkT7FZhKWZOCTZ69gDHiuU6MZgbs79+SXHykjKu4o7gPipPmhj6cf7HnHgI47CG/XS12F+3xj/4q7sYrOmI40ELDBxpfIesWBfuE6pnsneGS42qgAbC7SNV2iITtyXyFxnATuhQZ3FpvkEiTDhGTZKJt2lKbq8EH/jWVayfkX+lzAlrQ8A/9pE7iKWOdI9nFRgB9jFEmAvrh97BIF+ORTZgFoON88ihXP98aDK6EilH94giluurAP24fTH+8YBMHG9p69Wj3dS+WVGSURA372JVFsZrfDIUkxPAx6O8MgspLwJmAv8MKyR2OEfHGgYF/5pagm9FMcBxLoZSWmYz7/Rosh5JbTtfNH4dUTDh+TVg88JXjm+E+IfVMDf/3olJ77A6zRWfIWp5vWEux2+PFW1s6HFvkgwaEked5e4QQ9icwGLxZAEyMu6vgiqc6yTbQrJp5Ia2oqPOVzLOulpIMiFG7FN/BCoDW7qeDLMdZ1EOL84P9US7tBzr+a0Rz5T+0SwJzYvlbfK+G1JcebaCDdy/yvBlmOCF9g3MQwJx/RdxNACOWrDEMTcZDNz8LYsYdHicbAlojJV3XicgqnAlqkdn2NdRzodV1SRDItdCDqo/Epc2PD71Zw4jFzFlMymTgq5kNf2dqh0IZGL+MZpTx8f9HBDAufgdIOxVCa5u67Z6VyYNfH3OnwhffTc/3WaOykDPZYUarL3tIdvlaE5BeeIR6hWETV6gi1cBD/qWQ=";
        System.out.println(AESUtil.decryptData(A));
    }*/



}
