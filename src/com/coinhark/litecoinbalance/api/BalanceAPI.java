package com.coinhark.litecoinbalance.api;

import java.util.Random;

public class BalanceAPI {

	private static String[] sentUrls = {
		//"http://litecoinscout.com/chain/Litecoin/q/getsentbyaddress/"
		"http://explorer.litecoin.net/chain/Litecoin/q/getsentbyaddress/"
	};
	
	private static String[] receivedUrls = {
		//"http://litecoinscout.com/chain/Litecoin/q/getreceivedbyaddress/"
		"http://explorer.litecoin.net/chain/Litecoin/q/getreceivedbyaddress/"
	};
	
	private static String[] rateUrls = {
		"https://api.kraken.com/0/public/Ticker?pair=",
		"https://vircurex.com/api/get_last_trade.json?base=LTC&alt="
		//https://api.kraken.com/0/public/Ticker?pair=LTCUSD
		//https://api.kraken.com/0/public/Ticker?pair=LTCEUR
		//https://api.kraken.com/0/public/Ticker?pair=LTCKRW
		//https://api.kraken.com/0/public/Ticker?pair=XBTLTC
	};

	public static String getSentUrl() {
		return sentUrls[getRandom(0, sentUrls.length - 1)];
	}
	
	public static String getReceivedUrl() {
		return receivedUrls[getRandom(0, receivedUrls.length - 1)];
	}	
	
	public static int getRandom(int min, int max) {
		return new Random().nextInt(max - min + 1) + min;
	}
	
	public static String getRateUrl(int i) {
		return rateUrls[i];
	}
	
	
	// See CurrencyEnum for correct pairing
	public static String getKrakenParam(int value) {
		switch(value) {
		case 0:
			return "LTCUSD";
		case 1:
			return "LTCEUR";
		case 2:
			return "XBTLTC";
		}
		return "LTCUSD";
	}
	
	public static String getKrakenReturnParam(int value) {
		switch(value) {
		case 0:
			return "XLTCZUSD";
		case 1:
			return "XLTCZEUR";
		case 2:
			return "XXBTXLTC";
		}
		return "XLTCZUSD";
	}
	
}
