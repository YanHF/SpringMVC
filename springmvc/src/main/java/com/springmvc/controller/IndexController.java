package com.springmvc.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.springmvc.annotation.WebController;
import com.springmvc.annotation.WebQualifier;
import com.springmvc.annotation.WebRequestMapping;
import com.springmvc.annotation.WebRequestParam;
import com.springmvc.serviceinterface.IndexService;

@WebController
@WebRequestMapping("/index")
public class IndexController {
	@WebQualifier("ServiceImpl")
	private IndexService service;

	@WebRequestMapping("/query")
	public void query(HttpServletRequest request, HttpServletResponse response, @WebRequestParam("name") String name,
			@WebRequestParam("age") String age) {
		PrintWriter pWriter;
		try {
			request.getServletContext();
			pWriter = response.getWriter();
			String result = service.query(name, age);
			pWriter.write(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
