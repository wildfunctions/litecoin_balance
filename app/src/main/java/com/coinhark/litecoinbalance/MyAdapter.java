package com.coinhark.litecoinbalance;

import java.util.List;

import com.coinhark.litecoinbalance.api.CurrencyEnum;
import com.coinhark.litecoinbalance.db.Address;
import com.coinhark.litecoinbalance.db.UserSingleton;
import com.coinhark.litecoinbalance.utils.MathUtils;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyAdapter extends ArrayAdapter<Address> {

    Context context; 
    int layoutResourceId;    

    public MyAdapter(Context context, int layoutResourceId, List<Address> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        StringHolder holder = null;

        if(row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new StringHolder();
            holder.name = (TextView) row.findViewById(R.id.list_name);
            holder.address = (TextView) row.findViewById(R.id.list_address);

            row.setTag(holder);
        } else {
            holder = (StringHolder) row.getTag();
        }

        UserSingleton singelton = UserSingleton.getInstance();
        Address addressItem = getItem(position);
        Spanned address = Html.fromHtml(addressItem.getAddress());
        Spanned name = Html.fromHtml("<font color='grey'>" +  addressItem.getName() + " : " + 
        		MathUtils.trimCrypto(addressItem.getBalance()) + " LTC / " + MathUtils.trimFiat(addressItem.getBalance() * singelton.rate) + " " + 
        		CurrencyEnum.getString(singelton.currency) + "</font>");
        holder.address.setText(address);
        holder.name.setText(name);

        return row;
    }

    static class StringHolder {
        TextView address;
        TextView name;
    }
    
}