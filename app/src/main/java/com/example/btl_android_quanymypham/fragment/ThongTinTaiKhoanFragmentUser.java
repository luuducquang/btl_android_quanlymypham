package com.example.btl_android_quanymypham.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.btl_android_quanymypham.DAO.TaiKhoanDAOAdmin;
import com.example.btl_android_quanymypham.R;
import com.example.btl_android_quanymypham.model.TaiKhoanAdmin;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ThongTinTaiKhoanFragmentUser extends Fragment {

    TextView taikhoan;
    EditText hoten,email;
    Button save;
    ImageView img;
    ImageButton camera,folder;
    int REQUEST_CODE_CAMERA = 123;
    int REQUEST_CODE_FOLDER = 456;
    TaiKhoanAdmin taiKhoanAdmin;
    TaiKhoanDAOAdmin taiKhoanDAOAdmin;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thongtintaikhoan_fragment_user, container, false);
        taiKhoanDAOAdmin = new TaiKhoanDAOAdmin(requireContext());

        Bundle bundle = getArguments();
        if (bundle != null) {
            taiKhoanAdmin = (TaiKhoanAdmin) bundle.getSerializable("ObjectUser");
        }

        Innit(view);
        HandlerButton();
        SetData();
        HandlerImg();

        return view;
    }

    private void HandlerButton() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
                if (bitmapDrawable != null) {
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                    byte[]hinhanh = byteArrayOutputStream.toByteArray();


                    taiKhoanDAOAdmin.updateData(taiKhoanAdmin.getId(),taiKhoanAdmin.getTaikhoan(),taiKhoanAdmin.getMatkhau(),hoten.getText().toString(),email.getText().toString(),hinhanh,taiKhoanAdmin.getQuyen());
                    Toast.makeText(requireContext(), "Đăng nhập lại để cập nhật thay đổi", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(requireContext(), "Sửa thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void SetData() {
        hoten.setText(taiKhoanAdmin.getHoten());
        taikhoan.setText("Tài khoản: "+taiKhoanAdmin.getTaikhoan());
        email.setText(taiKhoanAdmin.getEmail());

        byte[]anhsanpham = taiKhoanAdmin.getAnhdaidien();
        Bitmap bitmap = BitmapFactory.decodeByteArray(anhsanpham,0,anhsanpham.length);
        img.setImageBitmap(bitmap);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==REQUEST_CODE_CAMERA && data != null){
            Bitmap bitmap =(Bitmap) data.getExtras().get("data");
            img.setImageBitmap(bitmap);
        }
        if(requestCode==REQUEST_CODE_FOLDER && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = requireActivity().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);
            }
            catch (FileNotFoundException e){
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void HandlerImg() {
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQUEST_CODE_CAMERA);
            }
        });

        folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_FOLDER);
            }
        });
    }

    private void Innit(View view) {
        hoten = view.findViewById(R.id.textFullName);
        taikhoan = view.findViewById(R.id.textUsername);
        email = view.findViewById(R.id.textEmail);
        save = view.findViewById(R.id.btn_save);
        img = view.findViewById(R.id.imgPreview);
        camera = view.findViewById(R.id.cameraBtn);
        folder = view.findViewById(R.id.folderBtn);
    }
}
