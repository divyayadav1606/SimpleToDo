package com.yadav.divya.simpletodo.ui;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.yadav.divya.simpletodo.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dyadav1 on 1/27/2017.
 */

public class AddEditTask extends DialogFragment {

    private EditText mTask;
    private Spinner mPriority;
    private DatePicker mDueDate;
    private String DATE_FORMAT = "yyyy-MM-dd";

    public interface AddEditTaskListener {
        void onFinishDialog(String task, String priority, String status, String date);
    }

    private AddEditTaskListener mListener;

    public void setFinishDialogListener(AddEditTaskListener listener) {
        mListener = listener;
    }

    public AddEditTask() {}

    public static AddEditTask newInstance(String task, String priority, String status, String date) {
        AddEditTask frag = new AddEditTask();
        Bundle args = new Bundle();
        if (task != null)
            args.putString("task", task);
        if (priority != null)
            args.putString("priority", priority);
        if (status != null)
            args.putString("status", status);
        if (date != null)
            args.putString("date", date);

        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.add_edit, container, false);
        final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

        mTask = (EditText) view.findViewById(R.id.title);
        mTask.setText(getArguments().getString("task", ""));

        mPriority = (Spinner) view.findViewById(R.id.priority);
        String priority = getArguments().getString("priority", "Normal");
        if (priority.equals("Low"))
            mPriority.setSelection(0);
        else if (priority.equals("High"))
            mPriority.setSelection(2);
        else
            mPriority.setSelection(1);

        mDueDate = (DatePicker) view.findViewById(R.id.duedate);
        String date = getArguments().getString("date", null);

        //convert to a time stamp and update the DatePicker
        if (date != null) {
            try {
                Date date2 = sdf.parse(date);
                mDueDate.updateDate(date2.getYear() + 1900, date2.getMonth(), date2.getDay());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        final Button button = (Button) view.findViewById(R.id.save);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
                Date date = new Date(mDueDate.getYear() - 1900,
                        mDueDate.getMonth(),
                        mDueDate.getDayOfMonth());

                mListener.onFinishDialog(mTask.getText().toString(), mPriority.getSelectedItem().toString(), "0", sdf.format(date ));
            }
        });

        return view;
    }
}

