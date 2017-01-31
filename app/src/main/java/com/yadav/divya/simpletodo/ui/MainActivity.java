package com.yadav.divya.simpletodo.ui;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.wdullaer.swipeactionadapter.SwipeActionAdapter;
import com.wdullaer.swipeactionadapter.SwipeDirection;
import com.yadav.divya.simpletodo.R;
import com.yadav.divya.simpletodo.adapter.TodoAdapter;
import com.yadav.divya.simpletodo.data.DbHelper;

public class MainActivity extends AppCompatActivity implements
        SwipeActionAdapter.SwipeActionListener{

    protected SwipeActionAdapter mAdapter;
    private TodoAdapter mtodoAdapter;
    private Cursor mCursor;
    private ListView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DbHelper dbHelper = new DbHelper(this.getApplicationContext());
        mCursor = dbHelper.getWritableDatabase().rawQuery("SELECT  * FROM TASK_LIST", null);

        mList = (ListView) findViewById(R.id.list);
        mtodoAdapter = new TodoAdapter(this, mCursor, 0);

        mAdapter = new SwipeActionAdapter(mtodoAdapter);
        mAdapter.setSwipeActionListener(MainActivity.this)
                .setDimBackgrounds(true)
                .setListView(mList);

        mList.setAdapter(mAdapter);

        // Set backgrounds for the swipe directions
        mAdapter.addBackground(SwipeDirection.DIRECTION_NORMAL_LEFT,R.layout.row_bg_left)
                .addBackground(SwipeDirection.DIRECTION_NORMAL_RIGHT,R.layout.row_bg_right);

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                showEditDialog();
            }
        });
    }

    private void updateDb(String task) {
        DbHelper dbHelper = new DbHelper(this);
        ContentValues values = new ContentValues();
        values.clear();

        values.put("task", task);
        values.put("status", "0");
        dbHelper.getWritableDatabase().insertWithOnConflict("TASK_LIST", null, values, SQLiteDatabase.CONFLICT_IGNORE);
    }

    @Override
    public boolean hasActions(int position, SwipeDirection direction) {
        if(direction.isLeft() || direction.isRight() )
            return true;
        return false;
    }

    @Override
    public boolean shouldDismiss(int position, SwipeDirection direction) {
        return false;
    }

    @Override
    public void onSwipe(int[] position, SwipeDirection[] direction) {

    }

    private void showEditDialog() {
        AddEditTask dFragment = new AddEditTask();

        dFragment.setFinishDialogListener(new AddEditTask.AddEditTaskListener() {
            @Override
            public void onFinishDialog(String task) {
                updateDb(task);
                mCursor.requery();
                mAdapter.notifyDataSetChanged();
                mList.setAdapter(mAdapter);
            }
        });

        dFragment.show(getSupportFragmentManager(), "");
    }
}
