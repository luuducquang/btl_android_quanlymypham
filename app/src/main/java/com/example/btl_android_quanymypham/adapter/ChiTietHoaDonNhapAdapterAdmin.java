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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android_quanymypham.R;
import com.example.btl_android_quanymypham.model.ChiTietHoaDonNhapAdmin;
import com.example.btl_android_quanymypham.model.HoaDonNhapAdmin;

import java.util.List;

public class ChiTietHoaDonNhapAdapterAdmin extends RecyclerView.Adapter<ChiTietHoaDonNhapAdapterAdmin.ChiTietHoaDonNhapViewHolder> {
    List<ChiTietHoaDonNhapAdmin> chiTietHoaDonNhapAdminList;
    Context context;
    private OnDelItemClickListener onDelItemClickListener;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(ChiTietHoaDonNhapAdmin chiTietHoaDonNhapAdmin);
    }

    public void setOnItemClickListener(ChiTietHoaDonNhapAdapterAdmin.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    public interface OnDelItemClickListener {
        void onDelItemClick(ChiTietHoaDonNhapAdmin chiTietHoaDonNhapAdmin);
    }

    public void setDelOnItemClickListener(ChiTietHoaDonNhapAdapterAdmin.OnDelItemClickListener listener) {
        this.onDelItemClickListener = listener;
    }

    public ChiTietHoaDonNhapAdapterAdmin(List<ChiTietHoaDonNhapAdmin> chiTietHoaDonNhapAdminList, Context context) {
        this.chiTietHoaDonNhapAdminList = chiTietHoaDonNhapAdminList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChiTietHoaDonNhapAdapterAdmin.ChiTietHoaDonNhapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chitiethoadonnhap_admin,parent,false);
        return new ChiTietHoaDonNhapAdapterAdmin.ChiTietHoaDonNhapViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChiTietHoaDonNhapAdapterAdmin.ChiTietHoaDonNhapViewHolder holder, int position) {
        ChiTietHoaDonNhapAdmin chiTietHoaDonNhapAdmin = chiTietHoaDonNhapAdminList.get(position);
        if (chiTietHoaDonNhapAdmin==null){
            return;
        }
        holder.item_tenmp.setText(chiTietHoaDonNhapAdmin.getTenMP());
        holder.item_soluong.setText(String.valueOf(chiTietHoaDonNhapAdmin.getSoLuong()));
        holder.item_dongia.setText(String.valueOf(chiTietHoaDonNhapAdmin.getDonGia()));
        holder.item_tongtien.setText(String.valueOf(chiTietHoaDonNhapAdmin.getTongTien()));
        byte[]anhsanpham = chiTietHoaDonNhapAdmin.getHinhAnh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(anhsanpham,0,anhsanpham.length);
        holder.img_mp.setImageBitmap(bitmap);

    }

    @Override
    public int getItemCount() {
        if (chiTietHoaDonNhapAdminList!=null){
            return chiTietHoaDonNhapAdminList.size();
        }
        return 0;
    }

    public class ChiTietHoaDonNhapViewHolder extends RecyclerView.ViewHolder {
        private TextView item_tenmp;
        private TextView item_soluong;
        private TextView item_dongia;
        private TextView item_tongtien;
        private ImageView img_del;
        private ImageView img_mp;
        public ChiTietHoaDonNhapViewHolder(@NonNull View itemView) {
            super(itemView);

            item_tenmp = itemView.findViewById(R.id.item_tenmp);
            item_soluong = itemView.findViewById(R.id.item_soluong);
            item_dongia = itemView.findViewById(R.id.item_dongia);
            item_tongtien = itemView.findViewById(R.id.item_tongtien);
            img_del = itemView.findViewById(R.id.img_delete);
            img_mp = itemView.findViewById(R.id.img_mp);

            img_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onDelItemClickListener != null) {
                        ChiTietHoaDonNhapAdmin chiTietHoaDonNhapAdmin = chiTietHoaDonNhapAdminList.get(position);
                        onDelItemClickListener.onDelItemClick(chiTietHoaDonNhapAdmin);
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                        ChiTietHoaDonNhapAdmin chiTietHoaDonNhapAdmin = chiTietHoaDonNhapAdminList.get(position);
                        onItemClickListener.onItemClick(chiTietHoaDonNhapAdmin);
                    }
                }
            });
        }
    }
}
