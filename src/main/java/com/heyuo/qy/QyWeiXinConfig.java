package com.heyuo.qy;

import com.google.common.eventbus.AsyncEventBus;
import com.heyuo.qy.controller.*;
import com.heyuo.qy.event.ClientConsultEventHandler;
import com.heyuo.qy.event.CustomerServiceAgentEventHandler;
import com.heyuo.qy.model._MappingKit;
import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.qy.weixin.sdk.api.ApiConfigKit;
import com.jfinal.render.ViewType;
import com.xiongl.weixin.sdk.ApiConfig;
import com.xiongl.weixin.sdk.Constant;
import com.xiongl.weixin.service.AccessTokenService;
import com.xiongl.weixin.service.DownloadJob;
import com.xiongl.weixin.service.JobService;
import org.quartz.SchedulerException;

import java.text.ParseException;
import java.util.concurrent.Executors;

public class QyWeiXinConfig extends JFinalConfig{

	public static final AsyncEventBus eventBus;

	static {
		eventBus = new AsyncEventBus(Executors.newFixedThreadPool(1));
		eventBus.register(new ClientConsultEventHandler());
		eventBus.register(new CustomerServiceAgentEventHandler());
	}

	/**
	 * 如果生产环境配置文件存在，则优先加载该配置，否则加载开发环境配置文件
	 * @param pro 生产环境配置文件
	 * @param dev 开发环境配置文件
	 */
	public void loadProp(String pro, String dev) {
		try {
			PropKit.use(pro);
		}
		catch (Exception e) {
			PropKit.use(dev);
		}
	}
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用PropKit.get(...)获取值
		loadProp("a_little_config_pro.txt", "a_little_config.txt");
		me.setDevMode(PropKit.getBoolean("devMode", false));
		me.setEncoding("utf-8");
		me.setViewType(ViewType.JSP);
		// ApiConfigKit 设为开发模式可以在开发阶段输出请求交互的 xml 与 json 数据
		ApiConfigKit.setDevMode(me.getDevMode());

		ApiConfig ac = new ApiConfig();
		ac.setToken(PropKit.get("token"));
		ac.setCorpId(PropKit.get("corpId"));
		ac.setCorpSecret(PropKit.get("yy_secret"));
		AccessTokenService.configMap.put(Constant.CONSULT_APP, ac);
	}
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		me.add("/", IndexController.class);
		me.add("/qy/consult", QyConsultController.class);
		me.add("/qy/kf", QyKfController.class);
		me.add("/qy/exkf", QyExKfController.class);
		me.add("/qy/record/list", QyConsultRecordApiController.class);
		me.add("/qy/fwh/consult", FWHController.class);
	}
	
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 配置C3p0数据库连接池插件
		C3p0Plugin c3p0Plugin = new C3p0Plugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
		me.add(c3p0Plugin);

		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		me.add(arp);
		_MappingKit.mapping(arp);
	}

	public void afterJFinalStart() {
		try {
			JobService.addJob("downloadFile", "g1", "trigger1", "g1", DownloadJob.class, "0 0 1 * * ?");
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		
	}
	
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		
	}
	
	/**
	 * 建议使用 JFinal 手册推荐的方式启动项目
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 */
	public static void main(String[] args) {
		JFinal.start("src/main/webapp", 8080, "/", 5);//启动配置项
	}

}
