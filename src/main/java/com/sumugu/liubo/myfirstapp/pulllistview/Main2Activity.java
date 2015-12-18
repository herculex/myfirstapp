package com.sumugu.liubo.myfirstapp.pulllistview;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sumugu.liubo.myfirstapp.Cheeses;
import com.sumugu.liubo.myfirstapp.DisplayUtils;
import com.sumugu.liubo.myfirstapp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class Main2Activity extends AppCompatActivity {

    FrameLayout container;
    TextView mTextView;
    int mMaxDown=200;
    boolean editShowAll=false;
    ListView mListView;
    CheeseAdapter mAdapter;
    ArrayList<String> cheeseList;
    final int SWIPE_DURATION=250;  //needed for velocity(加速) implementation
    final int MOVE_DURATION=150;

    final String TAG=Main2Activity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //my codes start here
        //int views

        container = (FrameLayout)findViewById(R.id.main2_container);
        LayoutTransition transition = container.getLayoutTransition();
        transition.enableTransitionType(LayoutTransition.CHANGING);

        mTextView = (TextView)findViewById(R.id.edit_message);
        mTextView.setBackgroundColor(DisplayUtils.randomColor());
        mTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_DONE:
                        Toast.makeText(Main2Activity.this, "Done", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            }
        });

        //int datas
        cheeseList = new ArrayList<String>();
        for (int i = 0; i < Cheeses.sCheeseStrings.length; ++i) {
            cheeseList.add(Cheeses.sCheeseStrings[i]);
        }

        //set listview adapter
        mListView = (ListView)findViewById(R.id.list_view);
        mAdapter = new CheeseAdapter(this,R.layout.list_item,R.id.list_tv,cheeseList,mTouchListener);
        mListView.setAdapter(mAdapter);




        //touchlistener for listview adapter
        mListView.setOnTouchListener(new View.OnTouchListener() {

            float mDownY = 0;
            float deltaY2 = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:

                        mDownY = event.getY();
                        deltaY2 = 0;

                        if (v.getTranslationY() == mTextView.getHeight())
                            editShowAll = true;
                        else
                            editShowAll = false;


                        Log.d(TAG, "mDownY=" + String.valueOf(mDownY) + ";firstChildAt=" + String.valueOf(mListView.getFirstVisiblePosition()));
                        Log.d(TAG, "listTrans=" + String.valueOf(v.getTranslationY()));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        v.setTranslationY(0);
                        break;
                    case MotionEvent.ACTION_MOVE: {

                        float mEndY = event.getY() + v.getTranslationY();
                        float deltaY = mEndY - mDownY;
                        Log.d(TAG, "deltaY=" + String.valueOf(deltaY));

                        float deltaAbs = Math.abs(deltaY);
                        //

                        if (deltaY > 0 && (mListView.getFirstVisiblePosition() == mListView.getChildAt(0).getTop())) {
                            float newDetalY = deltaY - deltaY2;


                            if (v.getTranslationY() > mTextView.getHeight()) {
                                mTextView.setTranslationY(newDetalY - mTextView.getHeight());
                            } else {
                                float alpha = newDetalY / mTextView.getHeight();
                                mTextView.setAlpha(alpha >= 1 ? 1 : alpha);
                            }

                            v.setTranslationY(newDetalY);

                            Log.d(TAG, "newDeltaY=" + String.valueOf(newDetalY) + "listTop=" + String.valueOf(v.getTop()));
                        } else {
                            deltaY2 = deltaY;     //保存listview上到顶之前滑动的距离
                            return false;       //listview 不能filing了
                        }

                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        if (v.getTranslationY() >= mTextView.getHeight()) {
                            editShowAll = true;
                            mTextView.setTranslationY(0);
                            mTextView.setAlpha(1);
                            v.setTranslationY(mTextView.getHeight());
                        } else {
                            editShowAll = false;
                            mTextView.setAlpha(0);
                            mTextView.setTranslationY(0);
                            v.setTranslationY(0);
                        }
                        Log.d(TAG,"listView ACTIONUP !");
                        break;
                    }

                }
                return true;
            }
        });


        //
    }
    boolean mItemPressed=false;
    boolean mSwiping=false;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {

        float mDownX;
        int mSwipeSlop=-1; //slop （从某物中）溅出 swipe
        boolean swiped;

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            final View v = view;
            if(mSwipeSlop<0)
            {
                mSwipeSlop = ViewConfiguration.get(Main2Activity.this).getScaledTouchSlop(); //默认滑动的最小标准＝24dp
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
                            mListView.requestDisallowInterceptTouchEvent(true);
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
                                            animateRemoval(mListView,v);
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
                                mListView.setEnabled(true);    //??
                            }
                        });
                    }
                    else    //user was not swiping;registers as a click
                    {
                        mItemPressed=false;
                        mListView.setEnabled(true);    //??

                        int i= mListView.getPositionForView(v);

                        Toast.makeText(Main2Activity.this, cheeseList.get(i).toString(), Toast.LENGTH_SHORT).show();

                        return false;
                    }
                }
                default:
                    return false;
            }

            return true;
        }
    };
    HashMap<Long,Integer> mItemIdTopMap = new HashMap<Long,Integer>();
    private void animateRemoval(final ListView listview, View viewToRemove) {
        int firstVisiblePosition = listview.getFirstVisiblePosition();
        for (int i = 0; i < listview.getChildCount(); ++i) {
            View child = listview.getChildAt(i);
            if (child != viewToRemove) {
                int position = firstVisiblePosition + i;
                long itemId = mAdapter.getItemId(position);
                mItemIdTopMap.put(itemId, child.getTop());
            }
        }
        // Delete the item from the adapter
        int position = mListView.getPositionForView(viewToRemove);
        mAdapter.remove(mAdapter.getItem(position));

        final ViewTreeObserver observer = listview.getViewTreeObserver();
        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                observer.removeOnPreDrawListener(this);
                boolean firstAnimation = true;
                int firstVisiblePosition = listview.getFirstVisiblePosition();
                for (int i = 0; i < listview.getChildCount(); ++i) {
                    final View child = listview.getChildAt(i);
                    int position = firstVisiblePosition + i;
                    long itemId = mAdapter.getItemId(position);
                    Integer startTop = mItemIdTopMap.get(itemId);
                    int top = child.getTop();
                    if (startTop != null) {
                        if (startTop != top) {
                            int delta = startTop - top;
                            child.setTranslationY(delta);
                            child.animate().setDuration(MOVE_DURATION).translationY(0);
                            if (firstAnimation) {
                                child.animate().withEndAction(new Runnable() {
                                    public void run() {
                                        mSwiping = false;
                                        mListView.setEnabled(true);
                                    }
                                });
                                firstAnimation = false;
                            }
                        }
                    } else {
                        // Animate new views along with the others. The catch is that they did not
                        // exist in the start state, so we must calculate their starting position
                        // based on neighboring views.
                        int childHeight = child.getHeight() + listview.getDividerHeight();
                        startTop = top + (i > 0 ? childHeight : -childHeight);
                        int delta = startTop - top;
                        child.setTranslationY(delta);
                        child.animate().setDuration(MOVE_DURATION).translationY(0);
                        if (firstAnimation) {
                            child.animate().withEndAction(new Runnable() {
                                public void run() {
                                    mSwiping = false;
                                    mListView.setEnabled(true);
                                }
                            });
                            firstAnimation = false;
                        }
                    }
                }
                mItemIdTopMap.clear();
                return true;
            }
        });
    }
}
