/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javaapplication2;
public class VongDoiLuong {
    public static void main(String[] args) {
        //tao 1 luong moi
        Thread th1 = new Thread(new DNRunable2());
        th1.start();
        // cho may giay
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        th1.interrupt();
    }
}
class DNRunable2 implements Runnable{

    @Override
    public void run() {
        System.out.print("Luong dang chay - Trang thai"
        + Thread.currentThread().getState());
        try{
            Thread.sleep(5000);// nghi 5s
        } catch (Exception e) {
            System.out .print("Luong bi ngat ngu = Trang thai"
            + Thread.currentThread().getState());
        }
        System.out.print("Luong da ket thuc - Trang thai" +
                Thread.currentThread().getState());
    }
}
// Vũ Xuân Sơn CNTT 1502 / MSV: 1571020227