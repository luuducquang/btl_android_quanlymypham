package com.example.btl_android_quanymypham.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android_quanymypham.DAO.HoaDonBanDAOAdmin;
import com.example.btl_android_quanymypham.DAO.TaiKhoanDAOAdmin;
import com.example.btl_android_quanymypham.R;
import com.example.btl_android_quanymypham.adapter.ChiTietHoaDonAdapterUser;
import com.example.btl_android_quanymypham.adapter.HoaDonAdapterUser;
import com.example.btl_android_quanymypham.adapter.HoaDonBanAdapterAdmin;
import com.example.btl_android_quanymypham.model.HoaDonBanAdmin;
import com.example.btl_android_quanymypham.model.TaiKhoanAdmin;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class HoaDonFragmentUser extends Fragment {
    private RecyclerView recyclerView;
    HoaDonAdapterUser hoaDonAdapterUser;
    HoaDonBanDAOAdmin db;
    private int DetailID;
    TaiKhoanAdmin taiKhoanAdmin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hoadon_fragment_user, container, false);

        db = new HoaDonBanDAOAdmin(requireContext());

        Bundle bundle = getArguments();
        if (bundle != null) {
            taiKhoanAdmin = (TaiKhoanAdmin) bundle.getSerializable("ObjectUser");
        }

        Innit(view);
        DataListView();

        return view;
    }

    private void DataListView() {
        Cursor cursor = db.getHoaDonByNguoiTao(taiKhoanAdmin.getId());
        if (cursor == null || cursor.getCount() == 0) {
            Toast.makeText(requireContext(), "Không có hoá đơn nào", Toast.LENGTH_SHORT).show();
            return;
        }
        List<HoaDonBanAdmin> data = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                int nguoitao = cursor.getInt(1);
                String tenkh = cursor.getString(2);
                String diachi = cursor.getString(3);
                String sdt = cursor.getString(4);
                String ngayban = cursor.getString(5);
                Long tongtien = cursor.getLong(6);
                String hoten = cursor.getString(7);

                HoaDonBanAdmin hoaDonBanAdmin = new HoaDonBanAdmin(id, nguoitao,tenkh,diachi,sdt,ngayban,tongtien,hoten);
                data.add(hoaDonBanAdmin);
            } while (cursor.moveToNext());
            cursor.close();
        }
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 1));

        hoaDonAdapterUser = new HoaDonAdapterUser(data,requireContext());

        recyclerView.setAdapter(hoaDonAdapterUser);

        hoaDonAdapterUser.setInfOnItemClickListener(new HoaDonAdapterUser.OnInfItemClickListener() {
            @Override
            public void onInfItemClick(HoaDonBanAdmin hoaDonBanAdmin) {
                DetailID = hoaDonBanAdmin.getId();

                ChiTietHoaDonFragmentUser chiTietDialog = ChiTietHoaDonFragmentUser.newInstance(DetailID);
                chiTietDialog.show(getFragmentManager(), "chi_tiet_dialog");
            }
        });
    }

    private void Innit(View view) {
        recyclerView = view.findViewById(R.id.rcv_hoadon);
    }


}
