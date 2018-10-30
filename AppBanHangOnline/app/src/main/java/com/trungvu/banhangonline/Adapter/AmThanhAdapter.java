package com.trungvu.banhangonline.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.trungvu.banhangonline.Model.SanPham;
import com.trungvu.banhangonline.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AmThanhAdapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham> arrayListAmThanh;

    public AmThanhAdapter(Context context, ArrayList<SanPham> arrayListAmThanh) {
        this.context = context;
        this.arrayListAmThanh = arrayListAmThanh;
    }

    @Override
    public int getCount() {
        return arrayListAmThanh.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListAmThanh.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        public ImageView imgAmThanh;
        public TextView txtTenAmThanh, txtGiaAmThanh, txtDacDiemNoiBatAmThanh;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;

        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_am_thanh, null);

            viewHolder.imgAmThanh = view.findViewById(R.id.imageViewAmThanh);
            viewHolder.txtTenAmThanh = view.findViewById(R.id.textViewTenAmThanh);
            viewHolder.txtGiaAmThanh = view.findViewById(R.id.textViewGiaAmThanh);
            viewHolder.txtDacDiemNoiBatAmThanh = view.findViewById(R.id.textViewDacDiemNoiBatAmThanh);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        SanPham sanPham = (SanPham) getItem(i);

        Picasso.with(context).load(sanPham.getHinhSanPham())
                .placeholder(R.drawable.no_image_available)
                .error(R.drawable.error)
                .into(viewHolder.imgAmThanh);

        viewHolder.txtTenAmThanh.setText(sanPham.getTenSanPham());

        // DecimalFormat: Định dạng chuỗi theo chuẩn mình quy định
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaAmThanh.setText("Giá : " + decimalFormat.format(sanPham.getGiaSanPham()) + " Đ");

        // set số lượng dòng tối đa
        viewHolder.txtDacDiemNoiBatAmThanh.setMaxLines(2);
        // set dấu 3 chấm ở phía sau cùng, nếu dài quá thì sẽ hiện dấu 3 chấm ở phía cuối...
        viewHolder.txtDacDiemNoiBatAmThanh.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtDacDiemNoiBatAmThanh.setText(sanPham.getDacDiemNoiBatSanPham());

        return view;
    }

}
