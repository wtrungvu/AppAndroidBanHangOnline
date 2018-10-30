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

public class MayAnhAdapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham> arrayMayAnh;

    public MayAnhAdapter(Context context, ArrayList<SanPham> arrayMayAnh) {
        this.context = context;
        this.arrayMayAnh = arrayMayAnh;
    }

    @Override
    public int getCount() {
        return arrayMayAnh.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayMayAnh.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        public ImageView imgMayAnh;
        public TextView txtTenMayAnh, txtGiaMayAnh, txtDacDiemNoiBatMayAnh;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;

        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_mayanh, null);

            viewHolder.imgMayAnh = view.findViewById(R.id.imageViewMayAnh);
            viewHolder.txtTenMayAnh = view.findViewById(R.id.textViewTenMayAnh);
            viewHolder.txtGiaMayAnh = view.findViewById(R.id.textViewGiaMayAnh);
            viewHolder.txtDacDiemNoiBatMayAnh = view.findViewById(R.id.textViewDacDiemNoiBatMayAnh);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        SanPham sanPham = (SanPham) getItem(i);

        Picasso.with(context).load(sanPham.getHinhSanPham())
                .placeholder(R.drawable.no_image_available)
                .error(R.drawable.error)
                .into(viewHolder.imgMayAnh);

        viewHolder.txtTenMayAnh.setText(sanPham.getTenSanPham());

        // DecimalFormat: Định dạng chuỗi theo chuẩn mình quy định
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaMayAnh.setText("Giá : " + decimalFormat.format(sanPham.getGiaSanPham()) + " Đ");

        // set số lượng dòng tối đa
        viewHolder.txtDacDiemNoiBatMayAnh.setMaxLines(2);
        // set dấu 3 chấm ở phía sau cùng, nếu dài quá thì sẽ hiện dấu 3 chấm ở phía cuối...
        viewHolder.txtDacDiemNoiBatMayAnh.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtDacDiemNoiBatMayAnh.setText(sanPham.getDacDiemNoiBatSanPham());

        return view;
    }

}
