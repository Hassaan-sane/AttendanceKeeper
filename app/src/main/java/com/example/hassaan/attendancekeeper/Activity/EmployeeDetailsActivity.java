package com.example.hassaan.attendancekeeper.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hassaan.attendancekeeper.Event.EmployeeDetailEvent;
import com.example.hassaan.attendancekeeper.Model.TblEmployee;
import com.example.hassaan.attendancekeeper.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EmployeeDetailsActivity extends AppCompatActivity {
    TextView tv_ED_name, tv_ED_email_new, tv_ED_DOB, tv_ED_number;
    EditText et_ED_name, et_ED_email_new, et_ED_DOB, et_ED_number;
    Button btn_Save;

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);
        getActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(EmployeeDetailEvent Event) {

        TblEmployee tblEmployee;
        int position;

        tblEmployee = Event.getMessage();
        position = Event.getPosition();
        tv_ED_name = findViewById(R.id.tv_ED_name);
        tv_ED_DOB = findViewById(R.id.tv_ED_DOB);
        tv_ED_number = findViewById(R.id.tv_ED_number);
        tv_ED_email_new = findViewById(R.id.tv_ED_email_new);


        et_ED_name = findViewById(R.id.et_ED_name);
        et_ED_DOB = findViewById(R.id.et_ED_DOB);
        et_ED_number = findViewById(R.id.et_ED_number);
        et_ED_email_new = findViewById(R.id.et_ED_email_new);


        btn_Save = findViewById(R.id.btn_Save);

        et_ED_name.setText(tblEmployee.getName());
        et_ED_DOB.setText(tblEmployee.getDOB());
        et_ED_email_new.setText(tblEmployee.getUsername());
        et_ED_number.setText(tblEmployee.getNumber());


        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
