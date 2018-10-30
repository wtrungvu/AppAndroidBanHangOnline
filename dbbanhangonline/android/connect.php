<?php
	$host = "localhost";
	$username = "root";
	$password = "";
	$database = "dbbanhangonline";

	$conn = mysqli_connect($host, $username, $password, $database);
	mysqli_query($conn, "SET NAMES 'utf8'");

	/*
	if ($conn) 
	{
		echo "Kết nối thành công";
	}
	else
	{
		echo "Kết nối thất bại";
	}
	*/
?>