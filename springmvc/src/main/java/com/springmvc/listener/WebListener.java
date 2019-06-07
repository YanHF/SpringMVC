package com.springmvc.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class WebListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent sce) {
		//资源销毁
		System.out.println("WebListener contextDestroyed");
	}

	public void contextInitialized(ServletContextEvent sce) {
		//可以用于服务预热
		System.out.println("WebListener contextInitialized");
	}

}
