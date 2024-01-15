package com.example.btl_android_quanymypham.fragment;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android_quanymypham.DAO.GioHangDAOUser;
import com.example.btl_android_quanymypham.R;
import com.example.btl_android_quanymypham.adapter.GioHangAdapterUser;
import com.example.btl_android_quanymypham.adapter.ProductHomeAdapterUser;
import com.example.btl_android_quanymypham.model.GioHangUser;
import com.example.btl_android_quanymypham.model.TTMyPhamAdmin;
import com.example.btl_android_quanymypham.model.TaiKhoanAdmin;

import java.util.ArrayList;
import java.util.List;

public class GioHangFragmentUser extends Fragment {

    TextView tongtientamtinh;
    Button muangay;
    private RecyclerView recyclerView;
    GioHangAdapterUser gioHangAdapterUser;
    TaiKhoanAdmin taiKhoanAdmin;
    GioHangDAOUser db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.giohang_fragment_user, container, false);

        db = new GioHangDAOUser(requireContext());

        Bundle bundle = getArguments();
        if (bundle != null) {
            taiKhoanAdmin = (TaiKhoanAdmin) bundle.getSerializable("ObjectUser");
        }

        Innit(view);
        DataListView();

        return view;
    }

    private void DataListView() {
        Cursor cursor = db.getByNguoiTao(taiKhoanAdmin.getId());
        if (cursor == null || cursor.getCount() == 0) {
            return;
        }
        List<GioHangUser> data = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                int mamp = cursor.getInt(1);
                int nguoitao = cursor.getInt(2);
                int soluong = cursor.getInt(3);
                Long giaban = cursor.getLong(4);
                byte[] anhsanpham = cursor.getBlob(5);
                String tenmp = cursor.getString(6);
                String hoten = cursor.getString(7);

                GioHangUser gioHangUser = new GioHangUser(id, mamp,nguoitao,soluong,giaban,anhsanpham,tenmp,hoten);
                data.add(gioHangUser);
            } while (cursor.moveToNext());
            cursor.close();
        }

        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 1));

        gioHangAdapterUser = new GioHangAdapterUser( data,requireContext());

        recyclerView.setAdapter(gioHangAdapterUser);

    }

    private void Innit(View view) {
        muangay = view.findViewById(R.id.btn_muangay);
        tongtientamtinh = view.findViewById(R.id.txt_tongtien);
        recyclerView = view.findViewById(R.id.rcv_giohang);
    }
}
