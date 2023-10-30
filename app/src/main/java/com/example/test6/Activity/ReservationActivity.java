package com.example.test6.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.test6.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ReservationActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        bottomNavigation();

        ConstraintLayout reservation1 = findViewById(R.id.reservation1);
        reservation1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ReservationActivity.this);
                builder.setTitle("테이블 선택")
                        .setMessage("1번 테이블을 예약하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 예 버튼을 클릭했을 때 수행할 동작
                                Toast.makeText(getApplicationContext(), "예 버튼을 클릭했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 아니오 버튼을 클릭했을 때 수행할 동작
                                Toast.makeText(getApplicationContext(), "아니오 버튼을 클릭했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 취소 버튼을 클릭했을 때 수행할 동작
                                Toast.makeText(getApplicationContext(), "취소 버튼을 클릭했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        ConstraintLayout reservation2 = findViewById(R.id.reservation2);
        reservation2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ReservationActivity.this);
                builder.setTitle("테이블 선택")
                        .setMessage("2번 테이블을 예약하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 예 버튼을 클릭했을 때 수행할 동작
                                Toast.makeText(getApplicationContext(), "예 버튼을 클릭했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 아니오 버튼을 클릭했을 때 수행할 동작
                                Toast.makeText(getApplicationContext(), "아니오 버튼을 클릭했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 취소 버튼을 클릭했을 때 수행할 동작
                                Toast.makeText(getApplicationContext(), "취소 버튼을 클릭했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        int[] reservationIds = {R.id.reservation3, R.id.reservation4, R.id.reservation5};

        for (int i = 0; i < reservationIds.length; i++) {
            ConstraintLayout reservation = findViewById(reservationIds[i]);
            final int tableNumber = i + 3;

            reservation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ReservationActivity.this);
                    builder.setTitle("테이블 선택")
                            .setMessage("예약중인 테이블입니다. 테이블 번호: " + tableNumber)
                            .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 취소 버튼을 클릭했을 때 수행할 동작
                                    Toast.makeText(getApplicationContext(), "취소 버튼을 클릭했습니다.", Toast.LENGTH_SHORT).show();
                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }

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
                startActivity(new Intent(ReservationActivity.this, CartListActivity.class));
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReservationActivity.this, MainActivity.class));
            }
        });
        reservationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReservationActivity.this, ReservationActivity.class));
            }
        });
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReservationActivity.this, SettingActivity.class));
            }
        });
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLoggedIn()) {
                    startActivity(new Intent(ReservationActivity.this, MyPageActivity.class));
                } else {
                    startActivity(new Intent(ReservationActivity.this, LoginActivity.class));
                }
            }
        });
    }

    private boolean isLoggedIn() {
        // 로그인 상태를 확인하여 반환합니다.
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }
}