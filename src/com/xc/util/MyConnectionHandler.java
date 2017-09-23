package com.xc.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;


public class MyConnectionHandler implements InvocationHandler{
	private Connection realConn;
	private Connection warpedConn;
	private SqlHelper helper;
	
	private static int maxConnectCount = 10;
	private static int currentConnectCount = 0;
	
	MyConnectionHandler(SqlHelper helper) {
		this.helper = helper;
	}
	
	Connection bind(Connection conn) {
		this.realConn = conn;
		this.warpedConn = (Connection)Proxy.newProxyInstance(
				this.getClass().getClassLoader(), new Class[] {Connection.class}, this);
		
		return warpedConn;
	}
	
	
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if("close".equals(method.getName())){
			if(currentConnectCount < maxConnectCount) {
				helper.list.add(this.warpedConn);				
			} else {
				this.realConn.close();
				currentConnectCount--;
			}
		}
		return method.invoke(this.realConn, args);
	}
}
