package com.sumugu.liubo.myfirstapp;

import android.app.Activity;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.media.Image;
import android.provider.ContactsContract;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.util.Attributes;
import com.sumugu.liubo.myfirstapp.adapter.SimpleCursorAdapterDemo;

public class ListContactActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {

//    SimpleCursorAdapter mAdapter;

//    ListView mListView;
    SimpleCursorAdapterDemo mAdapter;
    CursorAdapter cursorAdapter;

    static final String[] PROJECTION = new String[] {ContactsContract.Data._ID,ContactsContract.Data.DISPLAY_NAME};
    static final String SELECTION = "(("+
            ContactsContract.Data.DISPLAY_NAME+" NOTNULL) AND ("+
            ContactsContract.Data.DISPLAY_NAME+" !=''))";

    static final String[] fromColumns={ContactsContract.Data.DISPLAY_NAME};
    static final int[] toViews={R.id.displayText};

    ListView listView;
    final SimpleCursorAdapterDemo.ViewBinder mViewBinder = new SimpleCursorAdapterDemo.ViewBinder() {
        @Override
        public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
//            switch (view.getId())
//            {
//                case R.id.swipe_sample1:
                    Log.d("sumugu,id found:",view.getId()+":"+R.id.swipe_sample1);
                    return true;
//                default:
//                    return  false;
//            }
        }
    };
    class ViewHolder{
        TextView display_text;
        TextView archire_text;
        TextView delete_text;
        ImageView magnifier2_image;
        ImageView star2_image;
        ImageView trash2_image;
        View starboot;
        View bottom_wrapper;
        View bottom_wrapper_2;
        SwipeLayout swipeLayout;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contact);

//        mAdapter = new SimpleCursorAdapter(this,R.layout.sample1,null,fromColumns,toViews,0);
//        mAdapter.setMode(Attributes.Mode.Single);

        mAdapter = new SimpleCursorAdapterDemo(this,R.layout.sample4,null,fromColumns,toViews,0);
        cursorAdapter = new CursorAdapter(this,null,0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                ViewHolder holder = new ViewHolder();
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.sample4,null);

                holder.display_text = (TextView)view.findViewById(R.id.displayText);
                holder.archire_text = (TextView)view.findViewById(R.id.archive);
                holder.delete_text = (TextView)view.findViewById(R.id.delete);
                holder.magnifier2_image = (ImageView)view.findViewById(R.id.magnifier2);
                holder.star2_image = (ImageView)view.findViewById(R.id.star2);
                holder.trash2_image = (ImageView)view.findViewById(R.id.trash2);
                holder.starboot = view.findViewById(R.id.starbott);
                holder.bottom_wrapper = view.findViewById(R.id.bottom_wrapper);
                holder.bottom_wrapper_2 = view.findViewById(R.id.bottom_wrapper_2);
                holder.swipeLayout = (SwipeLayout)view.findViewById(R.id.swipe_sample1);

                view.setTag(holder);
                return view;
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                ViewHolder holder=(ViewHolder)view.getTag();
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
                holder.display_text.setText(name);

                holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
                holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, holder.bottom_wrapper);
                holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.bottom_wrapper_2);

                holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Top,holder.starboot);   //貌似焦点获取不到，只swipe listview 上下
                holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Bottom,holder.starboot);    //貌似焦点获取不到，只swipe listview 上下

                holder.swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
                    @Override
                    public void onDoubleClick(SwipeLayout layout, boolean surface) {
                        Toast.makeText(ListContactActivity.this, "Double click", Toast.LENGTH_SHORT).show();
                    }
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(ListContactActivity.this, "Click on surface", Toast.LENGTH_SHORT).show();
//                    }
                });
                holder.swipeLayout.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Toast.makeText(ListContactActivity.this, "longClick on surface", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                holder.star2_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ListContactActivity.this, "click the star", Toast.LENGTH_SHORT).show();
                    }
                });
                holder.trash2_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ListContactActivity.this, "Click the trash", Toast.LENGTH_SHORT).show();
                    }
                });
                holder.magnifier2_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ListContactActivity.this, "click the magnifier", Toast.LENGTH_SHORT).show();
                    }
                });
                holder.archire_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ListContactActivity.this, "click the archire", Toast.LENGTH_SHORT).show();
                    }
                });
                holder.delete_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ListContactActivity.this, "click the delele", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        };

//        mAdapter.setViewBinder(mViewBinder);
        setListAdapter(cursorAdapter);

//        listView = (ListView)findViewById(R.id.listview_contact);
//        listView.setAdapter(mAdapter);

        getLoaderManager().initLoader(0, null, this);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(ListContactActivity.this, "click on position:"+String.valueOf(position)+" id:"+String.valueOf(id), Toast.LENGTH_SHORT).show();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,ContactsContract.Data.CONTENT_URI,PROJECTION,SELECTION,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
        cursorAdapter.swapCursor(data);
        Log.d("sumugu,selected count:",String.valueOf(data.getCount()));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader)
    {
        mAdapter.swapCursor(null);
        cursorAdapter.swapCursor(null);
    }
}
