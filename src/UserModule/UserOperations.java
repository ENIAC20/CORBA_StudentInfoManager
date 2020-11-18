package UserModule;


/**
* UserModule/UserOperations.java .
* ��IDL-to-Java ������ (����ֲ), �汾 "3.2"����
* ��user.idl
* 2020��10��15�� ������ ����08ʱ28��08�� CST
*/

public interface UserOperations 
{
  boolean add (String studentName, String studentNum, String studentAge, String studentSex, String department);
  String query (String studentNum);
  boolean delete (String studentNum);
  String show ();
} // interface UserOperations
