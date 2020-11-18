package CreatorModule;

import someClasses.Person;

import org.omg.CORBA.ORB;
import server.StudentInfoServer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

//实现用户管理creator.idl中定义的接口
public class CreatorImpl extends CreatorPOA{
	private Map<String, Person> usersMap;
    //private ORB orb;
    private StudentInfoServer studentInfoServer;
    
    public CreatorImpl() {
        init();
    }
    
    //初始化
    private void init() {
        //从文件中读取用户列表,转化为HashMap
        try {
            FileInputStream fin = new FileInputStream("person.file");
            ObjectInputStream oin = new ObjectInputStream(fin);
            try {
                Object object = oin.readObject();
                usersMap = (HashMap<String, Person>) object;
            } catch (ClassNotFoundException e) {
                System.out.println("object cast error");
                usersMap = new HashMap<String, Person>();
            }
            oin.close();
            fin.close();
        } catch (Exception e) {
        	usersMap = new HashMap<String, Person>();
        }
    }
    
    //将用户表保存到本地文件中
    private void saveData() {
        try {
            FileOutputStream fout = new FileOutputStream("person.file");
            ObjectOutputStream oout = new ObjectOutputStream(fout);
            oout.writeObject(usersMap);
            oout.flush();
            fout.flush();
            oout.close();
            fout.close();
        } catch (Exception e) {
            System.out.println("save error.");
            e.printStackTrace();
        }
    }
    
    /*
    public void setORB(ORB orb) {
        this.orb = orb;
    }
    */
    
    public void setStudentInfoServer(StudentInfoServer studentInfoServer) {
        this.studentInfoServer = studentInfoServer;
    }
    
    public boolean login(String name, String password) {
        Person p = usersMap.get(name);
        if (p != null && p.getPassword().equals(password)) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean register(String name, String password) {
    	Person p = usersMap.get(name);
        if (p != null) {   //表中用户名为name的已存在
            return false;
        } else {
        	usersMap.put(name, new Person(name, password));
            saveData();
            return true;
        }
    }
}
