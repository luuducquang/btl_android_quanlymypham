package com.example.btl_android_quanymypham.fragment;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android_quanymypham.DAO.GioHangDAOUser;
import com.example.btl_android_quanymypham.DAO.HoaDonBanDAOAdmin;
import com.example.btl_android_quanymypham.R;
import com.example.btl_android_quanymypham.adapter.DatHangAdapterUser;
import com.example.btl_android_quanymypham.adapter.GioHangAdapterUser;
import com.example.btl_android_quanymypham.model.GioHangUser;
import com.example.btl_android_quanymypham.model.HoaDonBanAdmin;
import com.example.btl_android_quanymypham.model.TTMyPhamAdmin;
import com.example.btl_android_quanymypham.model.TaiKhoanAdmin;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class DatHangFragmentUser extends Fragment {

    TextInputLayout diachi,sdt;
    private RecyclerView recyclerView;
    TextView tongphu,tongtien,somathang,tongthanhtoan;
    Button btn_dathang;
    List<GioHangUser> gioHangUserList = new ArrayList<>();
    List<Integer> id_mp = new ArrayList<>();;
    DatHangAdapterUser datHangAdapterUser;
    TaiKhoanAdmin taiKhoanAdmin;
    HoaDonBanDAOAdmin db;

    GioHangDAOUser gioHangDAOUser;
    private Long TongTien = 0L;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dathang_fragment_user, container, false);
        
        db = new HoaDonBanDAOAdmin(requireContext());
        gioHangDAOUser = new GioHangDAOUser(requireContext());

        Bundle bundle = getArguments();
        if (bundle != null) {
            GioHangUser[] gioHangArray = (GioHangUser[]) bundle.getSerializable("ObjectListProduct");
            taiKhoanAdmin = (TaiKhoanAdmin) bundle.getSerializable("ObjectUser");
            gioHangUserList = new ArrayList<>(Arrays.asList(gioHangArray));
        }

        for (GioHangUser gioHangUser : gioHangUserList) {
            id_mp.add(gioHangUser.getId());
        }

        Innit(view);
        DataListView();
        HandlerButton();

        return view;
    }

    private void HandlerButton() {
        btn_dathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strDiachi = diachi.getEditText().getText().toString();
                String strSDT = sdt.getEditText().getText().toString();
                Calendar calendar =Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                if (strDiachi.isEmpty()||strSDT.isEmpty()){
                    Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    db.InsertListData(taiKhoanAdmin.getId(),taiKhoanAdmin.getHoten(),strDiachi,strSDT,sdf.format(calendar.getTime()),TongTien+30000,gioHangUserList);
                    Toast.makeText(requireContext(), "Đặt hàng thành công", Toast.LENGTH_SHORT).show();

                    ProductHomeFragmentUser productHomeFragmentUser = new ProductHomeFragmentUser();
                    AppCompatActivity activity = (AppCompatActivity) requireContext();
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, productHomeFragmentUser)
                            .addToBackStack(null)
                            .commit();

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ObjectUser", taiKhoanAdmin);
                    productHomeFragmentUser.setArguments(bundle);

                    gioHangDAOUser.deleteProducts(id_mp);
                }
            }
        });
    }

    private void DataListView() {
        if (gioHangUserList == null || gioHangUserList.isEmpty()) {
            return;
        }

        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 1));

        datHangAdapterUser = new DatHangAdapterUser(gioHangUserList, requireContext());

        recyclerView.setAdapter(datHangAdapterUser);

        for (int i = 0; i < gioHangUserList.size(); i++) {
            GioHangUser gioHangUser = gioHangUserList.get(i);
            TongTien += gioHangUser.getGia() * gioHangUser.getSoluong();
        }
        tongphu.setText(String.valueOf(TongTien));
        tongtien.setText(String.valueOf(TongTien+30000));
        tongthanhtoan.setText(String.valueOf(TongTien+30000));
        somathang.setText(String.valueOf(gioHangUserList.size()));
    }



    private void Innit(View view) {
        diachi = view.findViewById(R.id.til_dia_chi);
        sdt = view.findViewById(R.id.til_so_dien_thoai);
        recyclerView = view.findViewById(R.id.rcv_dathang);
        tongphu = view.findViewById(R.id.txt_tongphu);
        tongtien = view.findViewById(R.id.txt_tongtien);
        somathang = view.findViewById(R.id.soluong_mathang);
        tongthanhtoan = view.findViewById(R.id.tongtien_thanhtoan);
        btn_dathang = view.findViewById(R.id.btn_dathang);
    }
}
