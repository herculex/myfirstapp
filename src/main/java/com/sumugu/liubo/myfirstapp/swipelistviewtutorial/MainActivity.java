package com.sumugu.liubo.myfirstapp.swipelistviewtutorial;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sumugu.liubo.myfirstapp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    ArrayList arrayList;

    //swiping
    boolean mSwiping = false;   //detects if user is swiping on ACION_UP
    boolean mItemPressed = false;   //detects if user is currently holding down a view
    final int SWIPE_DURATION=250;  //needed for velocity(加速) implementation
    final int MOVE_DURATION=150;

    HashMap<Long,Integer> mItemIdTopMap = new HashMap<Long,Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //My codes
        arrayList = new ArrayList();
        arrayList.add("red");
        arrayList.add("green");
        arrayList.add("blue");
        arrayList.add("purple");
        arrayList.add("pink");
        arrayList.add("indigo");
        arrayList.add("brown");
        arrayList.add("black");
        arrayList.add("beige");
        arrayList.add("orange");
        arrayList.add("yellow");
        arrayList.add("blue");
        arrayList.add("magenta");
        arrayList.add("teal");
        arrayList.add("white");
        arrayList.add("pink");
        lv = (ListView)findViewById(R.id.list_view);
        StringAdapter adapter = new StringAdapter(MainActivity.this,arrayList,mTouchListener);
        lv.setAdapter(adapter);
    }

    View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        float mDownX;
        int mSwipeSlop=-1; //slop （从某物中）溅出 swipe
        boolean swiped;

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            final View v = view;
            if(mSwipeSlop<0)
            {
                mSwipeSlop = ViewConfiguration.get(MainActivity.this).getScaledTouchSlop(); //默认滑动的最小标准＝24dp
                Log.d("onTOUCH_sumugu:","mSwipeSlop:"+String.valueOf(mSwipeSlop));
            }
            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    if(mItemPressed)
                    {
                        return false;
                        //Do not allow swiping two items at same time
                    }
                    mItemPressed = true;
                    mDownX = event.getX();
                    swiped = false;
                    Log.d("DOWN_sumug:mDwonX=",String.valueOf(mDownX));
                    break;
                case MotionEvent.ACTION_CANCEL:
                    v.setTranslationX(0);
                    mItemPressed=false;
                    Log.d("CANCEL_sumugu:mDownX=",String.valueOf(mDownX));
                    break;
                case MotionEvent.ACTION_MOVE:
                {
                    float x = event.getX() + v.getTranslationX();

                    Log.d("MOVE_sumugu:","x="+String.valueOf(x)+":event.getX="+String.valueOf(event.getX())+";v.translationX="+String.valueOf(v.getTranslationX()));
                    float deltaX = x-mDownX;
                    float deltaAbs = Math.abs(deltaX);  //求绝对值
                    Log.d("MOVE_sumug:deltaX=",String.valueOf(deltaX));

                    if(!mSwiping)
                    {
                        if(deltaAbs>mSwipeSlop) //只有swipe超过mSwipeSlop才会认为是滑动
                        {
                            mSwiping = true;
                            lv.requestDisallowInterceptTouchEvent(true);
                            //tells if user is actually swiping or just touching in sloppy mannner
                        }
                    }
                    if(mSwiping && !swiped)
                    {
                        //need to make sure the user is both swiping and has not already completed a swipe action
                        v.setTranslationX(x-mDownX);    //moves the view as long as the user is swiping and has not already to swiped

                        if(deltaX>v.getWidth()/3)   //swipe to right action over 1/3 v's width
                        {
//                            mDownX=x; //其实没用
                            swiped=true;
                            mSwiping=false;
                            mItemPressed=false;

                            v.animate().setDuration(300).translationX(v.getWidth() / 3);//could pause here if you wan t,same way as delete
                            TextView tv = (TextView)v.findViewById(R.id.list_tv);
                            tv.setText("Swiped");
                            return true;
                        }
                        else if(deltaX<-1*(v.getWidth()/3)) //swipe to left action 1/3 v's width
                        {
                            v.setEnabled(false);//need to disable the view for the animation to run

                            //stacked the animations to have the pause before the views flings off screen
                            v.animate().setDuration(300).translationX(-v.getWidth()/3).withEndAction(new Runnable() {
                                @Override
                                public void run() {
                                    v.animate().setDuration(300).alpha(0).translationX(-v.getWidth()).withEndAction(new Runnable() {
                                        @Override
                                        public void run() {
                                            //removes the artist from featuredperfs and relaods mainListView
                                            mSwiping=false;
                                            mItemPressed=false;
                                            animateRemoval(lv,v);
                                        }
                                    });
                                }
                            });
//                            mDownX=x; //其实没用
                            swiped=true;
                            return true;
                        }
                    }

                }
                break;
                case MotionEvent.ACTION_UP:
                {
                    if(mSwiping)//if the user was swiping ,do not go to the  and just animate the view back to position
                    {
                        v.animate().setDuration(300).translationX(0).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                mSwiping=false;
                                mItemPressed=false;
                                lv.setEnabled(true);    //??
                            }
                        });
                    }
                    else    //user was not swiping;registers as a click
                    {
                        mItemPressed=false;
                        lv.setEnabled(true);    //??

                        int i=lv.getPositionForView(v);

                        Toast.makeText(MainActivity.this, arrayList.get(i).toString(), Toast.LENGTH_SHORT).show();

                        return false;
                    }
                }
                default:
                    return false;
            }

            return true;
        }
    };
    private void animateRemoval(final ListView listView,View viewToRemove)
    {
        final int firstVisiblePosition = listView.getFirstVisiblePosition();
        final ArrayAdapter adapter = (ArrayAdapter)lv.getAdapter();
        for(int i=0;i<listView.getChildCount();++i)
        {
            View child = listView.getChildAt(i);
            if(child!=viewToRemove){
                int position = firstVisiblePosition+i;
                long itemId = listView.getAdapter().getItemId(position);
                mItemIdTopMap.put(itemId,child.getTop());//??
            }
        }

        adapter.remove(adapter.getItem(listView.getPositionForView(viewToRemove)));

        final ViewTreeObserver observer= listView.getViewTreeObserver();

        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                observer.removeOnPreDrawListener(this);
                boolean firstAnimation = true;

                int firstVisiblePostion = listView.getFirstVisiblePosition();

                for(int i=0;i<listView.getChildCount();++i)
                {
                    final View child = listView.getChildAt(i);
                    int position = firstVisiblePosition+i;
                    long itemId = adapter.getItemId(position);
                    Integer startTop=mItemIdTopMap.get(itemId); //??
                    int top = child.getTop();
                    if(startTop!=null)
                    {
                        if(startTop !=top)
                        {
                            int delta = startTop-top;
                            child.setTranslationY(delta);
                            child.animate().setDuration(MOVE_DURATION).translationY(0);
                            if(firstAnimation){
                                child.animate().withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        mSwiping=false;
                                        lv.setEnabled(true);
                                    }
                                });
                                firstAnimation=false;
                            }
                        }
                    }
                    else
                    {
                        //animate new views along with the others.the catch is that they did not
                        //exist in the start state.so we must calculate their starting position
                        //based on neighboring views
                        int childHeight = child.getHeight()+listView.getDividerHeight();    //divider =隔板
                        startTop = top+(i>0?childHeight:-childHeight);
                        int delta = startTop-top;
                        child.setTranslationY(delta);
                        child.animate().setDuration(MOVE_DURATION).translationY(0);
                        if(firstAnimation){
                            child.animate().withEndAction(new Runnable() {
                                @Override
                                public void run() {
                                    mSwiping=false;
                                    lv.setEnabled(true);
                                }
                            });
                            firstAnimation=false;
                        }
                    }
                }
                mItemIdTopMap.clear();
                return true;
            }
        });
    }
}
