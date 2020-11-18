package UserModule;


/**
* UserModule/UserOperations.java .
* 由IDL-to-Java 编译器 (可移植), 版本 "3.2"生成
* 从user.idl
* 2020年10月15日 星期四 下午08时28分08秒 CST
*/

public interface UserOperations 
{
  boolean add (String studentName, String studentNum, String studentAge, String studentSex, String department);
  String query (String studentNum);
  boolean delete (String studentNum);
  String show ();
} // interface UserOperations
