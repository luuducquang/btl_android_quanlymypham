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
import com.example.btl_android_quanymypham.model.ChiTietHoaDonNhapAdmin;

import java.util.List;

public class ChiTietHoaDonBanAdapterAdmin extends RecyclerView.Adapter<ChiTietHoaDonBanAdapterAdmin.ChiTietHoaDonBanViewHolder>{

    List<ChiTietHoaDonBanAdmin> chiTietHoaDonBanAdminList;
    Context context;

    public ChiTietHoaDonBanAdapterAdmin(List<ChiTietHoaDonBanAdmin> chiTietHoaDonBanAdminList, Context context) {
        this.chiTietHoaDonBanAdminList = chiTietHoaDonBanAdminList;
        this.context = context;
    }

    private ChiTietHoaDonBanAdapterAdmin.OnDelItemClickListener onDelItemClickListener;

    private ChiTietHoaDonBanAdapterAdmin.OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(ChiTietHoaDonBanAdmin chiTietHoaDonBanAdmin);
    }

    public void setOnItemClickListener(ChiTietHoaDonBanAdapterAdmin.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnDelItemClickListener {
        void onDelItemClick(ChiTietHoaDonBanAdmin chiTietHoaDonBanAdmin);
    }
    public void setDelOnItemClickListener(ChiTietHoaDonBanAdapterAdmin.OnDelItemClickListener listener) {
        this.onDelItemClickListener = listener;
    }
    @NonNull
    @Override
    public ChiTietHoaDonBanAdapterAdmin.ChiTietHoaDonBanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chitiethoadonban_admin,parent,false);
        return new ChiTietHoaDonBanAdapterAdmin.ChiTietHoaDonBanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChiTietHoaDonBanAdapterAdmin.ChiTietHoaDonBanViewHolder holder, int position) {
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
        private ImageView img_del;
        private ImageView img_mp;
        public ChiTietHoaDonBanViewHolder(@NonNull View itemView) {
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
                        ChiTietHoaDonBanAdmin chiTietHoaDonBanAdmin = chiTietHoaDonBanAdminList.get(position);
                        onDelItemClickListener.onDelItemClick(chiTietHoaDonBanAdmin);
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                        ChiTietHoaDonBanAdmin chiTietHoaDonBanAdmin = chiTietHoaDonBanAdminList.get(position);
                        onItemClickListener.onItemClick(chiTietHoaDonBanAdmin);
                    }
                }
            });
        }
    }
}
