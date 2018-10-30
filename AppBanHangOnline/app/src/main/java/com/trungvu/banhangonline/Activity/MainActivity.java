package com.trungvu.banhangonline.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.trungvu.banhangonline.Adapter.LoaiSanPhamAdapter;
import com.trungvu.banhangonline.Adapter.SanPhamAdapter;
import com.trungvu.banhangonline.Adapter.SanPhamBanChayAdapter;
import com.trungvu.banhangonline.Model.GioHang;
import com.trungvu.banhangonline.Model.LoaiSanPham;
import com.trungvu.banhangonline.Model.SanPham;
import com.trungvu.banhangonline.R;
import com.trungvu.banhangonline.Ultil.CheckConnection;
import com.trungvu.banhangonline.Ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolBar;
    ViewFlipper viewFlipper;
    NavigationView navigationView;
    DrawerLayout drawerLayout;

    // ListView cho NavigationView
    ArrayList<LoaiSanPham> mangLoaiSanPham;
    LoaiSanPhamAdapter adapterLoaiSanPham;
    ListView listViewNavigation;

    int MaLoaiSanPham = 0;
    String TenLoaiSanPham = "";
    String HinhLogoLoaiSanPham = "";

    // Cho RecyclerView Sản Phẩm Mới Nhất
    ArrayList<SanPham> mangSanPham;
    SanPhamAdapter adapterSanPham;
    RecyclerView recyclerViewSanPhamMoiNhat;

    // Cho RecyclerView Sản Phẩm Bán Chạy
    ArrayList<SanPham> mangSanPhamBanChay;
    SanPhamBanChayAdapter adapterSanPhamBanChay;
    RecyclerView recyclerViewSanPhamBanChay;

    public static ArrayList<GioHang> mangGioHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
    }

    // Tạo custom menu trên thanh ToolBar (nằm ở bên phía tay phải)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // Bắt sự kiện các item custom menu trên thanh ToolBar (nằm ở bên phía tay phải)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuSearch:
                Intent intentSearch = new Intent(getApplicationContext(), TimKiemSanPhamActivity.class);
                startActivity(intentSearch);
                break;
            case R.id.menuGioHang:
                Intent intentGioHang = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intentGioHang);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addControls() {
        toolBar                     = findViewById(R.id.toolBarManHinhChinh);
        viewFlipper                 = findViewById(R.id.viewFlipperQuangCao);
        navigationView              = findViewById(R.id.navigationView);
        listViewNavigation        = findViewById(R.id.listViewNavigation);
        drawerLayout                = findViewById(R.id.drawerLayout);
        recyclerViewSanPhamMoiNhat    = findViewById(R.id.recyclerViewSanPhamMoiNhat);
        recyclerViewSanPhamBanChay = findViewById(R.id.recyclerViewSanPhamBanChay);

        // ListView cho NavigationView
        mangLoaiSanPham = new ArrayList<>();
        mangLoaiSanPham.add(0, new LoaiSanPham(0, "Trang chủ", "https://i2.wp.com/www.dnartl.co.th/wp-content/uploads/2018/04/store-icon.png?fit=500%2C500"));


        adapterLoaiSanPham   = new LoaiSanPhamAdapter(
                mangLoaiSanPham, getApplicationContext()
        );

        listViewNavigation.setAdapter(adapterLoaiSanPham);

        // RecyclerView cho mảng sản phẩm mới nhất
        mangSanPham = new ArrayList<>();
        adapterSanPham = new SanPhamAdapter(
                getApplicationContext(), mangSanPham
        );
        recyclerViewSanPhamMoiNhat.setAdapter(adapterSanPham);
        recyclerViewSanPhamMoiNhat.setHasFixedSize(true);
        recyclerViewSanPhamMoiNhat.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)); // recycler view sản phẩm mới nhất nằm ngang

        // RecyclerView cho mảng sản phẩm bán chạy
        mangSanPhamBanChay = new ArrayList<>();
        adapterSanPhamBanChay = new SanPhamBanChayAdapter(
                getApplicationContext(), mangSanPhamBanChay
        );
        recyclerViewSanPhamBanChay.setAdapter(adapterSanPhamBanChay);
        recyclerViewSanPhamBanChay.setHasFixedSize(true);
        recyclerViewSanPhamBanChay.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Nếu mảng giỏ hàng đã có dữ liệu rồi
        if (mangGioHang != null){
            // thì ko cần phải tạo lại mảng dữ liệu mới
        } else {
           mangGioHang = new ArrayList<>();
        }

    }

    private void addEvents() {
        // Nếu có kết nối -> tiến hành đọc dữ liệu
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            ActionBar();
            ActionViewFlipper();
            GetDuLieuLoaiSP();
            GetDuLieuSanPhamMoiNhat();
            GetDuLieuSanPhamBanChay();
            CatchOnItemListViewNavigation();
        }
        else {
            CheckConnection.showToast_Long(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối mạng!");
            finish(); // đóng màn hình hiện tại
            System.exit(0);
        }
    }

    // Bắt sự kiện khi ấn nút back
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Xác nhận thoát ứng dụng");
        builder.setMessage("Bạn có muốn thoát ứng dụng không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                System.exit(0);
            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.show();
    }

    // Load Data cho Navigation
    private void GetDuLieuLoaiSP() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongDanLoaiSP,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null){
                            for (int i = 0; i < response.length(); i++){
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);

                                    MaLoaiSanPham = jsonObject.getInt("MaLoaiSanPham");
                                    TenLoaiSanPham = jsonObject.getString("TenLoaiSanPham");
                                    HinhLogoLoaiSanPham = jsonObject.getString("HinhLogoLoaiSanPham");

                                    mangLoaiSanPham.add(new LoaiSanPham(MaLoaiSanPham, TenLoaiSanPham, HinhLogoLoaiSanPham));
                                    adapterLoaiSanPham.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            mangLoaiSanPham.add(9, new LoaiSanPham(0, "Liên hệ", "http://www.unicaremaintenance.com/wp-content/uploads/2014/11/Customer-Service.png"));
                            mangLoaiSanPham.add(10, new LoaiSanPham(0, "Thông tin", "https://freeiconshop.com/wp-content/uploads/edd/info-flat.png"));
                            adapterLoaiSanPham.notifyDataSetChanged();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        CheckConnection.showToast_Short(getApplicationContext(), error.toString());
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    // Bắt sự kiện cho từng item trong listView Navigation
    private void CatchOnItemListViewNavigation() {
        listViewNavigation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0: // Trang chủ
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
//                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        // Đóng thanh menu
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1: // Điện thoại
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, DienThoaiActivity.class);
                            intent.putExtra("MaLoaiSanPham", mangLoaiSanPham.get(i).getMaLoaiSanPham());
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        // Đóng thanh menu
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2: // Laptop
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, LaptopActivity.class);
                            intent.putExtra("MaLoaiSanPham", mangLoaiSanPham.get(i).getMaLoaiSanPham());
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        // Đóng thanh menu
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3: // Tablet
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, TabletActivity.class);
                            intent.putExtra("MaLoaiSanPham", mangLoaiSanPham.get(i).getMaLoaiSanPham());
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        // Đóng thanh menu
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4: // Phụ kiện
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, PhuKienActivity.class);
                            intent.putExtra("MaLoaiSanPham", mangLoaiSanPham.get(i).getMaLoaiSanPham());
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        // Đóng thanh menu
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 5: // Thiết bị đeo thông minh
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, ThietBiDeoThongMinhActivity.class);
                            intent.putExtra("MaLoaiSanPham", mangLoaiSanPham.get(i).getMaLoaiSanPham());
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        // Đóng thanh menu
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 6: // Máy ảnh
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, MayAnhActivity.class);
                            intent.putExtra("MaLoaiSanPham", mangLoaiSanPham.get(i).getMaLoaiSanPham());
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        // Đóng thanh menu
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 7: // Âm thanh
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, AmThanhActivity.class);
                            intent.putExtra("MaLoaiSanPham", mangLoaiSanPham.get(i).getMaLoaiSanPham());
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        // Đóng thanh menu
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 8: // Máy chơi game
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, MayChoiGameActivity.class);
                            intent.putExtra("MaLoaiSanPham", mangLoaiSanPham.get(i).getMaLoaiSanPham());
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        // Đóng thanh menu
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 9: // Liên hệ
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, LienHeActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        // Đóng thanh menu
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 10: // Thông tin
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, ThongTinActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        // Đóng thanh menu
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    // RecyclerView Sản Phẩm Mới Nhất
    private void GetDuLieuSanPhamMoiNhat() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongDanLoaiSPMoiNhat,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null){
                            int maSP = 0;
                            int maLoaiSP = 0;
                            int maThuongHieuSP = 0;
                            String tenSP = "";
                            Integer giaSP = 0;
                            String hinhSP = "";
                            String thongSoKiThuatSP = "";
                            String dacDiemNoiBatSP = "";
                            String phuKienTrongHopSP = "";
                            int thangBaoHanhSP = 0;

                            for (int i = 0; i < response.length(); i++){
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);

                                    maSP = jsonObject.getInt("MaSanPham");
                                    maLoaiSP = jsonObject.getInt("MaLoaiSanPham");
                                    maThuongHieuSP = jsonObject.getInt("MaThuongHieu");
                                    tenSP = jsonObject.getString("TenSanPham");
                                    giaSP = jsonObject.getInt("GiaSanPham");
                                    hinhSP = jsonObject.getString("HinhSanPham");
                                    thongSoKiThuatSP = jsonObject.getString("ThongSoKiThuat");
                                    dacDiemNoiBatSP = jsonObject.getString("DacDiemNoiBat");
                                    phuKienTrongHopSP = jsonObject.getString("PhuKienSanPham");
                                    thangBaoHanhSP = jsonObject.getInt("ThangBaoHanh");

                                    mangSanPham.add(new SanPham(maSP, maLoaiSP, maThuongHieuSP, tenSP, giaSP, hinhSP, thongSoKiThuatSP, dacDiemNoiBatSP, phuKienTrongHopSP, thangBaoHanhSP));
                                    adapterSanPham.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    // RecyclerView Sản Phẩm Bán Chạy
    private void GetDuLieuSanPhamBanChay() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongDanLoaiSPBanChay,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null){
                            int maSP = 0;
                            int maLoaiSP = 0;
                            int maThuongHieuSP = 0;
                            String tenSP = "";
                            Integer giaSP = 0;
                            String hinhSP = "";
                            String thongSoKiThuatSP = "";
                            String dacDiemNoiBatSP = "";
                            String phuKienTrongHopSP = "";
                            int thangBaoHanhSP = 0;

                            for (int i = 0; i < response.length(); i++){
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);

                                    maSP = jsonObject.getInt("MaSanPham");
                                    maLoaiSP = jsonObject.getInt("MaLoaiSanPham");
                                    maThuongHieuSP = jsonObject.getInt("MaThuongHieu");
                                    tenSP = jsonObject.getString("TenSanPham");
                                    giaSP = jsonObject.getInt("GiaSanPham");
                                    hinhSP = jsonObject.getString("HinhSanPham");
                                    thongSoKiThuatSP = jsonObject.getString("ThongSoKiThuat");
                                    dacDiemNoiBatSP = jsonObject.getString("DacDiemNoiBat");
                                    phuKienTrongHopSP = jsonObject.getString("PhuKienSanPham");
                                    thangBaoHanhSP = jsonObject.getInt("ThangBaoHanh");

                                    mangSanPhamBanChay.add(new SanPham(maSP, maLoaiSP, maThuongHieuSP, tenSP, giaSP, hinhSP, thongSoKiThuatSP, dacDiemNoiBatSP, phuKienTrongHopSP, thangBaoHanhSP));
                                    adapterSanPhamBanChay.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    // Load  ViewFlipper
    private void ActionViewFlipper() {
        ArrayList<String> arrayHinhQuangCao = new ArrayList<>();
        arrayHinhQuangCao.add("https://stcv4.hnammobile.com/uploads/banners/199779105324%2009.jpg?v=4832");
        arrayHinhQuangCao.add("https://stcv4.hnammobile.com/uploads/banners/2559189722xr.jpg?v=4832");
        arrayHinhQuangCao.add("https://cdn.tgdd.vn/qcao/30_09_2018_13_26_11_j4-j6-800-300.png");
        arrayHinhQuangCao.add("https://cdn.tgdd.vn/qcao/03_10_2018_15_47_40_Note9-Fix-T10-800-300.png");
        arrayHinhQuangCao.add("https://cdn.tgdd.vn/qcao/28_09_2018_17_00_41_800x300-2.png");
        arrayHinhQuangCao.add("https://cdn.tgdd.vn/qcao/04_10_2018_09_32_38_Huawei-nova-3i-800-300.png");
        arrayHinhQuangCao.add("https://stcv4.hnammobile.com/uploads/banners/4044541046TOP_X6_0207.png?v=4832");

        // ViewFlipper chỉ nhận ImageView
        for (int i = 0; i < arrayHinhQuangCao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(arrayHinhQuangCao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY); // chỉnh kích cỡ ảnh ImageView vừa đủ với ViewFlipper
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000); // Thời gian chuyển slide trong bao lâu ?
        viewFlipper.setAutoStart(true); // Bật tự động chuyển slide

        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        viewFlipper.setInAnimation(animation_slide_in);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setOutAnimation(animation_slide_out);

    }

    // ToolBar
    private void ActionBar() {
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // set button home toolBar
        toolBar.setNavigationIcon(R.drawable.menu_icon);

        // Khi click vào icon toolBar sẽ hiển thị thanh menu
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START); // Menu nhảy ra giữa
            }
        });
    }
}
