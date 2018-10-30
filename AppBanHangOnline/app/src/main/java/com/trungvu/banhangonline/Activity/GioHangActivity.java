package com.trungvu.banhangonline.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.trungvu.banhangonline.Adapter.GioHangAdapter;
import com.trungvu.banhangonline.R;
import com.trungvu.banhangonline.Ultil.CheckConnection;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangActivity extends AppCompatActivity {
    TextView txtThongBao;
    static TextView txtTongTien;
    Button btnThanhToan, btnTiepTucMua;
    Toolbar toolbarGioHang;
    ListView lvGioHang;
    public static GioHangAdapter gioHangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        addControls();
        addEvents();
    }

    private void addControls() {
        toolbarGioHang = findViewById(R.id.toolBarGioHang);
        txtThongBao = findViewById(R.id.textViewThongBao);
        txtTongTien = findViewById(R.id.textViewTongTien);
        btnTiepTucMua = findViewById(R.id.buttonTiepTucMuaHang);
        btnThanhToan = findViewById(R.id.buttonThanhToanGioHang);
        gioHangAdapter = new GioHangAdapter(
                GioHangActivity.this, MainActivity.mangGioHang);
        lvGioHang = findViewById(R.id.listViewGioHang);
        lvGioHang.setAdapter(gioHangAdapter);
    }

    private void addEvents() {
        actionToolBar();
        checkData();
        eventUlti();
        catchOnItemListView();
        eventButton();
    }

    private void eventButton() {
        btnTiepTucMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GioHangActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Có dữ liệu sản phẩm rồi
                if (MainActivity.mangGioHang.size() > 0){
//                    Intent intent = new Intent(getApplicationContext(), ThongTinKhachHang.class);
//                    startActivity(intent);
                    Intent intent = new Intent(getApplicationContext(), ThongTinKhachHang.class);
                    startActivity(intent);
                } else {
                    CheckConnection.showToast_Short(getApplicationContext(), "Giỏ hàng của bạn chưa có sản phẩm!");
                }
            }
        });
    }

    // Ấn giữ lâu sẽ hiện thông báo xóa
    private void catchOnItemListView() {
        lvGioHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GioHangActivity.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Xóa hết sạch ko có sản phẩm nào, sẽ hiện text Thông Báo Sản Phẩm Trống
                        if (MainActivity.mangGioHang.size() <= 0){
                            txtThongBao.setVisibility(View.VISIBLE);
                        } else {
                            MainActivity.mangGioHang.remove(position);
                            gioHangAdapter.notifyDataSetChanged();
                            eventUlti();
                            if (MainActivity.mangGioHang.size() <= 0){
                                txtThongBao.setVisibility(View.VISIBLE);
                            } else {
                                txtThongBao.setVisibility(View.INVISIBLE);
                                gioHangAdapter.notifyDataSetChanged(); // Sau khi xóa phải cập nhật lại adapter
                                eventUlti(); // Đồng thời cập nhật tổng tiền mới
                            }
                        }
                    }
                });

                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        gioHangAdapter.notifyDataSetChanged();
                        eventUlti();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static long tongTienDonHang = 0; // Để put vào table DonHang
    public static long tongTienSoLuongSanPham = 0;

    public static void eventUlti() {
        long tongTien = 0;
        for (int i = 0; i < MainActivity.mangGioHang.size(); i++){
            tongTien += MainActivity.mangGioHang.get(i).getGiaSanPhamGioHang();
            tongTienSoLuongSanPham = MainActivity.mangGioHang.get(i).getGiaSanPhamGioHang();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTongTien.setText(decimalFormat.format(tongTien) + " Đ");
        tongTienDonHang = tongTien;
    }

    private void checkData() {
        // Không có sản phẩm nào trong giỏ hàng
        if (MainActivity.mangGioHang.size() <= 0){
            gioHangAdapter.notifyDataSetChanged();
            txtThongBao.setVisibility(View.VISIBLE);
            lvGioHang.setVisibility(View.INVISIBLE);
        } else {
            gioHangAdapter.notifyDataSetChanged();
            txtThongBao.setVisibility(View.INVISIBLE);
            lvGioHang.setVisibility(View.VISIBLE);
        }
    }

    private void actionToolBar() {
        setSupportActionBar(toolbarGioHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarGioHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
