package com.example.springbootblog.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA加密，支持分片加密
 * <pre>
 * BCD码（Binary-Coded Decimal‎）亦称二进码十进数或二-十进制代码。
 * 用4位二进制数来表示1位十进制数中的0~9这10个数码。
 * 是一种二进制的数字编码形式，用二进制编码的十进制代码。
 * 注：日常所说的BCD码大都是指8421BCD码形式
 */
public class RSAUtil {
    /**
     * 指定加密算法为RSA
     */
    private static String ALGORITHM = "RSA";
    /**
     * 指定key的大小
     */
    private static int KEY_SIZE = 1024;
    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    public static String DEFAULT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwX1VBBvOHLW2IqBSbxSS/OtSlkGzYAmsORlNvd7gw4AHWIET2kAfnnhd2M3cNYLHH1C/IReLWhJt5qLElCN5yFL29TvB5vSPT6LDf5MB49aXUWpwWj30uwzXCQlFugmnEeX56zwKUSLGpoMOup7IuWs1D1QELQHXhfI+gZEZz9wIDAQAB";
    private static String DEFAULT_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALBfVUEG84ctbYioFJvFJL861KWQbNgCaw5GU293uDDgAdYgRPaQB+eeF3Yzdw1gscfUL8hF4taEm3mosSUI3nIUvb1O8Hm9I9PosN/kwHj1pdRanBaPfS7DNcJCUW6CacR5fnrPApRIsamgw66nsi5azUPVAQtAdeF8j6BkRnP3AgMBAAECgYBN8/TUZuQf7P4qlk91ga76LmBqROk2l+sFRiP+cmWghIGLSHvyljpILuGjxp9p9EDfV3M/C1R1B+tH3D1fZwoZlw5/6eIPGJGYkFDes4+CBxI/A3aovrPXbpgGkRXB2bfmfJtC47RbLQY2sAxOsviF1dAeWFGcJvSv8yu/Y4764QJBAP6/9q2OlvAWVhQbuuf+Doqc8psgbDjnqft0N8guY3yaW3E3Q0OcYYjtrCqvp1s5vGv8RXSytbCNfmVMBWso6lkCQQCxPOfXC+/krePo05WdRbvK9Zi/xM5wI2JcifCiSy0/dBhERbyVHCVoxS1KNy3kPhuDMDHTrU16sHPZUA/aLebPAkB/MbWmvnUaduyPqbDOuIjmKKrxTWT7nJ/Ajyxy5MaDpkZV5vPMxD3mslSuE3oKEG/FsvWy6K/S8TLUvPpPoOQxAkAthIXKDa/DXAE/Jy+yX1lWS3K6NVILLJszRt284G+zbx5YUkyXQs48tP7no3outuCb9YGmakiOrMhj1BLuJoGzAkEAhGZXKomxvNYYBLKrJr213MMYRNiJrObgHA6WIeqcE+QOpqAjXkmbGTE8LmvrPF0G86LUA84LGLMmloi4V57TWg==";

    /**
     * 生成密钥对
     */
//    public static void generateKeyPair() throws Exception {
//        if (getpublickey() == null || getprivatekey() == null) {
//            /** RSA算法要求有一个可信任的随机数源 */
//            SecureRandom sr = new SecureRandom();
//            /** 为RSA算法创建一个KeyPairGenerator对象 */
//            KeyPairGenerator kpg = KeyPairGenerator.getInstance(ALGORITHM);
//            /** 利用上面的随机数据源初始化这个KeyPairGenerator对象 */
//            kpg.initialize(KEY_SIZE, sr);
//            /** 生成密匙对 */
//            KeyPair kp = kpg.generateKeyPair();
//            /** 得到公钥 */
//            Key publicKey = kp.getPublic();
//            System.out.println("publicKey:" + encryptBASE64(publicKey.getEncoded()));
//            /** 得到私钥 */
//            Key privateKey = kp.getPrivate();
//            System.out.println("privateKey:" + encryptBASE64(privateKey.getEncoded()));
//            /** 用对象流将生成的密钥写入文件 */
//            ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(PUBLIC_KEY_FILE));
//            ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(PRIVATE_KEY_FILE));
//            oos1.writeObject(publicKey);
//            oos2.writeObject(privateKey);
//            /** 清空缓存，关闭文件输出流 */
//            oos1.close();
//            oos2.close();
//        }
//    }

    /**
     * 产生签名
     *
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        // 解密由base64编码的私钥  
        byte[] keyBytes = decryptBASE64(privateKey);

        // 构造PKCS8EncodedKeySpec对象  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

        // KEY_ALGORITHM 指定的加密算法  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 取私钥对象  
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 用私钥对信息生成数字签名  
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);

        return encryptBASE64(signature.sign());
    }

    /**
     * 验证签名
     *
     * @param data
     * @param publicKey
     * @param sign
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {

        // 解密由base64编码的公钥  
        byte[] keyBytes = decryptBASE64(publicKey);

        // 构造X509EncodedKeySpec对象  
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

        // KEY_ALGORITHM 指定的加密算法  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 取公钥对象  
        PublicKey pubKey = keyFactory.generatePublic(keySpec);

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);

        // 验证签名是否有效  
        return signature.verify(decryptBASE64(sign));
    }

    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    private static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    private static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    /**
     * 加密方法 source： 源数据
     */
    public static String encrypt(String source) throws Exception {
        byte[] publicKeyBytes = Base64.base64ToByteArray(DEFAULT_PUBLIC_KEY);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKeyBytes);

        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM, "SunRsaSign");
        PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
        /** 得到Cipher对象来实现对源数据的RSA加密 */
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int MaxBlockSize = KEY_SIZE / 8;
        int len = (MaxBlockSize - 11) / 8;
        String[] datas = splitString(source, len);
        StringBuffer mi = new StringBuffer();
        for (String s : datas) {
            mi.append(bcd2Str(cipher.doFinal(s.getBytes())));
        }
        return mi.toString();

    }

    /**
     * 字符串分片
     *
     * @param string 源字符串
     * @param len    单片的长度（keysize/8）
     * @return
     */
    private static String[] splitString(String string, int len) {
        int x = string.length() / len;
        int y = string.length() % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        String[] strings = new String[x + z];
        String str = "";
        for (int i = 0; i < x + z; i++) {
            if (i == x + z - 1 && y != 0) {
                str = string.substring(i * len, i * len + y);
            } else {
                str = string.substring(i * len, i * len + len);
            }
            strings[i] = str;
        }
        return strings;
    }

    /**
     * bcd 转 Str
     *
     * @param bytes
     * @return
     */
    private static String bcd2Str(byte[] bytes) {
        char temp[] = new char[bytes.length * 2], val;
        for (int i = 0; i < bytes.length; i++) {
            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');

            val = (char) (bytes[i] & 0x0f);
            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
        }
        return new String(temp);
    }

    /**
     * 解密
     *
     * @param cryptToGraph :密文
     * @return 解密后的明文
     * @throws Exception
     */
    public static String decrypt(String cryptToGraph) throws Exception {
        byte[] keyBytes = Base64.base64ToByteArray(DEFAULT_PRIVATE_KEY);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory factory = KeyFactory.getInstance(ALGORITHM, "SunRsaSign");
        PrivateKey privateKey = factory.generatePrivate(spec);
        //得到Cipher对象对已用公钥加密的数据进行RSA解密
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        int key_len = KEY_SIZE / 8;
        byte[] bytes = cryptToGraph.getBytes();
        byte[] bcd = ASCII2BCD(bytes, bytes.length);
        StringBuilder sBuffer = new StringBuilder();
        byte[][] arrays = splitArray(bcd, key_len);
        for (byte[] arr : arrays) {
            sBuffer.append(new String(cipher.doFinal(arr),"UTF-8"));
        }
        return sBuffer.toString();
    }

    /**
     * ASCII 转 BCD
     *
     * @param ascii
     * @param asc_len
     * @return
     */
    private static byte[] ASCII2BCD(byte[] ascii, int asc_len) {
        byte[] bcd = new byte[asc_len / 2];
        int j = 0;
        for (int i = 0; i < (asc_len + 1) / 2; i++) {
            bcd[i] = asc2bcd(ascii[j++]);
            bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc2bcd(ascii[j++])) + (bcd[i] << 4));
        }
        return bcd;
    }

    /**
     * asc转bcd
     *
     * @param asc
     * @return
     */
    private static byte asc2bcd(byte asc) {
        byte bcd;

        if ((asc >= '0') && (asc <= '9')) {
            bcd = (byte) (asc - '0');
        } else if ((asc >= 'A') && (asc <= 'F')) {
            bcd = (byte) (asc - 'A' + 10);
        } else if ((asc >= 'a') && (asc <= 'f')) {
            bcd = (byte) (asc - 'a' + 10);
        } else {
            bcd = (byte) (asc - 48);
        }
        return bcd;
    }

    /**
     * 字节数组分片
     *
     * @param data
     * @param len
     * @return
     */
    private static byte[][] splitArray(byte[] data, int len) {
        int x = data.length / len;
        int y = data.length % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        byte[][] arrays = new byte[x + z][];
        byte[] arr;
        for (int i = 0; i < x + z; i++) {
            arr = new byte[len];
            if (i == x + z - 1 && y != 0) {
                System.arraycopy(data, i * len, arr, 0, y);
            } else {
                System.arraycopy(data, i * len, arr, 0, len);
            }
            arrays[i] = arr;
        }
        return arrays;
    }

}