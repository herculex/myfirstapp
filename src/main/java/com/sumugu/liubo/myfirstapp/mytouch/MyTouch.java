package com.sumugu.liubo.myfirstapp.mytouch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.OverScroller;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sumugu.liubo.myfirstapp.Cheeses;
import com.sumugu.liubo.myfirstapp.R;
import com.sumugu.liubo.myfirstapp.pulllistview.CheeseAdapter;

import java.util.ArrayList;

public class MyTouch extends AppCompatActivity implements GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener {

    final static String TAG="sumugu_Gestures";
    GestureDetector mGestureDetector;
    OverScroller mOverScroller;
    VelocityTracker mVelocityTracker;

    LinearLayout container_main;
    ArrayList<String> cheeseList;
    MyListView mListView;
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
        mListView = (MyListView)findViewById(R.id.list_view);
        mAdapter = new MyAdapter(this,R.layout.list_item,R.id.list_tv,cheeseList,mTouchListener);
        mListView.setAdapter(mAdapter);
        //
        //find container
        container_main = (LinearLayout)findViewById(R.id.container_main);

//        mGestureDetector = new GestureDetector(this,this);
        mGestureDetector = new GestureDetector(this,new MyTouchGestureListener());
//        mGestureDetector.setOnDoubleTapListener(this);
//        mGestureDetector.setIsLongpressEnabled(true);


    }
    private View.OnTouchListener mTouchListener = new View.OnTouchListener(){

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Log.d(TAG,"listITEM_________ontouching");
            return true;
        }
    };
    public class MyAdapter extends ArrayAdapter<String> {

        View.OnTouchListener mTouchListener;

        public MyAdapter(Context context,int layout,int textViewResourceId,ArrayList source,View.OnTouchListener listener)
        {
            super(context, layout,textViewResourceId,source);
//            mTouchListener = listener;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            if (view != convertView) {
                // Add touch listener to every new view to track swipe motion
//                view.setOnTouchListener(mTouchListener);

                final EditText editText = (EditText)view.findViewById(R.id.list_tv);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        Log.d(TAG,"press what "+String.valueOf(actionId));
                        if(EditorInfo.IME_ACTION_DONE == actionId)
                        {
                            Log.d(TAG, "press the action done!");
                            mListView.requestFocus();

                            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                                    .hideSoftInputFromWindow(editText.getWindowToken(), 0);

                            return true;
                        }

                            return false;
                    }
                });

            }
            return view;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG,"myTouch_onTouchEvent");

//        switch (event.getActionMasked())
//        {
//            case MotionEvent.ACTION_DOWN:
//                Log.d(TAG,"AT____TextView__DOWN");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.d(TAG,"AT____TextView__MOVE");
//                break;
//            case MotionEvent.ACTION_CANCEL:
//                Log.d(TAG,"AT____TextView__CANCEL");
//                break;
//            case MotionEvent.ACTION_UP:
//                Log.d(TAG,"AT____TextView__UP");
//                break;
//            default:
//                return false;
//        }
//        return true;
        return mGestureDetector.onTouchEvent(event);
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
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d(TAG, "Let ME Move the ListView ,onScroll:y=" + String.valueOf(distanceY) + ":x=" + String.valueOf(distanceX));

            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d(TAG,"Let ME fling the ListView!_________");
            return true;
        }
    }

    int count =1;
    public void addItemToList(View view) {
//        SkewableTextView skewableTextView = (SkewableTextView) findViewById(R.id.edit_new_message);
//        ImageView imageView = (ImageView) findViewById(R.id.image_new_message);

//        float[] src = new float[]{
//                0,0,
//                skewableTextView.getWidth(),0,
//                skewableTextView.getWidth(),skewableTextView.getHeight(),
//                0,skewableTextView.getHeight()
//
//        };
//        float[] dst = new float[]{
//                10,2,
//                skewableTextView.getWidth()-10,0,
//                skewableTextView.getWidth(),skewableTextView.getHeight(),
//                0,skewableTextView.getHeight()
//        };
//        Matrix matrix = skewableTextView.getMatrix();
//        matrix.setPolyToPoly(src,0,dst,0,src.length>>1);


//
        //根据一个view的尺寸创建bitmap
//        Bitmap bp = Bitmap.createBitmap(skewableTextView.getWidth(), skewableTextView.getHeight(), Bitmap.Config.RGB_565);
//        imageView.setImageBitmap(bp);
        //

//        if (count < 100) {
//
//            skewableTextView.setVisibility(View.VISIBLE);
//            skewableTextView.setSkewX(count);
//            count=count+5;
//        }
//        else
//        {
//            skewableTextView.setVisibility(View.GONE);
//            count=1;
//        }
        if(count<100)
        {
            SkewableTextView myView = (SkewableTextView)findViewById(R.id.myView);
            myView.setRotationX(count);
            count=count+10;
            Log.d(TAG,"top="+myView.getTop()+";bottom="+myView.getBottom());
        }
        else
        {
            count=0;
        }
    }
}
