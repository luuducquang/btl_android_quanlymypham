package com.example.btl_android_quanymypham;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.btl_android_quanymypham.fragment.HomeFragmentUser;
import com.example.btl_android_quanymypham.fragment.LoaiMyPhamFragmentAdmin;
import com.example.btl_android_quanymypham.fragment.TTMyPhamFragmentAdmin;
import com.google.android.material.navigation.NavigationView;

public class MainAdmin extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private static final int FragmentTTmypham = 0;
    private static final int FragmentLoaimypham = 1;

    private int mCurrentFragment = FragmentTTmypham;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                MainAdmin.this,mDrawerLayout,toolbar,R.string.open_nav,R.string.close_nav
        );

        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView  navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(MainAdmin.this);

        replaceFragment(new TTMyPhamFragmentAdmin());
        navigationView.getMenu().findItem(R.id.nav_ttmypham).setChecked(true);

        View headerView = navigationView.getHeaderView(0);
        Button logout = headerView.findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainAdmin.this,Login.class);
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(it);
            }
        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_ttmypham){
            if (mCurrentFragment!=FragmentTTmypham){
                replaceFragment(new TTMyPhamFragmentAdmin());
                mCurrentFragment = FragmentTTmypham;
            }
        }
        else if (id == R.id.nav_loaimypham){
            if (mCurrentFragment!=FragmentLoaimypham){
                replaceFragment(new LoaiMyPhamFragmentAdmin());
                mCurrentFragment = FragmentLoaimypham;
            }
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame,fragment);
        transaction.commit();
    }
}