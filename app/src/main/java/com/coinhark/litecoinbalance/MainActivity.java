package com.coinhark.litecoinbalance;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import com.coinhark.litecoinbalance.db.AddressDBHandler;
import com.coinhark.litecoinbalance.db.Notice;
import com.coinhark.litecoinbalance.db.NoticeDBHandler;
import com.coinhark.litecoinbalance.db.User;
import com.coinhark.litecoinbalance.db.UserDBHandler;
import com.coinhark.litecoinbalance.db.UserSingleton;

public class MainActivity extends Activity {
	
    private ProgressBar mProgress;
    private boolean displayNotice = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progress);
		UserSingleton singleton = UserSingleton.getInstance();

        NoticeDBHandler noticeDBHandler = new NoticeDBHandler(this);
        noticeDBHandler.open();

        //noticeDBHandler.deleteAll();
        //System.exit(0);

        List<Notice> notices = noticeDBHandler.getAllNotices();
        if(notices.size() == 0) {
            displayNotice = true;
            noticeDBHandler.createNotice(1);
            noticeDBHandler.close();
            //Log.w("[Litecoin Balance]", notices.size() + " creating notice for first time");
            //System.out.println("[Litecoin Balance] " + notices.size() + " creating notice for first time");
        } else if(notices.size() == 1) {
            Notice singleNotice = notices.get(0);
            if(singleNotice.getNoticeDisplayed() == 0) {
                displayNotice = true;
                noticeDBHandler.deleteNoticeById(singleNotice.getId());
                noticeDBHandler.createNotice(1);
                //Log.w("[Litecoin Balance]", notices.size() + " show notice");
                //System.out.println("[Litecoin Balance] " + notices.size() + " show notice");
            } else {
                displayNotice = false;
                //Log.w("[Litecoin Balance]", notices.size() + " user has already seen notice");
                //System.out.println("[Litecoin Balance] " + notices.size() + " user has already seen notice");
            }
            noticeDBHandler.close();
        } else {
            displayNotice = true;
            noticeDBHandler.deleteAll();
            noticeDBHandler.createNotice(1);
            //Log.w("[Litecoin Balance]", notices.size() + " <-- more than one notice entry is bad!");
            //System.out.println("[Litecoin Balance] " + notices.size() + " <-- more than one notice entry is bad!");
            noticeDBHandler.close();
        }

		UserDBHandler userDB = new UserDBHandler(this);
		userDB.open();
		List<User> users = userDB.getAllUsers();
		userDB.close();
		if(users.size() > 0) {
			singleton.currency = 0;
			//Log.w("[Litecoin Balance]", users.size() + " Get User Info!");
		}
		
		mProgress = (ProgressBar) findViewById(R.id.progress_bar);
		
		AddressDBHandler db = new AddressDBHandler(this);
	    db.open();
		singleton.addressList = db.getAllAddresses();
		db.close();

		if(singleton.addressList.size() > 0) {
			new BalanceAsyncTask(this, mProgress).execute("");
		} else {
			setContentView(R.layout.main);
		}

		if(this.displayNotice) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    showPopup(MainActivity.this);
                }
            }, 200);
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_addresses:
				getAddressesView();
				break;
			case R.id.menu_donate:
				getDonateView();
				break;
			case R.id.menu_refresh:
				getMainView();
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void getAddressesView() {
		Intent nextScreen = new Intent(getApplicationContext(), AddressesActivity.class);
		startActivity(nextScreen);
	}
	
	public void getDonateView() {
		Intent nextScreen = new Intent(getApplicationContext(), DonateActivity.class);
		startActivity(nextScreen);
	}
	
	public void getMainView() {
		Intent nextScreen = new Intent(getApplicationContext(), MainActivity.class);
		startActivity(nextScreen);
	}

	// The method that displays the popup.
	public void showPopup(final Activity context) {
	    int widthOffset = 100;
	    int heightOffset = 300;
        int width = getWindowManager().getDefaultDisplay().getWidth();
        int height = getWindowManager().getDefaultDisplay().getHeight();

        Point p = new Point(widthOffset/2, heightOffset/2);
		int popupWidth = width - widthOffset;
		int popupHeight = height - heightOffset;

		// Inflate the notice.xml
		LinearLayout viewGroup = (LinearLayout) context.findViewById(R.id.notice);
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = layoutInflater.inflate(R.layout.notice, viewGroup);

		// Creating the PopupWindow
		final PopupWindow popup = new PopupWindow(context);
		popup.setContentView(layout);
		popup.setWidth(popupWidth);
		popup.setHeight(popupHeight);
		popup.setFocusable(true);
        popup.setTouchable(true);


		// Clear the default translucent background
		popup.setBackgroundDrawable(new BitmapDrawable());

		// Displaying the popup at the specified location, + offsets.
		popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x, p.y);

		// Getting a reference to Close button, and close the popup when clicked.
		Button close = (Button) layout.findViewById(R.id.close);
		close.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				popup.dismiss();
			}
		});
	}

}
