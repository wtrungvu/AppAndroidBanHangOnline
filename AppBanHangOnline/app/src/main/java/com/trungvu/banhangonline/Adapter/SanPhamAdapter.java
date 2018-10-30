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
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.trungvu.banhangonline.Activity.ChiTietSanPhamActivity;
import com.trungvu.banhangonline.Activity.MainActivity;
import com.trungvu.banhangonline.Model.SanPham;
import com.trungvu.banhangonline.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ItemHolder> {
    Context context;
    ArrayList<SanPham> arraySanPham;

    public SanPhamAdapter(Context context, ArrayList<SanPham> arraySanPham) {
        this.context = context;
        this.arraySanPham = arraySanPham;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanphammoinhat, null);
        ItemHolder itemHolder = new ItemHolder(v);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, final int position) {
        SanPham sanPham = arraySanPham.get(position);

        holder.txtTenSanPham.setText(sanPham.getTenSanPham());
        // DecimalFormat: Định dạng chuỗi theo chuẩn mình quy định
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiaSanPham.setText("Giá : " + decimalFormat.format(sanPham.getGiaSanPham()) + " Đ");
        Picasso.with(context).load(sanPham.getHinhSanPham())
                .placeholder(R.drawable.no_image_available)
                .error(R.drawable.error)
                .into(holder.imgHinhSanPham);

        // Tạo hiệu ứng nhấp nháy chữ New
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.alpha_new);
        holder.imgNew.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return arraySanPham.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        private ImageView imgHinhSanPham;
        private TextView txtTenSanPham, txtGiaSanPham;
        private ImageView imgNew;

        public ItemHolder(View itemView){
            super(itemView);
            imgHinhSanPham = itemView.findViewById(R.id.imageViewSanPham);
            txtTenSanPham = itemView.findViewById(R.id.textViewTenSanPham);
            txtGiaSanPham = itemView.findViewById(R.id.textViewGiaSP);
            imgNew = itemView.findViewById(R.id.imageViewNew);

            // Bắt sự kiện click item cho RecyclerViewManHinhChinh
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChiTietSanPhamActivity.class);
                    intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("ThongTinSanPham", arraySanPham.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
