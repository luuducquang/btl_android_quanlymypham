package com.example.btl_android_quanymypham;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.btl_android_quanymypham.fragment.HomeFragment;
import com.example.btl_android_quanymypham.fragment.TTMyPhamFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;

public class main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;

    private static final int FragmentHome = 0;
    private static final int FragmentTTmypham = 1;

    private int mCurrentFragment = FragmentHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                main.this,mDrawerLayout,toolbar,R.string.open_nav,R.string.close_nav
        );

        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView  navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(main.this);

//        replaceFragment(new HomeFragment());
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);

        searchnow();

    }
    ArrayAdapter<String> adapter;
    ArrayList<String> array = new ArrayList<>();
    private void searchnow(){
        ListView lvsearch = (ListView) findViewById(R.id.list_search_main);
        array.add("aasd");
        array.add("124e1");
        array.add("123gg");

        adapter = new ArrayAdapter<>(
                main.this, android.R.layout.simple_expandable_list_item_1,array
        );
        lvsearch.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home){
            if (mCurrentFragment!=FragmentHome){
                replaceFragment(new HomeFragment());
                mCurrentFragment = FragmentHome;
            }
        }
        else if (id == R.id.nav_ttmypham){
            if (mCurrentFragment!=FragmentTTmypham){
                replaceFragment(new TTMyPhamFragment());
                mCurrentFragment = FragmentTTmypham;
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