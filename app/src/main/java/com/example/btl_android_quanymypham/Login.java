package com.example.btl_android_quanymypham;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText tk,mk;
        Button log;

        tk = (EditText) findViewById(R.id.taikhoan);
        mk = (EditText) findViewById(R.id.matkhau);
        log = (Button) findViewById(R.id.login);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Login.this, MainAdmin.class);
                startActivity(it);
            }
        });
    }
}