package com.yadav.divya.simpletodo.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.wdullaer.swipeactionadapter.SwipeActionAdapter;
import com.wdullaer.swipeactionadapter.SwipeDirection;
import com.yadav.divya.simpletodo.R;
import com.yadav.divya.simpletodo.adapter.TodoAdapter;
import com.yadav.divya.simpletodo.data.DbHelper;
import com.yadav.divya.simpletodo.publisher.OverdueTaskPublisher;

public class MainActivity extends AppCompatActivity implements
        SwipeActionAdapter.SwipeActionListener{

    protected SwipeActionAdapter mAdapter;
    private Cursor mCursor;
    private ListView mList;
    private DbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new DbHelper(this.getApplicationContext());
        mCursor = mDbHelper.getWritableDatabase().rawQuery("SELECT  * FROM TASK_LIST Order By _id desc", null);

        mList = (ListView) findViewById(R.id.list);
        TextView emptyText = (TextView)findViewById(android.R.id.empty);
        mList.setEmptyView(emptyText);

        TodoAdapter mtodoAdapter = new TodoAdapter(this, mCursor, 0);

        mAdapter = new SwipeActionAdapter(mtodoAdapter);
        mAdapter.setSwipeActionListener(MainActivity.this)
                .setDimBackgrounds(true)
                .setListView(mList);

        mList.setAdapter(mAdapter);

        mAdapter.addBackground(SwipeDirection.DIRECTION_NORMAL_LEFT,R.layout.row_bg_left)
                .addBackground(SwipeDirection.DIRECTION_NORMAL_RIGHT,R.layout.row_bg_right);

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Open Dialog Fragment for editing
                Cursor c = (Cursor) mAdapter.getItem(i);
                final String where = "_ID = " + c.getString(0);

                AddEditTask dFragment = AddEditTask.newInstance(c.getString(1), c.getString(2), c.getString(3), c.getLong(4));

                dFragment.setFinishDialogListener(new AddEditTask.AddEditTaskListener() {
                    @Override
                    public void onFinishDialog(String task, String priority, String status, Long date) {
                        //Update DB
                        ContentValues values = new ContentValues();
                        values.clear();

                        values.put(DbHelper.COLUMN_TASK, task);
                        values.put(DbHelper.COLUMN_PRIORITY, priority);
                        values.put(DbHelper.COLUMN_STATUS, status);
                        values.put(DbHelper.COLUMN_DATE, date);

                        mDbHelper.getWritableDatabase().update(DbHelper.TABLE_NAME, values, where, null);
                        refreshUI();
                    }
                });

                dFragment.show(getSupportFragmentManager(), "");
            }
        });
        scheduleCheck();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_add:
                addTask();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean hasActions(int position, SwipeDirection direction) {
        return direction.isLeft() || direction.isRight();
    }

    @Override
    public boolean shouldDismiss(int position, SwipeDirection direction) {
        return false;
    }

    @Override
    public void onSwipe(int[] positionList, SwipeDirection[] directionList) {
        for (int i = 0; i < positionList.length; i++) {
            SwipeDirection direction = directionList[i];
            int position = positionList[i];

            Cursor c = (Cursor) mAdapter.getItem(position);
            String where = "_ID = " + c.getString(0);

            switch (direction) {
                case DIRECTION_FAR_LEFT:
                    ContentValues values = new ContentValues();

                    if (c.getString(c.getColumnIndexOrThrow(DbHelper.COLUMN_STATUS)).equals("false")) {
                        values.put(DbHelper.COLUMN_STATUS, "true");
                    } else {
                        values.put(DbHelper.COLUMN_STATUS, "false");
                    }
                    mDbHelper.getWritableDatabase().update(DbHelper.TABLE_NAME, values, where, null);
                    refreshUI();
                    break;
                case DIRECTION_NORMAL_LEFT:
                case DIRECTION_NORMAL_RIGHT:
                    break;
                case DIRECTION_FAR_RIGHT:
                    mDbHelper.getWritableDatabase().delete(DbHelper.TABLE_NAME, where, null);
                    refreshUI();
                    break;
            }
        }
    }

    private void addTask() {
        AddEditTask dFragment = AddEditTask.newInstance(null, null, null, null);

        dFragment.setFinishDialogListener(new AddEditTask.AddEditTaskListener() {
            @Override
            public void onFinishDialog(String task, String priority, String status, Long date) {
                ContentValues values = new ContentValues();
                values.clear();

                values.put(DbHelper.COLUMN_TASK, task);
                values.put(DbHelper.COLUMN_PRIORITY, priority);
                values.put(DbHelper.COLUMN_STATUS, status);
                values.put(DbHelper.COLUMN_DATE, date);
                mDbHelper.getWritableDatabase().insertWithOnConflict(DbHelper.TABLE_NAME,
                        null, values, SQLiteDatabase.CONFLICT_IGNORE);
                refreshUI();
            }
        });

        dFragment.show(getSupportFragmentManager(), "");
    }

    private void refreshUI() {
        mCursor.requery();
        mAdapter.notifyDataSetChanged();
        mList.setAdapter(mAdapter);
    }

    //Setup 12 hour notification
    private void scheduleCheck() {
        Intent intent = new Intent(this, OverdueTaskPublisher.class);
        intent.putExtra("notifyId", 1);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 12*60*60*1000, pendingIntent);
    }
}
