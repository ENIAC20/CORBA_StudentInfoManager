package UserModule;

import someClasses.Student;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

import org.omg.CORBA.ORB;

//实现学生信息管理user.idl中定义的接口
public class UserImpl extends UserPOA{
	private ORB orb;
	private List<Student> studentInfo;  //学生信息表Student-Info
	private Map<String, Student> studentInfoMap;  //学生信息HashMap表->根据学号去重
	private String numRegex;  //学号正则
	private String ageRegex;  //年龄正则
    
    public UserImpl() throws ClassNotFoundException, IOException {
    	
        studentInfo = new ArrayList<Student>();
        studentInfoMap = new HashMap<String, Student>();
        
        init();  //初始化
        
        numRegex = "[2][0][0-2][\\d]{11}";  //学号以200x~202x开头
		ageRegex = "[1-9][0-9]";  //年龄在10~99之间
    }
   
    public void setORB(ORB orb) {
        this.orb = orb;
    }
    
    //从文件中读取Student-Info列表,转化为HashMap
    private void init() throws IOException, ClassNotFoundException{
    	try{
    	ObjectInputStream ois = new ObjectInputStream(new FileInputStream("student.file"));  //创建对象输入流
    	
    	while(ois != null){
    	Student stu = (Student)ois.readObject();  //读档，一次读一个对象
    	studentInfo.add(stu);
    	studentInfoMap.put(stu.getStudentNum(), stu);
    	}
    	
    	ois.close();  //关闭对象输入流
    	} catch (EOFException e){
    		//System.out.println("无内容！");
    	}
    }
   
    //将Student-Info表保存到本地文件中
    private void saveData() throws IOException{
    	ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("student.file"));  //创建对象输出流
    	
    	Set<String> ks = studentInfoMap.keySet();  //keySet()方法--> 获取集合中所有的键，返回值类型是Set
    	//使用迭代器遍历Set
    	Iterator<String> it = ks.iterator();  //获取迭代器
		while(it.hasNext()) {  //判断键值集合中是否有元素
			String key = it.next();  //获取每一个元素
			Student value = studentInfoMap.get(key);  //根据键获取值
			oos.writeObject(value);  //写入文档
		}
		
		oos.flush();
		oos.close();
    }
    
    public boolean add(String studentName, String studentNum, String studentAge, String studentSex, String department){
    	if (studentNum.matches(numRegex) && studentAge.matches(ageRegex)){
    		Student stu = new Student(studentName, studentNum, studentAge, studentSex, department);
    		studentInfo.add(stu);
    		studentInfoMap.put(studentNum, stu);
    		try {
				saveData();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		return true;
    	}
    	return false;
    }
    
    public boolean ifExitStudent(String studentNum){  //查询、删除前应先判断表中是否存在该学生
    	boolean b = false;  //表中无该学生（初始值）
    	
    	Set<String> ks = studentInfoMap.keySet();
    	//使用迭代器遍历Set
    	Iterator<String> it = ks.iterator();  //获取迭代器
		while(it.hasNext()) {  //判断键值集合中是否有元素
			String key = it.next();  //获取每一个元素
			if (key.equals(studentNum))
				b = true;
		}
		
		return b;
    }
    
    public String query(String studentNum){
    	String queryResult;
    
		if (ifExitStudent(studentNum)){
			queryResult = "name:" + studentInfoMap.get(studentNum).getStudentName() + ",num:" + studentInfoMap.get(studentNum).getStudentNum() + 
					",age:" + studentInfoMap.get(studentNum).getStudentAge() + ",gender:" + studentInfoMap.get(studentNum).getStudentSex() + 
					",department:" + studentInfoMap.get(studentNum).getDepartment() + "\n";
		}
		else{
			queryResult = "No information for this student!";
		}
    	
    	return queryResult;
    }
    
    public boolean delete(String studentNum){
    	if (ifExitStudent(studentNum)){
    		studentInfoMap.remove(studentNum, studentInfoMap.get(studentNum));
        	try {
    			saveData();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	return true;
    	}
    	else {
    		System.out.println("No information for this student!");
    		return false;
    		}
    }
    
    public String show(){
    	String showResult = "";
    	Set<String> ks = studentInfoMap.keySet();
    	//使用迭代器遍历Set
    	Iterator<String> it = ks.iterator();  //获取迭代器
		while(it.hasNext()) {  //判断键值集合中是否有元素
			String key = it.next();  //获取每一个元素
			Student value = studentInfoMap.get(key);  //根据键获取值
			showResult += ("name:" + value.getStudentName() + ",num:" + value.getStudentNum() + 
					",age:" + value.getStudentAge() + ",gender:" + value.getStudentSex() + 
					",department:" + value.getDepartment() + "\n");
		}
    	return showResult;
    }
}
