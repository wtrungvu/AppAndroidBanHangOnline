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

public class PhuKienAdapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham> arrayListPhuKien;

    public PhuKienAdapter(Context context, ArrayList<SanPham> arrayListPhuKien) {
        this.context = context;
        this.arrayListPhuKien = arrayListPhuKien;
    }

    @Override
    public int getCount() {
        return arrayListPhuKien.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListPhuKien.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        public ImageView imgPhuKien;
        public TextView txtTenPhuKien, txtGiaPhuKien, txtDacDiemNoiBatPhuKien;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;

        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_phukien, null);

            viewHolder.imgPhuKien = view.findViewById(R.id.imageViewPhuKien);
            viewHolder.txtTenPhuKien = view.findViewById(R.id.textViewTenPhuKien);
            viewHolder.txtGiaPhuKien = view.findViewById(R.id.textViewGiaPhuKien);
            viewHolder.txtDacDiemNoiBatPhuKien = view.findViewById(R.id.textViewDacDiemNoiBatPhuKien);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        SanPham sanPham = (SanPham) getItem(i);

        Picasso.with(context).load(sanPham.getHinhSanPham())
                .placeholder(R.drawable.no_image_available)
                .error(R.drawable.error)
                .into(viewHolder.imgPhuKien);

        viewHolder.txtTenPhuKien.setText(sanPham.getTenSanPham());

        // DecimalFormat: Định dạng chuỗi theo chuẩn mình quy định
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaPhuKien.setText("Giá : " + decimalFormat.format(sanPham.getGiaSanPham()) + " Đ");

        // set số lượng dòng tối đa
        viewHolder.txtDacDiemNoiBatPhuKien.setMaxLines(2);
        // set dấu 3 chấm ở phía sau cùng, nếu dài quá thì sẽ hiện dấu 3 chấm ở phía cuối...
        viewHolder.txtDacDiemNoiBatPhuKien.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtDacDiemNoiBatPhuKien.setText(sanPham.getDacDiemNoiBatSanPham());

        return view;

    }
}
