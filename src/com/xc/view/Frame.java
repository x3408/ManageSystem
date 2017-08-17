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
		System.out.println("***********��ӭʹ�óɼ�����ϵͳ***********");
		boolean flag = true;
		while(flag){
			System.out.println("�������ѡ�");
			System.out.println("1.ѧ��");
			System.out.println("2.��ʦ");
			System.out.println("3.����Ա");
			System.out.println("0.�˳�");
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
				System.out.println("����ѧ��������");
				String name = br.readLine();
				if("exit".equals(name))
					break;
				System.out.println("�������룺");
				String pwd = br.readLine();
				stu = ss.Login(name, pwd);
				if(stu == null){
					System.out.println("������û����������������룡����exit������һ��");
					continue;
				}
				System.out.println("****************************************");
				System.out.println("��½�ɹ� 		��ӭ~"+name);
				while(flag) {
					System.out.println("   1.�޸ĸ�����Ϣ");
					System.out.println("   2.�鿴�ɼ�");
					System.out.println("   3.�鿴��ʦ��Ϣ");
					System.out.println("   0.������һ��");
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
						System.out.println("������������������");
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void lookTeacherMessage() {
		System.out.println("****************************************");
		System.out.print("���ѡ����ʦ�ֱ���:");
		ArrayList<String> arr = ss.lookTeacherMessage(stu.getID());
		for(Iterator<String> it = arr.iterator();it.hasNext();) {
			System.out.print(it.next()+"\t");
		}
		System.out.println();
	}

	private void lookScore() {
		System.out.println("****************************************");
		System.out.println("��ǰѧ����"+stu.getName());
		TreeMap<String,Integer> arr = ss.lookScore(stu.getID());
		Set<Map.Entry<String, Integer>> se = arr.entrySet();
		for(Iterator<Map.Entry<String, Integer>> it = se.iterator();it.hasNext();) {
			Map.Entry<String, Integer> temp = it.next();
			String name = temp.getKey();
			int score = temp.getValue();
			System.out.println("�γ�����"+name+"\t�ɼ���"+score);
		}
	}

	private void changeMessage() {
		boolean flag = true;
		while (flag) {
			try {
				System.out.println("�����������������Ա����գ����룬�γ̴���1��2��3���ÿո�ֿ���");	
				String [] parameter = br.readLine().split(" ");
				int i = ss.changeMessage(parameter,stu.getID());
				if(i==1){
					System.out.println("�޸���Ϣ�ɹ�");
					flag = false;
				}
				else
					System.out.println("������������������");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
