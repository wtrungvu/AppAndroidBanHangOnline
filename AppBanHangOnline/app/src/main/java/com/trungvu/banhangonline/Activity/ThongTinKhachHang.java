package com.trungvu.banhangonline.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.trungvu.banhangonline.R;
import com.trungvu.banhangonline.Ultil.CheckConnection;
import com.trungvu.banhangonline.Ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ThongTinKhachHang extends AppCompatActivity {
    Toolbar toolBarThongTinKhachHang;
    EditText edtTenKhachHang, edtSoDienThoai, edtEmail, edtDiaChi;
    Button btnXacNhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_khach_hang);

        addControls();
        addEvents();
    }

    private void addControls() {
        toolBarThongTinKhachHang = findViewById(R.id.toolBarThongTinKhachHang);
        edtTenKhachHang = findViewById(R.id.editTextTenKhachHang);
        edtSoDienThoai = findViewById(R.id.editTextSoDienThoai);
        edtEmail = findViewById(R.id.editTextEmail);
        edtDiaChi = findViewById(R.id.editTextDiaChi);
        btnXacNhan = findViewById(R.id.buttonXacNhan);
    }

    private void addEvents() {
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            actionToolBar();
            eventButton();
        } else {
            CheckConnection.showToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối!");
        }
    }

    // ToolBar Back Button
    private void actionToolBar() {
        setSupportActionBar(toolBarThongTinKhachHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolBarThongTinKhachHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void eventButton() {
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Nhập Thông Tin Khách Hàng
                final String tenKhachHang = edtTenKhachHang.getText().toString().trim();
                final String soDienThoai = edtSoDienThoai.getText().toString().trim();
                final String email = edtEmail.getText().toString().trim();
                final String diaChi = edtDiaChi.getText().toString().trim();

                // Lấy kiểu ngày-tháng-năm giờ-phút-giây để put vào database
                Date date = new Date();
                String strDateFormat = "yyyy/MM/dd HH:mm:ss";
                SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
                final String currentDate = sdf.format(date);

                if (tenKhachHang.length() > 0 && soDienThoai.length() > 0 && email.length() > 0 && diaChi.length() > 0) {
                    RequestQueue requestQueueThongTinKhachHang = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequestThongTinKhachHang = new StringRequest(Request.Method.POST, Server.duongDanThongTinKhachHang,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(final String MaKhachHang) {
                                    Log.d("MaKhachHang", MaKhachHang); // Sau khi thêm khách hàng thành công , ta lấy cái mã khách hàng vừa mới thêm
                                    if (Integer.parseInt(MaKhachHang) > 0) {
                                        RequestQueue requestQueueDonHang = Volley.newRequestQueue(getApplicationContext());
                                        StringRequest stringRequestDonHang = new StringRequest(Request.Method.POST, Server.duongDanDonHang,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(final String MaDonHang) {
                                                        Log.d("MaDonHang", MaDonHang); // Sau khi thêm đơn hàng thành công , ta lấy cái mã đơn hàng vừa mới thêm
                                                        if (Integer.parseInt(MaDonHang) > 0){
                                                            RequestQueue requestQueueChiTietDonHang = Volley.newRequestQueue(getApplicationContext());
                                                            StringRequest stringRequestChiTietDonHang = new StringRequest(Request.Method.POST, Server.duongDanChiTietDonHang,
                                                                    new Response.Listener<String>() {
                                                                        @Override
                                                                        public void onResponse(String response) {
                                                                            if (response.equals("1")) { // Thêm dữ liệu bảng Chi tiết đơn hàng thành công
                                                                                MainActivity.mangGioHang.clear();
                                                                                //CheckConnection.showToast_Short(getApplicationContext(), "Bạn đã thêm dữ liệu giỏ hàng thành công!");
                                                                                //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                                                Intent intent = new Intent(getApplicationContext(), ThongBaoDonHangActivity.class);
                                                                                startActivity(intent);
                                                                            } else {
                                                                                CheckConnection.showToast_Short(getApplicationContext(), "Dữ liệu giỏ hàng của bạn đã bị lỗi!");
                                                                            }
                                                                        }
                                                                    },
                                                                    new Response.ErrorListener() {
                                                                        @Override
                                                                        public void onErrorResponse(VolleyError error) {

                                                                        }
                                                                    }
                                                            ){
                                                                @Override
                                                                protected Map<String, String> getParams() throws AuthFailureError {
                                                                    JSONArray jsonArray = new JSONArray();
                                                                    for (int i = 0; i < MainActivity.mangGioHang.size(); i++) {
                                                                        JSONObject jsonObject = new JSONObject();
                                                                        try {
                                                                            jsonObject.put("MaDonHang", MaDonHang);
                                                                            jsonObject.put("MaSanPham", MainActivity.mangGioHang.get(i).getMaSanPhamGioHang());
                                                                            jsonObject.put("SoLuongSanPham", MainActivity.mangGioHang.get(i).getSoLuongSanPhamGioHang());
                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                        jsonArray.put(jsonObject);
                                                                    }
                                                                    HashMap<String, String> hashMap = new HashMap<String, String>();
                                                                    hashMap.put("json", jsonArray.toString());
                                                                    return hashMap;
                                                                }
                                                            };
                                                            requestQueueChiTietDonHang.add(stringRequestChiTietDonHang);
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
                                                HashMap<String, String> hashMap = new HashMap<String, String>();
                                                hashMap.put("MaKhachHang", MaKhachHang);
                                                hashMap.put("TongTienDonHang", String.valueOf(GioHangActivity.tongTienDonHang));
                                                hashMap.put("NgayGioDatHang", currentDate);
                                                hashMap.put("TrangThaiDonHang", "0");
                                                return hashMap;
                                            }
                                        };
                                        requestQueueDonHang.add(stringRequestDonHang);
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
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put("HoTenKhachHang", tenKhachHang);
                            hashMap.put("SoDienThoaiKhachHang", soDienThoai);
                            hashMap.put("EmailKhachHang", email);
                            hashMap.put("DiaChiKhachHang", diaChi);
                            return hashMap;
                        }
                    };
                    requestQueueThongTinKhachHang.add(stringRequestThongTinKhachHang);
                } else {
                    CheckConnection.showToast_Short(getApplicationContext(), "Hãy kiểm tra lại dữ liệu quý khách nhập!");
                }
            }
        });
    }
}
