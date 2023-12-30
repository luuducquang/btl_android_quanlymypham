package com.example.btl_android_quanymypham.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android_quanymypham.model.ProductHomeUser;
import com.example.btl_android_quanymypham.adapter.ProductHomeAdapterUser;
import com.example.btl_android_quanymypham.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragmentUser extends Fragment {
    private RecyclerView recyclerView;
    private ProductHomeAdapterUser productHomeAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment_user, container, false);

        recyclerView = view.findViewById(R.id.rcv_home);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);

        productHomeAdapter = new ProductHomeAdapterUser(requireContext(),getListProduct());
        recyclerView.setAdapter(productHomeAdapter);


        return view;


    }

    private List<ProductHomeUser> getListProduct(){
        List<ProductHomeUser> productHomeList = new ArrayList<>();
        productHomeList.add(new ProductHomeUser(R.drawable.bg_home,"Sữa rửa mặt Simple lành tính sạch thoáng - cho da nhạy cảm 150ml [CHÍNH HÃNG ĐỘC QUYỀN] [DIỆN MẠO MỚI]","150000"));
        productHomeList.add(new ProductHomeUser(R.drawable.bg_home,"Sữa rửa mặt Simple lành tính sạch thoáng  lành tính sạch thoáng - cho da nhạy cảm 150ml [CHÍNH HÃNG ĐỘC QUYỀN] [DIỆN MẠO MỚI]","150000"));
        productHomeList.add(new ProductHomeUser(R.drawable.bg_home,"Sữa rửa mặt Simple lành tính sạch thoáng - cho da nhạy cảm 150ml [CHÍNH HÃNG ĐỘC QUYỀN] [DIỆN MẠO MỚI]","150000"));
        productHomeList.add(new ProductHomeUser(R.drawable.bg_home,"Sữa rửa mặt Simple lành tính sạch thoáng - cho da nhạy cảm 150ml [CHÍNH HÃNG ĐỘC QUYỀN] [DIỆN MẠO MỚI]","150000"));
        productHomeList.add(new ProductHomeUser(R.drawable.bg_home,"Sữa rửa mặt Simple lành tính sạch thoáng  lành tính sạch thoáng - cho da nhạy cảm 150ml [CHÍNH HÃNG ĐỘC QUYỀN] [DIỆN MẠO MỚI]","150000"));
        productHomeList.add(new ProductHomeUser(R.drawable.bg_home,"Sữa rửa mặt Simple lành tính sạch thoáng - cho da nhạy cảm 150ml [CHÍNH HÃNG ĐỘC QUYỀN] [DIỆN MẠO MỚI]","150000"));
        productHomeList.add(new ProductHomeUser(R.drawable.bg_home,"Sữa rửa mặt Simple lành tính sạch thoáng - cho da nhạy cảm 150ml [CHÍNH HÃNG ĐỘC QUYỀN] [DIỆN MẠO MỚI]","150000"));
        productHomeList.add(new ProductHomeUser(R.drawable.bg_home,"Sữa rửa mặt Simple lành tính sạch thoáng  lành tính sạch thoáng - cho da nhạy cảm 150ml [CHÍNH HÃNG ĐỘC QUYỀN] [DIỆN MẠO MỚI]","150000"));
        productHomeList.add(new ProductHomeUser(R.drawable.bg_home,"Sữa rửa mặt Simple lành tính sạch thoáng - cho da nhạy cảm 150ml [CHÍNH HÃNG ĐỘC QUYỀN] [DIỆN MẠO MỚI]","150000"));
        return productHomeList;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(productHomeAdapter!=null){
            productHomeAdapter.release();
        }
    }
}
