package com.xc.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.xc.util.SqlHelper;


public abstract class AbstractDao {
	public int update(String sql,Object [] args) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		int count = 0;
		conn = SqlHelper.getInstance().getConnection();
		try {
			st = conn.prepareStatement(sql);
			for( int i=0;i<args.length;i++) {
				st.setObject(i+1, args[i]);
			}
			count = st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SqlHelper.free(conn, st, rs);
		}
		return count;
	}
	
	public void addorDelete(String sql, Object [] args) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		conn = SqlHelper.getInstance().getConnection();
		try {
			ps = conn.prepareStatement(sql);
			for( int i=0;i<args.length;i++) {
				ps.setObject(i+1, args[i]);
			}
			
			int i = ps.executeUpdate();
			System.out.println(i);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SqlHelper.free(conn, ps, rs);
		}
	}
	
	public ResultSet query(String sql, Object [] args) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		conn = SqlHelper.getInstance().getConnection();
		try {
			ps = conn.prepareStatement(sql);
			
			for( int i=0;i<args.length;i++) {
				ps.setObject(i+1, args[i]);
			}
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
}
