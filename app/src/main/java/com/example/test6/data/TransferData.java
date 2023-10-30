package com.example.test6.data;

import android.content.SharedPreferences;

import com.google.gson.annotations.SerializedName;

public class TransferData {
    @SerializedName("userID")
    String userID;

    @SerializedName("userEmail")
    String userEmail;

    @SerializedName("userPwd")
    String userPwd;

    @SerializedName("userName")
    String userName;

    @SerializedName("userBalance")
    String userBalance;

    @SerializedName("userStamps")
    String userStamps;

    public TransferData(SharedPreferences sharedPreferences) {
        this.userID = sharedPreferences.getString("userID", "");
        this.userEmail = sharedPreferences.getString("userEmail", "");
        this.userPwd = sharedPreferences.getString("userPwd", "");
        this.userName = sharedPreferences.getString("userName", "");
        this.userBalance = String.valueOf(sharedPreferences.getFloat("userBalance", 0.0f));
        this.userStamps = String.valueOf(sharedPreferences.getInt("userStamps", 0));
    }
}
