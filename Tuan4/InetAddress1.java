package com.mycompany.javaapplication4.Tuan4;
import java.net.InetAddress;
public class InetAddress1 {
    public static void main(String[] args) {
        // tao doi tuong InetAddress
        try {
            InetAddress addByName=InetAddress.getByName("www.google.com");
            // tao doi tuong INetAddress bang dia chi iP
            InetAddress addByIP=InetAddress.getByName("192.168.15.21");
            System.out.println("Address By Name:" +addByName);
            System.out.println("Address By IP:" +addByIP);
            // su dung getter, setter thong tin ve dia chi IP
            InetAddress addByName1=
                    InetAddress.getByName("www.vnexpress.net");
            String hostname= addByName1.getHostName();
            System.out.println("Host Name:" + hostname);
            // lay dia chi IP
            String IP=addByName1.getHostAddress();
            System.out.println("Dia chi IP:" +IP);
            // IP4 hay IP6
            InetAddress addByName2=
                    InetAddress.getByName("www.vnexpress.net");
            // Kiem tra dia chi IP cos phai la IP4 hay IP6 khong
            boolean isIP4 = addByName2 instanceof java.net.Inet4Address;
            System.out.println("Co phai IP4 khong?" +isIP4);
            // Ktra dia chi IP co phai IP6 khong
            boolean isIP6= addByName2 instanceof java.net.Inet6Address;
            System.out.println("Co phai IP6 khong?" + isIP6);
        } catch (Exception e){
        }
    }
}
