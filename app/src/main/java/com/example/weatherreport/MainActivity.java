package com.example.weatherreport;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager = getSupportFragmentManager();
    BottomNavigationView bottomNavigationView;
    FragmentTransaction transaction;
    private TodayWeather todayWeather = new TodayWeather();
    private WeekWeather weekWeather = new WeekWeather();
    private AirPollution airPollution = new AirPollution();
    private City_Weather city_weather = new City_Weather();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationBar();
    }

    private void navigationBar()
    {

        transaction = fragmentManager.beginTransaction();

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        // 첫 화면 지정
        transaction.replace(R.id.frame_layout, todayWeather).commitAllowingStateLoss();

        // bottomNavigationView의 아이템이 선택될 때 호출될 리스너 등록
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.navigation_menu1: {
                        transaction.replace(R.id.frame_layout, todayWeather).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_menu2: {
                        transaction.replace(R.id.frame_layout, weekWeather).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_menu3: {
                        transaction.replace(R.id.frame_layout, airPollution).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_menu4: {
                        transaction.replace(R.id.frame_layout, city_weather).commitAllowingStateLoss();
                        break;
                    }

                }

                return true;
            }
        });
    }
}




