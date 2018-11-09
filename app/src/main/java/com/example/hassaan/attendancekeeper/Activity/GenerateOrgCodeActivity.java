package com.example.hassaan.attendancekeeper.Activity;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hassaan.attendancekeeper.API.UserAPI;
import com.example.hassaan.attendancekeeper.GMailSender;
import com.example.hassaan.attendancekeeper.Model.TblLogin;
import com.example.hassaan.attendancekeeper.Model.TblQRCode;
import com.example.hassaan.attendancekeeper.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenerateOrgCodeActivity extends AppCompatActivity {

    TextView tv_GeneratedCode;
    Button btn_SendMail, btn_CopyCode, btn_Share, btn_login_register;
    String OrgName, email, pass, ORGCODE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_org_code);

        if (null != getIntent()) {
            Bundle bundle = getIntent().getExtras();
            OrgName = bundle.getString("OrgName");
            email = bundle.getString("email");
            pass = bundle.getString("pass");
        }

//        final ProgressDialog progressDialog = new ProgressDialog(SignUpOrgActivity.this);
//                progressDialog.setIndeterminate(true);
//                progressDialog.setMessage("Please Wait...");
//                progressDialog.show();



        tv_GeneratedCode = findViewById(R.id.tv_GeneratedCode);

        ORGCODE = OrgName;

        tv_GeneratedCode.setText("" + ORGCODE);

        btn_SendMail = findViewById(R.id.btn_SendMail);
        btn_CopyCode = findViewById(R.id.btn_CopyCode);
        btn_Share = findViewById(R.id.btn_Share);
        btn_login_register = findViewById(R.id.btn_login_register);

        final UserAPI service = UserAPI.retrofit.create(UserAPI.class);

        Call<TblQRCode> AddQRCode = service.AddQRCode(tv_GeneratedCode.getText().toString());
        AddQRCode.enqueue(new Callback<TblQRCode>() {
            @Override
            public void onResponse(Call<TblQRCode> call, Response<TblQRCode> response) {
                TblQRCode tblQRCode = response.body();
            }

            @Override
            public void onFailure(Call<TblQRCode> call, Throwable t) {
                Log.i("TAG", "onFailure: " + call + " Throwable: " + t);
            }
        });


        btn_CopyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", tv_GeneratedCode.getText());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(GenerateOrgCodeActivity.this, "Copied to Clipboard: ", Toast.LENGTH_SHORT).show();
            }
        });
        btn_Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/html");
                //  shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Test Mail");
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, ORGCODE);
//                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,"http://www.google.com");
                startActivity(Intent.createChooser(shareIntent, "Share"));
            }
        });

        btn_SendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {

                    public void run() {

                        try {
                            GMailSender sender = new GMailSender(
                                    "hassaanbinsajjad03@gmail.com",
                                    "");
                            //sender.addAttachment(Environment.getExternalStorageDirectory().getPath()+"/image.jpg");

                            sender.sendMail("Test mail", "This mail has been sent from android app along with ORGCODE" + tv_GeneratedCode.getText(),
                                    "hassaanbinsajjad03@gmail.com",
                                    email);

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }).start();

            }
        });

        btn_login_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = new ProgressDialog(GenerateOrgCodeActivity.this);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Authenticating...");
                progressDialog.show();

                Call<TblLogin> LoginUser = service.Login(email, pass);
                LoginUser.enqueue(new Callback<TblLogin>() {
                    @Override
                    public void onResponse(Call<TblLogin> call, Response<TblLogin> response) {
                        Log.i("TAG", "on LOGIN Response: " + response.body());
                        progressDialog.dismiss();
                        Intent intent = new Intent(GenerateOrgCodeActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<TblLogin> call, Throwable t) {
                        Log.i("TAG", "onFailure: " + call + " Throwable: " + t);
                    }
                });
            }
        });

    }
}
