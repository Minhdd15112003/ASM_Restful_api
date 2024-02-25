package com.example.asm_resfulapi.Accounts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.asm_resfulapi.MainActivity;
import com.example.asm_resfulapi.Model.UserModel;
import com.example.asm_resfulapi.R;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText edtEmail;
    EditText edtMatKhau;
    CheckBox cbNhoMatKhau;
    Button btnDangNhap;
    TextView tvQuenMatKhau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtEmail = findViewById(R.id.edtEmail);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        tvQuenMatKhau = findViewById(R.id.tvQuenMatKhau);

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUser();
            }

            private void SignUser() {
                String Email = edtEmail.getText().toString();
                String Password = edtMatKhau.getText().toString();
                //Log.d("cacccc", "onClick: " + Email);
                UserServices userServices = APIServer.getServer().create(UserServices.class);
                UserModel userModel = new UserModel(Email, Password);
                Call<UserModel> userModelCall = userServices.postLoginUser(userModel);
                userModelCall.enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if (response.body() != null) {
                            Toast.makeText(LoginActivity.this, "Đăng nhâp thành công", Toast.LENGTH_SHORT).show();
                            // Sau khi đăng nhập thành công:
                            SharedPreferences sharedPreferences = getSharedPreferences("account", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("id", response.body().getId());
                            editor.putString("email", response.body().getEmail());
                            editor.putString("username", response.body().getUsername());
                            editor.putString("password", response.body().getPassword());
                            editor.commit();
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);

                        } else {
                            Log.d("resLogin", "Response: " + response.body());
                            Toast.makeText(LoginActivity.this, "Đăng nhâp thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        Log.e("onFailure", "Request failed: ", t); // Always log the error

                        // Check error type
                        if (t instanceof IOException) {
                            // Likely a network connectivity error
                            Toast.makeText(LoginActivity.this, "Kiểm tra kết nối mạng", Toast.LENGTH_SHORT).show();
                        } else {
                            // Other potential errors (server-side, parsing, etc.)
                            Toast.makeText(LoginActivity.this, "Có lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        tvQuenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SiginActivity.class);
                startActivity(i);
            }
        });
    }
}