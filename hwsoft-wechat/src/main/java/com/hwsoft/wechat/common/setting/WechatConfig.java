package com.hwsoft.wechat.common.setting;

import com.google.common.cache.Cache;
import com.hwsoft.wechat.controller.WechatApiController;
import com.hwsoft.wechat.controller.WechatMsgController;
import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Config;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.cache.EhCache;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.EhCachePlugin;
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
            loadProp("properties/wechat-config-pro.properties", "properties/wechat-config-dev.properties");
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

            //C3p0Plugin 插件配置
            C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("jdbcUrl"), getProperty("user"), getProperty("password").trim());
            me.add(c3p0Plugin);

            //ActiveRecordPlugin插件配置
            ActiveRecordPlugin activeRecordPlugin = new ActiveRecordPlugin("mysql",c3p0Plugin);
            me.add(activeRecordPlugin);
            activeRecordPlugin.setShowSql(true);
            activeRecordPlugin.setCache(new EhCache());

            //缓存
            EhCachePlugin ehCachePlugin = new EhCachePlugin();
            me.add(ehCachePlugin);
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
            //Db.use("mysql").find("select * from user");
        }

}
