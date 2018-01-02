package com.coinhark.litecoinbalance.db;

import android.text.Html;
import android.text.Spanned;

/**
 * Created by tyrick on 1/1/18.
 */

public class Notice {
    private long id;
    private int noticedDisplayed;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNoticeDisplayed() {
        return noticedDisplayed;
    }

    public void setNoticeDisplayed(int noticedDisplayed) {
        this.noticedDisplayed = noticedDisplayed;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        Spanned format = Html.fromHtml("<br/>" + id + "<br/>" + noticedDisplayed + "<br/>");
        return format.toString();
    }
}
