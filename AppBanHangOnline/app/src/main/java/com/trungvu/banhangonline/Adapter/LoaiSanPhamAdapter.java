package com.trungvu.banhangonline.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.trungvu.banhangonline.Model.LoaiSanPham;
import com.trungvu.banhangonline.R;

import java.util.ArrayList;

public class LoaiSanPhamAdapter extends BaseAdapter{
    ArrayList<LoaiSanPham> arrayListLoaiSanPham;
    Context context;

    public LoaiSanPhamAdapter(ArrayList<LoaiSanPham> arrayListLoaiSanPham, Context context) {
        this.arrayListLoaiSanPham = arrayListLoaiSanPham;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListLoaiSanPham.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListLoaiSanPham.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        TextView txtTenLoaiSP;
        ImageView imgLoaiSP;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;

        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_listview_loaisp, null);

            viewHolder.txtTenLoaiSP = view.findViewById(R.id.textViewLoaiSP);
            viewHolder.imgLoaiSP = view.findViewById(R.id.imageViewLoaiSP);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }

        LoaiSanPham loaiSanPham = (LoaiSanPham) getItem(i);

        viewHolder.txtTenLoaiSP.setText(loaiSanPham.getTenLoaiSanPham());
        Picasso.with(context).load(loaiSanPham.getHinhLoaiSanPham())
                .placeholder(R.drawable.no_image_available)
                .error(R.drawable.error)
                .into(viewHolder.imgLoaiSP);

        // Tạo hiệu ứng animation (Item ListView)
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.scale_list);
        view.startAnimation(animation);

        return view;
    }
}
