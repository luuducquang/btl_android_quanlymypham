package com.example.btl_android_quanymypham.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android_quanymypham.R;
import com.example.btl_android_quanymypham.model.GioHangUser;

import java.util.List;

public class GioHangAdapterUser extends RecyclerView.Adapter<GioHangAdapterUser.GioHangViewHolder>{
    private List<GioHangUser> gioHangUsers;
    private Context mConText;

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
        }
    }
}
