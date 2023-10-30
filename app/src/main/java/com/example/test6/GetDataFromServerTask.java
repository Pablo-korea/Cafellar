package com.example.test6;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetDataFromServerTask extends AsyncTask<Void, Void, String> {

    private static final String TAG = "GetDataFromServerTask";
    private static final String SERVER_URL = "http://3.39.24.46/process.php";
    private static final String PARAM_DISTANCE = "distance";
    private static final String PARAM_USED = "used";
    private static final String PARAM_TIMER = "timer";

    private String distance;
    private String used;
    private String timer;
    private OnDataReceivedListener listener;

    public GetDataFromServerTask(String distance, String used, String timer, OnDataReceivedListener listener) {
        this.distance = distance;
        this.used = used;
        this.timer = timer;
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String response = null;

        try {
            // GET 요청 URL 생성
            StringBuilder urlBuilder = new StringBuilder(SERVER_URL);
            urlBuilder.append("?").append(PARAM_DISTANCE).append("=").append(distance);
            urlBuilder.append("&").append(PARAM_USED).append("=").append(used);
            urlBuilder.append("&").append(PARAM_TIMER).append("=").append(timer);
            URL url = new URL(urlBuilder.toString());

            // HttpURLConnection을 사용하여 GET 요청 전송
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // 응답 코드 확인
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 응답 데이터 읽기
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder responseBuilder = new StringBuilder();
                String line;
                try {
                    while ((line = reader.readLine()) != null) {
                        responseBuilder.append(line);
                    }
                } finally {
                    reader.close();
                }
                response = responseBuilder.toString();
            } else {
                // 응답이 실패한 경우에 대한 처리
                Log.e(TAG, "Response Code: " + responseCode);
            }

            // 연결 종료
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            // 에러 처리
            Log.e(TAG, "Error: " + e.getMessage());
        }

        return response;
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        if (listener != null) {
            listener.onDataReceived(response);
        }
    }

    public interface OnDataReceivedListener {
        void onDataReceived(String response);
    }
}
