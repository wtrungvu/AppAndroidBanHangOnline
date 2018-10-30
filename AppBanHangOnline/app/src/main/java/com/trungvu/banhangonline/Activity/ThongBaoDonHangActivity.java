package com.trungvu.banhangonline.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.trungvu.banhangonline.R;
import com.trungvu.banhangonline.Ultil.CheckConnection;

public class ThongBaoDonHangActivity extends AppCompatActivity {
    Toolbar toolBarThongBaoDonHang;
    Button btnTiepTucMuaHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_bao_don_hang);

        addControls();
        addEvents();
    }

    private void addControls() {
        toolBarThongBaoDonHang = findViewById(R.id.toolBarThongBaoDonHang);
        btnTiepTucMuaHang = findViewById(R.id.buttonTiepTucMuaHang);
    }

    private void addEvents() {
        btnTiepTucMuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThongBaoDonHangActivity.this, MainActivity.class);
                CheckConnection.showToast_Long(getApplicationContext(), "Mời bạn tiếp tục mua hàng!");
                startActivity(intent);
            }
        });
    }
}
