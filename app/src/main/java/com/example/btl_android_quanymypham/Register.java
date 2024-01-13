package com.example.btl_android_quanymypham;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btl_android_quanymypham.DAO.TaiKhoanDAOAdmin;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;

public class Register extends AppCompatActivity {

    TextInputLayout username,name, email,pass,repass;
    Button btn_register;
    TextView tv_login;

    TaiKhoanDAOAdmin db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username_register);
        name = findViewById(R.id.name_register);
        email = findViewById(R.id.email_register);
        pass = findViewById(R.id.pass_register);
        repass = findViewById(R.id.repass_register);
        btn_register = findViewById(R.id.btn_register);
        tv_login = findViewById(R.id.tv_login);

        db = new TaiKhoanDAOAdmin(Register.this);

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Register.this,Login.class);
                startActivity(intent1);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getEditText().getText().toString();
                String nameUser = name.getEditText().getText().toString();
                String emailUser = email.getEditText().getText().toString();
                String passUser = pass.getEditText().getText().toString();
                String repassUser = repass.getEditText().getText().toString();

                if(user.equals("")||nameUser.equals("")||emailUser.equals("")||passUser.equals("")||repassUser.equals("")) {
                    if (nameUser.equals("")) {
                        Toast.makeText(Register.this, "Kiểm tra lại họ tên", Toast.LENGTH_SHORT).show();
                    }
                    if (user.equals("")) {
                        Toast.makeText(Register.this, "Kiểm tra lại tên đăng nhập", Toast.LENGTH_SHORT).show();
                    } else {
                        if (db.isUsernameExist(user)) {
                            Toast.makeText(Register.this, "Tên đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    if (emailUser.equals("")) {
                        Toast.makeText(Register.this, "Kiểm tra lại email", Toast.LENGTH_SHORT).show();
                    }
                    if (passUser.equals("")) {
                        Toast.makeText(Register.this, "Kiểm tra lại mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                    if (repassUser.equals("")) {
                        Toast.makeText(Register.this, "Chưa nhập xác nhận mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    if (passUser.equals(repassUser)){
                        if (!db.isUsernameExist(user)) {
                            ImageView img = new ImageView(Register.this);
                            BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
                            img.setImageResource(R.drawable.user);
                            bitmapDrawable = (BitmapDrawable) img.getDrawable();
                            Bitmap bitmap = bitmapDrawable.getBitmap();
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                            byte[] hinhanh = byteArrayOutputStream.toByteArray();

                            db.insertData(user, passUser, nameUser, emailUser, hinhanh, "Người quản trị");
                            Toast.makeText(Register.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            Intent intent2 = new Intent(Register.this,Login.class);
                            startActivity(intent2);
                        }
                        else {
                            Toast.makeText(Register.this, "Tên đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(Register.this, "Mật khẩu xác nhận không trùng nhau", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}