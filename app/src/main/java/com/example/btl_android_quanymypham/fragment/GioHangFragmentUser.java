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
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android_quanymypham.DAO.GioHangDAOUser;
import com.example.btl_android_quanymypham.DAO.TTMyPhamDAOAdmin;
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
    TTMyPhamAdmin productHome;
    TTMyPhamDAOAdmin ttMyPhamDAOAdmin;
    GioHangDAOUser db;
    List<GioHangUser> gioHangUserList = new ArrayList<>();
    private Long TongTien ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.giohang_fragment_user, container, false);

        db = new GioHangDAOUser(requireContext());
        ttMyPhamDAOAdmin = new TTMyPhamDAOAdmin(requireContext());

        Bundle bundle = getArguments();
        if (bundle != null) {
            taiKhoanAdmin = (TaiKhoanAdmin) bundle.getSerializable("ObjectUser");
        }

        Innit(view);
        DataListView();
        HandlerButtoon();

        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Giỏ hàng");
        }

        return view;
    }

    private void HandlerButtoon() {
        muangay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gioHangUserList.size()!=0){
                    DatHangFragmentUser datHangFragmentUser = new DatHangFragmentUser();
                    AppCompatActivity activity = (AppCompatActivity) requireContext();
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, datHangFragmentUser)
                            .addToBackStack(null)
                            .commit();

                    GioHangUser[] gioHangArray = new GioHangUser[gioHangUserList.size()];
                    gioHangArray = gioHangUserList.toArray(gioHangArray);

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ObjectListProduct", gioHangArray);
                    bundle.putSerializable("ObjectUser", taiKhoanAdmin);
                    datHangFragmentUser.setArguments(bundle);
                }
                else{
                    Toast.makeText(requireContext(), "Không có sản phẩm nào được mua", Toast.LENGTH_SHORT).show();
                }

                handleExit();
            }
        });
    }

    private void handleExit() {
        TongTien = 0L;
        gioHangUserList.clear();
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

        gioHangAdapterUser.setOnCheckedChangeListener(new GioHangAdapterUser.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(boolean isChecked, GioHangUser gioHangUser) {
                if (isChecked){
                    gioHangUserList.add(gioHangUser);
                }else {
                    for (int i = 0; i < gioHangUserList.size(); i++) {
                        if (gioHangUserList.get(i).getMamp() == gioHangUser.getMamp()) {
                            gioHangUserList.remove(i);
                            break;
                        }
                    }
                }
                Tinhtien();
            }
        });

        gioHangAdapterUser.setDelOnItemClickListener(new GioHangAdapterUser.OnDelItemClickListener() {
            @Override
            public void onDelItemClick(GioHangUser gioHangUser) {
                db.deleteGioHang(gioHangUser.getId());
                Tinhtien();
                DataListView();
            }
        });

        gioHangAdapterUser.setOnItemClickListener(new GioHangAdapterUser.OnPlusItemClickListener() {
            @Override
            public void onPlusItemClick(GioHangUser gioHangUser) {
               if (ttMyPhamDAOAdmin.kiemTraSoLuong(gioHangUser.getMamp(),1)){
                   db.Tangsoluong(gioHangUser.getId());
                   gioHangUser.setSoluong(gioHangUser.getSoluong() + 1);
                   Tinhtien();
                   gioHangAdapterUser.notifyDataSetChanged();
               }
               else {
                   Toast.makeText(requireContext(), "Số lượng tồn kho không đủ", Toast.LENGTH_SHORT).show();
               }
            }
        });

        gioHangAdapterUser.setOnItemClickListener(new GioHangAdapterUser.OnMinusItemClickListener() {
            @Override
            public void onMinusItemClick(GioHangUser gioHangUser) {
                db.Giamsoluong(gioHangUser.getId());
                if (gioHangUser.getSoluong()>1){
                    gioHangUser.setSoluong(gioHangUser.getSoluong() - 1);
                }
                Tinhtien();
                gioHangAdapterUser.notifyDataSetChanged();
            }
        });

        gioHangAdapterUser.setOnItemClickListener(new GioHangAdapterUser.OnItemClickListener() {
            @Override
            public void onItemClick(GioHangUser gioHangUser) {

                productHome = db.getThongTinMyPham(gioHangUser.getMamp());


                DetailProductFragmentUser detailFragment = new DetailProductFragmentUser();

                Bundle bundle = new Bundle();
                bundle.putSerializable("ObjectProduct", productHome);
                bundle.putSerializable("ObjectUser", taiKhoanAdmin);
                detailFragment.setArguments(bundle);

                AppCompatActivity activity = (AppCompatActivity) requireContext();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, detailFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

    }

    private void Tinhtien() {
        TongTien = 0L;
        for (int i = 0; i < gioHangUserList.size(); i++) {
            TongTien += (gioHangUserList.get(i).getGia() * gioHangUserList.get(i).getSoluong());
        }
        tongtientamtinh.setText(String.valueOf(TongTien));
    }


    private void Innit(View view) {
        muangay = view.findViewById(R.id.btn_muangay);
        tongtientamtinh = view.findViewById(R.id.txt_tongtien);
        recyclerView = view.findViewById(R.id.rcv_giohang);
    }
}
