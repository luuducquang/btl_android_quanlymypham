package com.example.btl_android_quanymypham;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.btl_android_quanymypham.model.ProductHomeUser;

public class DetailProductUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product_user);

        Bundle bundle = getIntent().getExtras();
        if (bundle==null){
            return;
        }
        ProductHomeUser productHome = (ProductHomeUser) bundle.get("ObjectProduct");

        TextView tv = (TextView) findViewById(R.id.textView2);
        tv.setText(productHome.getName());


    }
}