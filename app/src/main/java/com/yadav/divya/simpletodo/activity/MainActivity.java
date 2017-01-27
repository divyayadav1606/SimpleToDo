package com.yadav.divya.simpletodo.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.wdullaer.swipeactionadapter.SwipeActionAdapter;
import com.wdullaer.swipeactionadapter.SwipeDirection;

import com.yadav.divya.simpletodo.R;
import com.yadav.divya.simpletodo.adapter.TodoAdapter;
import com.yadav.divya.simpletodo.data.DbHelper;

public class MainActivity extends AppCompatActivity implements
        SwipeActionAdapter.SwipeActionListener{

    protected SwipeActionAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DbHelper dbHelper = new DbHelper(this.getApplicationContext());
        Cursor cursor = dbHelper.getWritableDatabase().rawQuery("SELECT  * FROM todo", null);

        ListView list = (ListView) findViewById(R.id.list);
        TodoAdapter todoAdapter = new TodoAdapter(this, cursor, 0);

        mAdapter = new SwipeActionAdapter(todoAdapter);
        mAdapter.setSwipeActionListener(MainActivity.this)
                .setDimBackgrounds(true)
                .setListView(list);
        list.setAdapter(mAdapter);

        // Set backgrounds for the swipe directions
        mAdapter.addBackground(SwipeDirection.DIRECTION_NORMAL_LEFT,R.layout.row_bg_left)
                .addBackground(SwipeDirection.DIRECTION_NORMAL_RIGHT,R.layout.row_bg_right);

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                final EditText taskEditText = new EditText(MainActivity.this);
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Add Task")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());
                                updateDb(task);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
            }
        });
    }

    private void updateDb(String task) {
        DbHelper dbHelper = new DbHelper(this);
        ContentValues values = new ContentValues();
        values.clear();

        values.put("item", task);
        values.put("status", "0");
        dbHelper.getWritableDatabase().insertWithOnConflict("todo", null, values, SQLiteDatabase.CONFLICT_IGNORE);
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
}
