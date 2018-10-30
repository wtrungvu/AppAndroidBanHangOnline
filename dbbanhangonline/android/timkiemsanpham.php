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

    if (isset($_GET['key'])) {
        $key = $_GET['key'];
        $mangsanpham = array();
        $query = "SELECT * FROM sanpham WHERE TenSanPham LIKE '%$key%'";
        $data = mysqli_query($conn, $query);

        while ($row = mysqli_fetch_assoc($data)){
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
    }
    else {
        $query = "SELECT * FROM sanpham";
        $data = mysqli_query($conn, $query);
        $mangsanpham = array();
        while( $row = mysqli_fetch_assoc($data) ){
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
}

mysqli_close($conn);

?>