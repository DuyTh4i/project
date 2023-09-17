package com.duythai.project.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.duythai.project.R;
import com.duythai.project.model.Task;
import com.duythai.project.view.fragment.AddTaskDialogFragment;
import com.duythai.project.view.fragment.CategoryFragment;
import com.duythai.project.view.fragment.NormalFragment;
import com.duythai.project.view.fragment.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements NormalFragment.OnTaskAddedListener, CategoryFragment.OnTaskAddedListener{

    FloatingActionButton fabAdd;
    Fragment currentFragment;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(view -> {
            AddTaskDialogFragment addTaskFragment = new AddTaskDialogFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            addTaskFragment.show(fragmentManager, "AddTaskDialogFragment");
        });

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
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

    @Override
    public void onTaskAddedCategory(Task task) {
        CategoryFragment categoryFragment = (CategoryFragment) getSupportFragmentManager().findFragmentById(R.id.rvPersonal);
        if(categoryFragment != null)
            categoryFragment.addTaskToList(task);
    }

    @Override
    public boolean onNavigationItemSelectedCategory(@NonNull MenuItem item) {
        return changeFrag(item);
    }

    @Override
    public void onTaskAddedNormal(Task task) {
        NormalFragment normalFragment = (NormalFragment) getSupportFragmentManager().findFragmentById(R.id.rvToday);
        if(normalFragment != null)
            normalFragment.addTaskToList(task);
    }

    private boolean changeFrag(MenuItem item){
        Fragment selectedFragment;
        if (item.getItemId() == R.id.normal)
            selectedFragment = new NormalFragment();
        else if (item.getItemId() == R.id.setting)
            selectedFragment = new SettingFragment();
        else if (item.getItemId() == R.id.category)
            selectedFragment = new CategoryFragment();
        else return false;

        if (selectedFragment != currentFragment) {
            currentFragment = selectedFragment;
            fragmentManager.beginTransaction()
                    .replace(R.id.container, selectedFragment)
                    .commit();
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelectedNormal(@NonNull MenuItem item) {
        return changeFrag(item);
    }
}