package com.springmvc.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class WebListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent sce) {
		//��Դ����
		System.out.println("WebListener contextDestroyed");
	}

	public void contextInitialized(ServletContextEvent sce) {
		//�������ڷ���Ԥ��
		System.out.println("WebListener contextInitialized");
	}

}
