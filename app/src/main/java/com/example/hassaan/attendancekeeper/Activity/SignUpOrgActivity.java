package com.example.hassaan.attendancekeeper.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hassaan.attendancekeeper.API.UserAPI;
import com.example.hassaan.attendancekeeper.Model.TblLogin;
import com.example.hassaan.attendancekeeper.Model.TblOrgCode;
import com.example.hassaan.attendancekeeper.Model.TblUser;
import com.example.hassaan.attendancekeeper.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpOrgActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView tv_OrgName, tv_OrgAddress, tv_country, tv_NumberEmployees;
    EditText et_OrgName, et_OrgAddress, et_country;
    String name, email_new, password_new, confirmPassword, DOB, number;
    Button btn_OrgNext;
    int Employees;
    private TblOrgCode tblOrgCode;
    private TblLogin Userdata;
    String  ORGCODE;

    private Spinner spinner;
    private static final String[] paths = {"Enter Here", "10+", "20+", "100+", "200+"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_org);

        if (null != getIntent()) {
            Bundle bundle = getIntent().getExtras();

            name = bundle.getString("name");
            DOB = bundle.getString("DOB");
            number = bundle.getString("number");
            email_new = bundle.getString("email");
            password_new = bundle.getString("password");
            confirmPassword = bundle.getString("passwordC");

            tv_OrgName = findViewById(R.id.tv_OrgName);
            tv_OrgAddress = findViewById(R.id.tv_OrgAddress);
            tv_country = findViewById(R.id.tv_country);
            tv_NumberEmployees = findViewById(R.id.tv_NumberEmployees);
        }
        et_OrgName = findViewById(R.id.et_OrgName);
        et_OrgAddress = findViewById(R.id.et_OrgAddress);
        et_country = findViewById(R.id.et_country);

        btn_OrgNext = findViewById(R.id.btn_OrgNext);

        spinner = (Spinner) findViewById(R.id.spinnerEmployees);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SignUpOrgActivity.this,
                android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        btn_OrgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final UserAPI service = UserAPI.retrofit.create(UserAPI.class);

                final ProgressDialog progressDialog = new ProgressDialog(SignUpOrgActivity.this);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Please Wait...");
                progressDialog.show();

                if (et_OrgName.getText().length() == 0) {
                    Toast.makeText(SignUpOrgActivity.this, "Enter Organization Name", Toast.LENGTH_SHORT).show();
                } else if (tv_OrgAddress.getText().length() == 0) {
                    Toast.makeText(SignUpOrgActivity.this, "Enter Organization Address", Toast.LENGTH_SHORT).show();
                } else if (et_country.getText().length() == 0) {
                    Toast.makeText(SignUpOrgActivity.this, "Enter a Country", Toast.LENGTH_SHORT).show();
                } else {
                    Date date = new Date();
                    String minute = "";
                    String second = "";
                    SimpleDateFormat Formatout2 = new SimpleDateFormat("ss");
                    SimpleDateFormat Formatout = new SimpleDateFormat("mm");

                    minute = Formatout.format(date);
                    second = Formatout2.format(date);
                    ORGCODE ="ORG" + et_OrgName.getText().toString().toUpperCase() + minute + second;

                    Call<TblLogin> UserAdd = service.AddUser(
                            name,
                            email_new,
                            password_new,
                            confirmPassword);
                    UserAdd.enqueue(new Callback<TblLogin>() {
                        @Override
                        public void onResponse(Call<TblLogin> call, Response<TblLogin> response) {
                            Userdata = response.body();
                            Log.i("TAG", "onResponseMakingUser: " + response.body());
                            Call<TblOrgCode> OrgAdd = service.AddOrg(
                                    ORGCODE,
                                    et_OrgName.getText().toString(),
                                    et_OrgAddress.getText().toString(),
                                    et_country.getText().toString());
                            OrgAdd.enqueue(new Callback<TblOrgCode>() {
                                @Override
                                public void onResponse(Call<TblOrgCode> call, Response<TblOrgCode> response) {
                                    tblOrgCode = response.body();
                                    Log.i("TAG", "onResponse OrgTable: " + tblOrgCode.getCodeId());

                                    Call<TblUser> AdminAdd = service.AddAdmin(
                                            name,
                                            email_new,
                                            DOB,
                                            number,
                                            tblOrgCode.getCodeId().toString(),
                                            Userdata.getData().getId().toString(),
                                            Employees
                                    );
                                    AdminAdd.enqueue(new Callback<TblUser>() {
                                        @Override
                                        public void onResponse(Call<TblUser> call, Response<TblUser> response) {
                                            TblUser tblUser = response.body();
                                            Log.i("TAG", "onResponse: " + response);
                                            progressDialog.dismiss();
                                            Intent intent = new Intent(SignUpOrgActivity.this, GenerateOrgCodeActivity.class);
                                            intent.putExtra("OrgName",ORGCODE);
                                            intent.putExtra("email", email_new);
                                            intent.putExtra("pass", password_new);
                                            startActivity(intent);
                                            finish();
                                        }

                                        @Override
                                        public void onFailure(Call<TblUser> call, Throwable t) {
                                            progressDialog.dismiss();
                                            Log.i("TAG", "onFailure: " + call + " Throwable: " + t);
                                        }
                                    });
                                }

                                @Override
                                public void onFailure(Call<TblOrgCode> call, Throwable t) {
                                    progressDialog.dismiss();
                                    Log.i("TAG", "onFailure: " + call + " Throwable: " + t);
                                }
                            });


                        }

                        @Override
                        public void onFailure(Call<TblLogin> call, Throwable t) {
                            progressDialog.dismiss();
                            Log.i("TAG", "onFailure: " + call + " Throwable: " + t);
                        }
                    });


                }

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                Toast.makeText(this, "Please Select Number of Employees", Toast.LENGTH_SHORT).show();
                btn_OrgNext.setEnabled(false);
                break;
            case 1:
                Employees = 10;
                btn_OrgNext.setEnabled(true);
                break;
            case 2:
                Employees = 20;
                btn_OrgNext.setEnabled(true);
                break;
            case 3:
                Toast.makeText(this, "This Option is for Premium Members only", Toast.LENGTH_SHORT).show();
                Employees = 20;
                btn_OrgNext.setEnabled(false);
                break;
            case 4:
                Toast.makeText(this, "This option is for Premium members only", Toast.LENGTH_SHORT).show();
                Employees = 20;
                btn_OrgNext.setEnabled(false);
                break;


        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
