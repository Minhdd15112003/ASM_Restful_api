package com.example.asm_resfulapi.APIservice;

import com.example.asm_resfulapi.Model.ProductModel;
import com.example.asm_resfulapi.Model.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductServices {

    @GET("productHome")
    Call<List<ProductModel>> getProduct();


}
