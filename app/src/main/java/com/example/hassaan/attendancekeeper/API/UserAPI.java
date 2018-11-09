package com.example.hassaan.attendancekeeper.API;

import com.example.hassaan.attendancekeeper.Model.TblEmployee;
import com.example.hassaan.attendancekeeper.Model.TblLogin;
import com.example.hassaan.attendancekeeper.Model.TblOrgCode;
import com.example.hassaan.attendancekeeper.Model.TblQRCode;
import com.example.hassaan.attendancekeeper.Model.TblUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserAPI {

//    public static String BaseURL = "http://192.168.43.44:8000/api/";
    String BaseURL = "http://arascow.com/newapi/public/api/";

    @POST("login")
    @FormUrlEncoded
    Call<TblLogin> Login(@Field("email") String email,
                         @Field("password") String password
    );

    @POST("register")
    @FormUrlEncoded
    Call<TblLogin> AddUser(@Field("name") String Name,
                           @Field("email") String Email,
                           @Field("password") String Password,
                           @Field("password_confirmation") String ConfirmPass
    );

    @POST("admins")
    @FormUrlEncoded
    Call<TblUser> AddAdmin(@Field("Name") String name,
                           @Field("Username") String Username,
                           @Field("DOB") String Dob,
                           @Field("Number") String Number,
                           @Field("OrgCodeId") String OrgId,
                           @Field("UserId") String UserId,
                           @Field("TotalEmployee") int Employees
    );

    @POST("org_codes")
    @FormUrlEncoded
    Call<TblOrgCode> AddOrg(@Field("Org_code") String Code,
                            @Field("Org_name") String Name,
                            @Field("Address") String address,
                            @Field("Country") String Country
    );

    @GET("admins/getAdminId/{userId}")
    Call<List<TblUser>> getAdminId(@Path("userId") String UserId);

    @POST("qr_codes")
    @FormUrlEncoded
    Call<TblQRCode> AddQRCode(@Field("CodeString") String CodeString);


    @PUT("employees/{EmployeeId}")
            Call<TblEmployee> EditEmployee (@Path("EmployeeId") int Id,
                                            @Field("EmployeeId") int Id2);

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BaseURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
}
