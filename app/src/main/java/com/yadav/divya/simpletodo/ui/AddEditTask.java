package com.yadav.divya.simpletodo.ui;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.yadav.divya.simpletodo.R;

/**
 * Created by dyadav1 on 1/27/2017.
 */

public class AddEditTask extends DialogFragment {

    private EditText mEditText;

    public interface AddEditTaskListener {
        void onFinishDialog(String task);
    }

    private AddEditTaskListener mListener;

    public void setFinishDialogListener(AddEditTaskListener listener) {
        mListener = listener;
    }

    public AddEditTask() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.add_edit, container);

        mEditText = (EditText) view.findViewById(R.id.task_title);

        final Button button = (Button) view.findViewById(R.id.save);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();

                mListener.onFinishDialog(mEditText.getText().toString());
            }
        });

        return view;
    }
}

