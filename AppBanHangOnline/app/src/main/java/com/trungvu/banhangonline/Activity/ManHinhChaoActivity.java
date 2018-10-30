package com.trungvu.banhangonline.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.trungvu.banhangonline.R;
import com.trungvu.banhangonline.Ultil.CheckConnection;

public class ManHinhChaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chao);

        // Nếu có kết nối mạng (Wifi/Mobile)
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000); // Ngủ 2s xong chuyển sang Trang chủ
                    } catch (Exception e) {

                    } finally {
                        Intent intent = new Intent(ManHinhChaoActivity.this, MainActivity.class);
                        finish(); // close màn hình chào
                        startActivity(intent); // rồi nhảy qua Main Activity
                    }
                }
            });

            thread.start();

        } else {
            CheckConnection.showToast_Long(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối mạng!");
            finish();
        }
    }
}
