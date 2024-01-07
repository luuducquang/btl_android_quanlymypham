package com.example.btl_android_quanymypham.fragment;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android_quanymypham.adapter.LoaiMyPhamAdapterAdmin;
import com.example.btl_android_quanymypham.model.LoaiMyPhamAdmin;
import com.example.btl_android_quanymypham.DAO.LoaiMyPhamDAOAdmin;
import com.example.btl_android_quanymypham.R;

import java.util.ArrayList;
import java.util.List;

public class LoaiMyPhamFragmentAdmin extends Fragment {
    private RecyclerView recyclerView;
    private LoaiMyPhamAdapterAdmin loaiMyPhamAdapterAdmin;
    LoaiMyPhamDAOAdmin db;
    EditText tenloai,mota;
    private int selectedId;

    Button them,sua,lammoi;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.loaimypham_fragment_admin,container,false);

        db = new LoaiMyPhamDAOAdmin(requireContext());

        Init(view);
        DataListView();
        HandleButton();

        return view;
    }

    private void HandleButton() {
        lammoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tenloai.setText("");
                mota.setText("");
                selectedId = 0;
                tenloai.requestFocus();
                DataListView();
            }
        });
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strTenLoai = tenloai.getText().toString();
                String strMoTa = mota.getText().toString();

                if (strTenLoai.isEmpty() || strMoTa.isEmpty()) {
                    Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    db.insertData(strTenLoai, strMoTa);
                    DataListView();
                    tenloai.setText("");
                    mota.setText("");
                    tenloai.requestFocus();
                }
            }
        });

        sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strTenLoai = tenloai.getText().toString();
                String strMoTa = mota.getText().toString();

                if (strTenLoai.isEmpty() || strMoTa.isEmpty()) {
                    Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    db.updateData(selectedId,strTenLoai,strMoTa);
                    DataListView();
                }
            }
        });

    }
    private void DataListView() {
        Cursor cursor = db.getAllData();
        if (cursor.getCount() == 0) {
            return;
        }
        List<LoaiMyPhamAdmin> data = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String tenLoai = cursor.getString(1);
            String moTa = cursor.getString(2);
            LoaiMyPhamAdmin loaiMyPhamAdmin = new LoaiMyPhamAdmin(id, tenLoai, moTa);
            data.add(loaiMyPhamAdmin);
        }

        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 1));

        loaiMyPhamAdapterAdmin = new LoaiMyPhamAdapterAdmin(requireContext(), data,tenloai,mota);
        recyclerView.setAdapter(loaiMyPhamAdapterAdmin);

        loaiMyPhamAdapterAdmin.setOnItemClickListener(new LoaiMyPhamAdapterAdmin.OnItemClickListener() {
            @Override
            public void onItemClick(int id) {
                selectedId = id;
            }
        });

        loaiMyPhamAdapterAdmin.setOnItemLongClickListener(new LoaiMyPhamAdapterAdmin.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int id) {
                getActivity().openContextMenu(recyclerView);
                selectedId = id;
            }
        });
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
                Toast.makeText(requireContext(), "Đã xóa", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.menu_context_delete, menu);
    }


    private void Init(View view) {
        tenloai = view.findViewById(R.id.tenloai);
        mota = view.findViewById(R.id.mota);
        them = view.findViewById(R.id.btnThem);
        sua = view.findViewById(R.id.btnSua);
        lammoi = view.findViewById(R.id.btnMoi);
        recyclerView = view.findViewById(R.id.rcv_loaimypham);
        registerForContextMenu(recyclerView);
    }
}
