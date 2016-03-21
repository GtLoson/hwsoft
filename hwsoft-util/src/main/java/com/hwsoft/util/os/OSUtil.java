package com.hwsoft.util.os;

/**
 * 操作系统工具
 */
public class OSUtil {
    /**
     * 判断是否是UNIX文件系统
     *
     * @return
     */
    public static boolean isUnixFileSystem() {
        String osProperty = System.getProperty("os.name");
        if (osProperty.contains("linux") ||
                osProperty.contains("Linux") ||
                osProperty.startsWith("Mac OS X")) {
            return true;
        } else {
            return false;
        }
    }
}
