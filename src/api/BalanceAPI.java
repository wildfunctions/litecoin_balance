package api;

import java.util.Random;

public class BalanceAPI {

	private static String[] sentUrls = {
		"http://litecoinscout.com/chain/Litecoin/q/getsentbyaddress/"
	};
	
	private static String[] receivedUrls = {
		"http://litecoinscout.com/chain/Litecoin/q/getreceivedbyaddress/"
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
	
}
