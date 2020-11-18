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

//ʵ��ѧ����Ϣ����user.idl�ж���Ľӿ�
public class UserImpl extends UserPOA{
	private ORB orb;
	private List<Student> studentInfo;  //ѧ����Ϣ��Student-Info
	private Map<String, Student> studentInfoMap;  //ѧ����ϢHashMap��->����ѧ��ȥ��
	private String numRegex;  //ѧ������
	private String ageRegex;  //��������
    
    public UserImpl() throws ClassNotFoundException, IOException {
    	
        studentInfo = new ArrayList<Student>();
        studentInfoMap = new HashMap<String, Student>();
        
        init();  //��ʼ��
        
        numRegex = "[2][0][0-2][\\d]{11}";  //ѧ����200x~202x��ͷ
		ageRegex = "[1-9][0-9]";  //������10~99֮��
    }
   
    public void setORB(ORB orb) {
        this.orb = orb;
    }
    
    //���ļ��ж�ȡStudent-Info�б�,ת��ΪHashMap
    private void init() throws IOException, ClassNotFoundException{
    	try{
    	ObjectInputStream ois = new ObjectInputStream(new FileInputStream("student.file"));  //��������������
    	
    	while(ois != null){
    	Student stu = (Student)ois.readObject();  //������һ�ζ�һ������
    	studentInfo.add(stu);
    	studentInfoMap.put(stu.getStudentNum(), stu);
    	}
    	
    	ois.close();  //�رն���������
    	} catch (EOFException e){
    		//System.out.println("�����ݣ�");
    	}
    }
   
    //��Student-Info���浽�����ļ���
    private void saveData() throws IOException{
    	ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("student.file"));  //�������������
    	
    	Set<String> ks = studentInfoMap.keySet();  //keySet()����--> ��ȡ���������еļ�������ֵ������Set
    	//ʹ�õ���������Set
    	Iterator<String> it = ks.iterator();  //��ȡ������
		while(it.hasNext()) {  //�жϼ�ֵ�������Ƿ���Ԫ��
			String key = it.next();  //��ȡÿһ��Ԫ��
			Student value = studentInfoMap.get(key);  //���ݼ���ȡֵ
			oos.writeObject(value);  //д���ĵ�
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
    
    public boolean ifExitStudent(String studentNum){  //��ѯ��ɾ��ǰӦ���жϱ����Ƿ���ڸ�ѧ��
    	boolean b = false;  //�����޸�ѧ������ʼֵ��
    	
    	Set<String> ks = studentInfoMap.keySet();
    	//ʹ�õ���������Set
    	Iterator<String> it = ks.iterator();  //��ȡ������
		while(it.hasNext()) {  //�жϼ�ֵ�������Ƿ���Ԫ��
			String key = it.next();  //��ȡÿһ��Ԫ��
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
    	//ʹ�õ���������Set
    	Iterator<String> it = ks.iterator();  //��ȡ������
		while(it.hasNext()) {  //�жϼ�ֵ�������Ƿ���Ԫ��
			String key = it.next();  //��ȡÿһ��Ԫ��
			Student value = studentInfoMap.get(key);  //���ݼ���ȡֵ
			showResult += ("name:" + value.getStudentName() + ",num:" + value.getStudentNum() + 
					",age:" + value.getStudentAge() + ",gender:" + value.getStudentSex() + 
					",department:" + value.getDepartment() + "\n");
		}
    	return showResult;
    }
}
