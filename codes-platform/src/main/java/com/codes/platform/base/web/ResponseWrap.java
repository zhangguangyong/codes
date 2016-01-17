package com.codes.platform.base.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.codes.common.util.Jsons;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 响应包装
 * @author zhangguangyong
 *
 * 2015年11月7日 上午10:36:59
 */
public class ResponseWrap {
	
	private HttpServletResponse response;
	private Map<Object, Object> dataMap = Maps.newConcurrentMap();
	private List<Object> dataList = Lists.newArrayList();
	
	
	public static ResponseWrap getInstance(HttpServletResponse response){
		ResponseWrap responseWrap = new ResponseWrap();
		responseWrap.setResponse(response);
		return responseWrap;
	}

	public ResponseWrap add(String name, Object value){
		this.dataMap.put(name, value);
		return this;
	}

	public ResponseWrap add(Object value){
		this.dataList.add(value);
		return this;
	}
	
	public void responseDataMap(){
		responseJson(dataMap);
	}

	public void responseDataList(){
		responseJson(dataList);
	}
	
	private void responseJson(Object value){
		response.setContentType("application/x-json;charset=utf-8");
		PrintWriter w = null;
		try {
			w = response.getWriter();
			w.write( Jsons.format(value) );
			w.flush();
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Map<Object, Object> getDataMap() {
		return dataMap;
	}

	public ResponseWrap setDataMap(Map<Object, Object> dataMap) {
		this.dataMap = dataMap;
		return this;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public ResponseWrap setResponse(HttpServletResponse response) {
		this.response = response;
		return this;
	}
	
	
	
	
}
