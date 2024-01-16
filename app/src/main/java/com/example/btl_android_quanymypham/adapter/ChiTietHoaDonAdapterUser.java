package com.example.btl_android_quanymypham.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android_quanymypham.R;
import com.example.btl_android_quanymypham.model.ChiTietHoaDonBanAdmin;

import java.util.List;

public class ChiTietHoaDonAdapterUser extends RecyclerView.Adapter<ChiTietHoaDonAdapterUser.ChiTietHoaDonBanViewHolder>{

    List<ChiTietHoaDonBanAdmin> chiTietHoaDonBanAdminList;
    Context context;

    public ChiTietHoaDonAdapterUser(List<ChiTietHoaDonBanAdmin> chiTietHoaDonBanAdminList, Context context) {
        this.chiTietHoaDonBanAdminList = chiTietHoaDonBanAdminList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChiTietHoaDonAdapterUser.ChiTietHoaDonBanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chitiethoadon_user,parent,false);
        return new ChiTietHoaDonAdapterUser.ChiTietHoaDonBanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChiTietHoaDonAdapterUser.ChiTietHoaDonBanViewHolder holder, int position) {
        ChiTietHoaDonBanAdmin chiTietHoaDonBanAdmin = chiTietHoaDonBanAdminList.get(position);
        if (chiTietHoaDonBanAdmin==null){
            return;
        }
        holder.item_tenmp.setText(chiTietHoaDonBanAdmin.getTenMP());
        holder.item_soluong.setText(String.valueOf(chiTietHoaDonBanAdmin.getSoLuong()));
        holder.item_dongia.setText(String.valueOf(chiTietHoaDonBanAdmin.getDonGia()));
        holder.item_tongtien.setText(String.valueOf(chiTietHoaDonBanAdmin.getTongTien()));
        byte[]anhsanpham = chiTietHoaDonBanAdmin.getHinhAnh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(anhsanpham,0,anhsanpham.length);
        holder.img_mp.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        if (chiTietHoaDonBanAdminList!=null){
            return chiTietHoaDonBanAdminList.size();
        }
        return 0;
    }

    public class ChiTietHoaDonBanViewHolder extends RecyclerView.ViewHolder {
        private TextView item_tenmp;
        private TextView item_soluong;
        private TextView item_dongia;
        private TextView item_tongtien;
        private ImageView img_mp;
        public ChiTietHoaDonBanViewHolder(@NonNull View itemView) {
            super(itemView);

            item_tenmp = itemView.findViewById(R.id.item_tenmp);
            item_soluong = itemView.findViewById(R.id.item_soluong);
            item_dongia = itemView.findViewById(R.id.item_dongia);
            item_tongtien = itemView.findViewById(R.id.item_tongtien);
            img_mp = itemView.findViewById(R.id.img_mp);
        }
    }
}
