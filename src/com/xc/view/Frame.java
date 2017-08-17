package com.xc.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import com.xc.Service.StudentService;
import com.xc.domain.Student;
import com.xc.domain.Teacher;
public class Frame {
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private StudentService ss = new StudentService();
	Student stu = null;
	Teacher tea = null;
	public static void main(String[] args) throws IOException {
		new Frame();
		br.close();
	}
	
	public Frame() {
		System.out.println("***********欢迎使用成绩管理系统***********");
		boolean flag = true;
		while(flag){
			System.out.println("输入你的选项：");
			System.out.println("1.学生");
			System.out.println("2.老师");
			System.out.println("3.管理员");
			System.out.println("0.退出");
			String opinion = null;
			try {
				opinion = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			switch(opinion) {
			case "1":
				studentPanel();
				break;
			case "2":
				teacherPanel();
				break;
			case "3":
				break;
			case "0":
				flag = false;
				break;
			default:
			}	
		}
	}

	private void teacherPanel() {
		tea = new Teacher();
	}

	private void studentPanel() {
		stu = new Student();
		boolean flag = true;
		try {
			while(flag) {
				System.out.println("输入学生姓名：");
				String name = br.readLine();
				if("exit".equals(name))
					break;
				System.out.println("输入密码：");
				String pwd = br.readLine();
				stu = ss.Login(name, pwd);
				if(stu == null){
					System.out.println("密码或用户名错误，请重新输入！输入exit返回上一层");
					continue;
				}
				System.out.println("****************************************");
				System.out.println("登陆成功 		欢迎~"+name);
				while(flag) {
					System.out.println("   1.修改个人信息");
					System.out.println("   2.查看成绩");
					System.out.println("   3.查看老师信息");
					System.out.println("   0.返回上一层");
					switch(br.readLine()) {
					case "1":
						changeMessage();
						break;
					case "2":
						lookScore();
						break;
					case "3":
						lookTeacherMessage();
						break;
					case "0":
						flag = false;
						break;
					default:
						System.out.println("输入有误，请重新输入");
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void lookTeacherMessage() {
		System.out.println("****************************************");
		System.out.print("你的选课老师分别是:");
		ArrayList<String> arr = ss.lookTeacherMessage(stu.getID());
		for(Iterator<String> it = arr.iterator();it.hasNext();) {
			System.out.print(it.next()+"\t");
		}
		System.out.println();
	}

	private void lookScore() {
		System.out.println("****************************************");
		System.out.println("当前学生："+stu.getName());
		TreeMap<String,Integer> arr = ss.lookScore(stu.getID());
		Set<Map.Entry<String, Integer>> se = arr.entrySet();
		for(Iterator<Map.Entry<String, Integer>> it = se.iterator();it.hasNext();) {
			Map.Entry<String, Integer> temp = it.next();
			String name = temp.getKey();
			int score = temp.getValue();
			System.out.println("课程名："+name+"\t成绩："+score);
		}
	}

	private void changeMessage() {
		boolean flag = true;
		while (flag) {
			try {
				System.out.println("请依次输入姓名，性别，生日，密码，课程代码1、2、3，用空格分开：");	
				String [] parameter = br.readLine().split(" ");
				int i = ss.changeMessage(parameter,stu.getID());
				if(i==1){
					System.out.println("修改信息成功");
					flag = false;
				}
				else
					System.out.println("输入有误请重新输入");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
