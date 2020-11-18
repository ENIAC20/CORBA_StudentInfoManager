package someClasses;

import java.io.Serializable;

//”√ªß¿‡
public class Person implements Serializable{
	private String name;
    private String password;
    
    public Person(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    
    public String getName(){
    	return name;
    }
}
