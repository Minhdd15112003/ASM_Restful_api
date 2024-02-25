package com.example.asm_resfulapi.Adapter;

import android.app.Dialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm_resfulapi.APIservice.APIServer;
import com.example.asm_resfulapi.APIservice.ProductServices;
import com.example.asm_resfulapi.Model.ProductModel;
import com.example.asm_resfulapi.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHoler> {
    public List<ProductModel> productModelList;

    public ProductAdapter(List<ProductModel> productModelList) {
        this.productModelList = productModelList;
    }

    public void filterList(ArrayList<ProductModel> filteredList) {
        this.productModelList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductAdapter.ProductHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sanphammanhinhchinh, parent, false);
        return new ProductHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductHoler holder, int position) {
        ProductModel productModel = productModelList.get(holder.getAdapterPosition());
        holder.txtname.setText(productModel.getName());
        holder.txtprice.setText(productModel.getPrice() + "");
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView txtTensp;
                TextView txtDes;
                TextView txtGiaban;
                TextView txtCate;


               Dialog dialog = new Dialog(v.getContext());
               dialog.setContentView(R.layout.dialog_chi_tiet);



                txtTensp = dialog.findViewById(R.id.txtTensp);
                txtDes = dialog.findViewById(R.id.txtDes);
                txtGiaban = dialog.findViewById(R.id.txtGiaban);
                txtCate = dialog.findViewById(R.id.txtCate);


                // Gán dữ liệu từ product vào các TextView:
                txtTensp.setText(productModel.getName());
                txtDes.setText(productModel.getDescription());
                txtGiaban.setText(String.valueOf(productModel.getPrice()));
                txtCate.setText(productModel.getCateID());


                dialog.show();
            }
        });
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return productModelList.size();
    }

    public class ProductHoler extends RecyclerView.ViewHolder {

        public final LinearLayout linearLayout;
        public final TextView txtname;
        public final ImageView img;
        public final TextView txtprice;
        public final ImageView imgCartManHinhChinh;

        public ProductHoler(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            txtname = itemView.findViewById(R.id.txtname);
            img = itemView.findViewById(R.id.img);
            txtprice = itemView.findViewById(R.id.txtprice);
            imgCartManHinhChinh = itemView.findViewById(R.id.imgCartManHinhChinh);

        }
    }
}


