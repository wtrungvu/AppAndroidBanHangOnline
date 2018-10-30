package com.trungvu.banhangonline.Model;

import java.io.Serializable;

public class SanPham implements Serializable{
    private int MaSanPham;
    private int MaLoaiSanPham;
    private int MaThuongHieu;
    private String TenSanPham;
    private Integer GiaSanPham;
    private String HinhSanPham;
    private String ThongSoKiThuatSanPham;
    private String DacDiemNoiBatSanPham;
    private String PhuKienTrongHopSanPham;
    private int ThangBaoHanhSanPham;

    public SanPham() {
    }

    public SanPham(int maSanPham, int maLoaiSanPham, int maThuongHieu, String tenSanPham, Integer giaSanPham, String hinhSanPham, String thongSoKiThuatSanPham, String dacDiemNoiBatSanPham, String phuKienTrongHopSanPham, int thangBaoHanhSanPham) {
        MaSanPham = maSanPham;
        MaLoaiSanPham = maLoaiSanPham;
        MaThuongHieu = maThuongHieu;
        TenSanPham = tenSanPham;
        GiaSanPham = giaSanPham;
        HinhSanPham = hinhSanPham;
        ThongSoKiThuatSanPham = thongSoKiThuatSanPham;
        DacDiemNoiBatSanPham = dacDiemNoiBatSanPham;
        PhuKienTrongHopSanPham = phuKienTrongHopSanPham;
        ThangBaoHanhSanPham = thangBaoHanhSanPham;
    }

    public int getMaSanPham() {
        return MaSanPham;
    }

    public void setMaSanPham(int maSanPham) {
        MaSanPham = maSanPham;
    }

    public int getMaLoaiSanPham() {
        return MaLoaiSanPham;
    }

    public void setMaLoaiSanPham(int maLoaiSanPham) {
        MaLoaiSanPham = maLoaiSanPham;
    }

    public int getMaThuongHieu() {
        return MaThuongHieu;
    }

    public void setMaThuongHieu(int maThuongHieu) {
        MaThuongHieu = maThuongHieu;
    }

    public String getTenSanPham() {
        return TenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        TenSanPham = tenSanPham;
    }

    public Integer getGiaSanPham() {
        return GiaSanPham;
    }

    public void setGiaSanPham(Integer giaSanPham) {
        GiaSanPham = giaSanPham;
    }

    public String getHinhSanPham() {
        return HinhSanPham;
    }

    public void setHinhSanPham(String hinhSanPham) {
        HinhSanPham = hinhSanPham;
    }

    public String getThongSoKiThuatSanPham() {
        return ThongSoKiThuatSanPham;
    }

    public void setThongSoKiThuatSanPham(String thongSoKiThuatSanPham) {
        ThongSoKiThuatSanPham = thongSoKiThuatSanPham;
    }

    public String getDacDiemNoiBatSanPham() {
        return DacDiemNoiBatSanPham;
    }

    public void setDacDiemNoiBatSanPham(String dacDiemNoiBatSanPham) {
        DacDiemNoiBatSanPham = dacDiemNoiBatSanPham;
    }

    public String getPhuKienTrongHopSanPham() {
        return PhuKienTrongHopSanPham;
    }

    public void setPhuKienTrongHopSanPham(String phuKienTrongHopSanPham) {
        PhuKienTrongHopSanPham = phuKienTrongHopSanPham;
    }

    public int getThangBaoHanhSanPham() {
        return ThangBaoHanhSanPham;
    }

    public void setThangBaoHanhSanPham(int thangBaoHanhSanPham) {
        ThangBaoHanhSanPham = thangBaoHanhSanPham;
    }
}
