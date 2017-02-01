package com.yadav.divya.simpletodo.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Paint;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yadav.divya.simpletodo.R;
import com.yadav.divya.simpletodo.data.DbHelper;

/**
 * Created by dyadav1 on 1/24/2017.
 */

public class TodoAdapter extends CursorAdapter {


    public TodoAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    protected void onContentChanged() {
        // TODO Auto-generated method stub
        super.onContentChanged();
        notifyDataSetChanged();
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
        item.setText(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_TASK)));

        ImageView img = (ImageView) view.findViewById(R.id.priority_icon);

        if (cursor.getString(2).equals("Low"))
            img.setImageResource(R.drawable.low_priority);
        else if (cursor.getString(2).equals("High"))
            img.setImageResource(R.drawable.high_priority);
        else
           img.setImageResource(R.drawable.normal_priority);


        if(cursor.getString(3).equals("1"))
            item.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }
}