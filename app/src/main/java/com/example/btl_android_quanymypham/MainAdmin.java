package com.example.btl_android_quanymypham;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.btl_android_quanymypham.fragment.ChiTietHoaDonNhapFragmentAdmin;
import com.example.btl_android_quanymypham.fragment.HoaDonNhapFragmentAdmin;
import com.example.btl_android_quanymypham.fragment.HomeFragmentUser;
import com.example.btl_android_quanymypham.fragment.LoaiMyPhamFragmentAdmin;
import com.example.btl_android_quanymypham.fragment.NhaCungCapFragmentAdmin;
import com.example.btl_android_quanymypham.fragment.TTMyPhamFragmentAdmin;
import com.example.btl_android_quanymypham.fragment.TaiKhoanFragmentAdmin;
import com.example.btl_android_quanymypham.model.ProductHomeUser;
import com.example.btl_android_quanymypham.model.TaiKhoanAdmin;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainAdmin extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private static final int FragmentTTmypham = 0;
    private static final int FragmentLoaimypham = 1;
    private static final int FragmentNhaCungCap = 2;
    private static final int FragmentHoaDonBan = 3;
    private static final int FragmentHoaDonNhap = 4;
    private static final int FragmentThongKe = 5;
    private static final int FragmentTaiKhoan = 6;

    private int mCurrentFragment = FragmentTTmypham;

    TextView nameUser,emailUser;
    ImageView imgUser;

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
        getSupportActionBar().setTitle("Thông tin mỹ phẩm");
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

    private void replaceFragment(Fragment fragment) {
        TaiKhoanAdmin taiKhoanAdmin = (TaiKhoanAdmin) getIntent().getSerializableExtra("ObjectUser");

        Bundle bundle = new Bundle();
        bundle.putSerializable("ObjectUser", taiKhoanAdmin);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_ttmypham){
            if (mCurrentFragment!=FragmentTTmypham){
                replaceFragment(new TTMyPhamFragmentAdmin());
                mCurrentFragment = FragmentTTmypham;
                getSupportActionBar().setTitle("Thông tin mỹ phẩm");
            }
        }
        else if (id == R.id.nav_loaimypham){
            if (mCurrentFragment!=FragmentLoaimypham){
                replaceFragment(new LoaiMyPhamFragmentAdmin());
                mCurrentFragment = FragmentLoaimypham;
                getSupportActionBar().setTitle("Loại mỹ phẩm");
            }
        }
        else if (id == R.id.nav_nhacungcap){
            if (mCurrentFragment!=FragmentNhaCungCap){
                replaceFragment(new NhaCungCapFragmentAdmin());
                mCurrentFragment = FragmentNhaCungCap;
                getSupportActionBar().setTitle("Nhà cung cấp");
            }
        }
        else if (id == R.id.nav_hoadonban){
            if (mCurrentFragment!=FragmentHoaDonBan){
                replaceFragment(new ChiTietHoaDonNhapFragmentAdmin());
                mCurrentFragment = FragmentHoaDonBan;
                getSupportActionBar().setTitle("Hoá đơn bán");
            }
        }
        else if (id == R.id.nav_hoadonnhap){
            if (mCurrentFragment!=FragmentHoaDonNhap){
                replaceFragment(new HoaDonNhapFragmentAdmin());
                mCurrentFragment = FragmentHoaDonNhap;
                getSupportActionBar().setTitle("Hoá đơn nhập");
            }
        }
        else if (id == R.id.nav_taikhoan){
            if (mCurrentFragment!=FragmentTaiKhoan){
                replaceFragment(new TaiKhoanFragmentAdmin());
                mCurrentFragment = FragmentTaiKhoan;
                getSupportActionBar().setTitle("Tài khoản");
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

//    private void replaceFragment(Fragment fragment){
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.content_frame,fragment);
//        transaction.commit();
//    }
}