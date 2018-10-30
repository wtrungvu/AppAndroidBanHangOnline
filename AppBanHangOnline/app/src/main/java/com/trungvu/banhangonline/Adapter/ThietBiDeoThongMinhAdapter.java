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

public class ThietBiDeoThongMinhAdapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham> arrayListThietBiDeoThongMinh;

    public ThietBiDeoThongMinhAdapter(Context context, ArrayList<SanPham> arrayListThietBiDeoThongMinh) {
        this.context = context;
        this.arrayListThietBiDeoThongMinh = arrayListThietBiDeoThongMinh;
    }

    @Override
    public int getCount() {
        return arrayListThietBiDeoThongMinh.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListThietBiDeoThongMinh.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        public ImageView imgThietBiDeoThongMinh;
        public TextView txtTenThietBiDeoThongMinh, txtGiaThietBiDeoThongMinh, txtDacDiemNoiBatThietBiDeoThongMinh;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;

        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_thietbideothongminh, null);

            viewHolder.imgThietBiDeoThongMinh = view.findViewById(R.id.imageViewThietBiDeoThongMinh);
            viewHolder.txtTenThietBiDeoThongMinh = view.findViewById(R.id.textViewTenThietBiDeoThongMinh);
            viewHolder.txtGiaThietBiDeoThongMinh = view.findViewById(R.id.textViewGiaThietBiDeoThongMinh);
            viewHolder.txtDacDiemNoiBatThietBiDeoThongMinh = view.findViewById(R.id.textViewDacDiemNoiBatThietBiDeoThongMinh);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        SanPham sanPham = (SanPham) getItem(i);

        Picasso.with(context).load(sanPham.getHinhSanPham())
                .placeholder(R.drawable.no_image_available)
                .error(R.drawable.error)
                .into(viewHolder.imgThietBiDeoThongMinh);

        viewHolder.txtTenThietBiDeoThongMinh.setText(sanPham.getTenSanPham());

        // DecimalFormat: Định dạng chuỗi theo chuẩn mình quy định
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaThietBiDeoThongMinh.setText("Giá : " + decimalFormat.format(sanPham.getGiaSanPham()) + " Đ");

        // set số lượng dòng tối đa
        viewHolder.txtDacDiemNoiBatThietBiDeoThongMinh.setMaxLines(2);
        // set dấu 3 chấm ở phía sau cùng, nếu dài quá thì sẽ hiện dấu 3 chấm ở phía cuối...
        viewHolder.txtDacDiemNoiBatThietBiDeoThongMinh.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtDacDiemNoiBatThietBiDeoThongMinh.setText(sanPham.getDacDiemNoiBatSanPham());

        return view;
    }
}