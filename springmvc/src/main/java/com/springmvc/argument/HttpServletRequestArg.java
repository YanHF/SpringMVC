package com.springmvc.argument;

import java.lang.reflect.Method;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.springmvc.annotation.WebService;
@WebService("HttpServletRequestArg")
public class HttpServletRequestArg implements ArgumentResolver{

	public boolean support(Class<?> type, int index, Method method) {
		return ServletRequest.class.isAssignableFrom(type);
	}

	public Object argumentResolver(HttpServletRequest request, HttpServletResponse response, Class<?> type, int index,
			Method method) {
		// TODO Auto-generated method stub
		return request;
	}

}
