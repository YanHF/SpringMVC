package com.springmvc.serviceImpl;

import com.springmvc.annotation.WebService;
import com.springmvc.serviceinterface.IndexService;

@WebService("ServiceImpl")
public class IndexServiceImpl implements IndexService {

	public String query(String name, String age) {
		return "name="+name+"age="+age;
	}

	public String insert(String param) {
		// TODO Auto-generated method stub
		return null;
	}

	public String update(String param) {
		// TODO Auto-generated method stub
		return null;
	}

}
