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

import java.util.Date;

/**
 * Created by dyadav1 on 1/27/2017.
 */

public class AddEditTask extends DialogFragment {

    private EditText mEditText;
    private Spinner mSpinner;
    private DatePicker mDatePicker;

    public interface AddEditTaskListener {
        void onFinishDialog(String task, String priority, Date date);
    }

    private AddEditTaskListener mListener;

    public void setFinishDialogListener(AddEditTaskListener listener) {
        mListener = listener;
    }

    public AddEditTask() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.add_edit, container, false);

        mEditText = (EditText) view.findViewById(R.id.title);
        mSpinner = (Spinner) view.findViewById(R.id.priority);
        mDatePicker = (DatePicker) view.findViewById(R.id.duedate);
        final Button button = (Button) view.findViewById(R.id.save);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
                Date date = new Date(mDatePicker.getYear() - 1900,
                        mDatePicker.getMonth(),
                        mDatePicker.getDayOfMonth());

                mListener.onFinishDialog(mEditText.getText().toString(), mSpinner.getSelectedItem().toString(), date);
            }
        });

        return view;
    }
}

