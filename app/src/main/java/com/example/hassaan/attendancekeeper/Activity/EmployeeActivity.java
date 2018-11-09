package com.example.hassaan.attendancekeeper.Activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hassaan.attendancekeeper.API.ListAPI;
import com.example.hassaan.attendancekeeper.Adapter.EmployeeAdapter;
import com.example.hassaan.attendancekeeper.CSVUtils;
import com.example.hassaan.attendancekeeper.GMailSender;
import com.example.hassaan.attendancekeeper.Model.TblEmployee;
import com.example.hassaan.attendancekeeper.R;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private RecyclerView recyclerView;

    public static final String MY_PREFS_NAME = "AdminFile";
    private String emailKey = "email";
    private String OrgKey = "OrgCode";
    private String AdminIdKey = "AdminId";
    private EmployeeAdapter employeeAdapter;

    private List<TblEmployee> EmployeeListReport = new ArrayList<>();
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        fab = findViewById(R.id.fbtn_employeeAdd);
        final SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        final String OrgCode = prefs.getString(OrgKey, null);
        final String email = prefs.getString(emailKey, null);
        final int AdminId = prefs.getInt(AdminIdKey, 0);

        if (email==null) {
            Intent intent = new Intent(EmployeeActivity.this,LoginActivity.class);
            startActivity(intent);

        }



        final ListAPI service = ListAPI.retrofit.create(ListAPI.class);
        employeeAdapter = new EmployeeAdapter(getApplicationContext(), EmployeeListReport);
        recyclerView = findViewById(R.id.recyclerView_employee);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(employeeAdapter);

        Call<List<TblEmployee>> Employees = service.GetEmployees(AdminId);
        Employees.enqueue(new Callback<List<TblEmployee>>() {
            @Override
            public void onResponse(Call<List<TblEmployee>> call, Response<List<TblEmployee>> response) {
                EmployeeListReport = response.body();
                employeeAdapter.setList(EmployeeListReport);
            }

            @Override
            public void onFailure(Call<List<TblEmployee>> call, Throwable t) {
                Log.i("TAG", "onFailure: " + t);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                Call<String[]> getOrgCode = service.GetOrgCode(AdminId);
                getOrgCode.enqueue(new Callback<String[]>() {
                    @Override
                    public void onResponse(Call<String[]> call, Response<String[]> response) {
                        String[] orgCode = response.body();
                        Log.i("TAG", "onResponse: " + orgCode[0]);
                        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                        shareIntent.setType("text/html");
                        //  shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Test Mail");
                        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "OrganicationCode\n " + orgCode[0] + "\n and playstore Link for employee");
                        startActivity(Intent.createChooser(shareIntent, "Share"));
                    }

                    @Override
                    public void onFailure(Call<String[]> call, Throwable t) {
                        Log.i("TAG", "onFailure: " + t);
                    }
                });

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_menu, menu);
//        MenuItem cart_view = menu.findItem(R.id.navigation_cart);
        MenuItem searchView = menu.findItem(R.id.navigation_search);
        SearchView searchView1 = (SearchView) searchView.getActionView();
        searchView1.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                employeeAdapter.getFilter().filter(newText);

                return false;
            }
        });
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
