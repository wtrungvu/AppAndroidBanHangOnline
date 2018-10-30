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

public class TabletAdapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham> arrayListTablet;

    public TabletAdapter(Context context, ArrayList<SanPham> arrayListTablet) {
        this.context = context;
        this.arrayListTablet = arrayListTablet;
    }

    @Override
    public int getCount() {
        return arrayListTablet.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListTablet.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        public ImageView imgTablet;
        public TextView txtTenTablet, txtGiaTablet, txtDacDiemNoiBatTablet;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;

        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_tablet, null);

            viewHolder.imgTablet = view.findViewById(R.id.imageViewTablet);
            viewHolder.txtTenTablet = view.findViewById(R.id.textViewTenTablet);
            viewHolder.txtGiaTablet = view.findViewById(R.id.textViewGiaTablet);
            viewHolder.txtDacDiemNoiBatTablet = view.findViewById(R.id.textViewDacDiemNoiBatTablet);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        SanPham sanPham = (SanPham) getItem(i);

        Picasso.with(context).load(sanPham.getHinhSanPham())
                .placeholder(R.drawable.no_image_available)
                .error(R.drawable.error)
                .into(viewHolder.imgTablet);

        viewHolder.txtTenTablet.setText(sanPham.getTenSanPham());

        // DecimalFormat: Định dạng chuỗi theo chuẩn mình quy định
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaTablet.setText("Giá : " + decimalFormat.format(sanPham.getGiaSanPham()) + " Đ");

        // set số lượng dòng tối đa
        viewHolder.txtDacDiemNoiBatTablet.setMaxLines(2);
        // set dấu 3 chấm ở phía sau cùng, nếu dài quá thì sẽ hiện dấu 3 chấm ở phía cuối...
        viewHolder.txtDacDiemNoiBatTablet.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtDacDiemNoiBatTablet.setText(sanPham.getDacDiemNoiBatSanPham());

        return view;
    }
}
