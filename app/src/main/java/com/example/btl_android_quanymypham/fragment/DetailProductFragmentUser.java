package com.example.btl_android_quanymypham.fragment;

import static android.content.Intent.getIntent;

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

import com.example.btl_android_quanymypham.DAO.TTMyPhamDAOAdmin;
import com.example.btl_android_quanymypham.R;
import com.example.btl_android_quanymypham.adapter.ProductHomeAdapterUser;
import com.example.btl_android_quanymypham.model.TTMyPhamAdmin;

import java.util.ArrayList;
import java.util.List;

public class DetailProductFragmentUser extends Fragment {

    TextView tenmp,giatien,dungtich,mota,chitiet;
    ImageView img_detail,img_gohome;
    Button themgiohang,muangay;
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detailproduct_fragment_user, container, false);

        Innit(view);

        Bundle bundle = getArguments();
        if (bundle != null) {
            TTMyPhamAdmin productHome = (TTMyPhamAdmin) bundle.getSerializable("ObjectProduct");
            if (productHome != null) {
                tenmp.setText(productHome.getTenmypham());
                giatien.setText(String.valueOf(productHome.getGia()));
                dungtich.setText(productHome.getDungtich());
                mota.setText(productHome.getMota());
                chitiet.setText(productHome.getChitiet());

                Bitmap bitmap = BitmapFactory.decodeByteArray(productHome.getAnhsanpham(), 0, productHome.getAnhsanpham().length);
                img_detail.setImageBitmap(bitmap);
            }
        }
        return view;


    }

    private void Innit(View view) {
        img_detail = view.findViewById(R.id.img_detail);
        tenmp = view.findViewById(R.id.ten_detail);
        giatien = view.findViewById(R.id.gia_detail);
        dungtich = view.findViewById(R.id.dungtich_detail);
        mota = view.findViewById(R.id.mota_detail);
        chitiet = view.findViewById(R.id.chitiet_detail);
        img_gohome = view.findViewById(R.id.img_gohome);
        themgiohang = view.findViewById(R.id.btn_themgiohang);
        muangay = view.findViewById(R.id.btn_muangay);
    }
}
