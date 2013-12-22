package com.coinhark.litecoinbalance.db;

import java.util.List;

public class UserSingleton {
	
    private static volatile UserSingleton instance = null;
    
	public List<Address> addressList = null;
	
	public int currency = 0;
	
	public double rate = 0.00;
    
    private UserSingleton() {
    	
    }

    public static UserSingleton getInstance() {
            if (instance == null) {
                    synchronized (UserSingleton.class){
                            if (instance == null) {
                                    instance = new UserSingleton();
                            }
                    }
            }
            return instance;
    }
    
}
