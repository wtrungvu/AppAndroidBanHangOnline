package com.trungvu.banhangonline.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.trungvu.banhangonline.Adapter.MayChoiGameAdapter;
import com.trungvu.banhangonline.Model.SanPham;
import com.trungvu.banhangonline.R;
import com.trungvu.banhangonline.Ultil.CheckConnection;
import com.trungvu.banhangonline.Ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MayChoiGameActivity extends AppCompatActivity {
    Toolbar toolbarMayChoiGame;

    ArrayList<SanPham> mangMayChoiGame;
    MayChoiGameAdapter adapterMayChoiGame;
    ListView listViewMayChoiGame;

    int maMayChoiGame = 0;
    int page = 1;

    // Load More
    View footerView;
    boolean isLoading = false;
    myHandler myHandler;
    boolean limitData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_may_choi_game);

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
        toolbarMayChoiGame = findViewById(R.id.toolBarMayChoiGame);

        mangMayChoiGame = new ArrayList<>();

        adapterMayChoiGame = new MayChoiGameAdapter(
                getApplicationContext(), mangMayChoiGame
        );

        listViewMayChoiGame = findViewById(R.id.listViewMayChoiGame);
        listViewMayChoiGame.setAdapter(adapterMayChoiGame);

        // Load More
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progress_bar, null);
        myHandler = new myHandler();
    }

    private void addEvents() {
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            getMaLoaiSanPham();
            ActionToolBar();
            getData(page);
            loadMoreData();
        }
        else {
            CheckConnection.showToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối mạng!");
            finish();
        }
    }

    private void loadMoreData() {
        listViewMayChoiGame.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int FirstItem, int VisibleItem, int TotalItem) {
                // Vuốt ListView
                // FirstItem: dòng đâu tiên mình thấy đc, index của item đầu tiên được hiển thị, tức lá bằng 0 chứ ko phải 1
                // VisibleItem: tổng số dòng mình thấy được của listview
                // TotalItem : tổng số dòng của listview

                // Vị trí đầu tiên + Số lượng nhìn thấy = Tổng số lượng item
                // TotalItem != 0: tránh khi run lên nó nhảy vào function này
                // isLoading = false: chưa load dữ liệu
                // limitData = false: chưa hết đữ liệu
                if (FirstItem + VisibleItem == TotalItem && TotalItem != 0 && isLoading == false && limitData == false)
                {
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start(); // Thread hoạt động
                }
            }
        });

        listViewMayChoiGame.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ChiTietSanPhamActivity.class);
                intent.putExtra("ThongTinSanPham", mangMayChoiGame.get(i));
                startActivity(intent);
            }
        });
    }

    // Lấy dữ liệu sản phẩm
    private void getData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongDanSP = Server.duongDanSP + String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongDanSP,
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
                        if (response != null && response.length() != 2)
                        {
                            // tắt progressBar quay sau khi có dữ liệu trả về
                            listViewMayChoiGame.removeFooterView(footerView);

                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++){
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

                                    mangMayChoiGame.add(new SanPham(maSP, maLoaiSP, maThuongHieuSP, tenSP, giaSP, hinhSP, thongSoKiThuatSP, dacDiemNoiBatSP, phuKienTrongHopSP, thangBaoHanhSP));
                                    adapterMayChoiGame.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else { // Ko có dữ liệu
                            limitData = true;
                            listViewMayChoiGame.removeFooterView(footerView);
                            CheckConnection.showToast_Short(getApplicationContext(), "Đã hết dữ liệu!");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("MaSanPham", String.valueOf(maMayChoiGame));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    // Tạo nút back button trên thanh ToolBar
    private void ActionToolBar() {
        setSupportActionBar(toolbarMayChoiGame);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // set button back in toolbar

        toolbarMayChoiGame.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // đóng màn hình hiện tại
            }
        });
    }

    // Lấy mã loại sản phẩm
    private void getMaLoaiSanPham() {
        maMayChoiGame = getIntent().getIntExtra("MaLoaiSanPham", -1);
        Log.d("GiaTriLoaiSanPham = ", maMayChoiGame + "");
    }

    public class myHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    listViewMayChoiGame.addFooterView(footerView);
                    break;
                case 1:
                    getData(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public class ThreadData extends Thread{
        @Override
        public void run() {
            myHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = myHandler.obtainMessage(1);
            myHandler.sendMessage(message);
            super.run();
        }
    }

}
