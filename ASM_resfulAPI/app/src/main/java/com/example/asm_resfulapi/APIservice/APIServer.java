package com.example.asm_resfulapi.APIservice;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIServer {
    public static Retrofit retrofit;

    public static Retrofit getServer(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://172.18.128.1:3001/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
