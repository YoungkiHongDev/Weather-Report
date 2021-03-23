package com.example.weatherreport;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionActivity extends Activity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        ImageView init_icon = (ImageView)findViewById(R.id.icon_init);
        init_icon.setColorFilter(Color.WHITE);
        checkSelfPermission();
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        //권한을 허용 했을 경우
        if(requestCode == 1)
        {
            int length = permissions.length;
            for (int i = 0; i < length; i++)
            {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                {
                    // 동의
                    startActivity(new Intent(this,MainActivity.class));
                    finish();
                }
                else
                {
                    finish();
                }
            }
        }
    }
    public void checkSelfPermission()
    {
        String temp = "";
        //파일 읽기 권한 확인
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            temp += Manifest.permission.ACCESS_FINE_LOCATION + " ";
        } //파일 쓰기 권한 확인
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            temp += Manifest.permission.ACCESS_COARSE_LOCATION + " ";
        }
        if (TextUtils.isEmpty(temp) == false)
        {
            // 권한 요청
            ActivityCompat.requestPermissions(this, temp.trim().split(" "),1);
        }
        else
        {
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
    }




}

