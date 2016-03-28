package com.hwsoft.wechat.manager.faceplus;

import com.hwsoft.util.file.PropertiesUtil;

/**
 * Created by arvin on 16/3/26.
 */
public class FaceConfig {

    public static String API_SECRET = "";

    public static String API_KEY = "";

    static{
        String basePath = FaceConfig.class.getResource("/").getPath() + "properties/face-config.properties";
        API_SECRET = PropertiesUtil.readValueByKey(basePath,"API_SECRET");
        API_KEY    = PropertiesUtil.readValueByKey(basePath,"API_KEY");
    }

}




