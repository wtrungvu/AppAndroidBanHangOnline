<?php
    include "connect.php";

    class SanPhamBanChay
    {
        function SanPhamBanChay($MaSanPham, $MaLoaiSanPham, $MaThuongHieu, $TenSanPham, $GiaSanPham, $HinhSanPham, $ThongSoKiThuat, $DacDiemNoiBat, $PhuKienSanPham, $ThangBaoHanh)
         {
            $this->MaSanPham = $MaSanPham;
            $this->MaLoaiSanPham = $MaLoaiSanPham;
            $this->MaThuongHieu = $MaThuongHieu;
            $this->TenSanPham = $TenSanPham;
            $this->GiaSanPham = $GiaSanPham;
            $this->HinhSanPham = $HinhSanPham;
            $this->ThongSoKiThuat = $ThongSoKiThuat;
            $this->DacDiemNoiBat = $DacDiemNoiBat;
            $this->PhuKienSanPham = $PhuKienSanPham;
            $this->ThangBaoHanh = $ThangBaoHanh;
        }
    }

    $MangSPBanChay = array();
    $query = "SELECT * FROM sanpham WHERE MaSanPham IN (16,18,27,36,42,57)";

    $data = mysqli_query($conn, $query);
    while ($row = mysqli_fetch_assoc($data))
    {
        array_push($MangSPBanChay, new SanPhamBanChay
        (
            $row['MaSanPham'],
            $row['MaLoaiSanPham'],
            $row['MaThuongHieu'],
            $row['TenSanPham'],
            $row['GiaSanPham'],
            $row['HinhSanPham'],
            $row['ThongSoKiThuat'],
            $row['DacDiemNoiBat'],
            $row['PhuKienSanPham'],
            $row['ThangBaoHanh']
        ));
    }

    echo json_encode($MangSPBanChay);

?>