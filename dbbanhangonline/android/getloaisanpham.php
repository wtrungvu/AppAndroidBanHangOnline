<?php
	include "connect.php";

	class LoaiSP{
		function LoaiSP($MaLoaiSanPham, $TenLoaiSanPham, $HinhLogoLoaiSanPham)
		{
			$this->MaLoaiSanPham = $MaLoaiSanPham;
			$this->TenLoaiSanPham = $TenLoaiSanPham;
			$this->HinhLogoLoaiSanPham = $HinhLogoLoaiSanPham;
		}
	}

	$query = "SELECT * FROM loaisanpham";
	$data = mysqli_query($conn, $query);
	$mangloaisp = array();

	while ($row = mysqli_fetch_assoc($data)) 
	{
		array_push($mangloaisp, new LoaiSP
			(
				$row['MaLoaiSanPham'],
				$row['TenLoaiSanPham'],
				$row['HinhLogoLoaiSanPham']
			)
		);
	}

	echo json_encode($mangloaisp); // Đổ về dưới dạng json

?>