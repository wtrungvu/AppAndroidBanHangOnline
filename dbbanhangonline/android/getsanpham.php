<?php
    include "connect.php";

    class SanPham
    {
        function SanPham($MaSanPham, $MaLoaiSanPham, $MaThuongHieu, $TenSanPham, $GiaSanPham, $HinhSanPham, $ThongSoKiThuat, $DacDiemNoiBat, $PhuKienSanPham, $ThangBaoHanh)
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

    $page = $_GET['page'];
    $masanpham = $_POST['MaSanPham'];
    // $MaSanPham = 1;
    $space = 5;
    $limit = ($page - 1) * $space;
    $mangsanpham = array();
    $query = "SELECT * FROM sanpham WHERE MaLoaiSanPham = $masanpham LIMIT $limit,$space";
    $data = mysqli_query($conn,$query);
    
    while ($row = mysqli_fetch_assoc($data))
    {
        array_push($mangsanpham, new SanPham(
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

    echo json_encode($mangsanpham);

?>