package com.trungvu.banhangonline.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.trungvu.banhangonline.Activity.ChiTietSanPhamActivity;
import com.trungvu.banhangonline.Model.SanPham;
import com.trungvu.banhangonline.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class SanPhamBanChayAdapter extends RecyclerView.Adapter<SanPhamBanChayAdapter.ItemHolder> {
    Context context;
    ArrayList<SanPham> arraySanPhamBanChay;

    public SanPhamBanChayAdapter(Context context, ArrayList<SanPham> arraySanPhamBanChay) {
        this.context = context;
        this.arraySanPhamBanChay = arraySanPhamBanChay;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanphambanchay, null);
        ItemHolder itemHolder = new ItemHolder(v);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, final int position) {
        SanPham sanPham = arraySanPhamBanChay.get(position);

        holder.txtTenSanPhamBanChay.setText(sanPham.getTenSanPham());
        // DecimalFormat: Định dạng chuỗi theo chuẩn mình quy định
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiaSanPhamBanChay.setText("Giá : " + decimalFormat.format(sanPham.getGiaSanPham()) + " Đ");
        Picasso.with(context).load(sanPham.getHinhSanPham())
                .placeholder(R.drawable.no_image_available)
                .error(R.drawable.error)
                .into(holder.imgHinhSanPhamBanChay);

        // Tạo hiệu ứng nhấp nháy chữ Hot
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.alpha_new);
        holder.imgHot.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return arraySanPhamBanChay.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        private ImageView imgHinhSanPhamBanChay;
        private TextView txtTenSanPhamBanChay, txtGiaSanPhamBanChay;
        private ImageView imgHot;

        public ItemHolder(View itemView){
            super(itemView);
            imgHinhSanPhamBanChay = itemView.findViewById(R.id.imageViewSanPhamBanChay);
            txtTenSanPhamBanChay = itemView.findViewById(R.id.textViewTenSanPhamBanChay);
            txtGiaSanPhamBanChay = itemView.findViewById(R.id.textViewGiaSPBanChay);
            imgHot = itemView.findViewById(R.id.imageViewHot);

            // Bắt sự kiện click item cho RecyclerViewManHinhChinh
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChiTietSanPhamActivity.class);
                    intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("ThongTinSanPham", arraySanPhamBanChay.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
