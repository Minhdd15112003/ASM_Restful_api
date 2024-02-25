package com.example.asm_resfulapi;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.asm_resfulapi.APIservice.APIServer;
import com.example.asm_resfulapi.APIservice.ProductServices;
import com.example.asm_resfulapi.Accounts.LoginActivity;
import com.example.asm_resfulapi.Adapter.ProductAdapter;
import com.example.asm_resfulapi.Model.ProductModel;
import com.example.asm_resfulapi.loadmore.EndlessRecyclerViewScrollListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    SwipeRefreshLayout sLayout;
    EditText txtSeach;
    ImageView btntimkiem;
    Spinner spnCate;
    RecyclerView rcvList;

    ProductAdapter productAdapter;
    List<ProductModel> productModelList;

    // Biến đếm số trang
    private int currentPage = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // ... Khởi tạo các thành phần UI (txtSearch, btntimkiem, spnCate, rcvList, sLayout)
        txtSeach = view.findViewById(R.id.txtSeach);
        btntimkiem = view.findViewById(R.id.btntimkiem);
        spnCate = view.findViewById(R.id.spnCate);
        sLayout = view.findViewById(R.id.sLayout);
        rcvList = view.findViewById(R.id.rcvList);


        productModelList = new ArrayList<>();
        productAdapter = new ProductAdapter(productModelList);
        LinearLayoutManager ln = new LinearLayoutManager(getContext());
        rcvList.setLayoutManager(ln);
        rcvList.setAdapter(productAdapter);

        loadInitialData(); // Gọi hàm tải dữ liệu ban đầu

        rcvList.addOnScrollListener(new EndlessRecyclerViewScrollListener(ln) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                currentPage++;
               // loadMoreData(); // Khi cuộn, gọi loadMoreData()
            }
        });
        sLayout.setOnRefreshListener(() -> {
            currentPage = 1; // Reset số trang khi làm mới
            refreshData();  // Gọi hàm refreshData()
        });
 btntimkiem.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
         String searchText = txtSeach.getText().toString().trim();
         ArrayList<ProductModel> searchList = new ArrayList<>();

         if (searchText.trim().isEmpty()){
             searchList.addAll(productModelList);
         }else {
             String searchLowerCase = searchText.trim().toLowerCase();
             for (ProductModel product : productModelList) {
                 if (product.getName().toLowerCase().contains(searchLowerCase)) {
                     searchList.add(product);
                 }
             }
         }
         productAdapter.filterList(searchList);
     }
 });
        return view;
    }
    private void loadInitialData() {
        fetchData(currentPage);
    }

    private void refreshData() {
        productModelList.clear(); // Xóa dữ liệu cũ
        fetchData(currentPage);
    }

    private void fetchData(int page) {
        ProductServices service = APIServer.getServer().create(ProductServices.class);
        Call<List<ProductModel>> listCall = service.getProduct(/* Truyền các tham số cần thiết */);
        listCall.enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                if (response.body() != null) {
                    sLayout.setRefreshing(false); // Dừng refresh
                    productModelList.addAll(response.body());
                    productAdapter.notifyDataSetChanged();
                } else {
                    // Xử lý lỗi nếu HTTP request không thành công

                    Toast.makeText(getContext(), "Có lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                sLayout.setRefreshing(false); // Dừng refresh
                Log.e("onFailure", "Request failed: ", t); // Always log the error

                // Check error type
                if (t instanceof IOException) {
                    // Likely a network connectivity error
                    Toast.makeText(getContext(), "Kiểm tra kết nối mạng", Toast.LENGTH_SHORT).show();
                } else {
                    // Other potential errors (server-side, parsing, etc.)
                    Toast.makeText(getContext(), "Có lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
