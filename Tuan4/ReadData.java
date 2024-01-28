package com.mycompany.javaapplication4.Tuan4;

import java.io.BufferedReader;
import java.net.URL;
import java.io.InputStreamReader;
import java.net.URLConnection;

public class ReadData {
    public static void main(String[] args) {
        try {
            // tao doi tuong URL tu dia chi 
            URL url=new URL("https://www.google.com");
            // mo ket noi URL
            URLConnection uRLConnection=url.openConnection();
            // doc du lieu tu ket noi
            BufferedReader reader=new BufferedReader(
            new InputStreamReader(uRLConnection.getInputStream()));
            // doc tung dong
            String line;
            while((line=reader.readLine())!=null){
                System.out.println(line);// in ra du lieu doc duoc
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
// XuanSon CNTT1502-1571020227
