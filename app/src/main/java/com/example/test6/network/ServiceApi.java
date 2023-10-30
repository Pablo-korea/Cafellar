package com.example.test6.network;

import com.example.test6.data.JoinData;
import com.example.test6.data.JoinResponse;
import com.example.test6.data.LoginData;
import com.example.test6.data.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServiceApi {
    @POST("/user/login")
    Call<LoginResponse> userLogin(@Body LoginData data);
    @POST("/user/join")
    Call<JoinResponse> userJoin(@Body JoinData data);
    @GET("/user/data")
    Call<DataModel> getUserData();
}
