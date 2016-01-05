package com.sumugu.liubo.myfirstapp.mytouch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ListView;

/**
 * Created by liubo on 16/1/5.
 */
public class MyListView extends ListView {
    final static String TAG = "sumugu_MyListView";
    GestureDetector mDetector;

    public MyListView(Context context) {
        super(context);
        mDetector=new GestureDetector(context,new MyGesture());
    }

    public MyListView(Context context,AttributeSet attributeSet)
    {
        super(context,attributeSet);
        mDetector=new GestureDetector(context,new MyGesture());
    }
    public MyListView(Context context,AttributeSet attributeSet,int defAttri)
    {
        super(context,attributeSet,defAttri);
        mDetector=new GestureDetector(context,new MyGesture());
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        Log.d(TAG,"LV_Super_OnTouchEvent");

        if(getChildAt(0).getTop()==getFirstVisiblePosition())
            return false;

        return super.onTouchEvent(ev);

//        switch (ev.getActionMasked())
//        {
//            case MotionEvent.ACTION_DOWN:
//                Log.d(TAG,"LV____TextView__DOWN");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.d(TAG,"LV____TextView__MOVE");
//                return super.onTouchEvent(ev);
////                break;
//            case MotionEvent.ACTION_CANCEL:
//                Log.d(TAG,"LV____TextView__CANCEL");
//                break;
//            case MotionEvent.ACTION_UP:
//                Log.d(TAG,"LV____TextView__UP");
//                break;
//            default:
//                return false;
//        }
//        return true;
    }

    class MyGesture extends GestureDetector.SimpleOnGestureListener
    {
        final static String TAG="sumugu_LV_Gesture";

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {


            final  float slop= ViewConfiguration.get(getContext()).getScaledTouchSlop();
            Log.d(TAG,"MyTextView,onScroll:slop="+String.valueOf(slop));

            if(Math.abs(distanceY)>0)
            {
                if(distanceY>0)
                {
                    //pull down
                    if(getChildAt(0).getTop()==getFirstVisiblePosition()) {
                        Log.d(TAG,"Let Activity to Move the MyListView");
                        return false;
                    }
                }

            }
            return true;
        }

    }
}
