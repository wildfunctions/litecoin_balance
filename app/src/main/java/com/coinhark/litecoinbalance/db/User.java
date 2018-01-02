package com.coinhark.litecoinbalance.db;

import android.text.Html;
import android.text.Spanned;

public class User {
	
	  private long id;
	  private int currency;

	  public long getId() {
		  return id;
	  }

	  public void setId(long id) {
		  this.id = id;
	  }

	  public int getCurrency() {
		  return currency;
	  }

	  public void setCurrency(int currency) {
		  this.currency = currency;
	  }
	  
	  // Will be used by the ArrayAdapter in the ListView
	  @Override
	  public String toString() {
		Spanned format = Html.fromHtml("<br/>" + id + "<br/>" + currency + "<br/>");
	    return format.toString();
	  }
} 