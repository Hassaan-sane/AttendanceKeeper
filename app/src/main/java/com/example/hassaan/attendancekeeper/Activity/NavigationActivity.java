package com.example.hassaan.attendancekeeper.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hassaan.attendancekeeper.API.ListAPI;
import com.example.hassaan.attendancekeeper.Adapter.DashboardAdapter;
import com.example.hassaan.attendancekeeper.CSVUtils;
import com.example.hassaan.attendancekeeper.Fragment.ReportFragment;
import com.example.hassaan.attendancekeeper.Model.TblAdminxEmployeexAttendance;
import com.example.hassaan.attendancekeeper.Model.TblAttendance;
import com.example.hassaan.attendancekeeper.Model.TblEmployee;
import com.example.hassaan.attendancekeeper.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String MY_PREFS_NAME = "AdminFile";
    //    private SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
    private String emailKey = "email";
    private String OrgKey = "OrgCode";
    private String AdminIdKey = "AdminId";
    private String NameKey = "Name";
    private List<TblAdminxEmployeexAttendance> EmployeeList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView tv_CheckedIn, tv_CheckedInTotal;
    boolean doubleBackToExitPressedOnce = false;
    private DashboardAdapter dashboardAdapter;
    SharedPreferences prefs;
    TextView tv_Name, tv_Email;

    SwipeRefreshLayout swipeRefreshLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        prefs = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        final String OrgCode = prefs.getString(OrgKey, null);
        final String email = prefs.getString(emailKey, null);
        final String Name= prefs.getString(NameKey,null);
        final int AdminId = prefs.getInt(AdminIdKey, 0);

        if (email==null) {
            Intent intent = new Intent(NavigationActivity.this,LoginActivity.class);
            startActivity(intent);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);
        tv_Name = hView.findViewById(R.id.textView1);
        tv_Email = hView.findViewById(R.id.textView);

        tv_Email.setText(email + "");
        tv_Name.setText(Name+"");

        final ListAPI service = ListAPI.retrofit.create(ListAPI.class);

        dashboardAdapter = new DashboardAdapter(getApplicationContext(), EmployeeList);
        recyclerView = findViewById(R.id.recyclerView_dashboard);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(dashboardAdapter);

        tv_CheckedIn = findViewById(R.id.tv_checkedIn);
        tv_CheckedInTotal = findViewById(R.id.tv_checkedIn_total);

        Call<ArrayList<TblAdminxEmployeexAttendance>> getEmployeesAttendance = service.GetEmployeeAttendance(AdminId);
        getEmployeesAttendance.enqueue(new Callback<ArrayList<TblAdminxEmployeexAttendance>>() {
            @Override
            public void onResponse(Call<ArrayList<TblAdminxEmployeexAttendance>> call, Response<ArrayList<TblAdminxEmployeexAttendance>> response) {
                EmployeeList = response.body();
                Log.i("TAG", "onResponse on Dash: " + response);
                if (!EmployeeList.isEmpty()) {
                    dashboardAdapter.setList(EmployeeList);

                    tv_CheckedIn.setText("" + EmployeeList.size());
                    Log.i("TAG", "onResponse: " + EmployeeList.size());
                } else {
                    Toast.makeText(NavigationActivity.this, "No Employees to show", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<TblAdminxEmployeexAttendance>> call, Throwable t) {
                Log.i("TAG", "onFailureDash: " + t);
            }
        });
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout_cart);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Call<ArrayList<TblAdminxEmployeexAttendance>> getEmployeesAttendance = service.GetEmployeeAttendance(AdminId);
                getEmployeesAttendance.enqueue(new Callback<ArrayList<TblAdminxEmployeexAttendance>>() {
                    @Override
                    public void onResponse(Call<ArrayList<TblAdminxEmployeexAttendance>> call, Response<ArrayList<TblAdminxEmployeexAttendance>> response) {
                        EmployeeList = response.body();
                        Log.i("TAG", "onResponse on Dash: " + response);
                        if (!EmployeeList.isEmpty()) {
                            dashboardAdapter.setList(EmployeeList);

                            tv_CheckedIn.setText("" + EmployeeList.size());
                            Log.i("TAG", "onResponse: " + EmployeeList.size());
                            swipeRefreshLayout.setRefreshing(false);
                        } else {
                            Toast.makeText(NavigationActivity.this, "No Employees to show", Toast.LENGTH_SHORT).show();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<TblAdminxEmployeexAttendance>> call, Throwable t) {
                        Log.i("TAG", "onFailureDash: " + t);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
//                super.onBackPressed();

                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    moveTaskToBack(true);
                    doubleBackToExitPressedOnce = false;
                }
            }, 1000);
//            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            // Handle the camera action
            getIntent();

        } else if (id == R.id.nav_employee) {
            Intent intent = new Intent(NavigationActivity.this, EmployeeActivity.class);
            startActivity(intent);

//        } else if (id == R.id.nav_report) {
////            transaction.replace(R.id.maincontainer, new ReportFragment()).commit();
//            Intent intent = new Intent(NavigationActivity.this, ReportActivity.class);
//            startActivity(intent);

        } else if (id == R.id.nav_setting) {
            Intent intent = new Intent(NavigationActivity.this, SettingActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_logout) {
            final SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
            editor.remove(OrgKey);
            editor.remove(emailKey);
            editor.apply();
            Intent intent = new Intent(NavigationActivity.this, LoginActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                dashboardAdapter.getFilter().filter(newText);

                return false;
            }
        });
        return true;
    }

}
