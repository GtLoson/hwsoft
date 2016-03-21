package com.hwsoft.util.file;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 读取配置文件工具类
 * 
 * @author tzh
 */
public class ResourceBundleUtil {

	
	 /**
    * 读取配置文件，需文件名称
    */
   public static String getStringValue(String key, String fileName) {
       return getValue(key, fileName, null);
   }

   /**
    * 读取配置文件，需文件名称，平级目录的一个class文件
    */
   public static String getStringValue(String key, String fileName, Class<?> clazz) {
       String packagePath = clazz == null ? null : clazz.getPackage().getName();
       return getValue(key, fileName, packagePath);
   }
   
   /**
    * 读取配置文件，需文件名称
    */
   public static String getStringValue(String key, String fileName, String packagePath) {
       return getValue(key, fileName, packagePath);
   }
   
   /**
    * 读取配置文件，需文件名称
    */
   public static Integer getIntegerValue(String key, String fileName, boolean defaultZero) {
       String value = getValue(key, fileName, null);
       return value == null ? (defaultZero ? 0 : null) : Integer.valueOf(value);
   }

   /**
    * 读取配置文件，需文件名称，平级目录的一个class文件
    */
   public static Integer getIntegerValue(String key, String fileName, Class<?> clazz, boolean defaultZero) {
       String packagePath = clazz == null ? null : clazz.getPackage().getName();
       String value = getValue(key, fileName, packagePath);
       return value == null ? (defaultZero ? 0 : null) : Integer.valueOf(value);
   }
   
   /**
    * 读取配置文件，需文件名称
    */
   public static Integer getIntegerValue(String key, String fileName, String packagePath, boolean defaultZero) {
       String value = getValue(key, fileName, packagePath);
       return value == null ? (defaultZero ? 0 : null) : Integer.valueOf(value);
   }

   private static String getValue(String key, String fileName, String packagePath) {
       ResourceBundle rb = ResourceBundle.getBundle(packagePath == null ? fileName : (packagePath + "." + fileName),
               Locale.getDefault());
       if (rb.containsKey(key)) {
           return rb.getString(key);
       }
       return null;
   }
}
