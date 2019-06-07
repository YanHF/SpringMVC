package com.springmvc.servlet;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.springmvc.annotation.WebController;
import com.springmvc.annotation.WebQualifier;
import com.springmvc.annotation.WebRequestMapping;
import com.springmvc.annotation.WebService;
import com.springmvc.controller.IndexController;
import com.springmvc.hand.HandServer;

public class DispatcherServlet extends HttpServlet {
	private static List<String> classname = new ArrayList<String>();
	private static Map<String, Object> beaMap = new HashMap<String, Object>();
	private static Map<String, Method> hanMap = new HashMap<String, Method>();
	private static final long serialVersionUID = -7810309932739214778L;

	@Override
	public void init() throws ServletException {
		System.out.println("DispatcherServlet init");
		// 先扫描需要被实例化的类
		doScanPackage("com.springmvc");
		for (String string : classname) {
			System.out.println(string);
		}
		// 实例化bean
		doInstance();
		// 依赖注入
		iocDi();
		// 建立一个url跟controller映射关系
		HandMapper();
		for (Map.Entry<String, Method> entry : hanMap.entrySet()) {
			System.out.println(entry.getKey()+entry.getValue().getName());
		}
	}

	private void HandMapper() {
		for (Map.Entry<String, Object> entry : beaMap.entrySet()) {
			Object inObject = entry.getValue();
			Class<?> _claClass = inObject.getClass();
			if (_claClass.isAnnotationPresent(WebController.class)) {
				WebRequestMapping requestMapping = _claClass.getAnnotation(WebRequestMapping.class);
				String classpath = requestMapping.value();
				java.lang.reflect.Method[] methods = _claClass.getMethods();
				for (java.lang.reflect.Method meth : methods) {
					if (meth.isAnnotationPresent(WebRequestMapping.class)) {
						WebRequestMapping methodrequestMapping = meth.getAnnotation(WebRequestMapping.class);
						String methodpath = methodrequestMapping.value();
						String key = classpath + methodpath;
						hanMap.put(key, meth);
					} else {
						continue;
					}
				}
			} else {
				continue;
			}
		}
	}

	private void iocDi() {
		if (beaMap.isEmpty()) {
			System.out.println("没有实例化的Bean");
			return;
		}
		for (Map.Entry<String, Object> entry : beaMap.entrySet()) {
			Object inObject = entry.getValue();
			Class<?> clazzClass = inObject.getClass();
			if (clazzClass.isAnnotationPresent(WebController.class)) {
				Field[] files = clazzClass.getDeclaredFields();
				for (Field field : files) {
					if (field.isAnnotationPresent(WebQualifier.class)) {
						WebQualifier qualifier = field.getAnnotation(WebQualifier.class);
						String keyString = qualifier.value();
						field.setAccessible(true);// 权限
						try {
							field.set(inObject, beaMap.get(keyString));
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						continue;
					}

				}
			} else {
				continue;
			}

		}

	}

	// 实例化
	private void doInstance() {
		if (classname.isEmpty()) {
			System.out.println("没有扫描到任何bean");
			return;
		}
		for (String string : classname) {
			String cn = string.replaceAll(".class", "");
			try {
				Class<?> _class = Class.forName(cn);
				if (_class.isAnnotationPresent(WebController.class)) {
					Object insObject = _class.newInstance();
					WebRequestMapping mapping = _class.getAnnotation(WebRequestMapping.class);
					String key = mapping.value();
					beaMap.put(key, insObject);
				} else if (_class.isAnnotationPresent(WebService.class)) {
					WebService service = _class.getAnnotation(WebService.class);
					Object sObject = _class.newInstance();
					beaMap.put(service.value(), sObject);
				} else {
					continue;
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// 扫描class的类
	private void doScanPackage(String basePackage) {
		String vaString="/" + basePackage.replace("\\.", "/");
		URL url = this.getClass().getClassLoader().getResource("/" + basePackage.replaceAll("\\.", "/"));
		String fileStr = url.getFile();
		File file = new java.io.File(fileStr);
		String[] files = file.list();
		for (String path : files) {
			File filepath = new File(fileStr + path);
			if (filepath.isDirectory()) {
				doScanPackage(basePackage + "." + path);
			} else {
				classname.add(basePackage + "." + filepath.getName());
			}
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 String pathString=req.getRequestURI();
		    String conString=req.getContextPath();
		    String path=pathString.replace(conString,"");
		    Method method=hanMap.get(path);
		    String[] stsStrings=path.split("/");
		    String keyString=path.split("/")[1];
		    Object o=beaMap.get("/"+path.split("/")[1]);
		    IndexController controller=(IndexController) beaMap.get("/"+path.split("/")[1]);
		    HandServer handServer=(HandServer) beaMap.get("HandServer");
		    Object[] argsObjects=handServer.Hand(req, resp, method, beaMap);
		    try {
				method.invoke(controller, argsObjects);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    //策略模式
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    String pathString=req.getRequestURI();
	    String conString=req.getContextPath();
	    String path=pathString.replace(conString,"");
	    Method method=hanMap.get(path);
	    WebController controller=(WebController) beaMap.get("/"+path.split("/")[1]);
	    
	    HandServer handServer=(HandServer) beaMap.get("HandServer");
	    Object[] argsObjects=handServer.Hand(req, resp, method, beaMap);
	    try {
			method.invoke(controller, argsObjects);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    //策略模式
		
	}

}
