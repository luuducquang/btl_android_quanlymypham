package com.example.btl_android_quanymypham.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android_quanymypham.R;
import com.example.btl_android_quanymypham.adapter.TaiKhoanAdapterAdmin;
import com.example.btl_android_quanymypham.DAO.TaiKhoanDAOAdmin;
import com.example.btl_android_quanymypham.model.TaiKhoanAdmin;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TaiKhoanFragmentAdmin extends Fragment {
    EditText tk,mk,hoten,email;
    ImageButton camera,folder;
    Spinner quyen;
    Button lammoi,them,sua;
    private RecyclerView recyclerView;
    private TaiKhoanAdapterAdmin taiKhoanAdapterAdmin;
    TaiKhoanDAOAdmin db;
    private int selectedId;
    ImageView img;
    int REQUEST_CODE_CAMERA = 123;
    int REQUEST_CODE_FOLDER = 456;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.taikhoan_fragment_admin,container,false);

        db = new TaiKhoanDAOAdmin(requireContext());

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

        lammoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedId =0;
                tk.setText("");
                mk.setText("");
                hoten.setText("");
                email.setText("");
                img.setImageBitmap(null);
                tk.requestFocus();
                DataListView();
            }
        });
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();

                if (bitmapDrawable == null) {
                    img.setImageResource(R.drawable.user);
                    bitmapDrawable = (BitmapDrawable) img.getDrawable();
                }

                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] hinhanh = byteArrayOutputStream.toByteArray();

                String strTaikhoan = tk.getText().toString();
                String strMatkhau = mk.getText().toString();
                String strHoten = hoten.getText().toString();
                String strEmail = email.getText().toString();
                String strQuyen = quyen.getSelectedItem().toString();

                if (strTaikhoan.isEmpty() || strMatkhau.isEmpty() || strHoten.isEmpty() || strEmail.isEmpty()) {
                    Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    db.insertData(strTaikhoan, strMatkhau, strHoten, strEmail, hinhanh, strQuyen);
                    DataListView();
                    tk.setText("");
                    mk.setText("");
                    hoten.setText("");
                    email.setText("");
                    img.setImageBitmap(null);
                    tk.requestFocus();
                }
            }
        });

        sua.setOnClickListener(new View.OnClickListener() {
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
                        db.updateData(selectedId,strTaikhoan, strMatkhau,strHoten,strEmail,hinhanh,strQuyen);
                        DataListView();
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
        lammoi = view.findViewById(R.id.btnNew);
        them = view.findViewById(R.id.btnThem);
        sua = view.findViewById(R.id.btnSua);
        img = view.findViewById(R.id.imgPreview);
        recyclerView = view.findViewById(R.id.rcv_taikhoan);
        registerForContextMenu(recyclerView);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.menu_context_delete, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.delete){
            DeleteItem();
        }
        return super.onContextItemSelected(item);
    }

    private void DeleteItem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc muốn xóa?");
        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.deleteData(selectedId);
                DataListView();
                Toast.makeText(requireContext(), "Đã xóa" , Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
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

                TaiKhoanAdmin taiKhoanAdmin = new TaiKhoanAdmin(id, taiKhoan,matKhau,hoTen,email,anhDaiDien,quyen);
                data.add(taiKhoanAdmin);
            } while (cursor.moveToNext());
            cursor.close();
        }

        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 1));

        taiKhoanAdapterAdmin = new TaiKhoanAdapterAdmin(requireContext(), data);

        recyclerView.setAdapter(taiKhoanAdapterAdmin);

        taiKhoanAdapterAdmin.setOnItemClickListener(new TaiKhoanAdapterAdmin.OnItemClickListener() {
            @Override
            public void onItemClick(TaiKhoanAdmin taiKhoanAdmin) {
                selectedId = taiKhoanAdmin.getId();
                tk.setText(taiKhoanAdmin.getTaikhoan());
                mk.setText(taiKhoanAdmin.getMatkhau());
                hoten.setText(taiKhoanAdmin.getHoten());
                email.setText(taiKhoanAdmin.getEmail());

                Bitmap bitmap = BitmapFactory.decodeByteArray(taiKhoanAdmin.getAnhdaidien(), 0, taiKhoanAdmin.getAnhdaidien().length);
                img.setImageBitmap(bitmap);

                if ("Nhân viên".equals(taiKhoanAdmin.getQuyen())) {
                    quyen.setSelection(0);
                } else if ("Người quản trị".equals(taiKhoanAdmin.getQuyen())) {
                    quyen.setSelection(1);
                }
            }
        });

        taiKhoanAdapterAdmin.setOnItemLongClickListener(new TaiKhoanAdapterAdmin.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(TaiKhoanAdmin taiKhoanAdmin) {
                getActivity().openContextMenu(recyclerView);
                selectedId = taiKhoanAdmin.getId();
            }
        });
    }
}
