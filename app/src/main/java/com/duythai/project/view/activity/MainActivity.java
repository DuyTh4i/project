package com.duythai.project.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.duythai.project.R;
import com.duythai.project.view.fragment.AddTaskDialogFragment;
import com.duythai.project.view.fragment.CategoryFragment;
import com.duythai.project.view.fragment.NormalFragment;
import com.duythai.project.view.fragment.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity{

    FloatingActionButton fabAdd;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();

        fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(view -> {
            AddTaskDialogFragment addTaskFragment = new AddTaskDialogFragment();
            addTaskFragment.show(fragmentManager, "AddTaskDialogFragment");
        });

        NormalFragment normalFragment = new NormalFragment();
        CategoryFragment categoryFragment = new CategoryFragment();
        SettingFragment settingFragment = new SettingFragment();

        BottomNavigationView bnvMenu = findViewById(R.id.BNVmenu);
        bnvMenu.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.normal){
                getSupportFragmentManager().beginTransaction().replace(R.id.container, normalFragment).commit();
                return true;
            } else if(item.getItemId() == R.id.category){
                getSupportFragmentManager().beginTransaction().replace(R.id.container, categoryFragment).commit();
                return true;
            } else if(item.getItemId() == R.id.setting){
                getSupportFragmentManager().beginTransaction().replace(R.id.container, settingFragment).commit();
                return true;
            }
            return false;
        });

        if(savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().replace(R.id.container, normalFragment).commit();
    }
}