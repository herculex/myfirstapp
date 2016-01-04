package com.sumugu.liubo.myfirstapp.mytouch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.OverScroller;
import android.widget.RelativeLayout;

import com.sumugu.liubo.myfirstapp.Cheeses;
import com.sumugu.liubo.myfirstapp.R;
import com.sumugu.liubo.myfirstapp.pulllistview.CheeseAdapter;

import java.util.ArrayList;

public class MyTouch extends AppCompatActivity implements GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener {

    final static String TAG="sumugu_Gestures";
    GestureDetector mGestureDetector;
    OverScroller mOverScroller;
    VelocityTracker mVelocityTracker;

    RelativeLayout container_main;
    ArrayList<String> cheeseList;
    ListView mListView;
    ArrayAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_touch);

        //int ListView
        //int datas
        cheeseList = new ArrayList<String>();
        for (int i = 0; i < Cheeses.sCheeseStrings.length; ++i) {
            cheeseList.add(Cheeses.sCheeseStrings[i]);
        }

        //set listview adapter
        mListView = (ListView)findViewById(R.id.list_view);
        mAdapter = new ArrayAdapter(this,R.layout.list_item,R.id.list_tv,cheeseList);
        mListView.setAdapter(mAdapter);
        //
        //find container
        container_main = (RelativeLayout)findViewById(R.id.container_main);

//        mGestureDetector = new GestureDetector(this,this);
        mGestureDetector = new GestureDetector(this,new MyTouchGestureListener());
//        mGestureDetector.setOnDoubleTapListener(this);
//        mGestureDetector.setIsLongpressEnabled(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);

        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(mListView.getFirstVisiblePosition()== mListView.getChildAt(0).getTop()) {
            Log.d(TAG,"firstVisible ACT");
            onTouchEvent(ev);
            return true;
        }
        else
            return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Log.d(TAG,"onSingleTapConfirmed:"+e.toString());
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d(TAG,"onDoubleTap:"+e.toString());
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Log.d(TAG,"onDoubleTapEvent:"+e.toString());
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.d(TAG,"onDonw:"+e.toString());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.d(TAG,"onShowPress"+e.toString());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d(TAG,"onSingleTapUp"+e.toString());
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d(TAG,"onScroll:"+e1.toString()+e2.toString()+":dis-x="+String.valueOf(distanceX)+"dis-y="+String.valueOf(distanceY));
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.d(TAG,"onLongPress:"+e.toString());

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d(TAG,"onFling:"+e1.toString()+e2.toString()+":"+String.valueOf(velocityX)+":"+String.valueOf(velocityY));
        return true;
    }

    class MyTouchGestureListener extends GestureDetector.SimpleOnGestureListener
    {
        final static String TAG="sumugu_MyTouchGesture";

        @Override
        public boolean onDown(MotionEvent e) {
            Log.d(TAG,"onDonw:"+e.toString());
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d(TAG, "onScroll:y=" + String.valueOf(distanceY) + ":x=" + String.valueOf(distanceX));
            if(distanceY<0)
            {
                if(mListView.getFirstVisiblePosition()==mListView.getChildAt(0).getTop())
                {
                    mListView.setTranslationY(mListView.getTranslationY()-distanceY);


                }
            }
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d(TAG,"onFling:"+e1.toString()+e2.toString()+":"+String.valueOf(velocityX)+":"+String.valueOf(velocityY));
            return true;
        }
    }
}