package com.wherego.driver.activities;

/**
 * Created by Amit Kumar on 7/01/2020.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.wherego.driver.R;
import com.wherego.driver.model.banklist.Datum;
import com.wherego.driver.utills.MyTextView;

import java.util.ArrayList;


public class PaymentListAdapter extends ArrayAdapter<Datum> {

    public ArrayList<Datum> list;
    int vg;
    Context context;

    public PaymentListAdapter(Context context, int vg, ArrayList<Datum> list) {
        super(context, vg, list);
        this.context = context;
        this.vg = vg;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(vg, parent, false);
        ImageView paymentTypeImg = itemView.findViewById(R.id.paymentTypeImg);
        RadioButton radioButton = itemView.findViewById(R.id.radioButton);
        MyTextView accountNumber = itemView.findViewById(R.id.accountNumber);
        MyTextView accountName = itemView.findViewById(R.id.accountName);
        accountNumber.setText(list.get(position).getAccountNumber() + "");
        accountName.setText(list.get(position).getBankName());

        return itemView;
    }

}



