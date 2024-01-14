package com.example.btl_android_quanymypham.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android_quanymypham.model.LoaiMyPhamAdmin;
import com.example.btl_android_quanymypham.R;
import com.example.btl_android_quanymypham.model.TaiKhoanAdmin;

import java.util.ArrayList;
import java.util.List;

public class LoaiMyPhamAdapterAdmin extends RecyclerView.Adapter<LoaiMyPhamAdapterAdmin.LoaiMyPhamViewHolder> implements Filterable {
    private List<LoaiMyPhamAdmin> loaiMyPhams;
    private List<LoaiMyPhamAdmin> loaiMyPhamsOld;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    Context context;
    EditText tenloai;
    EditText mota;

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()){
                    loaiMyPhams = loaiMyPhamsOld;
                }
                else {
                    List<LoaiMyPhamAdmin> list = new ArrayList<>();
                    for (LoaiMyPhamAdmin loaiMyPhamAdmin : loaiMyPhamsOld){
                        if (loaiMyPhamAdmin.getTenloai().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(loaiMyPhamAdmin);
                        }
                    }

                    loaiMyPhams = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = loaiMyPhams;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                loaiMyPhams = (List<LoaiMyPhamAdmin>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface OnItemClickListener {
        void onItemClick(int id);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(int id);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    public LoaiMyPhamAdapterAdmin(Context context, List<LoaiMyPhamAdmin> loaiMyPhams, EditText tenloai, EditText mota) {
        this.context = context;
        this.loaiMyPhams = loaiMyPhams;
        this.loaiMyPhamsOld = loaiMyPhams;
        this.tenloai = tenloai;
        this.mota = mota;
    }

    @NonNull
    @Override
    public LoaiMyPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loaimypham_admin,parent,false);
        return new LoaiMyPhamAdapterAdmin.LoaiMyPhamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiMyPhamViewHolder holder, int position) {
        LoaiMyPhamAdmin loaiMyPhamAdmin = loaiMyPhams.get(position);
        if (loaiMyPhamAdmin==null){
            return;
        }
        holder.item_tenloai.setText(loaiMyPhamAdmin.getTenloai());
        holder.item_mota.setText(loaiMyPhamAdmin.getMota());
        holder.Itemloaimypham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tenloai.setText(loaiMyPhamAdmin.getTenloai());
                mota.setText(loaiMyPhamAdmin.getMota());
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(
                            Integer.valueOf(loaiMyPhamAdmin.getId())
                    );
                }
            }
        });

        holder.Itemloaimypham.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onItemLongClick(Integer.valueOf(loaiMyPhamAdmin.getId()));
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (loaiMyPhams!=null){
            return loaiMyPhams.size();
        }
        return 0;

    }

    public class LoaiMyPhamViewHolder extends RecyclerView.ViewHolder {
        private CardView Itemloaimypham;
        private TextView item_tenloai;
        private TextView item_mota;
        public LoaiMyPhamViewHolder(@NonNull View itemView) {
            super(itemView);

            Itemloaimypham = itemView.findViewById(R.id.item_loaimypham_admin);
            item_tenloai = itemView.findViewById(R.id.item_tenloai);
            item_mota = itemView.findViewById(R.id.item_mota);
        }
    }
}
