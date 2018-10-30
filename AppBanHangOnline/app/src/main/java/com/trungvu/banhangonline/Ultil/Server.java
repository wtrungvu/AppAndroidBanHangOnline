package com.trungvu.banhangonline.Ultil;

public class Server {
    public static String localhost = "192.168.1.107:8080";
//    public static String localhost = "192.168.56.1:8080";
//    public static String localhost = "ductrung584.000webhostapp.com";
    public static String location = "/dbbanhangonline/android/";
    public static String duongDanLoaiSP = "http://" + localhost + location + "getloaisanpham.php";
    public static String duongDanLoaiSPMoiNhat = "http://" + localhost + location + "getsanphammoinhat.php";
    public static String duongDanLoaiSPBanChay = "http://" + localhost + location + "getsanphambanchay.php";
    public static String duongDanSP = "http://" + localhost + location + "getsanpham.php?page=";

    public static String duongDanThongTinKhachHang = "http://" + localhost + location + "thongtinkhachhang.php";
    public static String duongDanDonHang = "http://" + localhost + location + "donhang.php";
    public static String duongDanChiTietDonHang = "http://" + localhost + location + "chitietdonhang.php";

    public static String duongDanTimKiemSanPham = "http://" + localhost + location + "timkiemsanpham.php?key=";
}
