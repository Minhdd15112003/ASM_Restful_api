package com.example.asm_resfulapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawerLayout;
    Toolbar toolbar;
    FrameLayout fragmentContainer;
    NavigationView navView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Lấy các view từ layout bằng ID
        drawerLayout = findViewById(R.id.drawer_layout);
        fragmentContainer = findViewById(R.id.fragment_container);
        toolbar = findViewById(R.id.toolbar);
        navView = findViewById(R.id.Nav);
        navView.setItemIconTintList(null);
        // Thiết lập toolbar làm Action Bar
        setSupportActionBar(toolbar);

        // Thiết lập sự kiện khi người dùng chọn các mục trong Navigation Drawer
        navView.setNavigationItemSelectedListener(this);

        // Tạo một toggle (nút) để mở và đóng Navigation Drawer
        ActionBarDrawerToggle togglev = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        //tạo trạng thái của icon
        drawerLayout.addDrawerListener(togglev);
        togglev.syncState();

        // Nếu savedInstanceState là null, tức là đây là lần đầu tiên hoạt động được tạo, nên
        // thay thế fragment hiện tại bằng fragment HomeFragment và đánh dấu mục Home là mục đang chọn
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment()).commit();
            navView.setCheckedItem(R.id.nav_home);
        }
        View headerView = navView.getHeaderView(0); // Lấy tham chiếu đến header layout
        TextView txtTen = headerView.findViewById(R.id.txtten);
        SharedPreferences sharedPreferences = getSharedPreferences("thongtin", MODE_PRIVATE);
        String hoten = sharedPreferences.getString("hoten", ""); // Lấy thông tin người dùng từ SharedPreferences
        txtTen.setText(hoten);

    }





    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        // Xử lý sự kiện khi người dùng chọn các mục trong Navigation Drawer
        if (itemId == R.id.nav_home) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment()).commit();
        } else if (itemId == R.id.nav_settings) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new SettingFragment()).commit();
        } else if (itemId == R.id.nav_Management) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ManagementFragment()).commit();
        } else if (itemId == R.id.nav_about) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AboutFragment()).commit();
        } else if (itemId == R.id.nav_logout) {
            // Khi người dùng chọn mục Logout, đóng ứng dụng bằng finishAffinity()
            finishAffinity();
            //   startActivity(new Intent(MainActivity.this, LoginActivity.class));
            Toast.makeText(this, "đã thoát", Toast.LENGTH_SHORT).show();
        }
        // Sau khi xử lý sự kiện, đóng Navigation Drawer
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        // Nếu Navigation Drawer đang mở, đóng nó khi người dùng bấm nút Back
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            // Nếu không, thực hiện hành động mặc định của nút Back (thoát hoặc quay lại)
            super.onBackPressed();
        }
    }

}