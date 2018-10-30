<?php
    include "connect.php";

    class SanPhamMoiNhat
    {
        function SanPhamMoiNhat($MaSanPham, $MaLoaiSanPham, $MaThuongHieu, $TenSanPham, $GiaSanPham, $HinhSanPham, $ThongSoKiThuat, $DacDiemNoiBat, $PhuKienSanPham, $ThangBaoHanh)
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

    $MangSPMoiNhat = array();
    $query = "SELECT * FROM sanpham ORDER BY MaSanPham DESC LIMIT 10";

    $data = mysqli_query($conn, $query);
    while ($row = mysqli_fetch_assoc($data))
    {
        array_push($MangSPMoiNhat, new SanPhamMoiNhat
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

    echo json_encode($MangSPMoiNhat);

?>