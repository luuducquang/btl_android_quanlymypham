package com.example.btl_android_quanymypham.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_android_quanymypham.DetailProductUser;
import com.example.btl_android_quanymypham.model.ProductHomeUser;
import com.example.btl_android_quanymypham.R;

import java.util.List;

public class ProductHomeAdapterUser extends RecyclerView.Adapter<ProductHomeAdapterUser.ProductHomeViewHolder>{
    private List<ProductHomeUser> productHomeList;
    private Context mConText;
    public ProductHomeAdapterUser(Context mConText, List<ProductHomeUser> productHomeList) {
        this.mConText = mConText;
        this.productHomeList = productHomeList;
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
        ProductHomeUser productHome = productHomeList.get(position);
        if (productHome==null){
            return;
        }
        holder.imgProductHome.setImageResource(productHome.getImage());
        holder.nameProductHome.setText(productHome.getName());
        holder.priceProductHome.setText(productHome.getPrice());
        holder.idItemHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoDetail(productHome);
            }
        });
    }

    private void gotoDetail(ProductHomeUser productHome) {
        Intent intent = new Intent(mConText, DetailProductUser.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("ObjectProduct",productHome);
        intent.putExtras(bundle);
        mConText.startActivity(intent);
    }
    @Override
    public int getItemCount() {
        if (productHomeList!=null){
            return productHomeList.size();
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
