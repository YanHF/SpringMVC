package com.springmvc.hand;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IHandServerInterface {
	Object[] Hand(HttpServletRequest req, HttpServletResponse resp,Method method,Map<String, Object> beans);
}
