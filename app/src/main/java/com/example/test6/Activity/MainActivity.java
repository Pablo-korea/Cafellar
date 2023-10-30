package com.example.test6.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test6.Adaptor.CategoryAdaptor;
import com.example.test6.Adaptor.PopularAdaptor;
import com.example.test6.Domain.CategoryDomain;
import com.example.test6.Domain.FoodDomain;
import com.example.test6.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private RecyclerView.Adapter adapter, adapter2;
    private RecyclerView recyclerViewCategoryList, recyclerViewPopularList;
    private Button loginbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);

        loginbutton = findViewById(R.id.loginbutton);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // LoginActivity로 화면 전환
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        recyclerViewCategory();
        recyclerViewPopular();
        bottomNavigation();
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
                startActivity(new Intent(MainActivity.this, CartListActivity.class));
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });
        reservationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ReservationActivity.class));
            }
        });
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TransferActivity.class));
            }
        });
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 로그인 여부를 확인하여 로그인이 된 상태인 경우에는 MyPage화면으로 넘어가고
                // 로그인 상태가 아니라면 Login 화면으로 넘어간다.
                if (isLoggedIn()) {
                    startActivity(new Intent(MainActivity.this, MyPageActivity.class));
                } else {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            }
        });
    }

    private boolean isLoggedIn() {
        // 로그인 상태를 확인하여 반환합니다.
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }

    private void recyclerViewCategory() {
        // 레이아웃 매니저를 이용하여 가로 스크롤을 사용하도록 설정하고, 아이템의 역순 배치가 아님을 나타냄.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList = findViewById(R.id.recyclerView);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        ArrayList<CategoryDomain> category = new ArrayList<>();
        // CategoryDomain 객체에서 ArrayList인 categoty를 생성한다.
        // add를 통해 각 객체의 이름과 사진을 category 리스트에 추가할 수 있다.
        category.add(new CategoryDomain("커피", "cat_1"));
        category.add(new CategoryDomain("빙수", "cat_2"));
        category.add(new CategoryDomain("빵", "cat_3"));
        category.add(new CategoryDomain("케이크", "cat_4"));
        category.add(new CategoryDomain("MD", "cat_5"));

        adapter = new CategoryAdaptor(category);
        recyclerViewCategoryList.setAdapter(adapter);
    }

    private void recyclerViewPopular() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopularList = findViewById(R.id.recyclerView2);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);

        ArrayList<FoodDomain> foodList = new ArrayList<>();
        foodList.add(new FoodDomain("아메리카노(ICE)", "americano", "아메리카노는 에스프레소 샷 두 개를 추출하여 바로 컵에 붓고, 그 위에 뜨거운 물을 재빠르게 부어 얇은 크레마 층이 형성되는 음료입니다.", 2500));
        foodList.add(new FoodDomain("크로플", "croffle", "크로플 (Crofle) 은 크로아상 (Croissant) + 와플 (Waffle)의 합성어로 크로아상 반죽으로 만든 와플을 뜻합니다. ", 4200));
        foodList.add(new FoodDomain("초코하우스 홀케이크", "chocohousecake", "마치 줄줄이 이어진 집들을 보여주는 데코레이션의 초코하우스케이크로 초코의 깊은 풍미와 부드러움을 느껴보세요.", 32000));

        adapter2 = new PopularAdaptor(foodList);
        recyclerViewPopularList.setAdapter(adapter2);
    }
}