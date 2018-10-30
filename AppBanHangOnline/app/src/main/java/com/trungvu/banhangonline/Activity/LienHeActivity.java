package com.trungvu.banhangonline.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.trungvu.banhangonline.R;

public class LienHeActivity extends AppCompatActivity {

    Toolbar toolbarLienHe;
    LinearLayout linearHoTenChuShop, linearSoDienThoaiChuShop, linearEmailChuShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lien_he);

        addControls();
        addEvents();
        actionBar();
    }

    private void addControls() {
        toolbarLienHe = findViewById(R.id.toolBarLienHe);
        linearHoTenChuShop = findViewById(R.id.linearHoTenChuShop);
        linearSoDienThoaiChuShop = findViewById(R.id.linearSoDienThoaiChuShop);
        linearEmailChuShop = findViewById(R.id.linearEmailChuShop);
    }

    private void addEvents() {
        linearSoDienThoaiChuShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ấn vào layout số điện thoại lập tức chuyển sang quay số trực tiếp
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri uri = Uri.parse("tel:0906693723");
                intent.setData(uri);
                startActivity(intent);
            }
        });
        linearEmailChuShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ấn vào layout số email lập tức chuyển sang chế độ gửi tin email
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                Uri uri = Uri.parse("mailto:Support@gmail.com");
                intent.setData(uri);
                startActivity(intent);
            }
        });
    }

    private void actionBar() {
        setSupportActionBar(toolbarLienHe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarLienHe.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
