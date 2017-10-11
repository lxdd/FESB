package cn.com.eju.deal.base.file.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * MD5加密处理辅助类
 *
 * @author (mimi.sun)
 * @date 2015年10月21日
 */
public class EncryptUtil {

    /**
     * @param sourceStr
     * @return
     * @throws Exception
     * @Title: MD5
     * @Description: MD5加密处理
     */
    public static String MD5(String sourceStr)
            throws Exception {

        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @Fields strKey : 密钥
     */
    private static final String strKey = "ehouse";

    /**
     * @param content
     * @return
     * @throws Exception
     * @Title: Encrypt
     * @Description: 加密
     */
    public String Encrypt(String content)
            throws Exception {
        return parseByte2HexStr(enCrypt(content));
    }

    /**
     * @param content
     * @return
     * @throws Exception
     * @Title: Decrypt
     * @Description: 解密
     */
    public String Decrypt(String content)
            throws Exception {

        return deCrypt(parseHexStr2Byte(content));
    }

    /**
     * @param content
     * @return
     * @throws Exception
     * @Title: 加密函数
     * @Description: TODO(这里用一句话描述这个方法的作用)
     */
    private byte[] enCrypt(String content)
            throws Exception {
        KeyGenerator keygen;
        SecretKey desKey;
        Cipher c;
        byte[] cByte;
        String str = content;

        keygen = KeyGenerator.getInstance("AES");

        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(strKey.getBytes());

        keygen.init(128, secureRandom);

        desKey = keygen.generateKey();
        c = Cipher.getInstance("AES");

        c.init(Cipher.ENCRYPT_MODE, desKey);

        cByte = c.doFinal(str.getBytes("UTF-8"));

        return cByte;
    }

    /**
     * @param src
     * @return
     * @throws Exception
     * @Title: deCrypt
     * @Description: 解密函数
     */
    private static String deCrypt(byte[] src)
            throws Exception {
        KeyGenerator keygen;
        SecretKey desKey;
        Cipher c;
        byte[] cByte;

        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(strKey.getBytes());

        keygen = KeyGenerator.getInstance("AES");
        keygen.init(128, secureRandom);

        desKey = keygen.generateKey();
        c = Cipher.getInstance("AES");

        c.init(Cipher.DECRYPT_MODE, desKey);

        cByte = c.doFinal(src);

        return new String(cByte, "UTF-8");
    }

    /**
     * @param buf
     * @return
     * @Title: parseByte2HexStr
     * @Description: 2进制转化成16进制
     */
    private String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * @param hexStr
     * @return
     * @Title: parseHexStr2Byte
     * @Description: 将16进制转换为二进制
     */
    private byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

}
