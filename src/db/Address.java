package db;

import android.text.Html;
import android.text.Spanned;

public class Address {
	
	  private long id;
	  private String name;
	  private String address;

	  public long getId() {
		  return id;
	  }

	  public void setId(long id) {
		  this.id = id;
	  }

	  public String getName() {
		  return name;
	  }

	  public void setName(String name) {
		  this.name = name;
	  }

	  public String getAddress() {
		  return address;
	  }

	  public void setAddress(String address) {
		  this.address = address;
	  }
	  
	  // Will be used by the ArrayAdapter in the ListView
	  @Override
	  public String toString() {
		Spanned format = Html.fromHtml("<br/>" + address + "<br/>" + name + "<br/>");
	    return format.toString();
	  }
} 