package com.sumugu.liubo.myfirstapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.sumugu.liubo.myfirstapp.swipelistviewtutorial.MainActivity;


public class MyActivity extends ActionBarActivity {

    public final static String EXTRA_MESSAGE = "com.sumugu.liubo.myfirstapp.MESSAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

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

    public void openSearch()
    {}
    public void openSettings(){}
}
