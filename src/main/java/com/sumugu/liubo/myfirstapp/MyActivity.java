package com.sumugu.liubo.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Visibility;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sumugu.liubo.myfirstapp.customtouch.TouchInterceptActivity;
import com.sumugu.liubo.myfirstapp.mytouch.MyTouch;
import com.sumugu.liubo.myfirstapp.pulllistview.Main2Activity;
import com.sumugu.liubo.myfirstapp.swipelistviewtutorial.MainActivity;


public class MyActivity extends ActionBarActivity implements GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener{

    public final static String EXTRA_MESSAGE = "com.sumugu.liubo.myfirstapp.MESSAGE";

    private final static String TAG="Gestures.Detector";
    private GestureDetector mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        mDetector = new GestureDetector(this,this);
        mDetector.setOnDoubleTapListener(this);

        final TextView textView = (TextView)findViewById(R.id.view_message);
        final EditText editText = (EditText)findViewById(R.id.edit_message);
        Button button = (Button)findViewById(R.id.ok_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
                textView.requestFocus();
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setVisibility(View.GONE);

                editText.setVisibility(View.VISIBLE);
                editText.requestFocus();
                editText.setSelection(editText.getText().length());
                InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        });
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id)
        {
            case R.id.action_search:
                openSearch();
                return true;
            case R.id.action_setting:
                openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    public void openMyTouch(View view)
    {
        startActivity(new Intent(this, MyTouch.class));
    }
    public void openPullListView(View view){
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }

    public void swipeListViewTutorial(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this,DisplayMessageActivity.class);
        EditText editText = (EditText)findViewById(R.id.edit_message);
        editText.animate().setDuration(1000).translationX(200);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE,editText.getTranslationX());
        startActivity(intent);
    }
    public void TouchIntercept(View view)
    {
        Intent intent=new Intent(this, TouchInterceptActivity.class);
        startActivity(intent);
    }

    public void openSwipedemo(View view)
    {
        Intent intent = new Intent(this,SwipeActivity.class);
        startActivity(intent);
    }

    public void openListViewSwipeDemo(View view)
    {
        Intent intent = new Intent(this, ListViewExample.class);
        startActivity(intent);
    }
    public void openThreeDemos(View view)
    {
        Intent intent = new Intent(this, MySwipes.class);
        startActivity(intent);
    }
    public void openListContact(View view)
    {
        Intent intent = new Intent(this,ListContactActivity.class);
        startActivity(intent);
    }
    public void openFourInOne(View view)
    {
        Intent intent = new Intent(this,fourinone.class);
        startActivity(intent);
    }

    public void translationY(View view)
    {
        ((Button)view).setText(String.valueOf(view.getTranslationY()));
        view.setTranslationY(200);
        view.animate().translationY(0).setDuration(1000);

    }

    public void openSearch()
    {}
    public void openSettings(){}

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);

        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.d(TAG,"onDown:"+e.toString());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.d(TAG,"onShowPress:"+e.toString());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d(TAG,"onSingleTapUp:"+e.toString());
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d(TAG,"onScroll:"+e1.toString()+e2.toString());
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.d(TAG,"onLongPress:"+e.toString());

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d(TAG,"onFling:"+e1.toString()+e2.toString());
        return true;
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
}
