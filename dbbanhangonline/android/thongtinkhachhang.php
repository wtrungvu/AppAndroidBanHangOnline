<?php
    include "connect.php";

    $MaLoaiNhanVien = 2; // Mã Loại nhân viên khách hàng = 2
    $HoTenKhachHang = $_POST['HoTenKhachHang'];
    $SoDienThoaiKhachHang = $_POST['SoDienThoaiKhachHang'];
    $EmailKhachHang = $_POST['EmailKhachHang'];
    $DiaChiKhachHang = $_POST['DiaChiKhachHang'];

    if(strlen($HoTenKhachHang) > 0 && strlen($SoDienThoaiKhachHang) > 0 && strlen($EmailKhachHang) > 0 && strlen($DiaChiKhachHang) > 0){
        $query = "INSERT INTO khachhang(MaKhachHang, MaLoaiNhanVien, EmailKhachHang, HoTenKhachHang, SoDienThoaiKhachHang, DiaChiKhachHang)
                    VALUES (null, '$MaLoaiNhanVien', '$EmailKhachHang', '$HoTenKhachHang', '$SoDienThoaiKhachHang', '$DiaChiKhachHang')";

        if(mysqli_query($conn,$query)){
            $MaKhachHang = $conn->insert_id;
            echo $MaKhachHang;
        } else{
            echo "Failed!";
        }
    } else {
        echo "Bạn hãy kiểm tra lại các dữ liệu";
    }
?>