package com.sumugu.liubo.myfirstapp.pulllistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by liubo on 15/12/10.
 */
public class CheeseAdapter extends ArrayAdapter<String> {

    View.OnTouchListener mTouchListener;

    public CheeseAdapter(Context context,int layout,int textViewResourceId,ArrayList source,View.OnTouchListener listener)
    {
        super(context, layout,textViewResourceId,source);
        mTouchListener = listener;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        if (view != convertView) {
            // Add touch listener to every new view to track swipe motion
            view.setOnTouchListener(mTouchListener);
        }
        return view;
    }
}