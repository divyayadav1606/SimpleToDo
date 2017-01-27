package com.yadav.divya.simpletodo.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yadav.divya.simpletodo.R;

/**
 * Created by dyadav1 on 1/24/2017.
 */

public class TodoAdapter extends CursorAdapter {


    public TodoAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor == null)
            return;

        TextView item = (TextView) view.findViewById(R.id.item);
        item.setText(cursor.getString(cursor.getColumnIndexOrThrow("item")));
    }
}