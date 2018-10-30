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

public class MayChoiGameAdapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham> arrayListMayChoiGame;

    public MayChoiGameAdapter(Context context, ArrayList<SanPham> arrayListMayChoiGame) {
        this.context = context;
        this.arrayListMayChoiGame = arrayListMayChoiGame;
    }

    @Override
    public int getCount() {
        return arrayListMayChoiGame.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListMayChoiGame.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        public ImageView imgMayChoiGame;
        public TextView txtTenMayChoiGame, txtGiaMayChoiGame, txtDacDiemNoiBatMayChoiGame;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;

        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_may_choi_game, null);

            viewHolder.imgMayChoiGame = view.findViewById(R.id.imageViewMayChoiGame);
            viewHolder.txtTenMayChoiGame = view.findViewById(R.id.textViewTenMayChoiGame);
            viewHolder.txtGiaMayChoiGame = view.findViewById(R.id.textViewGiaMayChoiGame);
            viewHolder.txtDacDiemNoiBatMayChoiGame = view.findViewById(R.id.textViewDacDiemNoiBatMayChoiGame);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        SanPham sanPham = (SanPham) getItem(i);

        Picasso.with(context).load(sanPham.getHinhSanPham())
                .placeholder(R.drawable.no_image_available)
                .error(R.drawable.error)
                .into(viewHolder.imgMayChoiGame);

        viewHolder.txtTenMayChoiGame.setText(sanPham.getTenSanPham());

        // DecimalFormat: Định dạng chuỗi theo chuẩn mình quy định
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaMayChoiGame.setText("Giá : " + decimalFormat.format(sanPham.getGiaSanPham()) + " Đ");

        // set số lượng dòng tối đa
        viewHolder.txtDacDiemNoiBatMayChoiGame.setMaxLines(2);
        // set dấu 3 chấm ở phía sau cùng, nếu dài quá thì sẽ hiện dấu 3 chấm ở phía cuối...
        viewHolder.txtDacDiemNoiBatMayChoiGame.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtDacDiemNoiBatMayChoiGame.setText(sanPham.getDacDiemNoiBatSanPham());

        return view;
    }

}
