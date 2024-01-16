package com.example.btl_android_quanymypham;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import com.example.btl_android_quanymypham.fragment.DoiMatKhauFragmentUser;
import com.example.btl_android_quanymypham.fragment.GioHangFragmentUser;
import com.example.btl_android_quanymypham.fragment.HoaDonFragmentUser;
import com.example.btl_android_quanymypham.fragment.ProductHomeFragmentUser;
import com.example.btl_android_quanymypham.fragment.TTMyPhamFragmentAdmin;
import com.example.btl_android_quanymypham.fragment.ThongTinTaiKhoanFragmentUser;
import com.example.btl_android_quanymypham.model.TTMyPhamAdmin;
import com.example.btl_android_quanymypham.model.TaiKhoanAdmin;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainUser extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private static final int FragmentHome = 0;
    private static final int FragmentDonHang = 1;
    private static final int FragmentThongtinTK = 2;
    private static final int FragmentDoiMK = 3;
    private int mCurrentFragment = FragmentHome;
    TextView nameUser,emailUser;
    ImageView imgUser;
    TaiKhoanAdmin taiKhoanAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Trang chủ");

        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                MainUser.this,mDrawerLayout,toolbar,R.string.open_nav,R.string.close_nav
        );

        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView  navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(MainUser.this);

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
        taiKhoanAdmin = (TaiKhoanAdmin) bundle.get("ObjectUser");
        byte[]anhdaidien = taiKhoanAdmin.getAnhdaidien();
        Bitmap bitmap = BitmapFactory.decodeByteArray(anhdaidien,0,anhdaidien.length);
        imgUser.setImageBitmap(bitmap);
        nameUser.setText(taiKhoanAdmin.getHoten());
        emailUser.setText(taiKhoanAdmin.getEmail());

        replaceFragment(new ProductHomeFragmentUser());
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shop_user,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.action_cart){
            GioHangFragmentUser gioHangFragmentUser = new GioHangFragmentUser();
            AppCompatActivity activity = (AppCompatActivity) this;
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, gioHangFragmentUser)
                    .addToBackStack(null)
                    .commit();

            Bundle bundle = new Bundle();
            bundle.putSerializable("ObjectUser", taiKhoanAdmin);
            gioHangFragmentUser.setArguments(bundle);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home){
            replaceFragment(new ProductHomeFragmentUser());
            mCurrentFragment = FragmentHome;
            this.getSupportFragmentManager().popBackStack();
            getSupportActionBar().setTitle("Trang chủ");

        }
        else if (id == R.id.nav_donhang){
            replaceFragment(new HoaDonFragmentUser());
            mCurrentFragment = FragmentDonHang;
            this.getSupportFragmentManager().popBackStack();
            getSupportActionBar().setTitle("Đơn hàng của bạn");

        }
        else if (id == R.id.nav_taikhoan){
            replaceFragment(new ThongTinTaiKhoanFragmentUser());
            mCurrentFragment = FragmentThongtinTK;
            this.getSupportFragmentManager().popBackStack();
            getSupportActionBar().setTitle("Thông tin tài khoản");

        }
        else if (id == R.id.nav_changepass){
            replaceFragment(new DoiMatKhauFragmentUser());
            mCurrentFragment = FragmentDoiMK;
            this.getSupportFragmentManager().popBackStack();
            getSupportActionBar().setTitle("Đổi mật khẩu");

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
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        Bundle bundle = new Bundle();
        bundle.putSerializable("ObjectUser", taiKhoanAdmin);
        fragment.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame,fragment);
        transaction.commit();
    }
}