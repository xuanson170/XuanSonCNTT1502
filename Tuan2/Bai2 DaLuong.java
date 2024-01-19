/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javaapplication2;
import java.util.logging.Logger;
public class DaLuong {
    public static void main(String[] args) {
        // goi luong 
        Thread th1=new Thread(new DNRunable("Luong 1"));
        th1.start();
        
        Thread th2=new Thread(new DNRunable("Luong 2"));
        th2.start();
    }
    
}
class DNRunable implements Runnable{
    private String threadName; // qly theo ten

    public DNRunable(String name) {
        this.threadName=name;
    }
    
    @Override
    public void run() {
        System.out.println("Bắt đầu thực hiện công việc trong "
        + threadName);
        // Thuc hien cviec cua luong
        for (int i=0; i<=5; i++)
        {
            System.out.println(threadName + "; Buoc " + i );
            try {
                 Thread.sleep(1000); // ngu trong 1s
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Ket thuc cong viec "+ threadName);
           
        }
    }
}
// Vũ Xuân Sơn CNTT 15-02 + MSV: 1571020227
