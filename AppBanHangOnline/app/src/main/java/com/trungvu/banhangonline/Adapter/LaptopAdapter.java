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

public class LaptopAdapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham> arrayListLaptop;

    public LaptopAdapter(Context context, ArrayList<SanPham> arrayListLaptop) {
        this.context = context;
        this.arrayListLaptop = arrayListLaptop;
    }

    @Override
    public int getCount() {
        return arrayListLaptop.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListLaptop.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        public ImageView imgLaptop;
        public TextView txtTenLaptop, txtGiaLaptop, txtDacDiemNoiBatLaptop;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;

        if (view == null){
            viewHolder = new LaptopAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_laptop, null);

            viewHolder.imgLaptop = view.findViewById(R.id.imageViewLaptop);
            viewHolder.txtTenLaptop = view.findViewById(R.id.textViewLaptop);
            viewHolder.txtGiaLaptop = view.findViewById(R.id.textViewGiaLaptop);
            viewHolder.txtDacDiemNoiBatLaptop = view.findViewById(R.id.textViewDacDiemNoiBatLaptop);
            view.setTag(viewHolder);
        } else {
            viewHolder = (LaptopAdapter.ViewHolder) view.getTag();
        }

        SanPham sanPham = (SanPham) getItem(i);

        Picasso.with(context).load(sanPham.getHinhSanPham())
                .placeholder(R.drawable.no_image_available)
                .error(R.drawable.error)
                .into(viewHolder.imgLaptop);

        viewHolder.txtTenLaptop.setText(sanPham.getTenSanPham());

        // DecimalFormat: Định dạng chuỗi theo chuẩn mình quy định
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaLaptop.setText("Giá : " + decimalFormat.format(sanPham.getGiaSanPham()) + " Đ");

        // set số lượng dòng tối đa
        viewHolder.txtDacDiemNoiBatLaptop.setMaxLines(2);
        // set dấu 3 chấm ở phía sau cùng, nếu dài quá thì sẽ hiện dấu 3 chấm ở phía cuối...
        viewHolder.txtDacDiemNoiBatLaptop.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtDacDiemNoiBatLaptop.setText(sanPham.getDacDiemNoiBatSanPham());

        return view;
    }
}
