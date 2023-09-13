package com.duythai.project.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.duythai.project.R;
import com.duythai.project.view.fragment.CategoryFragment;
import com.duythai.project.view.fragment.NormalFragment;
import com.duythai.project.view.fragment.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    NormalFragment normal = new NormalFragment();
    CategoryFragment category = new CategoryFragment();
    SettingFragment setting = new SettingFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.BNVmenu);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, normal).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if(item.getItemId() == R.id.setting){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,setting).commit();
                    return true;
                }
                if(item.getItemId() == R.id.category){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,category).commit();
                    return true;
                }
                if(item.getItemId() == R.id.normal){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,normal).commit();
                    return true;
                }
                return false;
            }
        });
    }
}