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
import com.example.btl_android_quanymypham.model.TTMyPhamAdmin;
import com.example.btl_android_quanymypham.model.TaiKhoanAdmin;

import java.util.List;

public class TTMyPhamAdapterAdmin extends RecyclerView.Adapter<TTMyPhamAdapterAdmin.TTMyPhamViewHolder>{

    private List<TTMyPhamAdmin> ttMyPhamAdminList;

    Context context;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public interface OnItemClickListener {
        void onItemClick(TTMyPhamAdmin ttMyPhamAdmin);
    }

    public void setOnItemClickListener(TTMyPhamAdapterAdmin.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(TTMyPhamAdmin ttMyPhamAdmin);
    }

    public void setOnItemLongClickListener(TTMyPhamAdapterAdmin.OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }
    public TTMyPhamAdapterAdmin(List<TTMyPhamAdmin> ttMyPhamAdminList, Context context) {
        this.ttMyPhamAdminList = ttMyPhamAdminList;
        this.context = context;
    }

    @NonNull
    @Override
    public TTMyPhamAdapterAdmin.TTMyPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ttmypham_admin,parent,false);
        return new TTMyPhamAdapterAdmin.TTMyPhamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TTMyPhamAdapterAdmin.TTMyPhamViewHolder holder, int position) {
        TTMyPhamAdmin ttMyPhamAdmin = ttMyPhamAdminList.get(position);
        if (ttMyPhamAdmin==null){
            return;
        }
        holder.item_id.setText(String.valueOf(ttMyPhamAdmin.getId()));
        holder.item_temmypham.setText(ttMyPhamAdmin.getTenmypham());
        holder.item_dungtich.setText(ttMyPhamAdmin.getDungtich());
        holder.item_loaimp.setText(ttMyPhamAdmin.getTenloaimypham());
        holder.item_giaban.setText(String.valueOf(ttMyPhamAdmin.getGia()));
        holder.item_mota.setText(ttMyPhamAdmin.getMota());
        holder.item_chitiet.setText(ttMyPhamAdmin.getChitiet());
        byte[]anhsanpham = ttMyPhamAdmin.getAnhsanpham();
        Bitmap bitmap = BitmapFactory.decodeByteArray(anhsanpham,0,anhsanpham.length);
        holder.item_anh.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        if (ttMyPhamAdminList!=null){
            return ttMyPhamAdminList.size();
        }
        return 0;
    }

    public class TTMyPhamViewHolder extends RecyclerView.ViewHolder {
        private CardView ItemTTMyPham;
        private TextView item_id;
        private TextView item_temmypham;
        private TextView item_dungtich;
        private TextView item_loaimp;
        private ImageView item_anh;
        private TextView item_giaban;
        private TextView item_mota;
        private TextView item_chitiet;
        public TTMyPhamViewHolder(@NonNull View itemView) {
            super(itemView);

            ItemTTMyPham = itemView.findViewById(R.id.item_ttmypham_admin);
            item_id = itemView.findViewById(R.id.item_id);
            item_temmypham = itemView.findViewById(R.id.item_tenmypham);
            item_dungtich = itemView.findViewById(R.id.item_dungtich);
            item_loaimp = itemView.findViewById(R.id.item_loaimypham);
            item_anh = itemView.findViewById(R.id.item_anhsanpham);
            item_giaban = itemView.findViewById(R.id.item_gia);
            item_mota = itemView.findViewById(R.id.item_mota);
            item_chitiet = itemView.findViewById(R.id.item_chitiet);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                        TTMyPhamAdmin ttMyPhamAdmin = ttMyPhamAdminList.get(position);
                        onItemClickListener.onItemClick(ttMyPhamAdmin);
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onItemLongClickListener != null) {
                        TTMyPhamAdmin ttMyPhamAdmin = ttMyPhamAdminList.get(position);
                        onItemLongClickListener.onItemLongClick(ttMyPhamAdmin);
                        return true;
                    }
                    return false;
                }
            });

        }
    }
}
