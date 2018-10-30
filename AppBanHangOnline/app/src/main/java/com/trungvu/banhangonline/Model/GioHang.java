package com.trungvu.banhangonline.Model;

import java.io.Serializable;

public class GioHang implements Serializable {
    private int MaSanPhamGioHang;
    private String TenSanPhamGioHang;
    private long GiaSanPhamGioHang;
    private String HinhSanPhamGioHang;
    private int SoLuongSanPhamGioHang;

    public GioHang() {
    }

    public GioHang(int maSanPhamGioHang, String tenSanPhamGioHang, long giaSanPhamGioHang, String hinhSanPhamGioHang, int soLuongSanPhamGioHang) {
        MaSanPhamGioHang = maSanPhamGioHang;
        TenSanPhamGioHang = tenSanPhamGioHang;
        GiaSanPhamGioHang = giaSanPhamGioHang;
        HinhSanPhamGioHang = hinhSanPhamGioHang;
        SoLuongSanPhamGioHang = soLuongSanPhamGioHang;
    }

    public int getMaSanPhamGioHang() {
        return MaSanPhamGioHang;
    }

    public void setMaSanPhamGioHang(int maSanPhamGioHang) {
        MaSanPhamGioHang = maSanPhamGioHang;
    }

    public String getTenSanPhamGioHang() {
        return TenSanPhamGioHang;
    }

    public void setTenSanPhamGioHang(String tenSanPhamGioHang) {
        TenSanPhamGioHang = tenSanPhamGioHang;
    }

    public long getGiaSanPhamGioHang() {
        return GiaSanPhamGioHang;
    }

    public void setGiaSanPhamGioHang(long giaSanPhamGioHang) {
        GiaSanPhamGioHang = giaSanPhamGioHang;
    }

    public String getHinhSanPhamGioHang() {
        return HinhSanPhamGioHang;
    }

    public void setHinhSanPhamGioHang(String hinhSanPhamGioHang) {
        HinhSanPhamGioHang = hinhSanPhamGioHang;
    }

    public int getSoLuongSanPhamGioHang() {
        return SoLuongSanPhamGioHang;
    }

    public void setSoLuongSanPhamGioHang(int soLuongSanPhamGioHang) {
        SoLuongSanPhamGioHang = soLuongSanPhamGioHang;
    }
}
