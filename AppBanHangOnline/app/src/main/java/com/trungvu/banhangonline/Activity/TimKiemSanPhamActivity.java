package com.trungvu.banhangonline.Activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.trungvu.banhangonline.Adapter.SanPhamAdapter;
import com.trungvu.banhangonline.Adapter.TimKiemSanPhamAdapter;
import com.trungvu.banhangonline.Model.SanPham;
import com.trungvu.banhangonline.R;
import com.trungvu.banhangonline.Ultil.CheckConnection;
import com.trungvu.banhangonline.Ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TimKiemSanPhamActivity extends AppCompatActivity {
    Toolbar toolbarTimKiemSanPham;
    ProgressBar progressBarSearch;
    TextView txtThongBao, txtTimKiem;

    ArrayList<SanPham> mangSanPham;
    TimKiemSanPhamAdapter adapterTimKiemSanPham;
    ListView listViewSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem_san_pham);

        addControls();
        addEvents();
    }

    private void addControls() {
        toolbarTimKiemSanPham = findViewById(R.id.toolBarTimKiemSanPham);
        progressBarSearch = findViewById(R.id.progressBarSearch);
        progressBarSearch.setVisibility(View.GONE); // tắt progressBar quay sau khi có dữ liệu trả về

        txtTimKiem = findViewById(R.id.textViewTimKiem);
        txtTimKiem.setVisibility(View.VISIBLE); // hiện text hướng dẫn

        txtThongBao = findViewById(R.id.textViewThongBao);
        txtThongBao.setVisibility(View.INVISIBLE); // ẩn text không tìm thấy SP

        mangSanPham = new ArrayList<>();
        adapterTimKiemSanPham = new TimKiemSanPhamAdapter(
                getApplicationContext(), mangSanPham
        );
        listViewSearch = findViewById(R.id.listViewSearch);
        listViewSearch.setAdapter(adapterTimKiemSanPham);

    }

    private void addEvents() {
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            actionBar();
            actionListViewSearch();
        } else {
            CheckConnection.showToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối mạng!");
            finish();
        }
    }

    private void actionListViewSearch() {
        listViewSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ChiTietSanPhamActivity.class);
                intent.putExtra("ThongTinSanPham", mangSanPham.get(i));
                startActivity(intent);
            }
        });
    }

    private void actionBar() {
        setSupportActionBar(toolbarTimKiemSanPham);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // set button home toolBar

        toolbarTimKiemSanPham.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // đóng màn hình
            }
        });
    }

    private void getData(String key) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongDanTimKiemSanPham = Server.duongDanTimKiemSanPham + key;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongDanTimKiemSanPham,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
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

                        // Nếu có dữ liệu
                        // response.length() != 2 : respone trả về 1 mảng jsonArray, luôn có 1 giá trị là 1 cặp ngoặc "[]"
                        if (response != null && response.length() != 2) {
                            progressBarSearch.setVisibility(View.GONE); // tắt progressBar quay sau khi có dữ liệu trả về
                            txtTimKiem.setVisibility(View.INVISIBLE); // ẩn text hướng dẫn
                            txtThongBao.setVisibility(View.INVISIBLE); // ẩn text thông báo "Không tìm thấy"
                            listViewSearch.setVisibility(View.VISIBLE); // Hiện listView

                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

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
                                    adapterTimKiemSanPham.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else { // Ko có dữ liệu
                            progressBarSearch.setVisibility(View.GONE); // tắt progressBar quay sau khi có dữ liệu trả về
                            //CheckConnection.showToast_Short(getApplicationContext(), "Không có dữ liệu!");
                            txtTimKiem.setVisibility(View.INVISIBLE); // ẩn text hướng dẫn
                            txtThongBao.setVisibility(View.VISIBLE); // Không tìm thấy đc sản phẩm bạn cần thì show text thông báo "Không tìm thấy"
                            listViewSearch.setVisibility(View.INVISIBLE); // ẩn listView
                            adapterTimKiemSanPham.notifyDataSetChanged();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(stringRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.searchSP).getActionView();

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName())
        );

        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // Nhập từ khóa tìm kiếm xong ấn icon search
            @Override
            public boolean onQueryTextSubmit(String query) {
                mangSanPham.clear();
                adapterTimKiemSanPham.notifyDataSetChanged();
                query = query.replace(" ", "+"); // Thay thế kí tự khoảng trắng , chuyển đổi thành dấu '+'
                getData(query);
                return false;
            }

            // Mỗi lần nhập từng một từ là nó tự search ngay lập tức
            @Override
            public boolean onQueryTextChange(String newText) {
                mangSanPham.clear();
                adapterTimKiemSanPham.notifyDataSetChanged();
                newText = newText.replace(" ", "+"); // Thay thế kí tự khoảng trắng , chuyển đổi thành dấu '+'
                getData(newText);
                return false;
            }
        });

        return true;
    }

}
