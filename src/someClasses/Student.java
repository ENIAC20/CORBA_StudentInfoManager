package someClasses;

import java.io.Serializable;

//学生类
public class Student implements Serializable{  //实现序列化接口
	private String studentName;  //姓名
	private String studentNum;  //学号
	private String studentAge;  //年龄
	private String studentSex;  //性别
	private String department;  //专业
	
	public Student(String studentName, String studentNum, String studentAge, String studentSex, String department){
		this.studentName = studentName;
		this.studentNum = studentNum;
		this.studentAge = studentAge;
		this.studentSex = studentSex;
		this.department = department;
	}
	
	public String getStudentName(){
		return studentName;
	}
	
	public String getStudentNum(){
		return studentNum;
	}
	
	public String getStudentAge(){
		return studentAge;
	}
	
	public String getStudentSex(){
		return studentSex;
	}
	
	public String getDepartment(){
		return department;
	}
}
