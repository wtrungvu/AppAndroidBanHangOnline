<?php
    include "connect.php";

    $MaKhachHang = $_POST['MaKhachHang'];
    $TongTienDonHang = $_POST['TongTienDonHang'];
    $NgayGioDatHang = $_POST['NgayGioDatHang'];
    $TrangThaiDonHang = $_POST['TrangThaiDonHang'];

    if(strlen($MaKhachHang) > 0){
        $query = "INSERT INTO donhang(MaDonHang, MaKhachHang, TongTienDonHang, NgayGioDatHang, TrangThaiDonHang)
                    VALUES (null, '$MaKhachHang', '$TongTienDonHang', '$NgayGioDatHang', '$TrangThaiDonHang')";

        if(mysqli_query($conn,$query)){
            $MaDonHang = $conn->insert_id;
            echo $MaDonHang;
        } else{
            echo "Failed!";
        }
    } else {
        echo "Bạn hãy kiểm tra lại các dữ liệu";
    }

?>