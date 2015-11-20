package com.sumugu.liubo.myfirstapp.swipelistviewtutorial;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sumugu.liubo.myfirstapp.R;

import java.util.ArrayList;

/**
 * Created by liubo on 15/11/20.
 */
public class StringAdapter extends ArrayAdapter<String> {

    View.OnTouchListener mTouchListener;
    public StringAdapter(Context context,ArrayList<String> values,View.OnTouchListener listener)
    {
        super(context, R.layout.list_item,values);
        mTouchListener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        View v = inflater.inflate(R.layout.list_item, parent, false);

        TextView b = (TextView)v.findViewById(R.id.list_tv);
        b.setText(getItem(position));

        v.setOnTouchListener(mTouchListener);

        return v;
    }
}
