package com.example.asm_resfulapi.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm_resfulapi.Model.ProductModel;
import com.example.asm_resfulapi.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHoler> {
    public List<ProductModel> productModelList;

    public ProductAdapter(List<ProductModel> productModelList) {
        this.productModelList = productModelList;
    }

    @NonNull
    @Override
    public ProductAdapter.ProductHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sanphammanhinhchinh, parent, false);
        return new ProductHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductHoler holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ProductHoler extends RecyclerView.ViewHolder {
        public ProductHoler(@NonNull View itemView) {
            super(itemView);
        }
    }
}
