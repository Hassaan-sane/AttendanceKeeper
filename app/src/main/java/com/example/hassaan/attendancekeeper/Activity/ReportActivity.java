package com.example.hassaan.attendancekeeper.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.hassaan.attendancekeeper.API.ListAPI;
import com.example.hassaan.attendancekeeper.Adapter.DashboardAdapter;
import com.example.hassaan.attendancekeeper.Adapter.ReportAdapter;
import com.example.hassaan.attendancekeeper.Model.TblAttendance;
import com.example.hassaan.attendancekeeper.Model.TblEmployee;
import com.example.hassaan.attendancekeeper.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportActivity extends AppCompatActivity {

    private List<TblAttendance> AttendanceList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ListAPI service = ListAPI.retrofit.create(ListAPI.class);

        final ReportAdapter reportAdapter = new ReportAdapter(getApplicationContext(), AttendanceList);
        recyclerView = findViewById(R.id.recyclerView_report);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(reportAdapter);

        Call<List<TblAttendance>> GetAttendance = service.GetAttendance();
        GetAttendance.enqueue(new Callback<List<TblAttendance>>() {
            @Override
            public void onResponse(Call<List<TblAttendance>> call, Response<List<TblAttendance>> response) {
                AttendanceList = response.body();
                Log.i("TAG", "onResponse in Report: " + response);
                reportAdapter.setList(AttendanceList);
            }

            @Override
            public void onFailure(Call<List<TblAttendance>> call, Throwable t) {

                Log.i("TAG", "onFailure: "+ t);
            }
        });
    }
}
