package com.springmvc.hand;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.springmvc.annotation.WebService;
import com.springmvc.argument.ArgumentResolver;

@WebService("HandServer")
public class HandServer implements IHandServerInterface {

	public Object[] Hand(HttpServletRequest req, HttpServletResponse resp, Method method, Map<String, Object> beans) {
		Class<?>[] _paramclassClass = method.getParameterTypes();
		Object[] args = new Object[_paramclassClass.length];
		Map<String, Object> argResolverMap = getInstanceType(beans, ArgumentResolver.class);
		int index = 0;
		int i = 0;
		for (Class<?> cla : _paramclassClass) {
			// ²ßÂÔÄ£Ê½
			for (Map.Entry<String, Object> entry : argResolverMap.entrySet()) {
				System.out.println(entry.getKey() + entry.getValue());
				ArgumentResolver aResolver = (ArgumentResolver) entry.getValue();
				if (aResolver.support(cla, index, method)) {
					args[i++] = aResolver.argumentResolver(req, resp, cla, index, method);
				}
			}
			index++;
		}
		return args;
	}

	private Map<String, Object> getInstanceType(Map<String, Object> beans, Class<?> type) {
		Map<String, Object> argResolverMap = new HashMap<String, Object>();
		for (Map.Entry<String, Object> entry : beans.entrySet()) {
			Class<?>[] infsClass = entry.getValue().getClass().getInterfaces();
			if (infsClass != null && infsClass.length > 0) {
				for (Class<?> info : infsClass) {
					if (info.isAssignableFrom(ArgumentResolver.class)) {
						argResolverMap.put(entry.getKey(), entry.getValue());
					}
				}
			}
		}
		return argResolverMap;
	}
}
