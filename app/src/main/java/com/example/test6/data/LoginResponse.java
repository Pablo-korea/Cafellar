package com.example.test6.data;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("userId")
    private int userId;

    public boolean isSuccessful() {
        // 예를 들어, 응답 코드가 200이면 성공으로 간주할 수 있습니다.
        return code == 200;
    }

    public int getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }

    public int getUserId() {
        return userId;
    }
}
