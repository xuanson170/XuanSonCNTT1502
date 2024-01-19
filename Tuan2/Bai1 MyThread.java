/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.javaapplication2;


public class MyThread extends Thread{

    @Override
    public void run() {
        System.out.println("Luong dang chay");
    }
    public static void main(String[] args) {
        MyThread th = new MyThread();
        th.start();
    }
}
// Vũ Xuân Sơn CNTT1502 - MSV 1571020227
