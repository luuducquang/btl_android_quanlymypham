package com.example.btl_android_quanymypham.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android_quanymypham.DAO.TTMyPhamDAOAdmin;
import com.example.btl_android_quanymypham.adapter.ProductHomeAdapterUser;
import com.example.btl_android_quanymypham.R;
import com.example.btl_android_quanymypham.adapter.TTMyPhamAdapterAdmin;
import com.example.btl_android_quanymypham.model.TTMyPhamAdmin;
import com.example.btl_android_quanymypham.model.TaiKhoanAdmin;

import java.util.ArrayList;
import java.util.List;

public class ProductHomeFragmentUser extends Fragment {
    private RecyclerView recyclerView;
    private ProductHomeAdapterUser productHomeAdapter;

    EditText search;
    private int IdMyPham;
    TTMyPhamDAOAdmin db;
    TaiKhoanAdmin taiKhoanAdmin;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment_user, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            taiKhoanAdmin = (TaiKhoanAdmin) bundle.getSerializable("ObjectUser");
        }

        db = new TTMyPhamDAOAdmin(requireContext());

        Innit(view);
        DataListView();
        handlerbutton();

        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Trang chủ");
        }

        return view;
    }

    private void handlerbutton() {
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                productHomeAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

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

        productHomeAdapter.setOnItemClickListener(new ProductHomeAdapterUser.OnItemClickListener() {
            @Override
            public void onItemClick(TTMyPhamAdmin ttMyPhamAdmin) {
                gotoDetail(ttMyPhamAdmin);
            }
        });
    }
    private void gotoDetail(TTMyPhamAdmin ttMyPhamAdmin) {
        DetailProductFragmentUser detailFragment = new DetailProductFragmentUser();

        Bundle bundle = new Bundle();
        bundle.putSerializable("ObjectProduct", ttMyPhamAdmin);
        bundle.putSerializable("ObjectUser", taiKhoanAdmin);
        detailFragment.setArguments(bundle);

        AppCompatActivity activity = (AppCompatActivity) requireContext();
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, detailFragment)
                .addToBackStack(null)
                .commit();
    }


    private void Innit(View view) {
        recyclerView = view.findViewById(R.id.rcv_home);
        search = view.findViewById(R.id.searchbar);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(productHomeAdapter!=null){
            productHomeAdapter.release();
        }
    }
}
