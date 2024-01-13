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
import android.widget.AdapterView;
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

import com.example.btl_android_quanymypham.DAO.LoaiMyPhamDAOAdmin;
import com.example.btl_android_quanymypham.DAO.TTMyPhamDAOAdmin;
import com.example.btl_android_quanymypham.R;
import com.example.btl_android_quanymypham.adapter.TTMyPhamAdapterAdmin;
import com.example.btl_android_quanymypham.adapter.TaiKhoanAdapterAdmin;
import com.example.btl_android_quanymypham.model.TTMyPhamAdmin;
import com.example.btl_android_quanymypham.model.TaiKhoanAdmin;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TTMyPhamFragmentAdmin extends Fragment {
    EditText tenmp,dungtich,giaban;
    TextInputLayout mota,chitiet;
    ImageButton camera,folder;
    Spinner spinnerLoaiMP;
    Button lammoi,them,sua;
    private RecyclerView recyclerView;
    private TTMyPhamAdapterAdmin ttMyPhamAdapterAdmin;

    private LoaiMyPhamDAOAdmin loaiMyPhamDAOAdmin;
    private List<String> listMaLoaiMP;
    private List<String> listTenLoaiMP;
    private  int index;
    TTMyPhamDAOAdmin db;
    private int selectedId;

    private int SpinnerValue;
    ImageView img;
    int REQUEST_CODE_CAMERA = 123;
    int REQUEST_CODE_FOLDER = 456;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ttmypham_fragment_admin,container,false);

        db = new TTMyPhamDAOAdmin(requireContext());
        loaiMyPhamDAOAdmin = new LoaiMyPhamDAOAdmin(requireContext());


        listMaLoaiMP = new ArrayList<>();
        listTenLoaiMP = new ArrayList<>();


        Init(view);
        Getspinner();
        HandlerImg();
        DataListView();
        HandlerButton();

        return view;
    }

    private void Getspinner() {
        Cursor cursor2 = loaiMyPhamDAOAdmin.getAllData();
        if (cursor2 == null || cursor2.getCount() == 0) {
            return;
        }
        if (cursor2 != null && cursor2.moveToFirst()) {
            do {
                listMaLoaiMP.add(String.valueOf(cursor2.getInt(0)));
                listTenLoaiMP.add(cursor2.getString(1));
            } while (cursor2.moveToNext());
            cursor2.close();
        }
        ArrayAdapter adapterLoaiMP = new ArrayAdapter(
                requireContext(), android.R.layout.simple_list_item_1,listTenLoaiMP
        );
        spinnerLoaiMP.setAdapter(adapterLoaiMP);

        spinnerLoaiMP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerValue = Integer.parseInt(listMaLoaiMP.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void HandlerButton() {
        lammoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedId =0;
                tenmp.setText("");
                dungtich.setText("");
                giaban.setText("");
                mota.getEditText().setText("");
                chitiet.getEditText().setText("");
                img.setImageBitmap(null);
                tenmp.requestFocus();
                DataListView();
                Toast.makeText(requireContext(), "Làm mới thành công", Toast.LENGTH_SHORT).show();
            }
        });

        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();

                if (bitmapDrawable == null) {
                    img.setImageResource(R.drawable.product);
                    bitmapDrawable = (BitmapDrawable) img.getDrawable();
                }

                Bitmap bitmap = bitmapDrawable.getBitmap();
                if (bitmap == null) {
                    Toast.makeText(requireContext(), "Không có ảnh", Toast.LENGTH_SHORT).show();
                    return;
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] hinhanh = byteArrayOutputStream.toByteArray();

                String strTenMyPham = tenmp.getText().toString();
                String strDungTich = dungtich.getText().toString();
                String strGiaBan = giaban.getText().toString();
                String strMoTa = mota.getEditText().getText().toString();
                String strCHiTiet = chitiet.getEditText().getText().toString();

                if (strTenMyPham.isEmpty() || strDungTich.isEmpty() || strGiaBan.isEmpty() || strMoTa.isEmpty() || strCHiTiet.isEmpty() || SpinnerValue == 0) {
                    Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        Long Gia = Long.valueOf(strGiaBan);
                        db.insertThongTinMyPham(strTenMyPham, strDungTich, SpinnerValue, hinhanh, Gia, strMoTa, strCHiTiet);
                        DataListView();
                        tenmp.setText("");
                        dungtich.setText("");
                        giaban.setText("");
                        mota.getEditText().setText("");
                        chitiet.getEditText().setText("");
                        img.setImageBitmap(null);
                        tenmp.requestFocus();
                        Toast.makeText(requireContext(), "Thêm dữ liệu thành công", Toast.LENGTH_SHORT).show();
                    } catch (NumberFormatException e) {

                    }
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

                    String strTenMyPham = tenmp.getText().toString();
                    String strDungTich = dungtich.getText().toString();
                    String strGiaBan = giaban.getText().toString();
                    String strMoTa = mota.getEditText().getText().toString();
                    String strCHiTiet = chitiet.getEditText().getText().toString();

                    if (strTenMyPham.isEmpty() || strDungTich.isEmpty() || strGiaBan.isEmpty() || strMoTa.isEmpty() || strCHiTiet.isEmpty() || SpinnerValue == 0) {
                        Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    }else {
                        Long Gia = Long.valueOf(strGiaBan);
                        db.updateThongTinMyPham(selectedId,strTenMyPham, strDungTich, SpinnerValue, hinhanh, Gia, strMoTa, strCHiTiet);
                        DataListView();
                        Toast.makeText(requireContext(), "Sửa dữ liệu thành công", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
    }

    private void DataListView() {
        Cursor cursor = db.getTenLoaiThongTinMyPham();
        if (cursor == null || cursor.getCount() == 0) {
            return;
        }
        List<TTMyPhamAdmin> data = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String tenmp = cursor.getString(1);
                String dungtich = cursor.getString(2);
                Integer loaimypham = cursor.getInt(3);
                byte[] anhsanpham = cursor.getBlob(4);
                Long giaban = cursor.getLong(5);
                String mota = cursor.getString(6);
                String chitiet = cursor.getString(7);
                String tenloai = cursor.getString(8);

                TTMyPhamAdmin ttMyPhamAdmin = new TTMyPhamAdmin(id, tenmp,dungtich,loaimypham,anhsanpham,giaban,mota,chitiet,tenloai);
                data.add(ttMyPhamAdmin);
            } while (cursor.moveToNext());
            cursor.close();
        }

        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 1));

        ttMyPhamAdapterAdmin = new TTMyPhamAdapterAdmin(data,requireContext());

        recyclerView.setAdapter(ttMyPhamAdapterAdmin);

        ttMyPhamAdapterAdmin.setOnItemClickListener(new TTMyPhamAdapterAdmin.OnItemClickListener() {
            @Override
            public void onItemClick(TTMyPhamAdmin ttMyPhamAdmin) {
                selectedId = ttMyPhamAdmin.getId();
                tenmp.setText(ttMyPhamAdmin.getTenmypham());
                dungtich.setText(ttMyPhamAdmin.getDungtich());
                giaban.setText(String.valueOf(ttMyPhamAdmin.getGia()));
                mota.getEditText().setText(ttMyPhamAdmin.getMota());
                chitiet.getEditText().setText(ttMyPhamAdmin.getChitiet());

                Bitmap bitmap = BitmapFactory.decodeByteArray(ttMyPhamAdmin.getAnhsanpham(), 0, ttMyPhamAdmin.getAnhsanpham().length);
                img.setImageBitmap(bitmap);

                index = findIndex(listMaLoaiMP, String.valueOf(ttMyPhamAdmin.getLoaimypham()));
                spinnerLoaiMP.setSelection(index);
            }
        });

        ttMyPhamAdapterAdmin.setOnItemLongClickListener(new TTMyPhamAdapterAdmin.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(TTMyPhamAdmin ttMyPhamAdmin) {
                getActivity().openContextMenu(recyclerView);
                selectedId = ttMyPhamAdmin.getId();
            }
        });

        ttMyPhamAdapterAdmin.setDelOnItemClickListener(new TTMyPhamAdapterAdmin.OnDelItemClickListener(){
            @Override
            public void onDelItemClick(TTMyPhamAdmin ttMyPhamAdmin) {
                selectedId = ttMyPhamAdmin.getId();
                DeleteItem();
            }
        });
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
                db.deleteThongTinMyPham(selectedId);
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

    private static int findIndex(List<String> array, String targetValue) {
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).equals(targetValue)) {
                return i;
            }
        }
        return -1;
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
        tenmp = view.findViewById(R.id.txtTenMP);
        dungtich = view.findViewById(R.id.txtDungTich);
        spinnerLoaiMP = view.findViewById(R.id.spinnerLoaiMP);
        img = view.findViewById(R.id.imgPreview);
        camera = view.findViewById(R.id.cameraBtn);
        folder = view.findViewById(R.id.folderBtn);
        giaban = view.findViewById(R.id.txtGiaBan);
        mota = view.findViewById(R.id.txtMoTa);
        chitiet = view.findViewById(R.id.txtChiTiet);
        lammoi = view.findViewById(R.id.btnLamMoi);
        them = view.findViewById(R.id.btnThem);
        sua = view.findViewById(R.id.btnSua);
        recyclerView = view.findViewById(R.id.rcv_ttmypham);
        registerForContextMenu(recyclerView);
    }
}
