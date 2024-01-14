package com.example.btl_android_quanymypham.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android_quanymypham.DAO.TTMyPhamDAOAdmin;
import com.example.btl_android_quanymypham.adapter.ProductHomeAdapterUser;
import com.example.btl_android_quanymypham.R;
import com.example.btl_android_quanymypham.adapter.TTMyPhamAdapterAdmin;
import com.example.btl_android_quanymypham.model.TTMyPhamAdmin;

import java.util.ArrayList;
import java.util.List;

public class ProductHomeFragmentUser extends Fragment {
    private RecyclerView recyclerView;
    private ProductHomeAdapterUser productHomeAdapter;
    private int IdMyPham;
    TTMyPhamDAOAdmin db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment_user, container, false);

        db = new TTMyPhamDAOAdmin(requireContext());

        Innit(view);
        DataListView();

        return view;


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
                Integer soluong = cursor.getInt(8);
                String tenloai = cursor.getString(9);

                TTMyPhamAdmin ttMyPhamAdmin = new TTMyPhamAdmin(id, tenmp,dungtich,loaimypham,anhsanpham,giaban,mota,chitiet,soluong,tenloai);
                data.add(ttMyPhamAdmin);
            } while (cursor.moveToNext());
            cursor.close();
        }

        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        productHomeAdapter = new ProductHomeAdapterUser( requireContext(),data);

        recyclerView.setAdapter(productHomeAdapter);
    }

    private void Innit(View view) {
        recyclerView = view.findViewById(R.id.rcv_home);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(productHomeAdapter!=null){
            productHomeAdapter.release();
        }
    }
}
