package com.hwsoft.wechat.common;

/**
 * Created with IntelliJ IDEA.
 * User: zhaowancheng
 * Date: 14-12-5
 * Time: 下午2:49
 * To change this template use File | Settings | File Templates.
 */
import java.security.MessageDigest;

public class MD5Utils {
        public final static String MD5(String s) {
            char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
            try {
                byte[] btInput = s.getBytes();
                // 获得MD5摘要算法的 MessageDigest 对象
                MessageDigest mdInst = MessageDigest.getInstance("MD5");
                // 使用指定的字节更新摘要
                mdInst.update(btInput);
                // 获得密文
                byte[] md = mdInst.digest();
                // 把密文转换成十六进制的字符串形式
                int j = md.length;
                char str[] = new char[j * 2];
                int k = 0;
                for (int i = 0; i < j; i++) {
                    byte byte0 = md[i];
                    str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                    str[k++] = hexDigits[byte0 & 0xf];
                }
                return new String(str);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        public static void main(String[] args) throws Exception{
            System.out.println(MD5Utils.MD5("20121221"));
            System.out.println(MD5Utils.MD5("加密"));
            System.out.println("==================");
            System.out.println(Bit16("abc"));
            System.out.println(Bit32("abc"));
        }

        private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'h', 'i', 'j', 'k', 'l', 'm' };

        private static String toHexString(byte[] b) {
            StringBuilder sb = new StringBuilder(b.length * 2);
            for (int i = 0; i < b.length; i++) {
                sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
                sb.append(HEX_DIGITS[b[i] & 0x0f]);
            }
            return sb.toString();
        }

        public static String Bit32(String SourceString) throws Exception {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(SourceString.getBytes());
            byte messageDigest[] = digest.digest();
            return toHexString(messageDigest);
        }

        public static String Bit16(String SourceString) throws Exception {
            return Bit32(SourceString).substring(8, 24);
        }
}
