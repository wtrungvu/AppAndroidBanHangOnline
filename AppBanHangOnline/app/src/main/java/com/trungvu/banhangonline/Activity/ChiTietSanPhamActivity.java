package com.trungvu.banhangonline.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.trungvu.banhangonline.Adapter.GioHangAdapter;
import com.trungvu.banhangonline.Model.GioHang;
import com.trungvu.banhangonline.Model.SanPham;
import com.trungvu.banhangonline.R;

import java.text.DecimalFormat;

import static com.trungvu.banhangonline.Activity.GioHangActivity.gioHangAdapter;

public class ChiTietSanPhamActivity extends AppCompatActivity {
    Toolbar toolBarChiTietSanPham;
    ImageView imgChiTietSanPham;
    TextView txtTenChiTietSanPham, txtGiaChiTietSanPham, txtThongSoKiThuatChiTietSanPham, txtDacDiemNoiBatChiTietSanPham, txtPhuKienTrongHopChiTietSanPham, txtThangBaoHanhSanPhamChiTietSanPham;
    Button btnThemGioHang;
    Spinner spinnerSoLuong;

    int maChiTietSP = 0;
    int maLoaiChiTietSP = 0;
    int maThuongHieuChiTietSP = 0;
    String tenChiTietSP = "";
    Integer giaChiTietSP = 0;
    String hinhChiTietSP = "";
    String thongSoKiThuatChiTietSP = "";
    String dacDiemNoiBatChiTietSP = "";
    String phuKienTrongHopChiTietSP = "";
    int thangBaoHanhChiTietSP = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);

        addControls();
        addEvents();
    }

    // Tạo menu shopping cart
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // Bắt sự kiện các item trong menu shopping cart
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuSearch:
                Intent intentSearch = new Intent(getApplicationContext(), TimKiemSanPhamActivity.class);
                startActivity(intentSearch);
                break;
            case R.id.menuGioHang:
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addControls() {
        toolBarChiTietSanPham = findViewById(R.id.toolBarChiTietSanPham);
        imgChiTietSanPham = findViewById(R.id.imageViewChiTietSanPham);
        txtTenChiTietSanPham = findViewById(R.id.textViewTenChiTietSanPham);
        txtGiaChiTietSanPham = findViewById(R.id.textViewGiaChiTietSanPham);
        txtThongSoKiThuatChiTietSanPham = findViewById(R.id.textViewThongSoKiThuatChiTietSanPham);
        txtDacDiemNoiBatChiTietSanPham = findViewById(R.id.textViewDacDiemNoiBatChiTietSanPham);
        txtPhuKienTrongHopChiTietSanPham = findViewById(R.id.textViewPhuKienTrongHopChiTietSanPham);
        txtThangBaoHanhSanPhamChiTietSanPham = findViewById(R.id.textViewThangBaoHanhSanPhamChiTietSanPham);
        spinnerSoLuong = findViewById(R.id.spinnerSoLuong);
        btnThemGioHang = findViewById(R.id.buttonThemGioHang);
    }

    private void addEvents() {
        actionToolBar();
        getInformation();

        // Giới hạn giá trị cho Spinner từ 1 -> 10
        catchEventSpinner();

        eventButton();
    }

    private void eventButton() {
        btnThemGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Nếu mảng giỏ hàng có dữ liệu rồi thì chỉ việc kiểm tra coi có sản phẩm nào có cần update lại số lượng hay ko?
                if (MainActivity.mangGioHang.size() > 0){
                    // Lấy số lượng từ Spinner
                    int sl = Integer.parseInt(spinnerSoLuong.getSelectedItem().toString());
                    boolean exists = false;
                    for (int i = 0; i < MainActivity.mangGioHang.size(); i++){
                        if (MainActivity.mangGioHang.get(i).getMaSanPhamGioHang() == maChiTietSP) { // Nếu trùng mã sản phẩm
                            MainActivity.mangGioHang.get(i).setSoLuongSanPhamGioHang(MainActivity.mangGioHang.get(i).getSoLuongSanPhamGioHang() + sl); // Cập nhật lại số lượng sản phẩm
                            // Chỉ cho đặt sản phẩm max tối đa = 10
                            if (MainActivity.mangGioHang.get(i).getSoLuongSanPhamGioHang() >= 10) {
                                MainActivity.mangGioHang.get(i).setSoLuongSanPhamGioHang(10);
                            }
                            MainActivity.mangGioHang.get(i).setGiaSanPhamGioHang(giaChiTietSP * MainActivity.mangGioHang.get(i).getSoLuongSanPhamGioHang()); // Tính giá sản phẩm = Giá sản phẩm x Số lượng sản phẩm
                            exists = true;
                        }
                    }
                    // Ko tìm thấy sản phẩm nào trùng id, thì sẽ ko cập nhật lại dữ liệu , tạo dữ liệu mới
                    if (exists == false)
                    {
                        int soLuong = Integer.parseInt(spinnerSoLuong.getSelectedItem().toString());
                        long giaMoi = soLuong * giaChiTietSP;
                        MainActivity.mangGioHang.add(new GioHang(maChiTietSP, tenChiTietSP, giaMoi, hinhChiTietSP, soLuong));
                        gioHangAdapter.notifyDataSetChanged();
                    }

                }
                else {
                    // Lấy số lượng từ Spinner
                    int soLuong = Integer.parseInt(spinnerSoLuong.getSelectedItem().toString());
                    long giaMoi = soLuong * giaChiTietSP;
                    MainActivity.mangGioHang.add(new GioHang(maChiTietSP, tenChiTietSP, giaMoi, hinhChiTietSP, soLuong));
                    //gioHangAdapter.notifyDataSetChanged();
                }
                // Chuyển qua màn hình giỏ hàng
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void catchEventSpinner() {
        Integer[] soLuong = new Integer[]{1,2,3,4,5,6,7,8,9,10};

        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(
                this,
                R.layout.spinner_item,
                soLuong
        );
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerSoLuong.setAdapter(arrayAdapter);
    }

    private void getInformation() {
        SanPham sanPham = (SanPham) getIntent().getSerializableExtra("ThongTinSanPham");

        maChiTietSP = sanPham.getMaSanPham();
        maLoaiChiTietSP = sanPham.getMaLoaiSanPham();
        maThuongHieuChiTietSP = sanPham.getMaThuongHieu();
        tenChiTietSP = sanPham.getTenSanPham();
        giaChiTietSP = sanPham.getGiaSanPham();
        hinhChiTietSP = sanPham.getHinhSanPham();
        thongSoKiThuatChiTietSP = sanPham.getThongSoKiThuatSanPham();
        dacDiemNoiBatChiTietSP = sanPham.getDacDiemNoiBatSanPham();
        phuKienTrongHopChiTietSP = sanPham.getPhuKienTrongHopSanPham();
        thangBaoHanhChiTietSP = sanPham.getThangBaoHanhSanPham();

        txtTenChiTietSanPham.setText(tenChiTietSP);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtGiaChiTietSanPham.setText("Giá : " + decimalFormat.format(giaChiTietSP) + " Đ");
        txtThongSoKiThuatChiTietSanPham.setText(thongSoKiThuatChiTietSP);
        txtDacDiemNoiBatChiTietSanPham.setText(dacDiemNoiBatChiTietSP);
        txtPhuKienTrongHopChiTietSanPham.setText(phuKienTrongHopChiTietSP);
        txtThangBaoHanhSanPhamChiTietSanPham.setText(Integer.toString(thangBaoHanhChiTietSP) + " tháng");

        Picasso.with(getApplicationContext()).load(hinhChiTietSP)
                .placeholder(R.drawable.no_image_available)
                .error(R.drawable.error)
                .into(imgChiTietSanPham);

    }

    private void actionToolBar() {
        setSupportActionBar(toolBarChiTietSanPham);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBarChiTietSanPham.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
