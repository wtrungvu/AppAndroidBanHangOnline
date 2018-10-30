package com.trungvu.banhangonline.Model;

import java.io.Serializable;

public class LoaiSanPham implements Serializable {
    private int MaLoaiSanPham;
    private String TenLoaiSanPham;
    private String HinhLoaiSanPham;

    public LoaiSanPham() {
    }

    public LoaiSanPham(int maLoaiSanPham, String tenLoaiSanPham, String hinhLoaiSanPham) {
        MaLoaiSanPham = maLoaiSanPham;
        TenLoaiSanPham = tenLoaiSanPham;
        HinhLoaiSanPham = hinhLoaiSanPham;
    }

    public int getMaLoaiSanPham() {
        return MaLoaiSanPham;
    }

    public void setMaLoaiSanPham(int maLoaiSanPham) {
        MaLoaiSanPham = maLoaiSanPham;
    }

    public String getTenLoaiSanPham() {
        return TenLoaiSanPham;
    }

    public void setTenLoaiSanPham(String tenLoaiSanPham) {
        TenLoaiSanPham = tenLoaiSanPham;
    }

    public String getHinhLoaiSanPham() {
        return HinhLoaiSanPham;
    }

    public void setHinhLoaiSanPham(String hinhLoaiSanPham) {
        HinhLoaiSanPham = hinhLoaiSanPham;
    }
}
