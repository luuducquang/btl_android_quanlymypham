package com.example.btl_android_quanymypham.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android_quanymypham.R;
import com.example.btl_android_quanymypham.fragment.DetailProductFragmentUser;
import com.example.btl_android_quanymypham.model.TTMyPhamAdmin;

import java.util.List;

public class ProductHomeAdapterUser extends RecyclerView.Adapter<ProductHomeAdapterUser.ProductHomeViewHolder>{
    private List<TTMyPhamAdmin> ttMyPhamAdminList;
    private Context mConText;
    public ProductHomeAdapterUser(Context mConText, List<TTMyPhamAdmin> ttMyPhamAdminList) {
        this.mConText = mConText;
        this.ttMyPhamAdminList = ttMyPhamAdminList;
    }

    public void release(){
        mConText = null;
    }
    @NonNull
    @Override
    public ProductHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_user,parent,false);
        return new ProductHomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHomeViewHolder holder, int position) {
        TTMyPhamAdmin ttMyPhamAdmin = ttMyPhamAdminList.get(position);
        if (ttMyPhamAdmin==null){
            return;
        }
        holder.nameProductHome.setText(ttMyPhamAdmin.getTenmypham());
        holder.priceProductHome.setText(String.valueOf(ttMyPhamAdmin.getGia()));

        byte[]anhsanpham = ttMyPhamAdmin.getAnhsanpham();
        Bitmap bitmap = BitmapFactory.decodeByteArray(anhsanpham,0,anhsanpham.length);
        holder.imgProductHome.setImageBitmap(bitmap);

        holder.idItemHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoDetail(ttMyPhamAdmin);
            }
        });
    }

    private void gotoDetail(TTMyPhamAdmin ttMyPhamAdmin) {
        DetailProductFragmentUser detailFragment = new DetailProductFragmentUser();

        Bundle bundle = new Bundle();
        bundle.putSerializable("ObjectProduct", ttMyPhamAdmin);
        detailFragment.setArguments(bundle);

        AppCompatActivity activity = (AppCompatActivity) mConText;
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, detailFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public int getItemCount() {
        if (ttMyPhamAdminList!=null){
            return ttMyPhamAdminList.size();
        }
        return 0;
    }

    public class ProductHomeViewHolder extends RecyclerView.ViewHolder {
        private CardView idItemHome;
        private ImageView imgProductHome;
        private TextView nameProductHome;
        private TextView priceProductHome;
        public ProductHomeViewHolder(@NonNull View itemView) {
            super(itemView);

            idItemHome = itemView.findViewById(R.id.id_item_home);
            imgProductHome = itemView.findViewById(R.id.img_product_home);
            nameProductHome = itemView.findViewById(R.id.name_product_home);
            priceProductHome = itemView.findViewById(R.id.price_product_home);
        }
    }

}
