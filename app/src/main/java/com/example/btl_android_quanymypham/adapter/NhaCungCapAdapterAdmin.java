package com.example.btl_android_quanymypham.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android_quanymypham.R;
import com.example.btl_android_quanymypham.model.NhaCungCapAdmin;
import com.example.btl_android_quanymypham.model.TaiKhoanAdmin;

import java.util.ArrayList;
import java.util.List;

public class NhaCungCapAdapterAdmin extends RecyclerView.Adapter<NhaCungCapAdapterAdmin.NhaCungCapViewHolder> implements Filterable {
    List<NhaCungCapAdmin> nhaCungCapAdminList;
    List<NhaCungCapAdmin> nhaCungCapAdminListOld;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    Context context;

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()){
                    nhaCungCapAdminList = nhaCungCapAdminListOld;
                }
                else {
                    List<NhaCungCapAdmin> list = new ArrayList<>();
                    for (NhaCungCapAdmin nhaCungCapAdmin : nhaCungCapAdminListOld){
                        if (nhaCungCapAdmin.getTen().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(nhaCungCapAdmin);
                        }
                    }

                    nhaCungCapAdminList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = nhaCungCapAdminList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                nhaCungCapAdminList = (List<NhaCungCapAdmin>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface OnItemClickListener {
        void onItemClick(NhaCungCapAdmin nhaCungCapAdmin);
    }

    public void setOnItemClickListener(NhaCungCapAdapterAdmin.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(NhaCungCapAdmin nhaCungCapAdmin);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    public NhaCungCapAdapterAdmin(List<NhaCungCapAdmin> nhaCungCapAdminList, Context context) {
        this.nhaCungCapAdminList = nhaCungCapAdminList;
        this.nhaCungCapAdminListOld = nhaCungCapAdminList;
        this.context = context;
    }

    @NonNull
    @Override
    public NhaCungCapAdapterAdmin.NhaCungCapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nhacungcap_admin,parent,false);
        return new NhaCungCapAdapterAdmin.NhaCungCapViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NhaCungCapAdapterAdmin.NhaCungCapViewHolder holder, int position) {
        NhaCungCapAdmin nhaCungCapAdmin = nhaCungCapAdminList.get(position);
        if (nhaCungCapAdmin==null){
            return;
        }
        holder.item_ten.setText(nhaCungCapAdmin.getTen());
        holder.item_diachi.setText(nhaCungCapAdmin.getDiachi());
        holder.item_sdt.setText(nhaCungCapAdmin.getSdt());
    }

    @Override
    public int getItemCount() {
        if (nhaCungCapAdminList!=null){
            return nhaCungCapAdminList.size();
        }
        return 0;
    }

    public class NhaCungCapViewHolder extends RecyclerView.ViewHolder {
        private CardView Itemnhacungcap;
        private TextView item_ten;
        private TextView item_diachi;
        private TextView item_sdt;

        public NhaCungCapViewHolder(@NonNull View itemView) {
            super(itemView);
            Itemnhacungcap = itemView.findViewById(R.id.item_nhacungcap_admin);
            item_ten = itemView.findViewById(R.id.item_tenncc);
            item_diachi = itemView.findViewById(R.id.item_diachincc);
            item_sdt = itemView.findViewById(R.id.item_sdtncc);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                        NhaCungCapAdmin nhaCungCapAdmin = nhaCungCapAdminList.get(position);
                        onItemClickListener.onItemClick(nhaCungCapAdmin);
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onItemLongClickListener != null) {
                        NhaCungCapAdmin nhaCungCapAdmin = nhaCungCapAdminList.get(position);
                        onItemLongClickListener.onItemLongClick(nhaCungCapAdmin);
                        return true;
                    }
                    return false;
                }
            });

        }
    }
}
