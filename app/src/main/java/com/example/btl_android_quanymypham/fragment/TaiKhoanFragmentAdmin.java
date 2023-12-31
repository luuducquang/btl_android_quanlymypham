package com.example.btl_android_quanymypham.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android_quanymypham.R;
import com.example.btl_android_quanymypham.adapter.LoaiMyPhamAdapterAdmin;
import com.example.btl_android_quanymypham.adapter.TaiKhoanAdapterAdmin;
import com.example.btl_android_quanymypham.database.LoaiMyPhamDataBaseHandlerAdmin;
import com.example.btl_android_quanymypham.database.TaiKhoanDataBaseHandlerAdmin;
import com.example.btl_android_quanymypham.model.LoaiMyPhamAdmin;
import com.example.btl_android_quanymypham.model.TaiKhoanAdmin;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class TaiKhoanFragmentAdmin extends Fragment {
    EditText tk,mk,hoten,email;
    ImageButton camera,folder;
    Spinner quyen;
    Button them,sua;
    private RecyclerView recyclerView;
    private TaiKhoanAdapterAdmin taiKhoanAdapterAdmin;
    TaiKhoanDataBaseHandlerAdmin db;
    private int selectedId;
    ImageView img;
    int REQUEST_CODE_CAMERA = 123;
    int REQUEST_CODE_FOLDER = 456;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.taikhoan_fragment_admin,container,false);

        db = new TaiKhoanDataBaseHandlerAdmin(requireContext());

        Init(view);
        HandlerImg();
        DataListView();
        HandlerButton();
        GetQuyen();

        return view;
    }

    private void GetQuyen() {
        ArrayList<String> arrQuyen = new ArrayList<>();
        arrQuyen.add("Nhân viên");
        arrQuyen.add("Người quản trị");

        if (quyen != null) {
            ArrayAdapter adapter = new ArrayAdapter(
                    requireContext(), android.R.layout.simple_list_item_1, arrQuyen
            );
            quyen.setAdapter(adapter);
        }
    }

    private void HandlerButton() {
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
                if (bitmapDrawable != null) {
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                    byte[]hinhanh = byteArrayOutputStream.toByteArray();

                    String strTaikhoan = tk.getText().toString();
                    String strMatkhau = mk.getText().toString();
                    String strHoten = hoten.getText().toString();
                    String strEmail = email.getText().toString();
                    String strQuyen = quyen.getSelectedItem().toString();

                    if (strTaikhoan.isEmpty() || strMatkhau.isEmpty()|| strHoten.isEmpty()|| strEmail.isEmpty()||hinhanh ==null||hinhanh.length==0) {
                        Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    } else {
                        db.insertData(strTaikhoan, strMatkhau,strHoten,strEmail,hinhanh,strQuyen);
                        DataListView();
                        tk.setText("");
                        mk.setText("");
                        hoten.setText("");
                        email.setText("");
                        tk.requestFocus();
                    }
                } else {
                    Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
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

    private void Init(View view) {
        tk = view.findViewById(R.id.taikhoan);
        mk = view.findViewById(R.id.matkhau);
        hoten = view.findViewById(R.id.hoten);
        email = view.findViewById(R.id.email);
        camera = view.findViewById(R.id.cameraBtn);
        folder = view.findViewById(R.id.folderBtn);
        quyen = view.findViewById(R.id.spinnerQuyen);
        them = view.findViewById(R.id.btnThem);
        sua = view.findViewById(R.id.btnSua);
        img = view.findViewById(R.id.imgPreview);
        recyclerView = view.findViewById(R.id.rcv_taikhoan);
    }

    private void DataListView() {
        Cursor cursor = db.getAllData();
        if (cursor == null || cursor.getCount() == 0) {
            return;
        }
        List<TaiKhoanAdmin> data = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String taiKhoan = cursor.getString(1);
                String matKhau = cursor.getString(2);
                String hoTen = cursor.getString(3);
                String email = cursor.getString(4);
                byte[] anhDaiDien = cursor.getBlob(5);
                String quyen = cursor.getString(6);

                if (anhDaiDien == null) {
                    anhDaiDien = getDefaultImageByteArray();
                }

                TaiKhoanAdmin taiKhoanAdmin = new TaiKhoanAdmin(id, taiKhoan,matKhau,hoTen,email,anhDaiDien,quyen);
                data.add(taiKhoanAdmin);
            } while (cursor.moveToNext());
            cursor.close();
        } else {

        }

        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 1));

        taiKhoanAdapterAdmin = new TaiKhoanAdapterAdmin(requireContext(), data);

        recyclerView.setAdapter(taiKhoanAdapterAdmin);
    }

    private byte[] getDefaultImageByteArray() {
        Bitmap defaultImage = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_foreground);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        defaultImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
