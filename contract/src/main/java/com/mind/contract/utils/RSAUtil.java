package com.mind.contract.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;


/**
 * @author ：long
 * @date ：Created in 2024/8/14 0014 15:42
 * @description：RSA加密工具
 */
public class RSAUtil {
    // 数字签名，密钥算法
    private static final String RSA_KEY_ALGORITHM = "RSA";

    // 数字签名签名/验证算法
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    // RSA密钥长度
    private static final int KEY_SIZE = 1024;

    private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDHEg+yeHXNR+DE/AtcxLfQYa/VWMZybfKCERjtIvY4w6jNdzO7DZymcXXSK8k5EXs3jNckmaUPocGtplUWS+b/ZfZLFNrgEQHRIbT/OT7fj6swiSGLBkrdE8UoXP9eRwS9WTqA0aVimtQOAYwl5hRkCdow5vIUdmKOmXXVBC9czwIDAQAB";

    public static final String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMcSD7J4dc1H4MT8C1zEt9Bhr9VYxnJt8oIRGO0i9jjDqM13M7sNnKZxddIryTkRezeM1ySZpQ+hwa2mVRZL5v9l9ksU2uARAdEhtP85Pt+PqzCJIYsGSt0TxShc/15HBL1ZOoDRpWKa1A4BjCXmFGQJ2jDm8hR2Yo6ZddUEL1zPAgMBAAECgYBsGkXF8B5gpKjNr+VnsjfzhCjdHYFegJBRy4ESvYqiFrZjJVlFOcYYqMKHqFl0kSatauEmMf1tuLSZXRsVqZA9qUIL7tpyiwGrbSdxaeiM5PoRZ1SnOxjE49m0kAU+5E1ENzQfPgY0Cwfb1e92Wj5VrDnq1XXLL6bJ7NpOnuBBIQJBAPMN3woqI6wRo6QS6iEGTvECBtp2lNDjKu/Yt1fwXAkIjcS0NhF1aCZ/OyX8gSebfDO3BtQWnBiCf4glIsiEVX8CQQDRrHU2vwapQgxH5o7SvA0sjC+Mkkyfaz/ZipE/Vu89qQdCO0caSznPjKiTysUUaX+RBxiOMB9pdrW1a4Qx78CxAkEAsjkhq5pqvenRTKZx9/VgPWl3DQqW5PUuruEC0AD3+8fB7NMYMoUc8d2pyZj2O8zPrjwvTaWmoHJQgcx5TP98kQJAMobdpDi6cTVSYk1EI7Jfdlm956Elx9D/jPvgbqQTeQdvkV1ZkBOKteJOiXb2sSmPLTLJjxI3r6GEvlhPff0psQJBAOCCYjJJnjmuK8ZXnUd0+pAus9lxLBSKDu/JmxqF06Zsg6bf7ICLpf77u9g35YPxSUvlX7wTnZ7ZeX1p+h0B52c=";


    /**
     * 初始化RSA密钥对
     *
     * @return RSA密钥对
     * @throws Exception 抛出异常
     */
    private static Map<String, String> initKey() throws Exception {
        KeyPairGenerator keygen = KeyPairGenerator
                .getInstance(RSA_KEY_ALGORITHM);
        SecureRandom secrand = new SecureRandom();
        secrand.setSeed("zlife".getBytes());// 初始化随机产生器
        keygen.initialize(KEY_SIZE, secrand); // 初始化密钥生成器
        KeyPair keys = keygen.genKeyPair();
        String pub_key = Base64.encodeBase64String(keys.getPublic().getEncoded());
        String pri_key = Base64.encodeBase64String(keys.getPrivate().getEncoded());
        Map<String, String> keyMap = new HashMap<>();
        keyMap.put("publicKey", pub_key);
        keyMap.put("privateKey", pri_key);
        System.out.println("publicKey:" + pub_key);
        System.out.println("privateKey:" + pri_key);
        return keyMap;
    }

    /**
     * 得到公钥
     *
     * @param keyMap RSA密钥对
     * @return 公钥
     * @throws Exception 抛出异常
     */
    public static String getPublicKey(Map<String, String> keyMap) throws Exception {
        return keyMap.get("publicKey");
    }

    /**
     * 得到私钥
     *
     * @param keyMap RSA密钥对
     * @return 私钥
     * @throws Exception 抛出异常
     */
    public static String getPrivateKey(Map<String, String> keyMap) throws Exception {
        return keyMap.get("privateKey");
    }

    /**
     * 数字签名
     *
     * @param data    待签名数据
     * @param pri_key 私钥
     * @return 签名
     * @throws Exception 抛出异常
     */
    public static String sign(byte[] data, String pri_key) throws Exception {
        // 取得私钥
        byte[] pri_key_bytes = Base64.decodeBase64(pri_key);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(pri_key_bytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        // 生成私钥
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 实例化Signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        // 初始化Signature
        signature.initSign(priKey);
        // 更新
        signature.update(data);

        return Base64.encodeBase64String(signature.sign());
    }

    /**
     * RSA校验数字签名
     *
     * @param data    数据
     * @param sign    签名
     * @param pub_key 公钥
     * @return 校验结果，成功为true，失败为false
     * @throws Exception 抛出异常
     */
    public boolean verify(byte[] data, byte[] sign, String pub_key) throws Exception {
        // 转换公钥材料
        // 实例化密钥工厂
        byte[] pub_key_bytes = Base64.decodeBase64(pub_key);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        // 初始化公钥
        // 密钥材料转换
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pub_key_bytes);
        // 产生公钥
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
        // 实例化Signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        // 初始化Signature
        signature.initVerify(pubKey);
        // 更新
        signature.update(data);
        // 验证
        return signature.verify(sign);
    }

    /**
     * 公钥加密
     *
     * @param data    待加密数据
     * @param pub_key 公钥
     * @return 密文
     * @throws Exception 抛出异常
     */
    private static byte[] encryptByPubKey(byte[] data, byte[] pub_key) throws Exception {
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pub_key);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * 公钥加密
     *
     * @param data    待加密数据
     * @param pub_key 公钥
     * @return 密文
     * @throws Exception 抛出异常
     */
    public static String encryptByPubKey(String data, String pub_key) throws Exception {
        // 私匙加密
        byte[] pub_key_bytes = Base64.decodeBase64(pub_key);
        byte[] enSign = encryptByPubKey(data.getBytes(), pub_key_bytes);
        return Base64.encodeBase64String(enSign);
    }

    /**
     * 私钥加密
     *
     * @param data    待加密数据
     * @param pri_key 私钥
     * @return 密文
     * @throws Exception 抛出异常
     */
    private static byte[] encryptByPriKey(byte[] data, byte[] pri_key) throws Exception {
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(pri_key);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 私钥加密
     *
     * @param data    待加密数据
     * @param pri_key 私钥
     * @return 密文
     * @throws Exception 抛出异常
     */
    public static String encryptByPriKey(String data, String pri_key) throws Exception {
        // 私匙加密
        byte[] pri_key_bytes = Base64.decodeBase64(pri_key);
        byte[] enSign = encryptByPriKey(data.getBytes(), pri_key_bytes);
        return Base64.encodeBase64String(enSign);
    }

    /**
     * 公钥解密
     *
     * @param data    待解密数据
     * @param pub_key 公钥
     * @return 明文
     * @throws Exception 抛出异常
     */
    private static byte[] decryptByPubKey(byte[] data, byte[] pub_key) throws Exception {
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pub_key);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * 公钥解密
     *
     * @param data    待解密数据
     * @param pub_key 公钥
     * @return 明文
     * @throws Exception 抛出异常
     */
    public static String decryptByPubKey(String data, String pub_key) throws Exception {
        // 公匙解密
        byte[] pub_key_bytes = Base64.decodeBase64(pub_key);
        byte[] design = decryptByPubKey(Base64.decodeBase64(data), pub_key_bytes);
        return new String(design);
    }

    /**
     * 私钥解密
     *
     * @param data    待解密数据
     * @param pri_key 私钥
     * @return 明文
     * @throws Exception 抛出异常
     */
    private static byte[] decryptByPriKey(byte[] data, byte[] pri_key) throws Exception {
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(pri_key);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 私钥解密
     *
     * @param data    待解密数据
     * @param pri_key 私钥
     * @return 明文
     * @throws Exception 抛出异常
     */
    public static String decryptByPriKey(String data, String pri_key) throws Exception {
        // 私匙解密
        byte[] pri_key_bytes = Base64.decodeBase64(pri_key);
        byte[] design = decryptByPriKey(Base64.decodeBase64(data), pri_key_bytes);
        return new String(design);
    }


    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
//        initKey();
        String datastr = "123456";
        System.out.println("待加密数据：\n" + datastr);
        //公匙加密
        String pubKeyStr = RSAUtil.encryptByPubKey(datastr, PUBLIC_KEY);
        System.out.println("公匙加密结果：\n" + pubKeyStr);
        // 私匙解密
        String priKeyStr = RSAUtil.decryptByPriKey(pubKeyStr, PRIVATE_KEY);
        System.out.println("私匙解密结果：\n" + priKeyStr);
    }
}