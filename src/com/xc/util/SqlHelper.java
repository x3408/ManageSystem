package com.xc.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class SqlHelper {
	private static String url = null;
	private static String link = null;
	private static String name = null;
	private static String password = null;
	
	private static SqlHelper instance = new SqlHelper();
	
	private static Connection conn = null;

	private static ResultSet rs = null;
	private static PreparedStatement ps = null;
	private static Statement st = null;
	private static Properties prop = new Properties();
	public static Connection getConn() {
		return conn;
	}
	public static ResultSet getRs() {
		return rs;
	}
	public static PreparedStatement getPs() {
		return ps;
	}
	public static Statement getSt() {
		return st;
	}
	
	
	static {
		try {
			prop.load(SqlHelper.class.getClassLoader().getResourceAsStream("util.properties"));
			url = prop.getProperty("url");
			Class.forName(url);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	private SqlHelper() {}
	
	
	public static SqlHelper getInstance() {
		return instance;
	}
	
	
	public Connection getConnection() {
		link = prop.getProperty("link");
		name = prop.getProperty("name");
		password = prop.getProperty("password");
		try {
			conn = DriverManager.getConnection(link,name,password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	
	public static void free(Connection conn, Statement st, ResultSet rs) {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (rs != null)
						rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public static void free(Connection conn,Statement st){
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * @author x3408
	 * @param ��ѯ������String�ؼ��֣���ֹSQLע��©���� û�йؼ����򴫵�null
	 * @return ��ѯ����� ResultSet
	 */
	public static ResultSet executeStringQuery(String sql,String [] parameters) {
		
		try {
			conn = instance.getConnection();
			if(parameters == null) {
				st = conn.createStatement();
				rs = st.executeQuery(sql);
			} else {
				ps = conn.prepareStatement(sql);
				for(int i=0;i<parameters.length;i++) {
					ps.setString(i+1, parameters[i]);
				}
				rs = ps.executeQuery();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return rs;
	}
}