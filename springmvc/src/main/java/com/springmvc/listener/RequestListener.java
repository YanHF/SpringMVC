package com.springmvc.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

public class RequestListener implements ServletRequestListener {

	public void requestDestroyed(ServletRequestEvent sre) {
	 HttpServletRequest request=(HttpServletRequest) sre.getServletRequest();
	 String string= request.getRequestURI();
	 System.out.println("Request url"+string+"Destroyed");
	}
	public void requestInitialized(ServletRequestEvent sre) {
		 HttpServletRequest request=(HttpServletRequest) sre.getServletRequest();
		 String string= request.getRequestURI();
		 System.out.println("Request url"+string+"Initialized");
	}

}
