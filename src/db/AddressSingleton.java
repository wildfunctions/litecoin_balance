package db;

import java.util.List;

public class AddressSingleton {
	
    private static volatile AddressSingleton instance = null;
    
	public List<Address> addressList = null;
    
    private AddressSingleton() {
    	
    }

    public static AddressSingleton getInstance() {
            if (instance == null) {
                    synchronized (AddressSingleton.class){
                            if (instance == null) {
                                    instance = new AddressSingleton();
                            }
                    }
            }
            return instance;
    }
    
}
