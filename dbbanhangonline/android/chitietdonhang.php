<?php
    include "connect.php";

    $json = $_POST['json'];
    $data = json_decode($json, true);  
    
    foreach($data as $value){
        $MaDonHang = $value['MaDonHang'];
        $MaSanPham = $value['MaSanPham'];
        $SoLuongSanPham = $value['SoLuongSanPham'];

        $query = "INSERT INTO chitietdonhang(MACHITIETDONHANG,MaDonHang,MaSanPham,SoLuongSanPham) 
                    VALUES (null,'$MaDonHang','$MaSanPham','$SoLuongSanPham')";
        $data2 = mysqli_query($conn,$query);
    }

    if($data2){
        echo "1";
    } else {
        echo "0";
    }
?>