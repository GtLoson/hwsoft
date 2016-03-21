/**
 *
 */
package com.hwsoft.common.version;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * app操作系统类型
 * note:枚举的顺序不可改变，api中用枚举的顺序确认枚举
 *
 * @author tzh
 */
public enum AppOSType {

    // IOS
    OS_IOS() {
        @Override
        public String toString() {
            return "ios";
        }
    },
    // android
    OS_ANDROID() {
        @Override
        public String toString() {
            return "android";
        }
    },
    // ios或者android，无需区分的情况使用
    OS_IOS_ANDROID() {
        @Override
        public String toString() {
            return "ios_android";
        }
    },
    // wap
    OS_WAP(){
        @Override
        public String toString() {
            return "wap";
        }
    };
    /**
     * 获取系统类型集合
     *
     * @return List of VersionType
     */
    public static List<AppOSType> valueList() {
        return Lists.newArrayList(OS_IOS, OS_ANDROID);
    }

    /**
     * 使用int构造
     *
     * @param ordinal 枚举的index
     * @return
     */
    public AppOSType formInteger(Integer ordinal) {
        for (AppOSType osType : AppOSType.values()) {
            if (osType.ordinal() == ordinal) {
                return osType;
            } else {
                return OS_IOS_ANDROID;
            }
        }
        return OS_IOS_ANDROID;
    }
}
