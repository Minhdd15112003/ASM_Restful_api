package com.example.asm_resfulapi;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm_resfulapi.APIservice.APIServer;
import com.example.asm_resfulapi.APIservice.UserServices;
import com.example.asm_resfulapi.Accounts.LoginActivity;
import com.example.asm_resfulapi.Model.UserModel;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutFragment extends Fragment {
    private ImageView imageView;
    private TextView txtmasv;
    private TextView txtlop;
    private TextView txtmonhoc;
    private TextView txttaikhoan;
    private TextView txtmatkhau;
    private Button btnSua;
    private TextView txtUsername;


    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        imageView = view.findViewById(R.id.imageView);
        txtmasv = view.findViewById(R.id.txtmasv);
        txtlop = view.findViewById(R.id.txtlop);
        txtmonhoc = view.findViewById(R.id.txtmonhoc);
        txttaikhoan = view.findViewById(R.id.txttaikhoan);
        txtmatkhau = view.findViewById(R.id.txtmatkhau);
        btnSua = view.findViewById(R.id.btnSua);
        txtUsername = view.findViewById(R.id.txtUsername);


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("account", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", null);
        String username = sharedPreferences.getString("username", null);
        String password = sharedPreferences.getString("password", null);
        txtUsername.setText(username);
        txttaikhoan.setText(email);
        txtmatkhau.setText(password);

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogSuaThongTin();
            }
        });


        return view;
    }

    private void showDialogSuaThongTin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View viewDialog = inflater.inflate(R.layout.item_sua_thong_tin, null);
        builder.setView(viewDialog);
        AlertDialog dialog = builder.create();
        dialog.setContentView(R.layout.item_sua_thong_tin);

        EditText txtDialogEmail;
        EditText txtDialogUsername;
        EditText txtDialogPassword;
        Button btnthemA;

        txtDialogEmail = viewDialog.findViewById(R.id.txtDialogEmail);
        txtDialogUsername = viewDialog.findViewById(R.id.txtDialogUsername);
        txtDialogPassword = viewDialog.findViewById(R.id.txtDialogPassword);
        btnthemA = viewDialog.findViewById(R.id.btnthemA);
        btnthemA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy ID người dùng
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("account", Context.MODE_PRIVATE);
                String id = sharedPreferences.getString("id", null);

                // Tạo UserModel mới với ID và dữ liệu cập nhật
                String email = txtDialogEmail.getText().toString();
                String username = txtDialogUsername.getText().toString();
                String password = txtDialogPassword.getText().toString();
                UserModel userModel = new UserModel(id, email, username, password);
                // Gửi yêu cầu cập nhật
                UserServices userServices = APIServer.getServer().create(UserServices.class);
                Call<UserModel> updateCall = userServices.updateUser(id,userModel);
                updateCall.enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if (response.body() != null) {
                            Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("account", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("emailnew", response.body().getEmail());
                            editor.putString("usernamenew", response.body().getUsername());
                            editor.putString("passwordnew", response.body().getPassword());
                            editor.apply();
                            editor.commit();

                            UserModel userModel1 = response.body();
                            txtUsername.setText(userModel1.getUsername());
                            txttaikhoan.setText(userModel1.getEmail());
                            txtmatkhau.setText(userModel1.getPassword());

                        } else {
                            Log.d("capnhat", "onResponse: " + response.body());
                            Toast.makeText(getContext(), "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }


                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
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

                dialog.dismiss();
            }
        });
        dialog.show();

    }

}