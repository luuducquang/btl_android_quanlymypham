package com.example.btl_android_quanymypham.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android_quanymypham.R;
import com.example.btl_android_quanymypham.model.HoaDonNhapAdmin;
import com.example.btl_android_quanymypham.model.TTMyPhamAdmin;

import java.util.List;

public class HoaDonNhapAdapterAdmin extends RecyclerView.Adapter<HoaDonNhapAdapterAdmin.HoaDonNhapViewHolder> {
    List<HoaDonNhapAdmin> hoaDonNhapAdminList;
    Context context;
    private OnDelItemClickListener onDelItemClickListener;
    private OnInfItemClickListener onInfItemClickListener;

    public interface OnInfItemClickListener {
        void onInfItemClick(HoaDonNhapAdmin hoaDonNhapAdmin);
    }

    public void setInfOnItemClickListener(HoaDonNhapAdapterAdmin.OnInfItemClickListener listener) {
        this.onInfItemClickListener = listener;
    }

    public interface OnDelItemClickListener {
        void onDelItemClick(HoaDonNhapAdmin hoaDonNhapAdmin);
    }

    public void setDelOnItemClickListener(HoaDonNhapAdapterAdmin.OnDelItemClickListener listener) {
        this.onDelItemClickListener = listener;
    }

    public HoaDonNhapAdapterAdmin(List<HoaDonNhapAdmin> hoaDonNhapAdminList, Context context) {
        this.hoaDonNhapAdminList = hoaDonNhapAdminList;
        this.context = context;
    }

    @NonNull
    @Override
    public HoaDonNhapAdapterAdmin.HoaDonNhapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoadonnhap_admin,parent,false);
        return new HoaDonNhapAdapterAdmin.HoaDonNhapViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonNhapAdapterAdmin.HoaDonNhapViewHolder holder, int position) {
        HoaDonNhapAdmin hoaDonNhapAdmin = hoaDonNhapAdminList.get(position);
        if (hoaDonNhapAdmin==null){
            return;
        }
        holder.item_tenncc.setText(hoaDonNhapAdmin.getTenNCC());
        holder.item_ngaynhap.setText(hoaDonNhapAdmin.getNgayNhap());
        holder.item_tongtien.setText(String.valueOf(hoaDonNhapAdmin.getTongTien()));
        holder.item_hoten.setText(hoaDonNhapAdmin.getHoTen());
    }

    @Override
    public int getItemCount() {
        if (hoaDonNhapAdminList!=null){
            return hoaDonNhapAdminList.size();
        }
        return 0;
    }

    public class HoaDonNhapViewHolder extends RecyclerView.ViewHolder {
        private CardView Itemhoadonnhap;
        private TextView item_tenncc;
        private TextView item_ngaynhap;
        private TextView item_tongtien;
        private TextView item_hoten;
        private ImageView img_inf;
        private ImageView img_del;
        public HoaDonNhapViewHolder(@NonNull View itemView) {
            super(itemView);

            Itemhoadonnhap = itemView.findViewById(R.id.item_hoadonnhap_admin);
            item_tenncc = itemView.findViewById(R.id.item_tenncc);
            item_ngaynhap = itemView.findViewById(R.id.item_ngaynhap);
            item_tongtien = itemView.findViewById(R.id.item_tongtien);
            item_hoten = itemView.findViewById(R.id.item_nguoitao);
            img_inf = itemView.findViewById(R.id.img_info);
            img_del = itemView.findViewById(R.id.img_delete);

            img_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onDelItemClickListener != null) {
                        HoaDonNhapAdmin hoaDonNhapAdmin = hoaDonNhapAdminList.get(position);
                        onDelItemClickListener.onDelItemClick(hoaDonNhapAdmin);
                    }
                }
            });

            img_inf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onInfItemClickListener != null) {
                        HoaDonNhapAdmin hoaDonNhapAdmin = hoaDonNhapAdminList.get(position);
                        onInfItemClickListener.onInfItemClick(hoaDonNhapAdmin);
                    }
                }
            });
        }
    }
}
