package com.coinhark.litecoinbalance.api;

public enum CurrencyEnum {
	
	USD(0),
	EUR(1),
	BTC(2);
	
	public int value;
	
	CurrencyEnum(int value) {
		this.value = value;
	}
	
	public static String getPostParam(int value) {
		switch(value) {
		case 0:
			return "LTC/USD";
		case 1:
			return "LTC/EUR";
		case 2:
			return "LTC/BTC";
		}
		return "USD/LTC";
	}
	
	public static String getString(int value) {
		switch(value) {
		case 0:
			return "USD";
		case 1:
			return "EUR";
		case 2:
			return "BTC";
		}
		return "USD";
	}	
	
}
