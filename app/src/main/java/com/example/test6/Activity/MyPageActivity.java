package com.example.test6.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test6.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class MyPageActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private TextView MystampTxt, balanceTxt, cash_5000_Btn, cash_10000_Btn, resetBtn, Logout_Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        MystampTxt = findViewById(R.id.MystampTxt);
        balanceTxt = findViewById(R.id.balanceTxt);
        cash_5000_Btn = findViewById(R.id.cash_5000_Btn);
        cash_10000_Btn = findViewById(R.id.cash_10000_Btn);
        resetBtn = findViewById(R.id.resetBtn);
        Logout_Btn = findViewById(R.id.Logout_Btn);

        bottomNavigation();
        displayBalance();
        displayStamps();
        redirectToLogin();

        cash_5000_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 잔액 증가 처리
                increaseBalance(5000);

                // 잔액 텍스트 업데이트
                displayBalance();
            }
        });

        cash_10000_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 잔액 증가 처리
                increaseBalance(10000);

                // 잔액 텍스트 업데이트
                displayBalance();
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // AlertDialog를 생성하여 "정말로 잔액을 초기화 하시겠습니까?"라는 메시지를 표시
                AlertDialog.Builder builder = new AlertDialog.Builder(MyPageActivity.this);
                builder.setMessage("정말 잔액을 초기화 하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // 예 버튼을 클릭한 경우 잔액 초기화 처리
                                resetBalance();

                                // 잔액 및 스탬프 텍스트 업데이트
                                displayBalance();
                                displayStamps();
                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // 아니오 버튼을 클릭한 경우 아무 작업 없이 다이얼로그를 닫음
                                dialog.dismiss();
                            }
                        });

                // AlertDialog를 생성하고 표시
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        Logout_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 로그아웃 처리
                logout();

                // 로그인 페이지로 이동
                redirectToLogin();
            }
        });
    }

    private void increaseBalance(float amount) {
        // SharedPreferences에서 잔액 정보 가져오기
        float balance = sharedPreferences.getFloat("balance", 0.0f);

        // 잔액 증가
        balance += amount;

        // 증가한 잔액 저장
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("balance", balance);
        editor.apply();
    }

    private void resetBalance() {
        // 잔액을 0으로 초기화
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("balance", 0.0f);
        editor.putInt("stamps", 0);
        editor.apply();
    }

    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout reservationBtn = findViewById(R.id.reservationBtn);
        LinearLayout settingBtn = findViewById(R.id.settingBtn);
        LinearLayout profileBtn = findViewById(R.id.profileBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyPageActivity.this, CartListActivity.class));
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyPageActivity.this, MainActivity.class));
            }
        });
        reservationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyPageActivity.this, ReservationActivity.class));
            }
        });
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyPageActivity.this, SettingActivity.class));
            }
        });
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLoggedIn()) {
                    startActivity(new Intent(MyPageActivity.this, MyPageActivity.class));
                } else {
                    startActivity(new Intent(MyPageActivity.this, LoginActivity.class));
                }
            }
        });
    }

    private boolean isLoggedIn() {
        // 로그인 상태를 확인하여 반환합니다.
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }

    private void logout() {
        // 로그아웃 처리
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", false);
        editor.apply();
    }

    private void redirectToLogin() {
        if (!isLoggedIn()) {
            // 로그인 페이지로 이동합니다.
            Intent intent = new Intent(MyPageActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // 현재 MyPageActivity 종료
        }
    }

    private void displayBalance() {
        // SharedPreferences에서 잔액 정보 가져오기
        float balance = sharedPreferences.getFloat("balance", 0.0f);
        int balanceInt = (int) balance;
        balanceTxt.setText(String.format(Locale.getDefault(), "%d₩", balanceInt));
    }

    private void displayStamps() {
        // SharedPreferences에서 스탬프 정보 가져오기
        int stamps = sharedPreferences.getInt("stamps", 0);
        MystampTxt.setText(String.format(Locale.getDefault(), "%d개", stamps));
    }

}