package com.example.asm_resfulapi.APIservice;

import com.example.asm_resfulapi.Model.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserServices {

    @GET("getUser")
    Call<UserModel> getUser(@Path("id") String user);
    @POST("updateUser")
    Call<UserModel> updateUser(@Body UserModel userModel);
    @POST("loginUser")
    Call<UserModel> postLoginUser(@Body UserModel userModel);

    @POST("insertUser")
    Call<UserModel> postRegisternUser (@Body UserModel userModel);
}
