package com.springmvc.argument;

import java.lang.reflect.Method;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.springmvc.annotation.WebService;

@WebService("HttpServletResponseArg")
public class HttpServletResponseArg implements ArgumentResolver {

	public boolean support(Class<?> type, int index, Method method) {
		// TODO Auto-generated method stub
		return ServletResponse.class.isAssignableFrom(type);
	}

	public Object argumentResolver(HttpServletRequest request, HttpServletResponse response, Class<?> type, int index,
			Method method) {
		// TODO Auto-generated method stub
		return null;
	}

}
