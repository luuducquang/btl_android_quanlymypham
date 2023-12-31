package com.example.btl_android_quanymypham.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android_quanymypham.R;
import com.example.btl_android_quanymypham.model.LoaiMyPhamAdmin;
import com.example.btl_android_quanymypham.model.TaiKhoanAdmin;

import java.util.List;

public class TaiKhoanAdapterAdmin extends RecyclerView.Adapter<TaiKhoanAdapterAdmin.TaiKhoanViewHolder>{
    private List<TaiKhoanAdmin> taiKhoanAdmins;
    Context context;

    public TaiKhoanAdapterAdmin(Context context, List<TaiKhoanAdmin> taiKhoanAdmins) {
        this.context = context;
        this.taiKhoanAdmins = taiKhoanAdmins;
    }
    @NonNull
    @Override
    public TaiKhoanAdapterAdmin.TaiKhoanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_taikhoan_admin,parent,false);
        return new TaiKhoanAdapterAdmin.TaiKhoanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaiKhoanAdapterAdmin.TaiKhoanViewHolder holder, int position) {
        TaiKhoanAdmin taiKhoanAdmin = taiKhoanAdmins.get(position);
        if (taiKhoanAdmin==null){
            return;
        }
        holder.item_id.setText(String.valueOf(taiKhoanAdmin.getId()));
        holder.item_taikhoan.setText(taiKhoanAdmin.getTaikhoan());
        holder.item_matkhau.setText(taiKhoanAdmin.getMatkhau());
        holder.item_hoten.setText(taiKhoanAdmin.getHoten());
        holder.item_email.setText(taiKhoanAdmin.getEmail());
        holder.item_quyen.setText(taiKhoanAdmin.getQuyen());
        byte[]anhdaidien = taiKhoanAdmin.getAnhdaidien();
        Bitmap bitmap = BitmapFactory.decodeByteArray(anhdaidien,0,anhdaidien.length);
        holder.item_anhdaidien.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        if (taiKhoanAdmins!=null){
            return taiKhoanAdmins.size();
        }
        return 0;
    }

    public class TaiKhoanViewHolder extends RecyclerView.ViewHolder {
        private CardView Itemtaikhoan;
        private TextView item_id;
        private TextView item_taikhoan;
        private TextView item_matkhau;
        private TextView item_hoten;
        private TextView item_email;
        private TextView item_quyen;
        private ImageView item_anhdaidien;
        public TaiKhoanViewHolder(@NonNull View itemView) {
            super(itemView);

            Itemtaikhoan = itemView.findViewById(R.id.item_taikhoan_admin);
            item_id = itemView.findViewById(R.id.item_id);
            item_taikhoan = itemView.findViewById(R.id.item_taikhoan);
            item_matkhau = itemView.findViewById(R.id.item_matkhau);
            item_hoten = itemView.findViewById(R.id.item_hoten);
            item_email = itemView.findViewById(R.id.item_email);
            item_quyen = itemView.findViewById(R.id.item_quyen);
            item_anhdaidien = itemView.findViewById(R.id.item_anhdaidien);
        }
    }
}
