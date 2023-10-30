package com.example.test6.Activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test6.GetDataFromServerTask;
import com.example.test6.R;

import org.json.JSONException;
import org.json.JSONObject;

public class TransferActivity extends AppCompatActivity implements GetDataFromServerTask.OnDataReceivedListener {
    private TextView usedTxt;
    private TextView distanceTxt;
    private TextView timerTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        usedTxt = findViewById(R.id.usedTxt);
        distanceTxt = findViewById(R.id.distanceTxt);
        timerTxt = findViewById(R.id.timerTxt);
    }

    @Override
    public void onDataReceived(String response) {
        // 응답 데이터 처리
        if (response != null) {
            try {
                JSONObject jsonResponse = new JSONObject(response);
                String distance = jsonResponse.getString("distance");
                String used = jsonResponse.getString("used");
                String timer = jsonResponse.getString("timer");

                // 가져온 값을 사용하여 원하는 작업 수행
                // 예: 텍스트뷰에 값을 설정
                usedTxt.setText(used);
                distanceTxt.setText(distance);
                timerTxt.setText(timer);

                // ...
            } catch (JSONException e) {
                e.printStackTrace();
                // JSON 파싱 오류 처리
                // TODO: JSON 파싱 오류가 발생한 경우에 대한 처리를 추가하세요.
            }
        } else {
            // 응답이 없는 경우에 대한 처리
            // TODO: 응답이 없는 경우에 대한 처리를 추가하세요.
        }
    }

}
