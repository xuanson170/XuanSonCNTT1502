package com.mycompany.javaapplication4.Tuan4;

import java.net.URL;

public class DiaChiURL {
    public static void main(String[] args) {
        try {
            // tao dtuong tu URL
            URL url = new URL("https://cafef.vn/thi-truong-chung-khoan.chn");
            // phan tich thong tin tu URL
            System.out.println("Protocol:" +url.getProtocol());
            System.out.print("Host:" + url.getHost());
            System.out.println("Port:" + url.getPath());
            System.out.println("Truy van:" + url.getQuery());
            System.out.println("Tham chieu:" + url.getRef());
        } catch (Exception e) {
        }
    }
}
// VuXuanSon CNTT 1502
