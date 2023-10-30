package com.example.test6.network;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private final static String BASE_URL = "http://ec2-54-180-112-118.ap-northeast-2.compute.amazonaws.com:3000";
    private static Retrofit retrofit = null;

    private RetrofitClient() {
    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

    public static ServiceApi getServiceApi() {
        Retrofit retrofit = getClient();
        return retrofit.create(ServiceApi.class);
    }

    public void fetchData() {
        ServiceApi serviceApi = RetrofitClient.getServiceApi();

        Call<DataModel> call = serviceApi.getUserData();
        call.enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                if (response.isSuccessful()) {
                    DataModel data = response.body();
                    // 데이터 값을 사용하여 원하는 작업을 수행합니다.
                    float distance = data.getDistance();
                    int used = data.getUsed();
                    int timer = data.getTimer();
                    // 데이터 값을 사용하여 필요한 로직을 처리합니다.
                } else {
                    // 응답이 실패한 경우에 대한 처리를 수행합니다.
                }
            }

            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {
                // 네트워크 오류 또는 서버와의 통신 실패에 대한 처리를 수행합니다.
            }
        });
    }
}
