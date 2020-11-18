package UserModule;


/**
* UserModule/UserPOA.java .
* ��IDL-to-Java ������ (����ֲ), �汾 "3.2"����
* ��user.idl
* 2020��10��15�� ������ ����08ʱ28��08�� CST
*/

public abstract class UserPOA extends org.omg.PortableServer.Servant
 implements UserModule.UserOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("add", new java.lang.Integer (0));
    _methods.put ("query", new java.lang.Integer (1));
    _methods.put ("delete", new java.lang.Integer (2));
    _methods.put ("show", new java.lang.Integer (3));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // UserModule/User/add
       {
         String studentName = in.read_string ();
         String studentNum = in.read_string ();
         String studentAge = in.read_string ();
         String studentSex = in.read_string ();
         String department = in.read_string ();
         boolean $result = false;
         $result = this.add (studentName, studentNum, studentAge, studentSex, department);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 1:  // UserModule/User/query
       {
         String studentNum = in.read_string ();
         String $result = null;
         $result = this.query (studentNum);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 2:  // UserModule/User/delete
       {
         String studentNum = in.read_string ();
         boolean $result = false;
         $result = this.delete (studentNum);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 3:  // UserModule/User/show
       {
         String $result = null;
         $result = this.show ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:UserModule/User:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public User _this() 
  {
    return UserHelper.narrow(
    super._this_object());
  }

  public User _this(org.omg.CORBA.ORB orb) 
  {
    return UserHelper.narrow(
    super._this_object(orb));
  }


} // class UserPOA
