package com.example.test6.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test6.Adaptor.CartListAdapter;
import com.example.test6.Helper.ManagementCart;
import com.example.test6.Interface.ChangeNumberItemsListener;
import com.example.test6.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CartListActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private ManagementCart managementCart;
    TextView totalFeeTxt, totalTxt, emptyTxt, stampTxt, balanceTxt, MystampTxt;
    private ScrollView scrollView;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        managementCart = new ManagementCart(this);
        TextView payBtn = findViewById(R.id.payBtn);

        initView();
        initList();
        CalculateCart();
        bottomNavigation();

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 잔액 정보 가져오기
                double balance = getBalance();
                int stamps = getStamps();

                // 총 결제 금액 가져오기
                double total = managementCart.getTotalFee();

                if (balance >= total) {
                    // 잔액이 충분하면 결제 처리
                    double remainingBalance = balance - total;
                    saveBalance(remainingBalance);

                    // 결제 완료 메시지 표시
                    Toast.makeText(CartListActivity.this, "결제가 완료되었습니다.", Toast.LENGTH_SHORT).show();

                    // 카트 비우기
                    managementCart.clearCart();

                    // 장바구니 목록 업데이트
                    adapter.notifyDataSetChanged();

                    // 장바구니가 비어있는지 확인하여 뷰 업데이트
                    if (managementCart.getListCart().isEmpty()) {
                        emptyTxt.setVisibility(View.VISIBLE);
                        scrollView.setVisibility(View.GONE);
                    } else {
                        emptyTxt.setVisibility(View.GONE);
                        scrollView.setVisibility(View.VISIBLE);
                    }

                    int expectingStamps = (int) (stamps + (total / 5000));
                    saveStampCount(expectingStamps);

                    // 잔액 및 스탬프 재계산
                    CalculateCart();
                } else {
                    // 잔액이 부족한 경우 메시지 표시
                    Toast.makeText(CartListActivity.this, "잔액이 부족합니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
                startActivity(new Intent(CartListActivity.this, CartListActivity.class));
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartListActivity.this, MainActivity.class));
            }
        });
        reservationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartListActivity.this, ReservationActivity.class));
            }
        });
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartListActivity.this, SettingActivity.class));
            }
        });
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLoggedIn()) {
                    startActivity(new Intent(CartListActivity.this, MyPageActivity.class));
                } else {
                    startActivity(new Intent(CartListActivity.this, LoginActivity.class));
                }
            }
        });
    }

    private void initView() {
        recyclerViewList = findViewById(R.id.recyclerView);
        totalFeeTxt = findViewById(R.id.totalFeeTxt);
        totalTxt = findViewById(R.id.totalTxt);
        emptyTxt = findViewById(R.id.emptyTxt);
        scrollView = findViewById(R.id.scrollView3);
        stampTxt = findViewById(R.id.stampTxt);
        recyclerViewList = findViewById(R.id.cartView);
        balanceTxt = findViewById(R.id.balanceTxt);
        MystampTxt = findViewById(R.id.MystampTxt);
    }

    private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        adapter = new CartListAdapter(managementCart.getListCart(), this, new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                CalculateCart();
            }
        });

        recyclerViewList.setAdapter(adapter);
        if (managementCart.getListCart().isEmpty()) {
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        } else {
            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }

    private void CalculateCart() {
        int total = (int) (Math.round(managementCart.getTotalFee() * 100) / 100);
        int itemTotal = (int) (Math.round(managementCart.getTotalFee() * 100) / 100);
        totalFeeTxt.setText(itemTotal + "₩");
        totalTxt.setText(total + "₩");
        stampTxt.setText(total / 5000 + "개");

        // 잔액 계산 및 표시
        int balance = (int) getBalance(); // 잔액 정보 가져오기
        int remainingBalance = balance - total;
        balanceTxt.setText(remainingBalance + "₩");

        // 스탬프 계산 및 표시
        int stamps = getStamps();
        int expectingStamps = stamps + (total / 5000);
        MystampTxt.setText(expectingStamps + "개");
    }

    private boolean isLoggedIn() {
        // 로그인 상태를 확인하여 반환합니다.
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }

    private double getBalance() {
        // SharedPreferences에서 잔액 정보 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        float balance = sharedPreferences.getFloat("balance", 0.0f);
        return (int) balance;
    }

    private int getStamps() {
        // SharedPreferences에서 스탬프 정보 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        return sharedPreferences.getInt("stamps", 0);
    }

    private void saveBalance(double balance) {
        // SharedPreferences에 잔액 정보 저장
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("balance", (float) balance);
        editor.apply();
    }

    private void saveStampCount(int stamps) {
        // SharedPreferences에 스탬프 정보 저장
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("stamps", stamps);
        editor.apply();
    }


}