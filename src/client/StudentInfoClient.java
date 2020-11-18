package client;

import CreatorModule.Creator;
import CreatorModule.CreatorHelper;
import UserModule.User;
import UserModule.UserHelper;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import server.StudentInfoServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

//���ڿͻ��˴������д�ͻ�������ó���
public class StudentInfoClient {
	private Creator creator;
    private User user;
    private BufferedReader reader;
    private ORB orb;
    private org.omg.CORBA.Object objRef;
    private NamingContextExt ncRef;
    
    public StudentInfoClient(){
    	reader = new BufferedReader(new InputStreamReader(System.in));
    }
    
    public static void main(String[] args){
    	StudentInfoClient studentInfoClient = new StudentInfoClient();
    	
    	studentInfoClient.init();
    	
    	studentInfoClient.procedure();
    }
    
    private void init(){
    	System.out.println("Student_Info_Manager-Client init config starts....");
        String[] args = {};
        Properties properties = new Properties();
        properties.put("org.omg.CORBA.ORBInitialHost", "127.0.0.1");  //ָ��ORB��ip��ַ
        properties.put("org.omg.CORBA.ORBInitialPort", "1050");       //ָ��ORB�Ķ˿�

        //����һ��ORBʵ��
        orb = ORB.init(args, properties);

        //��ȡ������������
        try {
            objRef = orb.resolve_initial_references("NameService");
        } catch (InvalidName e) {
            e.printStackTrace();
        }
        ncRef = NamingContextExtHelper.narrow(objRef);  

        String name = "Creator";
        String name1 = "User";
        try {
            //ͨ��ORB�õ�serverʵ�����õ�Creator��
            creator = CreatorHelper.narrow(ncRef.resolve_str(name));  
            user = UserHelper.narrow(ncRef.resolve_str(name1));
        } catch (NotFound e) {
            e.printStackTrace();
        } catch (CannotProceed e) {
            e.printStackTrace();
        } catch (org.omg.CosNaming.NamingContextPackage.InvalidName e) {
            e.printStackTrace();
        }

        System.out.println("Student_Info_Manager-Client init config ends...");
    }
    
    //���û�����
    public void procedure(){
    	String choice;
    	String studentName, studentNum, studentAge, studentSex, department;
    	String str1, str2;
    	try{
    		while (true){
    			System.out.println("Welcome to Student_Infomation_Manager_APP!Please choose:");
                System.out.println("1.Register\n2.Login\n3.Exit");
                choice = reader.readLine();
                
                switch (choice) {
                case "1":
                    while (true) {
                        if (register()) {
                            break;
                        }
                    }
                    break;
                case "2":
                	while (true) {
                        if (login()) {
                            //System.out.println("Login Successful!");
                            do {
                                System.out.println("Please choose following command:");
                                System.out.println("1.Add Student-Information\n" +
                                        "2.Query Student-Information\n" +
                                        "3.Show Student-Information\n" +
                                        "4.Delete Student-Information\n" +
                                        "5.Logout");
                                choice = reader.readLine();

                                switch (choice) {
                                    case "1":  //���
                                        System.out.println("please input studentName:");
                                        studentName = reader.readLine().trim();
                                        System.out.println("please input studentNum:");
                                        studentNum = reader.readLine().trim();
                                        System.out.println("please input studentAge:");
                                        studentAge = reader.readLine().trim();
                                        System.out.println("please input studentGender:");
                                        studentSex = reader.readLine().trim();
                                        System.out.println("please input department:");
                                        department = reader.readLine().trim();
                                        if (user.add(studentName, studentNum, studentAge, studentSex, department)) {
                                            System.out.println("Add Student-Information successful!");
                                        } else {
                                            System.out.println("Add Student-Information fail!");
                                        }
                                        break;
                                    case "2":  //��ѧ�Ų�ѯ
                                        System.out.println("please input studentNum:");
                                        str1 = reader.readLine().trim();
                                        System.out.println(user.query(str1));
                                        break;
                                    case "3":  //��ʾѧ����Ϣ��
                                        System.out.println(user.show());
                                        break;
                                    case "4":  //ɾ��
                                        System.out.println("please input studentNum:");
                                        str2 = reader.readLine();
                                        if (user.delete(str2.trim())) {
                                            System.out.println("Delete item successful!");
                                        } else {
                                            System.out.println("Delete item fail!");
                                        }
                                        break;
                                }
                            } while (!choice.equals("5"));
                            break;
                        } else {
                            //System.out.println("Login fail!");
                            break;
                        }
                    }
                    break;
                case "3":
                	return;
                }
    		}
    	} catch (Exception e) {
            e.printStackTrace();
        }
    	try {
			reader.close();  //����
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    //ע��
    private boolean register() {
        String username, password;

        try {
            System.out.println("please input username:");
            username = reader.readLine().trim();
            System.out.println("please input password:");
            password = reader.readLine().trim();
            if (creator.register(username, password)) {
                System.out.println("Register successful!");
                return true;
            } else {
                System.out.println("Register fail!");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    //��¼
    private boolean login() {
        String username, password;

        try {
            System.out.println("please input username:");
            username = reader.readLine().trim();
            System.out.println("please input password:");
            password = reader.readLine().trim();
            if (creator.login(username, password)) {
            	System.out.println("Login successful!");
                return true;
            } else {
            	System.out.println("Login fail!");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}