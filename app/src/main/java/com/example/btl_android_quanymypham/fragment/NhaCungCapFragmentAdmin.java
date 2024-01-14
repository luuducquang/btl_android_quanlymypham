package com.example.btl_android_quanymypham.fragment;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.example.btl_android_quanymypham.R;
import com.example.btl_android_quanymypham.adapter.NhaCungCapAdapterAdmin;
import com.example.btl_android_quanymypham.DAO.NhaCungCapDAOAdmin;
import com.example.btl_android_quanymypham.model.NhaCungCapAdmin;

import java.util.ArrayList;
import java.util.List;

public class NhaCungCapFragmentAdmin extends Fragment {
    private RecyclerView recyclerView;
    private NhaCungCapAdapterAdmin nhaCungCapAdapterAdmin;
    NhaCungCapDAOAdmin db;
    EditText tenncc,diachincc,sdtncc,search;
    private int selectedId;

    Button them,sua,lammoi;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nhacungcap_fragment_admin,container,false);

        db = new NhaCungCapDAOAdmin(requireContext());

        Init(view);
        DataListView();
        HandlerButton();

        return view;
    }

    private void HandlerButton() {
        lammoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tenncc.setText("");
                diachincc.setText("");
                sdtncc.setText("");
                selectedId =0;
                tenncc.requestFocus();
                DataListView();
                Toast.makeText(requireContext(), "Làm mới thành công", Toast.LENGTH_SHORT).show();
            }
        });
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strTenNCC = tenncc.getText().toString();
                String strDiaChiNCC = diachincc.getText().toString();
                String strSDT = sdtncc.getText().toString();

                if (strTenNCC.isEmpty() || strDiaChiNCC.isEmpty()|| strSDT.isEmpty()) {
                    Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    db.insertData(strTenNCC, strDiaChiNCC,strSDT);
                    DataListView();
                    tenncc.setText("");
                    diachincc.setText("");
                    sdtncc.setText("");
                    tenncc.requestFocus();
                    Toast.makeText(requireContext(), "Thêm dữ liệu thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strTenNCC = tenncc.getText().toString();
                String strDiaChiNCC = diachincc.getText().toString();
                String strSDT = sdtncc.getText().toString();

                if (strTenNCC.isEmpty() || strDiaChiNCC.isEmpty()|| strSDT.isEmpty()) {
                    Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    db.updateData(selectedId,strTenNCC, strDiaChiNCC,strSDT);
                    DataListView();
                    Toast.makeText(requireContext(), "Sửa dữ liệu thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nhaCungCapAdapterAdmin.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void DataListView() {
        Cursor cursor = db.getAllData();
        if (cursor.getCount() == 0) {
            return;
        }
        List<NhaCungCapAdmin> data = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String TenNCC = cursor.getString(1);
            String DiaChiNCC = cursor.getString(2);
            String SdtNCC = cursor.getString(3);
            NhaCungCapAdmin nhaCungCapAdmin = new NhaCungCapAdmin(id, TenNCC, DiaChiNCC,SdtNCC);
            data.add(nhaCungCapAdmin);
        }

        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 1));

        nhaCungCapAdapterAdmin = new NhaCungCapAdapterAdmin(data,requireContext());
        recyclerView.setAdapter(nhaCungCapAdapterAdmin);

        nhaCungCapAdapterAdmin.setOnItemClickListener(new NhaCungCapAdapterAdmin.OnItemClickListener() {
            @Override
            public void onItemClick(NhaCungCapAdmin nhaCungCapAdmin) {
                selectedId = nhaCungCapAdmin.getId();
                tenncc.setText(nhaCungCapAdmin.getTen());
                diachincc.setText(nhaCungCapAdmin.getDiachi());
                sdtncc.setText(nhaCungCapAdmin.getSdt());
            }
        });

        nhaCungCapAdapterAdmin.setOnItemLongClickListener(new NhaCungCapAdapterAdmin.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(NhaCungCapAdmin nhaCungCapAdmin) {
                getActivity().openContextMenu(recyclerView);
                selectedId = nhaCungCapAdmin.getId();
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

    private void Init(View view) {
        tenncc = view.findViewById(R.id.ten_ncc);
        diachincc = view.findViewById(R.id.diachi_ncc);
        sdtncc = view.findViewById(R.id.sdt_ncc);
        lammoi = view.findViewById(R.id.btnMoi);
        them = view.findViewById(R.id.btnThem);
        sua = view.findViewById(R.id.btnSua);
        recyclerView = view.findViewById(R.id.rcv_nhacungcap);
        registerForContextMenu(recyclerView);
        search = view.findViewById(R.id.searchbar);
    }
}
