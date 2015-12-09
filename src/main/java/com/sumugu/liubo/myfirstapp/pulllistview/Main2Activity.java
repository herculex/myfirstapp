package com.sumugu.liubo.myfirstapp.pulllistview;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sumugu.liubo.myfirstapp.Cheeses;
import com.sumugu.liubo.myfirstapp.DisplayUtils;
import com.sumugu.liubo.myfirstapp.R;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    FrameLayout container;
    TextView mTextView;
    int mMaxDown=200;
    boolean editShowAll=false;

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

        container = (FrameLayout)findViewById(R.id.main2_container);
        LayoutTransition transition = container.getLayoutTransition();
        transition.enableTransitionType(LayoutTransition.CHANGING);

        mTextView = (TextView)findViewById(R.id.edit_message);
        mTextView.setBackgroundColor(DisplayUtils.randomColor());

        //int datas
        final ArrayList<String> cheeseList = new ArrayList<String>();
        for (int i = 0; i < Cheeses.sCheeseStrings.length; ++i) {
            cheeseList.add(Cheeses.sCheeseStrings[i]);
        }

        //set listview adapter
        final ListView listView = (ListView)findViewById(R.id.list_view);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.list_item,R.id.list_tv,cheeseList);
        listView.setAdapter(arrayAdapter);




        //touchlistener for listview adapter
        listView.setOnTouchListener(new View.OnTouchListener() {

            float mDownY = 0;
            float deltaY2 = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        mDownY = event.getY();
                        deltaY2 = 0;

                        if (v.getTranslationY() == mTextView.getHeight())
                            editShowAll = true;
                        else
                            editShowAll = false;


                        Log.d(TAG, "mDownY=" + String.valueOf(mDownY) + ";firstChildAt=" + String.valueOf(listView.getFirstVisiblePosition()));
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

                        if (deltaY > 0 && (listView.getFirstVisiblePosition() == listView.getChildAt(0).getTop())) {
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
                        break;
                    }

                }
                return true;
            }
        });


        //
    }

}
