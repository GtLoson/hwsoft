package com.hwsoft.wechat.common.setting;

import com.hwsoft.wechat.controller.WechatApiController;
import com.hwsoft.wechat.controller.WechatMsgController;
import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.render.ViewType;
import com.hwsoft.wechat.common.GlobInterceptor;
import com.jfinal.weixin.sdk.api.ApiConfigKit;

import java.util.Properties;

/**
 *
 * 微信配置设置
 *
 * Created by loson on 16/3/17.
 */
public class WechatConfig extends JFinalConfig{

        public Properties loadProp(String pro, String dev) {
            try {
                return loadPropertyFile(pro);
            }
            catch (Exception e){return loadPropertyFile(dev);}
        }

        public void configConstant(Constants me) {
            // 如果生产环境配置文件存在，则优先加载该配置，否则加载开发环境配置文件
            loadProp("properties/wechat-config.properties", "properties/wechat-config.properties");
            me.setDevMode(getPropertyToBoolean("devMode", true));

            // ApiConfigKit 设为开发模式可以在开发阶段输出请求交互的 xml 与 json 数据
            ApiConfigKit.setDevMode(me.getDevMode());
            me.setViewType(ViewType.JSP);
            me.setBaseViewPath("/WEB-INF/jsp/");


        }

        /**
         * 路由规则配置
         * @param me
         */
        public void configRoute(Routes me) {
            /**
             * 微信处理
             */
            me.add("/msg", WechatMsgController.class);
            me.add("/api", WechatApiController.class);

        }

        public void configPlugin(Plugins me) {
//		C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("jdbcUrl"), getProperty("user"), getProperty("password").trim());
//		me.add(c3p0Plugin);
//
//		EhCachePlugin ecp = new EhCachePlugin();
//		me.add(ecp);
//
//        // 配置ActiveRecord插件
//        ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
//        me.add(arp);
//        arp.addMapping("blog", Blog.class);	// 映射blog 表到 Blog模型
//        arp.addMapping("apply_order", ApplyOrder.class);


        }
        public void afterJFinalStart() {
            String defaultPage = getProperty("defaultPage");
            String defaultCompany = getProperty("defaultCompany");
            String defaultTitle = getProperty("defaultTitle_"+defaultCompany);
            String defaultKeywords = getProperty("defaultKeywords_"+defaultCompany);
            String defaultDescription = getProperty("defaultDescription_"+defaultCompany);

            JFinal.me().getServletContext().setAttribute("defaultPage", defaultPage);
            JFinal.me().getServletContext().setAttribute("defaultCompany", defaultCompany);
            JFinal.me().getServletContext().setAttribute("defaultTitle", defaultTitle);
            JFinal.me().getServletContext().setAttribute("defaultKeywords", defaultKeywords);
            JFinal.me().getServletContext().setAttribute("defaultDescription", defaultDescription);
        }

        public void configInterceptor(Interceptors me) {
            me.add(new GlobInterceptor());
        }

        public void configHandler(Handlers me) {
            //me.add(new GlobHandler());
        }

        public static void main(String[] args) {
            JFinal.start("webapp", 80, "/", 5);
        }

}
