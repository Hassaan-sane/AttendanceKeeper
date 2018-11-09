package com.example.hassaan.attendancekeeper.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hassaan.attendancekeeper.API.ListAPI;
import com.example.hassaan.attendancekeeper.API.UserAPI;
import com.example.hassaan.attendancekeeper.Model.TblLogin;
import com.example.hassaan.attendancekeeper.Model.TblUser;
import com.example.hassaan.attendancekeeper.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    public static final String MY_PREFS_NAME = "AdminFile";
    private String emailKey = "email";
    private String OrgKey = "OrgCode";
    private String AdminIdKey = "AdminId";
    private String NameKey = "Name";

    boolean doubleBackToExitPressedOnce = false;


    TextView tv_email, tv_password, tv_OrgCode_old;
    EditText et_email, et_password, et_OrgCode_old;
    Button btn_signup, btn_login;
    String stremail;
    List<TblUser> tblAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();

        final SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        final String OrgCode = prefs.getString(OrgKey, null);

        if (OrgCode != null) {

            Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
            startActivity(intent);
        }

        tv_email = findViewById(R.id.tv_email);
        tv_password = findViewById(R.id.tv_password);
        tv_OrgCode_old = findViewById(R.id.tv_OrgCode_old);

        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_OrgCode_old = findViewById(R.id.et_OrgCode_old);

        btn_login = findViewById(R.id.btn_login);
        btn_signup = findViewById(R.id.btn_signup);

        final UserAPI service = UserAPI.retrofit.create(UserAPI.class);
        final ListAPI service2 = ListAPI.retrofit.create(ListAPI.class);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et_email.getText().length() == 0) {
                    Toast.makeText(LoginActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                } else if (et_password.getText().length() == 0) {
                    Toast.makeText(LoginActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();

                } else if (et_OrgCode_old.getText().length() == 0) {
                    Toast.makeText(LoginActivity.this, "Please Enter Organization Code", Toast.LENGTH_SHORT).show();
                } else {

                    final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Authenticating...");
                    progressDialog.show();

                    Call<TblLogin> LoginUser = service.Login(et_email.getText().toString(), et_password.getText().toString());
                    LoginUser.enqueue(new Callback<TblLogin>() {
                        @Override
                        public void onResponse(Call<TblLogin> call, Response<TblLogin> response) {
                            Log.i("TAG", "on LOGIN Response: " + response.body());
                            if (response.isSuccessful()) {
                                final TblLogin UserData = response.body();


                            Call<List<TblUser>> Admin = service.getAdminId(UserData.getData().getId().toString());
                            Admin.enqueue(new Callback<List<TblUser>>() {
                                @Override
                                public void onResponse(Call<List<TblUser>> call, Response<List<TblUser>> response) {
                                    tblAdmin = response.body();
                                    Log.i("TAG", "onResponseAdmin: " + response);

                                    Call<String[]> getOrgCode = service2.GetOrgCode(tblAdmin.get(0).getAdminId());
                                    getOrgCode.enqueue(new Callback<String[]>() {
                                        @Override
                                        public void onResponse(Call<String[]> call, Response<String[]> response) {
                                            String[] orgCode = response.body();
                                            Log.i("TAG", "onResponseORGz: " + response);
                                            Log.i("", "et orgcode: " + et_OrgCode_old.getText().toString());
                                            Log.i("", "et orgcode: " + orgCode[0]);
                                            if (orgCode[0].equals(et_OrgCode_old.getText().toString().toUpperCase())) {

                                                editor.putString(emailKey, UserData.getData().getEmail());
                                                editor.putInt(AdminIdKey, tblAdmin.get(0).getAdminId());
                                                editor.putString(OrgKey, orgCode[0]);
                                                editor.putString(NameKey,UserData.getData().getName());
                                                editor.apply();

                                                progressDialog.dismiss();

                                                Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(LoginActivity.this, "Wrong Organization Code", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<String[]> call, Throwable t) {
                                            Log.i("TAG", "onFailureOrgCode: " + call + " Throwable: " + t);
                                            progressDialog.dismiss();
                                        }
                                    });
                                }

                                @Override
                                public void onFailure(Call<List<TblUser>> call, Throwable t) {
                                    Log.i("TAG", "onFailureAdmin: " + call + " Throwable: " + t);
                                    progressDialog.dismiss();
                                }
                            });

                        }
                        }

                        @Override
                        public void onFailure(Call<TblLogin> call, Throwable t) {
                            progressDialog.dismiss();
                            Log.i("TAG", "onFailureUser: " + call + " Throwable: " + t);
                        }
                    });

                }
            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpUserActivity.class);
                startActivity(intent);
                finish();
            }
        });




    }

    @Override
    public void onBackPressed() {
        final SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.remove(OrgKey);
        editor.remove(emailKey);
        editor.apply();
        if (doubleBackToExitPressedOnce) {
//                super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Exiting...", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                moveTaskToBack(true);
                doubleBackToExitPressedOnce = false;
            }
        }, 1000);
    }
}
