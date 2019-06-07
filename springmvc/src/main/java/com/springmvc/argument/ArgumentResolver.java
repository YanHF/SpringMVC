package com.springmvc.argument;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ArgumentResolver {

	//判断当前类是否需要解析
	boolean support(Class<?> type,int index,Method method);
	
	Object argumentResolver(HttpServletRequest request,HttpServletResponse response,Class<?> type,int index,Method method);
}
