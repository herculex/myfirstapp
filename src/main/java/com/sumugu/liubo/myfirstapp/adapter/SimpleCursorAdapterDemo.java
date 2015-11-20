package com.sumugu.liubo.myfirstapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.swipe.adapters.SimpleCursorSwipeAdapter;
import com.sumugu.liubo.myfirstapp.R;

/**
 * Created by liubo on 15/10/16.
 */
public class SimpleCursorAdapterDemo extends SimpleCursorSwipeAdapter {
    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_sample1;
    }

    @Override
    public void closeAllItems() {

    }


    public SimpleCursorAdapterDemo(Context context, int layout, Cursor cursor, String[] fromColumns, int[] toViews, int flag)
    {
        super(context,layout,cursor,fromColumns,toViews,flag);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return super.newView(context, cursor, parent);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
    }
    //
//    SimpleCursorSwipeAdapter simpleCursorSwipeAdapter = new SimpleCursorSwipeAdapter(this, R.id.action0,null,null,null) {
//        @Override
//        public int getSwipeLayoutResourceId(int i) {
//            return 0;
//        }
//
//        @Override
//        public void closeAllItems() {
//
//        }
//    }
}
