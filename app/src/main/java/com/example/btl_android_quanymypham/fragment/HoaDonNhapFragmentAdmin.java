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

import com.example.btl_android_quanymypham.DAO.LoaiMyPhamDAOAdmin;
import com.example.btl_android_quanymypham.R;
import com.example.btl_android_quanymypham.adapter.LoaiMyPhamAdapterAdmin;
import com.example.btl_android_quanymypham.model.LoaiMyPhamAdmin;

import java.util.ArrayList;
import java.util.List;

public class HoaDonNhapFragmentAdmin extends Fragment {
    private RecyclerView recyclerView;
    private LoaiMyPhamAdapterAdmin loaiMyPhamAdapterAdmin;
    LoaiMyPhamDAOAdmin db;
    EditText tenloai,mota;
    private int selectedId;

    Button them,sua,lammoi;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hoadonnhap_fragment_admin,container,false);



        return view;
    }
}
