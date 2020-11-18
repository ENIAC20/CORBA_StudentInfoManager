package server;

import CreatorModule.Creator;
import CreatorModule.CreatorHelper;
import CreatorModule.CreatorImpl;
import UserModule.User;
import UserModule.UserHelper;
import UserModule.UserImpl;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import java.util.Properties;

//���ڷ��������,��д����������
public class StudentInfoServer {
	private ORB orb;
    private POA rootPOA;
    private org.omg.CORBA.Object obj;
    private CreatorImpl creatorImpl;
    private UserImpl userImpl;
    private org.omg.CORBA.Object ref;
    private Creator creatorhref;
    private User userhref;
    private org.omg.CORBA.Object objRef;
    private NamingContextExt ncRef;
    
    public static void main(String[] args) {
    	StudentInfoServer studentInfoServer = new StudentInfoServer();
    	studentInfoServer.init();
    }
    
    //��ʼ��,ע��Creator��User��������
    private void init() {
        try {
            String[] args = {};
            Properties properties = new Properties();

            properties.put("org.omg.CORBA.ORBInitialHost", "127.0.0.1");  //ָ��ORB��ip��ַ
            properties.put("org.omg.CORBA.ORBInitialPort", "1050");       //ָ��ORB�Ķ˿�

            //����һ��ORBʵ��
            orb = ORB.init(args, properties);  //��ʼ��orb

            //�õ�RootPOA������,������POAManager,�൱��������server
            obj = orb.resolve_initial_references("RootPOA");
            rootPOA = POAHelper.narrow(obj);
            rootPOA.the_POAManager().activate();

            //����һ��CreatorImplʵ��
            creatorImpl = new CreatorImpl();
            creatorImpl.setStudentInfoServer(this);
            
            //�ӷ����еõ����������,��ע�ᵽ������
            ref = rootPOA.servant_to_reference(creatorImpl);
            creatorhref = CreatorHelper.narrow(ref);
            
            //�õ�һ����������������
            objRef = orb.resolve_initial_references("NameService");
            ncRef = NamingContextExtHelper.narrow(objRef);

            //�������������а��������
            String name = "Creator";
            NameComponent path[] = ncRef.to_name(name);
            ncRef.rebind(path, creatorhref);
            
            //����һ��UserImplʵ��
            userImpl = new UserImpl();
            userImpl.setORB(orb);
            
            //�ӷ����еõ����������,��ע�ᵽ������
            ref = rootPOA.servant_to_reference(userImpl);
            userhref = UserHelper.narrow(ref);
            
            //�������������а��������
            String name1 = "User";
            NameComponent path1[] = ncRef.to_name(name1);
            ncRef.rebind(path1, userhref);
            
            System.out.println("server.StudentInfoServer is ready and waiting....");

            //�����̷߳���,�ȴ��ͻ��˵���
            orb.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}