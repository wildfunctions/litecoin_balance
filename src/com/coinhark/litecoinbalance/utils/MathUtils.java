package com.coinhark.litecoinbalance.utils;


public class MathUtils {

	public static boolean isNumeric(String str) {  
		try {  
			Double.parseDouble(str);  
		} catch(NumberFormatException nfe) {  
			return false;  
		}  
		return true;  
	}
	
	public static double trimFiat(double value) {
		return (double) Math.round(value * 100) / 100;
	}
	
	public static double trimCrypto(double value) {
		//0.00000001 smallest unit for LTC
		return (double) Math.round(value * 100000000) / 100000000;
	}
	
}
