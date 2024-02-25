package com.example.asm_resfulapi.Accounts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm_resfulapi.APIservice.APIServer;
import com.example.asm_resfulapi.APIservice.UserServices;
import com.example.asm_resfulapi.HomeFragment;
import com.example.asm_resfulapi.Model.UserModel;
import com.example.asm_resfulapi.R;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SiginActivity extends AppCompatActivity {
    EditText edtEmail;
    EditText edUsername;
    EditText edtMatKhau;
    CheckBox cbNhoMatKhau;
    Button btnDangNhap;
    TextView tvQuenMatKhau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sigin);
        edtEmail = findViewById(R.id.edtEmail);
        edUsername = findViewById(R.id.edUsername);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        cbNhoMatKhau = findViewById(R.id.cbNhoMatKhau);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        tvQuenMatKhau = findViewById(R.id.tvQuenMatKhau);

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = edtEmail.getText().toString().trim();
                String Username = edUsername.getText().toString().trim();
                String Password = edtMatKhau.getText().toString().trim();
                UserServices userServices = APIServer.getServer().create(UserServices.class);
                UserModel userModel = new UserModel(Email, Username, Password);
                Call<UserModel> userModelCall = userServices.postRegisternUser(userModel);
                userModelCall.enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if (response.body() != null) {
                            Toast.makeText(SiginActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(SiginActivity.this, LoginActivity.class);
                            startActivity(i);
                        } else {
                            Log.d("resLogin", "Response: " + response.body());
                            Toast.makeText(SiginActivity.this, "Đăng kí thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        Log.e("onFailure", "Request failed: ", t); // Always log the error
                        // Check error type
                        if (t instanceof IOException) {
                            // Likely a network connectivity error
                            Toast.makeText(SiginActivity.this, "Kiểm tra kết nối mạng", Toast.LENGTH_SHORT).show();
                        } else {
                            // Other potential errors (server-side, parsing, etc.)
                            Toast.makeText(SiginActivity.this, "Có lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}