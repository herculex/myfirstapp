package com.sumugu.liubo.myfirstapp.mytouch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by liubo on 16/1/5.
 */
public class MyTextView extends EditText{
    final static String TAG="sumugu_MyTextView";
    GestureDetector mDetector;
    public MyTextView(Context context) {
        super(context);

        mDetector=new GestureDetector(context,new MyGesture());

    }
    public MyTextView(Context context,AttributeSet attrs)
    {
        super(context,attrs);

        mDetector=new GestureDetector(context,new MyGesture());
    }
    public MyTextView(Context context,AttributeSet attributeSet,int defStyleAttr)
    {
        super(context,attributeSet,defStyleAttr);
        mDetector=new GestureDetector(context,new MyGesture());

    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        Log.d(TAG,"write down your keycode:"+keyCode);
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            Log.d(TAG, "you press the backkey");
            if(isFocused())
                clearFocus();

        }
        return super.onKeyPreIme(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.d(TAG,"MyTextView,onTouchEvent");
        return super.onTouchEvent(event);

//        return mDetector.onTouchEvent(event);

//        switch (event.getActionMasked())
//        {
//            case MotionEvent.ACTION_DOWN:
//                Log.d(TAG,getText()+"____TextView__DOWN");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.d(TAG,getText()+"____TextView__MOVE");
////                getParent().requestDisallowInterceptTouchEvent(true);
//                break;
//            case MotionEvent.ACTION_CANCEL:
//                Log.d(TAG,getText()+"____TextView__CANCEL");
//                break;
//            case MotionEvent.ACTION_UP:
//                Log.d(TAG,getText()+"____TextView__UP");
//                break;
//            default:
//                return false;
//
//        }
//        return true;
    }


    class MyGesture extends GestureDetector.SimpleOnGestureListener{
        final static String TAG="sumugu_TV_Gesture";
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            float slop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
            if(Math.abs(distanceX)>0)
            {
                Log.d(TAG,getText()+" start to move left or right!");
                //requestDisallowInterceptTouchEvent
                getParent().requestDisallowInterceptTouchEvent(true);

                return true;
            }
            else
            {
                Log.d(TAG,getText()+" ____NOTING__TO__MOVE");
                return false;
            }
        }

        @Override
        public boolean onDown(MotionEvent e) {
            Log.d(TAG,getText()+"__onDown.");
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return true;
        }
    }
}
