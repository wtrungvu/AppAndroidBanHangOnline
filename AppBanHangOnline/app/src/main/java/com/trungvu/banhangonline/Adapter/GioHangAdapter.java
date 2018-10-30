package com.trungvu.banhangonline.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.trungvu.banhangonline.Activity.GioHangActivity;
import com.trungvu.banhangonline.Activity.MainActivity;
import com.trungvu.banhangonline.Model.GioHang;
import com.trungvu.banhangonline.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends BaseAdapter {
    Context context;
    ArrayList<GioHang> arrayListGioHang;

    public GioHangAdapter(GioHangActivity context, ArrayList<GioHang> arrayListGioHang) {
        this.context = context;
        this.arrayListGioHang = arrayListGioHang;
    }

    @Override
    public int getCount() {
        return arrayListGioHang.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListGioHang.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        public ImageView imgGioHang;
        public TextView txtTenGioHang, txtGiaGioHang;
        public Button btnGiam, btnValue, btnTang;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_giohang, null);

            viewHolder.imgGioHang = view.findViewById(R.id.imageViewGioHang);
            viewHolder.txtTenGioHang = view.findViewById(R.id.textViewTenGioHang);
            viewHolder.txtGiaGioHang = view.findViewById(R.id.textViewGiaGioHang);
            viewHolder.btnGiam = view.findViewById(R.id.buttonGiam);
            viewHolder.btnValue = view.findViewById(R.id.buttonValue);
            viewHolder.btnTang = view.findViewById(R.id.buttonTang);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        GioHang gioHang = (GioHang) getItem(i);

        Picasso.with(context).load(gioHang.getHinhSanPhamGioHang())
                .placeholder(R.drawable.no_image_available)
                .error(R.drawable.error)
                .into(viewHolder.imgGioHang);
        viewHolder.txtTenGioHang.setText(gioHang.getTenSanPhamGioHang());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaGioHang.setText(decimalFormat.format(gioHang.getGiaSanPhamGioHang()) + " Đ");
        viewHolder.btnValue.setText(gioHang.getSoLuongSanPhamGioHang() + "");

        // Xử lý button value, button tăng, giảm
        int sl = Integer.parseInt(viewHolder.btnValue.getText() + "");
        // Nếu ấn button tăng >= 10 thì button tăng sẽ biến mất, bắt sự kiện khi Run app lên
        if (sl >= 10){
            viewHolder.btnTang.setVisibility(View.INVISIBLE);
            viewHolder.btnGiam.setVisibility(View.VISIBLE);
        } else if (sl <= 1){ // button giảm biến mất
            viewHolder.btnGiam.setVisibility(View.INVISIBLE);
        } else if (sl >= 1){ // button xuất hiện trở lại
            viewHolder.btnTang.setVisibility(View.VISIBLE);
            viewHolder.btnGiam.setVisibility(View.VISIBLE);
        }

        // Bắt sự kiện update lại giá khi click vào button tăng
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slMoiNhat = Integer.parseInt(finalViewHolder.btnValue.getText().toString()) + 1;
                int slHienTai = MainActivity.mangGioHang.get(i).getSoLuongSanPhamGioHang();
                long giaHienTai = MainActivity.mangGioHang.get(i).getGiaSanPhamGioHang();
                MainActivity.mangGioHang.get(i).setSoLuongSanPhamGioHang(slMoiNhat);
                long giaMoiNhat = (giaHienTai * slMoiNhat) / slHienTai;
                MainActivity.mangGioHang.get(i).setGiaSanPhamGioHang(giaMoiNhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtGiaGioHang.setText(decimalFormat.format(giaMoiNhat) + " Đ");
                GioHangActivity.eventUlti(); // Update lại dữ liệu TextView Tổng Tiền
                // Bắt sự kiện click vào
                if (slMoiNhat > 9){
                    finalViewHolder.btnTang.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnGiam.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValue.setText(String.valueOf(slMoiNhat));
                } else {
                    finalViewHolder.btnTang.setVisibility(View.VISIBLE);
                    finalViewHolder.btnGiam.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValue.setText(String.valueOf(slMoiNhat));
                }
            }
        });

        // Bắt sự kiện update lại giá khi click vào button giảm
        final ViewHolder finalViewHolder1 = viewHolder;
        viewHolder.btnGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slMoiNhat = Integer.parseInt(finalViewHolder1.btnValue.getText().toString()) - 1;
                int slHienTai = MainActivity.mangGioHang.get(i).getSoLuongSanPhamGioHang();
                long giaHienTai = MainActivity.mangGioHang.get(i).getGiaSanPhamGioHang();
                MainActivity.mangGioHang.get(i).setSoLuongSanPhamGioHang(slMoiNhat);
                long giaMoiNhat = (giaHienTai * slMoiNhat) / slHienTai;
                MainActivity.mangGioHang.get(i).setGiaSanPhamGioHang(giaMoiNhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder1.txtGiaGioHang.setText(decimalFormat.format(giaMoiNhat) + " Đ");
                GioHangActivity.eventUlti(); // Update lại dữ liệu TextView Tổng Tiền
                // Bắt sự kiện click vào
                if (slMoiNhat < 2){
                    finalViewHolder1.btnTang.setVisibility(View.VISIBLE);
                    finalViewHolder1.btnGiam.setVisibility(View.INVISIBLE);
                    finalViewHolder1.btnValue.setText(String.valueOf(slMoiNhat));
                } else {
                    finalViewHolder1.btnTang.setVisibility(View.VISIBLE);
                    finalViewHolder1.btnGiam.setVisibility(View.VISIBLE);
                    finalViewHolder1.btnValue.setText(String.valueOf(slMoiNhat));
                }
            }
        });

        return view;
    }

}
