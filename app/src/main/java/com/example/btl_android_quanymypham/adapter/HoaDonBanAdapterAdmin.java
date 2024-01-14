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
import com.example.btl_android_quanymypham.model.HoaDonBanAdmin;
import com.example.btl_android_quanymypham.model.HoaDonNhapAdmin;

import java.util.List;

public class HoaDonBanAdapterAdmin extends RecyclerView.Adapter<HoaDonBanAdapterAdmin.HoaDonBanViewHolder>{
    List<HoaDonBanAdmin> hoaDonBanAdminList;
    Context context;

    public HoaDonBanAdapterAdmin(List<HoaDonBanAdmin> hoaDonBanAdminList, Context context) {
        this.hoaDonBanAdminList = hoaDonBanAdminList;
        this.context = context;
    }

    private OnDelItemClickListener onDelItemClickListener;
    private OnInfItemClickListener onInfItemClickListener;

    public interface OnInfItemClickListener {
        void onInfItemClick(HoaDonBanAdmin hoaDonBanAdmin);
    }

    public void setInfOnItemClickListener(HoaDonBanAdapterAdmin.OnInfItemClickListener listener) {
        this.onInfItemClickListener = listener;
    }

    public interface OnDelItemClickListener {
        void onDelItemClick(HoaDonBanAdmin hoaDonBanAdmin);
    }

    public void setDelOnItemClickListener(HoaDonBanAdapterAdmin.OnDelItemClickListener listener) {
        this.onDelItemClickListener = listener;
    }
    @NonNull
    @Override
    public HoaDonBanAdapterAdmin.HoaDonBanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoadonban_admin,parent,false);
        return new HoaDonBanAdapterAdmin.HoaDonBanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonBanAdapterAdmin.HoaDonBanViewHolder holder, int position) {
        HoaDonBanAdmin hoaDonBanAdmin = hoaDonBanAdminList.get(position);
        if (hoaDonBanAdmin==null){
            return;
        }
        holder.item_tenkh.setText(hoaDonBanAdmin.getTenKH());
        holder.item_diachi.setText(hoaDonBanAdmin.getDiaChi());
        holder.item_sdt.setText(hoaDonBanAdmin.getSdt());
        holder.item_ngayban.setText(hoaDonBanAdmin.getNgayBan());
        holder.item_tongtien.setText(String.valueOf(hoaDonBanAdmin.getTongTien()));
        holder.item_nguoitao.setText(hoaDonBanAdmin.getHoTen());
    }

    @Override
    public int getItemCount() {
        if (hoaDonBanAdminList!=null){
            return hoaDonBanAdminList.size();
        }
        return 0;
    }

    public class HoaDonBanViewHolder extends RecyclerView.ViewHolder {
        private CardView Itemhoadonban;
        private TextView item_tenkh;
        private TextView item_diachi;
        private TextView item_sdt;
        private TextView item_ngayban;
        private TextView item_tongtien;
        private TextView item_nguoitao;
        private ImageView img_inf;
        private ImageView img_del;
        public HoaDonBanViewHolder(@NonNull View itemView) {
            super(itemView);

            Itemhoadonban = itemView.findViewById(R.id.item_hoadonban_admin);
            item_tenkh = itemView.findViewById(R.id.item_tenkh);
            item_diachi = itemView.findViewById(R.id.item_diachi);
            item_sdt = itemView.findViewById(R.id.item_sdt);
            item_ngayban = itemView.findViewById(R.id.item_ngayban);
            item_tongtien = itemView.findViewById(R.id.item_tongtien);
            item_nguoitao = itemView.findViewById(R.id.item_nguoitao);
            img_inf = itemView.findViewById(R.id.img_info);
            img_del = itemView.findViewById(R.id.img_delete);

            img_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onDelItemClickListener != null) {
                        HoaDonBanAdmin hoaDonBanAdmin = hoaDonBanAdminList.get(position);
                        onDelItemClickListener.onDelItemClick(hoaDonBanAdmin);
                    }
                }
            });

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
