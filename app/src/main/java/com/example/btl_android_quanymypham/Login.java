package com.example.btl_android_quanymypham;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.btl_android_quanymypham.database.TaiKhoanDataBaseHandlerAdmin;
import com.example.btl_android_quanymypham.model.TaiKhoanAdmin;

public class Login extends AppCompatActivity {

    TaiKhoanDataBaseHandlerAdmin db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText tk,mk;
        Button log;

        tk = (EditText) findViewById(R.id.taikhoan);
        mk = (EditText) findViewById(R.id.matkhau);
        log = (Button) findViewById(R.id.login);

        db = new TaiKhoanDataBaseHandlerAdmin(Login.this);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.checkLogin(tk.getText().toString(), mk.getText().toString())) {
                    Toast.makeText(Login.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    TaiKhoanAdmin taiKhoanAdmin = db.getAccountInfo(tk.getText().toString(), mk.getText().toString());
                    if (taiKhoanAdmin.getQuyen().equals("Người quản trị")){
                        Intent it = new Intent(Login.this, MainAdmin.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("ObjectUser",taiKhoanAdmin);
                        it.putExtras(bundle);
                        Login.this.startActivity(it);
                    }
                    if (taiKhoanAdmin.getQuyen().equals("Nhân viên")){
                        Intent it = new Intent(Login.this, MainUser.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("ObjectUser",taiKhoanAdmin);
                        it.putExtras(bundle);
                        Login.this.startActivity(it);
                    }
                } else {
                    Toast.makeText(Login.this, "Đăng nhập thất bại. Kiểm tra lại tài khoản và mật khẩu.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}