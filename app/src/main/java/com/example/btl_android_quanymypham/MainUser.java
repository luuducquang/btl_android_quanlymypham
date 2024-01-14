package com.example.btl_android_quanymypham;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btl_android_quanymypham.adapter.ProductHomeAdapterUser;
import com.example.btl_android_quanymypham.fragment.DetailProductFragmentUser;
import com.example.btl_android_quanymypham.fragment.ProductHomeFragmentUser;
import com.example.btl_android_quanymypham.fragment.TTMyPhamFragmentAdmin;
import com.example.btl_android_quanymypham.model.TTMyPhamAdmin;
import com.example.btl_android_quanymypham.model.TaiKhoanAdmin;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainUser extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private static final int FragmentHome = 0;
    private static final int FragmentCategory = 1;
    private int mCurrentFragment = FragmentHome;
    TextView nameUser,emailUser;
    ImageView imgUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");

        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                MainUser.this,mDrawerLayout,toolbar,R.string.open_nav,R.string.close_nav
        );

        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView  navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(MainUser.this);

        replaceFragment(new ProductHomeFragmentUser());
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);

        View headerView = navigationView.getHeaderView(0);
        Button logout = headerView.findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainUser.this,Login.class);
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(it);
            }
        });
        

        imgUser = headerView.findViewById(R.id.img_user);
        nameUser = headerView.findViewById(R.id.name_user);
        emailUser = headerView.findViewById(R.id.email_user);

        Bundle bundle = getIntent().getExtras();
        if (bundle==null){
            return;
        }
        TaiKhoanAdmin taiKhoanAdmin = (TaiKhoanAdmin) bundle.get("ObjectUser");
        byte[]anhdaidien = taiKhoanAdmin.getAnhdaidien();
        Bitmap bitmap = BitmapFactory.decodeByteArray(anhdaidien,0,anhdaidien.length);
        imgUser.setImageBitmap(bitmap);
        nameUser.setText(taiKhoanAdmin.getHoten());
        emailUser.setText(taiKhoanAdmin.getEmail());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        getMenuInflater().inflate(R.menu.menu_shop_user,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.action_cart){
            Toast.makeText(MainUser.this,"Quan ly cua hang",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home){
            if (mCurrentFragment!=FragmentHome){
                replaceFragment(new ProductHomeFragmentUser());
                mCurrentFragment = FragmentHome;
            }
        }
        else if (id == R.id.nav_ttmypham){
            if (mCurrentFragment!=FragmentCategory){
                replaceFragment(new TTMyPhamFragmentAdmin());
                mCurrentFragment = FragmentCategory;
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