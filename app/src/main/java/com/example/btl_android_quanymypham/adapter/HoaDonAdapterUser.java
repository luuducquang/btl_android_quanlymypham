package com.example.btl_android_quanymypham.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android_quanymypham.R;
import com.example.btl_android_quanymypham.model.HoaDonBanAdmin;

import java.util.ArrayList;
import java.util.List;

public class HoaDonAdapterUser extends RecyclerView.Adapter<HoaDonAdapterUser.HoaDonViewHolder> {
    List<HoaDonBanAdmin> hoaDonBanAdminList;
    Context context;

    public HoaDonAdapterUser(List<HoaDonBanAdmin> hoaDonBanAdminList, Context context) {
        this.hoaDonBanAdminList = hoaDonBanAdminList;
        this.context = context;
    }

    private OnInfItemClickListener onInfItemClickListener;


    public interface OnInfItemClickListener {
        void onInfItemClick(HoaDonBanAdmin hoaDonBanAdmin);
    }

    public void setInfOnItemClickListener(HoaDonAdapterUser.OnInfItemClickListener listener) {
        this.onInfItemClickListener = listener;
    }
    @NonNull
    @Override
    public HoaDonAdapterUser.HoaDonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoadon_user,parent,false);
        return new HoaDonAdapterUser.HoaDonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonAdapterUser.HoaDonViewHolder holder, int position) {
        HoaDonBanAdmin hoaDonBanAdmin = hoaDonBanAdminList.get(position);
        if (hoaDonBanAdmin==null){
            return;
        }
        holder.item_diachi.setText(hoaDonBanAdmin.getDiaChi());
        holder.item_sdt.setText(hoaDonBanAdmin.getSdt());
        holder.item_ngayban.setText(hoaDonBanAdmin.getNgayBan());
        holder.item_tongtien.setText(String.valueOf(hoaDonBanAdmin.getTongTien()));
    }

    @Override
    public int getItemCount() {
        if (hoaDonBanAdminList!=null){
            return hoaDonBanAdminList.size();
        }
        return 0;
    }

    public class HoaDonViewHolder extends RecyclerView.ViewHolder {
        private TextView item_diachi;
        private TextView item_sdt;
        private TextView item_ngayban;
        private TextView item_tongtien;
        private ImageView img_inf;
        public HoaDonViewHolder(@NonNull View itemView) {
            super(itemView);

            item_diachi = itemView.findViewById(R.id.item_diachi);
            item_sdt = itemView.findViewById(R.id.item_sdt);
            item_ngayban = itemView.findViewById(R.id.item_ngayban);
            item_tongtien = itemView.findViewById(R.id.item_tongtien);
            img_inf = itemView.findViewById(R.id.img_info);

            img_inf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onInfItemClickListener != null) {
                        HoaDonBanAdmin hoaDonBanAdmin = hoaDonBanAdminList.get(position);
                        onInfItemClickListener.onInfItemClick(hoaDonBanAdmin);
                    }
                }
            });
        }
    }
}
