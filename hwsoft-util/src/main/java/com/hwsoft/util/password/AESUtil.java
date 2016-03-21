/**
 * Copyright(c) 2010-2013 by XiangShang Inc.
 * All Rights Reserved
 */
package com.hwsoft.util.password;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * AES算法 为cookie加密提供加密算法 使用ThreadLocal保证线程安全
 *
 * @author tzh
 */
public class AESUtil {

    private static Log LOG = LogFactory.getLog(AESUtil.class);

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = ALGORITHM
            + "/ECB/PKCS5Padding";

    private static final ThreadLocal<Cipher> CIPHERS = new ThreadLocal<Cipher>() {
        @Override
        protected Cipher initialValue() {
            try {
                return Cipher.getInstance(TRANSFORMATION);
            } catch (NoSuchAlgorithmException e) {
                // 不可能出现在这里
                LOG.fatal("AES Cipher init error", e);
                throw new RuntimeException(e);
            } catch (NoSuchPaddingException e) {
                // 不可能出现在这里
                LOG.fatal("AES Cipher init error", e);
                throw new RuntimeException(e);
            }
        }
    };

    private static final byte[] SEED;

    static {
        SEED = new byte[16];
        SEED[0] = (byte) 0xA8;
        SEED[1] = (byte) 0x3;
        SEED[2] = (byte) 0x94;
        SEED[3] = (byte) 0x27;
        SEED[4] = (byte) 0xE8;
        SEED[5] = (byte) 0x9F;
        SEED[6] = (byte) 0x76;
        SEED[7] = (byte) 0x2;
        SEED[8] = (byte) 0xD0;
        SEED[9] = (byte) 0x9D;
        SEED[10] = (byte) 0xBB;
        SEED[11] = (byte) 0xC4;
        SEED[12] = (byte) 0x9A;
        SEED[13] = (byte) 0x6E;
        SEED[14] = (byte) 0x37;
        SEED[15] = (byte) 0x93;
    }

    /**
     * 加密
     *
     * @param input 输入字符串
     * @return hex形式的加密数据
     * @throws AESFailedException AES加密失败
     */
    public static String encrypt(String input) throws AESFailedException {
        try {
            byte[] output = encrypt(SEED, input.getBytes("UTF-8"));
            return DatatypeConverter.printHexBinary(output);
        } catch (UnsupportedEncodingException e) {
            // 不可能出现在这里
            throw new AESFailedException(e);
        }
    }

    /**
     * 解密
     *
     * @param input hex形式的加密数据
     * @return 解密后字符串
     * @throws AESFailedException 输入字符串不合法或AES解密失败
     */
    public static String decrypt(String input) throws AESFailedException {
        try {
            byte[] val = DatatypeConverter.parseHexBinary(input);
            return new String(decrypt(SEED, val), "UTF-8");
        } catch (IllegalArgumentException e) {
            throw new AESFailedException(e);
        } catch (UnsupportedEncodingException e) {
            // 不可能出现在这里
            throw new AESFailedException(e);
        }
    }

    private static byte[] encrypt(byte[] seed, byte[] input)
            throws AESFailedException {
        return crypt(seed, input, Cipher.ENCRYPT_MODE);
    }

    private static byte[] decrypt(byte[] seed, byte[] input)
            throws AESFailedException {
        return crypt(seed, input, Cipher.DECRYPT_MODE);
    }

    private static byte[] crypt(byte[] seed, byte[] input, int mode)
            throws AESFailedException {
        SecretKeySpec key = new SecretKeySpec(seed, ALGORITHM);

        Cipher cipher = CIPHERS.get();
        try {
            cipher.init(mode, key);
        } catch (InvalidKeyException e) {
            // 不可能出现在这里
            LOG.fatal("init AES cipher failed", e);
            throw new AESFailedException(e);
        }
        try {
            return cipher.doFinal(input);
        } catch (IllegalBlockSizeException e) {
            LOG.fatal("AES crypt failed", e);
            throw new AESFailedException(e);
        } catch (BadPaddingException e) {
            LOG.fatal("AES crypt failed", e);
            throw new AESFailedException(e);
        }
    }

    public static class AESFailedException extends Exception {
        private static final long serialVersionUID = 6084222930129639661L;

        public AESFailedException(String msg) {
            super(msg);
        }

        public AESFailedException(Throwable cause) {
            super(cause);
        }

        public AESFailedException(String msg, Throwable cause) {
            super(msg, cause);
        }
    }
}
