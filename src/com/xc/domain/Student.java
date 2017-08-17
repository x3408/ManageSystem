package com.xc.domain;

public class Student {
	private int ID;
	private String name;
	private String gender;
	private java.util.Date birthday;
	private String pwd;
	private int course1;
	private int course2;
	private int course3;
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setBirthday(java.util.Date birthday) {
		this.birthday = birthday;
	}
	public java.util.Date getBirthday() {
		return birthday;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public int getCourse1() {
		return course1;
	}
	public void setCourse1(int course1) {
		this.course1 = course1;
	}
	public int getCourse2() {
		return course2;
	}
	public void setCourse2(int course2) {
		this.course2 = course2;
	}
	public int getCourse3() {
		return course3;
	}
	public void setCourse3(int course3) {
		this.course3 = course3;
	}
}
