package com.springmvc.argument;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.springmvc.annotation.WebRequestParam;
import com.springmvc.annotation.WebService;
@WebService("RequestParamArgument")
public class RequestParamArgument implements ArgumentResolver {

	public boolean support(Class<?> type, int index, Method method) {
		Annotation[][] annotations=method.getParameterAnnotations();
		Annotation[] parAnno=annotations[index];
		for (Annotation annotation : parAnno) {
			if(WebRequestParam.class.isAssignableFrom(annotation.getClass()))
			{
				return true;
			}
		}
		return false;
	}

	public Object argumentResolver(HttpServletRequest request, HttpServletResponse response, Class<?> type, int index,
			Method method) {
		Annotation[][] annotations=method.getParameterAnnotations();
		Annotation[] parAnno=annotations[index];
		for (Annotation annotation : parAnno) {
			if(WebRequestParam.class.isAssignableFrom(annotation.getClass()))
			{
				WebRequestParam param=(WebRequestParam)annotation;
				String vaString=param.value();
				return request.getParameter(vaString);
			}
		}
		return null;
	}

}
