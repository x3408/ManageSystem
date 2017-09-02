package junit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import com.xc.domain.Student;
import com.xc.util.SqlHelper;
public class test {
	@Test
	public void getStudent() {
		Student stu = new Student();
		String sql = "select * from student where id = ?";
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			conn = SqlHelper.getInstance().getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, 1);
			
			rs = ps.executeQuery();
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
		} finally {
			SqlHelper.free(conn, ps, rs);
		}
	}
}
