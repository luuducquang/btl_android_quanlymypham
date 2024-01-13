package com.example.btl_android_quanymypham;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btl_android_quanymypham.DAO.TaiKhoanDAOAdmin;
import com.example.btl_android_quanymypham.model.TaiKhoanAdmin;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {

    TextInputEditText txt_username, txt_password;
    TextInputLayout layout_username, layout_password;
    Button btn_login;

    CheckBox SaveInf;
    TextView tv_register;

    TaiKhoanDAOAdmin db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txt_username = findViewById(R.id.txt_username);
        txt_password = findViewById(R.id.txt_password);
        layout_username = findViewById(R.id.layout_username);
        layout_password = findViewById(R.id.layout_password);
        btn_login = findViewById(R.id.btn_login);
        tv_register = findViewById(R.id.register);
        SaveInf = findViewById(R.id.login_remember);

        SharedPreferences preferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        boolean remember = preferences.getBoolean("remember", false);

        if (remember) {
            String savedUsername = preferences.getString("username", "");
            String savedPassword = preferences.getString("password", "");

            layout_username.getEditText().setText(savedUsername);
            layout_password.getEditText().setText(savedPassword);
            SaveInf.setChecked(true);
        }


        db = new TaiKhoanDAOAdmin(Login.this);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUserName = layout_username.getEditText() != null ? layout_username.getEditText().getText().toString() : "";
                String strPassword = layout_password.getEditText() != null ? layout_password.getEditText().getText().toString() : "";

                if (strUserName.isEmpty()){
                    layout_username.setError("Tên đăng nhập không được để trống");
                }else{
                    layout_username.setError("");
                }
                if(strPassword.isEmpty()){
                    layout_password.setError("Mât khẩu không được để trống");
                }else{
                    layout_password.setError("");
                }

                if (db.checkLogin(strUserName, strPassword)) {
                    Toast.makeText(Login.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    TaiKhoanAdmin taiKhoanAdmin = db.getAccountInfo(strUserName, strPassword);
                    if (taiKhoanAdmin.getQuyen().equals("Người quản trị")) {
                        Intent it = new Intent(Login.this, MainAdmin.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("ObjectUser", taiKhoanAdmin);
                        it.putExtras(bundle);
                        Login.this.startActivity(it);
                    }
                    if (taiKhoanAdmin.getQuyen().equals("Nhân viên")) {
                        Intent it = new Intent(Login.this, MainUser.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("ObjectUser", taiKhoanAdmin);
                        it.putExtras(bundle);
                        Login.this.startActivity(it);
                    }
                    SharedPreferences preferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("username", strUserName);
                    editor.putString("password", strPassword);
                    editor.putBoolean("remember", SaveInf.isChecked());
                    editor.apply();
                } else {
                    Toast.makeText(Login.this, "Đăng nhập thất bại. Kiểm tra lại tài khoản và mật khẩu.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Login.this,Register.class);
                startActivity(intent1);
            }
        });
    }
}
