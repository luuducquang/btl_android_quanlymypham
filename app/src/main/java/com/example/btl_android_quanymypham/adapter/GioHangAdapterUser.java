package com.example.btl_android_quanymypham.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android_quanymypham.R;
import com.example.btl_android_quanymypham.model.ChiTietHoaDonNhapAdmin;
import com.example.btl_android_quanymypham.model.GioHangUser;
import com.example.btl_android_quanymypham.model.NhaCungCapAdmin;

import java.util.List;

public class GioHangAdapterUser extends RecyclerView.Adapter<GioHangAdapterUser.GioHangViewHolder>{
    private List<GioHangUser> gioHangUsers;
    private Context mConText;

    private OnDelItemClickListener onDelItemClickListener;

    private OnCheckedChangeListener onCheckedChangeListener;
    private OnPlusItemClickListener onPlusItemClickListener;
    private OnMinusItemClickListener onMinusItemClickListener;

    public interface OnPlusItemClickListener {
        void onPlusItemClick(GioHangUser gioHangUser);
    }

    public void setOnItemClickListener(GioHangAdapterUser.OnPlusItemClickListener listener) {
        this.onPlusItemClickListener = listener;
    }

    public interface OnMinusItemClickListener {
        void onMinusItemClick(GioHangUser gioHangUser);
    }

    public void setOnItemClickListener(GioHangAdapterUser.OnMinusItemClickListener listener) {
        this.onMinusItemClickListener = listener;
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(boolean isChecked, GioHangUser gioHangUser);
    }


    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.onCheckedChangeListener = listener;
    }



    public interface OnDelItemClickListener {
        void onDelItemClick(GioHangUser gioHangUser);
    }

    public void setDelOnItemClickListener(GioHangAdapterUser.OnDelItemClickListener listener) {
        this.onDelItemClickListener = listener;
    }

    public GioHangAdapterUser(List<GioHangUser> gioHangUsers, Context mConText) {
        this.gioHangUsers = gioHangUsers;
        this.mConText = mConText;
    }

    @NonNull
    @Override
    public GioHangAdapterUser.GioHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang_user,parent,false);
        return new GioHangAdapterUser.GioHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GioHangAdapterUser.GioHangViewHolder holder, int position) {
        GioHangUser gioHangUser = gioHangUsers.get(position);
        if (gioHangUser==null){
            return;
        }
        holder.item_ten.setText(gioHangUser.getTenmp());
        holder.item_gia.setText(String.valueOf(gioHangUser.getGia()));
        holder.item_soluong.setText(String.valueOf(gioHangUser.getSoluong()));

        byte[]anhsanpham = gioHangUser.getAnh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(anhsanpham,0,anhsanpham.length);
        holder.item_img.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        if (gioHangUsers!=null){
            return gioHangUsers.size();
        }
        return 0;
    }

    public class GioHangViewHolder extends RecyclerView.ViewHolder{
        private CardView ItemGioHang;
        private CheckBox item_select;
        private ImageView item_img;
        private TextView item_ten;
        private TextView item_gia;
        private ImageView item_minus;
        private TextView item_soluong;
        private ImageView item_plus;
        private ImageView item_delete;

        public GioHangViewHolder(@NonNull View itemView) {
            super(itemView);

            ItemGioHang = itemView.findViewById(R.id.id_item_giohang);
            item_select = itemView.findViewById(R.id.cb_select_giohang);
            item_img = itemView.findViewById(R.id.anh_giohang);
            item_ten = itemView.findViewById(R.id.ten_giohang);
            item_gia = itemView.findViewById(R.id.gia_giohang);
            item_minus = itemView.findViewById(R.id.minus_giohang);
            item_soluong = itemView.findViewById(R.id.soluong_giohang);
            item_plus = itemView.findViewById(R.id.plus_giohang);
            item_delete = itemView.findViewById(R.id.item_delete);

            item_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    final int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onCheckedChangeListener != null) {
                        final GioHangUser gioHangUser = gioHangUsers.get(position);
                        onCheckedChangeListener.onCheckedChanged(isChecked, gioHangUser);
                    }
                }
            });

            item_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onDelItemClickListener != null) {
                        GioHangUser gioHangUser = gioHangUsers.get(position);
                        onDelItemClickListener.onDelItemClick(gioHangUser);
                    }
                }
            });

            item_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onPlusItemClickListener != null) {
                        GioHangUser gioHangUser = gioHangUsers.get(position);
                        onPlusItemClickListener.onPlusItemClick(gioHangUser);
                    }
                }
            });

            item_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onMinusItemClickListener != null) {
                        GioHangUser gioHangUser = gioHangUsers.get(position);
                        onMinusItemClickListener.onMinusItemClick(gioHangUser);
                    }
                }
            });


        }
    }
}
