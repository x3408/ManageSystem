package junit;

import java.util.ArrayList;

import org.junit.Test;

import com.xc.Service.StudentService;
import com.xc.domain.Student;
public class test {
	@Test
	public void getStudent() {
		Student stu = new Student();
		stu = new StudentService().getStudent(1);
		
		System.out.println(stu.getName());
	}
	
	@Test
	public void getTeacherMessage() {
		ArrayList<String> arr = new StudentService().lookTeacherMessage(1);
		for(String s : arr) {
			System.out.println(s);
		}
	}
}
