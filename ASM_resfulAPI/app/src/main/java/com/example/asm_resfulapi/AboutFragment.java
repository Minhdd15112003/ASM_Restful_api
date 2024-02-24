package com.example.asm_resfulapi;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asm_resfulapi.APIservice.APIServer;
import com.example.asm_resfulapi.APIservice.UserServices;

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

        String masv = txtmasv.getText().toString();
        String lop = txtlop.getText().toString();
        String monhoc = txtmonhoc.getText().toString();
        String taikhoan = txttaikhoan.getText().toString();
        String matkhau = txtmatkhau.getText().toString();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("account", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", null);
        String username = sharedPreferences.getString("username", null);
        String password = sharedPreferences.getString("password", null);
        txtUsername.setText(username);
        txttaikhoan.setText(email);
        txtmatkhau.setText(password);

        UserServices userServices = APIServer.getServer().create(UserServices.class);
        

        return view;
    }
}