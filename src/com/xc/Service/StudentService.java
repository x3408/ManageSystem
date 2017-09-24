package com.xc.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import com.xc.domain.Student;

public class StudentService extends AbstractDao{
	/**
	 * 
	 * @param name
	 * @param pwd
	 * @return 学生对象，如果没有匹配的对象返回null
	 */
	public Student Login(String name, String pwd) {
		Student stu = null;
		ResultSet rs = null;
		String sql = "select id from student where (pwd = ?) and (name = ?)";
		Object [] args = new Object[] {pwd, name};
		rs = query(sql, args);
		try {
			if(rs.next()) {
				stu = getStudent(rs.getInt("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stu;
	}
/**
 * 
 * @param studentId
 * @return 学生对象，如果没有匹配的对象返回null
 */
	public Student getStudent(int studentId) {
		Student stu = new Student();
		ResultSet rs = null;
		String sql = "select * from student where id = ?";
		Object [] args = new Object[] {studentId};
		rs = query(sql, args);
		try {
			if(rs.next()) {
				stu.setID(rs.getInt("id"));
				stu.setName(rs.getString("name"));
				stu.setGender(rs.getString("gender"));
				stu.setBirthday(rs.getDate("birthday"));
				stu.setPwd(rs.getString("pwd"));
				stu.setCourse1(rs.getInt("course1"));
				stu.setCourse2(rs.getInt("course2"));
				stu.setCourse3(rs.getInt("course3"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return stu;
	}
	
	
	public void addStudent(String [] parameter) {
		Student stu = new Student();
		stu.setID(Integer.valueOf(parameter[0]));
		stu.setName(parameter[1]);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		stu.setGender(parameter[2]);
		try {
			stu.setBirthday(sdf.parse(parameter[3]));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		stu.setPwd(parameter[4]);
		stu.setCourse1(Integer.valueOf(parameter[5]));
		stu.setCourse2(Integer.valueOf(parameter[6]));
		stu.setCourse3(Integer.valueOf(parameter[7]));		
	}
	
	
	public int changeMessage(String [] parameter,int id){
		
		for(String s : parameter){
			System.out.print(s+" ");
		}
		String sql = "update student set name = ?,gender=?,birthday=?,pwd=?,course1=?,course2=?,course3=? where id=?";
		Object [] args = new Object[]{};
		for(int i=0;i<parameter.length; i++) {
			args[i] = parameter[i];
		}
		int i = update(sql, args);
		return i;
	}
	
	
	/*public TreeMap<Integer,Integer> lookScore(int studentId) {
		String sql = "select courseId,score from opinion where studentId = ?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TreeMap<Integer,Integer> arr = new TreeMap<Integer,Integer>();
		try {
			conn = SqlHelper.getInstance().getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, studentId);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				arr.put(rs.getInt(1), rs.getInt(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SqlHelper.free(conn, ps, rs);
		}
		return arr;
	}*/
	
	
	public TreeMap<String,Integer> lookScore(int studentId) {
		ResultSet rs = null;
		String sql = "select id,score from score where studentId = ?";
		Object [] args = new Object[] {studentId};
		rs = query(sql, args);
		TreeMap<String,Integer> arr = new TreeMap<String,Integer>();
		try {
			while(rs.next()){
				for(Iterator<String> it = lookCourseName(rs.getInt("id")).iterator();it.hasNext();) {
					String names = it.next();
					arr.put(names, rs.getInt("score"));
				}		
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arr;
	}
	
	
	private ArrayList<String> lookCourseName(int id) {
		ResultSet rs = null;
		String sql = "select name from course where id = ?";
		Object [] args = new Object[] {id};
		rs = query(sql, args);
		ArrayList<String> names = new ArrayList<String>();
		try {
			while (rs.next()){
				names.add(rs.getString("name"));			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return names;
	}
	
	
	public ArrayList<String> lookTeacherMessage(int studentId) {
		ArrayList<String> names = new ArrayList<String>();
		ResultSet rs = null;
		ResultSet rs1 = null;
		
		String sql = "select course1,course2,course3 from student where id = ?";
		Object [] args = new Object[] {studentId};
		rs = query(sql, args);
		
		try {
			while(rs.next()) {
				sql = "select name from teacher where id = ?";
				Object args1 [] = new Object [3];
				for(int i=1;i<4;i++) {
					args1[i-1] = rs.getInt("course"+i);
					rs1 = query(sql, new Object[] {args1[i-1]});
					if(rs1.next()) {
						names.add(rs1.getString("name"));
					}
				}
			}
			rs1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return names;
	}
}
