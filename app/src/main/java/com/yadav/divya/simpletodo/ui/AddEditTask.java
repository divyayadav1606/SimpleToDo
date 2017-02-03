package com.yadav.divya.simpletodo.ui;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.yadav.divya.simpletodo.R;

import java.util.Calendar;

/**
 * Created by dyadav1 on 1/27/2017.
 */

public class AddEditTask extends DialogFragment {

    private EditText mTask;
    private Spinner mPriority;
    private DatePicker mDueDate;
    private CheckBox mCheck;
    private TextView mCheckText;

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

        mTask = (EditText) view.findViewById(R.id.title);
        mTask.setText(getArguments().getString("task", ""));



        mPriority = (Spinner) view.findViewById(R.id.priority);
        String priority = getArguments().getString("priority", "Normal");
        switch (priority) {
            case "Low":
                mPriority.setSelection(0);
                break;
            case "High":
                mPriority.setSelection(2);
                break;
            default:
                mPriority.setSelection(1);
                break;
        }

        mDueDate = (DatePicker) view.findViewById(R.id.duedate);
        String date = getArguments().getString("date", null);

        //convert to a time stamp and update the DatePicker
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(Long.parseLong(date));
            mDueDate.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
        }

        final Button button = (Button) view.findViewById(R.id.save);

        //Checkbox
        String is_checked = getArguments().getString("status", "false");
        mCheck = (CheckBox) view.findViewById(R.id.is_complete);
        mCheckText = (TextView) view.findViewById(R.id.is_complete_text);

        if (is_checked.equals("true")) {
            mCheck.setChecked(true);
            mCheckText.setText(R.string.mark_as_incomplete);
        }

        mCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCheck.isChecked())
                    mCheckText.setText(R.string.mark_as_incomplete);
                else
                    mCheckText.setText(R.string.mark_as_complete);

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
                //Set date in milliseconds
                Calendar date = Calendar.getInstance();

                date.set(Calendar.DAY_OF_MONTH, mDueDate.getDayOfMonth());
                date.set(Calendar.MONTH, mDueDate.getMonth());
                date.set(Calendar.YEAR, mDueDate.getYear());

                mListener.onFinishDialog(mTask.getText().toString(),
                        mPriority.getSelectedItem().toString(),
                        String.valueOf(mCheck.isChecked()),
                        String.valueOf(date.getTimeInMillis()));
            }
        });

        return view;
    }
}

