package com.example.hassaan.attendancekeeper.API;

import com.example.hassaan.attendancekeeper.Model.TblAdminxEmployeexAttendance;
import com.example.hassaan.attendancekeeper.Model.TblAttendance;
import com.example.hassaan.attendancekeeper.Model.TblEmployee;
import com.example.hassaan.attendancekeeper.Model.TblOrgCode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ListAPI {

//    String BaseURL = "http://192.168.43.44:8000/api/";

    String BaseURL = "http://arascow.com/newapi/public/api/";

    //////Enter Admin Id to get All employees Under that admin from employee Table
    @GET("employees/EmployeeOf/{employees}")
    Call<List<TblEmployee>> GetEmployees(@Path("employees") int AdminId);


    ////// Enter Admin Id to get the CHeckedin employee of that admin
    @GET("attendances/CheckedIn/{attendance}")
    Call<ArrayList<TblAdminxEmployeexAttendance>> GetEmployeeAttendance(@Path("attendance") int AdminId );


    ////// Enter EmployeeId whose report need to be generated
    @GET("attendances/Report/{id}")
    Call<List<TblAdminxEmployeexAttendance>> GetEmployeeReport(@Path("id") int EmployeeId );

///////Enter AdminId to get OrgCode
    @GET("admins/OrgCode/{adminId}")
    Call<String[]> GetOrgCode(@Path("adminId") int AdminId);

    @GET("attendances")
    Call<List<TblAttendance>> GetAttendance();


    Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BaseURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

}
