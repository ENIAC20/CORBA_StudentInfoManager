package someClasses;

import java.io.Serializable;

//ѧ����
public class Student implements Serializable{  //ʵ�����л��ӿ�
	private String studentName;  //����
	private String studentNum;  //ѧ��
	private String studentAge;  //����
	private String studentSex;  //�Ա�
	private String department;  //רҵ
	
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
