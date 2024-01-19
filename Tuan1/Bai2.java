/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javaapplication;
import java.net.InetAddress;
/**
 *
 * @author Son
 */
public class Bai2 {
    public static void main(String[] args) {
        try {
            InetAddress inet=InetAddress.getByName("www.google.com");
            System.out.println("Dia chi ip cua trang web google: " +
                    inet.getHostAddress());
        } catch (Exception e){
            e.printStackTrace();
        }
        
    }
    
}
// Vu Xuan Son CNTT1502