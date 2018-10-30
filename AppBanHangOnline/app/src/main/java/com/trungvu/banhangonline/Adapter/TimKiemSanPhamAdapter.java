package com.trungvu.banhangonline.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.trungvu.banhangonline.Model.SanPham;
import com.trungvu.banhangonline.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class TimKiemSanPhamAdapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham> mangSanPham;

    public TimKiemSanPhamAdapter(Context context, ArrayList<SanPham> mangSanPham) {
        this.context = context;
        this.mangSanPham = mangSanPham;
    }

    @Override
    public int getCount() {
        return mangSanPham.size();
    }

    @Override
    public Object getItem(int i) {
        return mangSanPham.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        public ImageView imgSanPham;
        public TextView txtTenSanPham, txtGiaSanPham, txtDacDiemNoiBatDienThoai;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;

        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_dienthoai, null);

            viewHolder.imgSanPham = view.findViewById(R.id.imageViewDienThoai);
            viewHolder.txtTenSanPham = view.findViewById(R.id.textViewDienThoai);
            viewHolder.txtGiaSanPham = view.findViewById(R.id.textViewGiaDienThoai);
            viewHolder.txtDacDiemNoiBatDienThoai = view.findViewById(R.id.textViewDacDiemNoiBatDienThoai);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        SanPham sanPham = (SanPham) getItem(i);

        Picasso.with(context).load(sanPham.getHinhSanPham())
                .placeholder(R.drawable.no_image_available)
                .error(R.drawable.error)
                .into(viewHolder.imgSanPham);

        viewHolder.txtTenSanPham.setText(sanPham.getTenSanPham());

        // DecimalFormat: Định dạng chuỗi theo chuẩn mình quy định
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaSanPham.setText("Giá : " + decimalFormat.format(sanPham.getGiaSanPham()) + " Đ");

        // set số lượng dòng tối đa
        viewHolder.txtDacDiemNoiBatDienThoai.setMaxLines(2);
        // set dấu 3 chấm ở phía sau cùng, nếu dài quá thì sẽ hiện dấu 3 chấm ở phía cuối...
        viewHolder.txtDacDiemNoiBatDienThoai.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtDacDiemNoiBatDienThoai.setText(sanPham.getDacDiemNoiBatSanPham());

        return view;
    }


}
