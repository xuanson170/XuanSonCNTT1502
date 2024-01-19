/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// Bai4
package com.mycompany.javaapplication2;
public class TinhTongMang {
    public static void main(String[] args) {
        //mang cac so nguyen
        int[] array={1,2,3,4,5,6,7,8,9,10};
        //so luong thread
        int numThreads=2;
        //mang cac luolng
        Thread[] ths=new Thread[numThreads];//tao cac luong
        //tao 2 doi tuong cho luong
        LopTinhTong[] cal=new LopTinhTong[numThreads];
        for(int i=0;i<numThreads;i++)
        {
            cal[i]=new LopTinhTong(array, i, numThreads);//gan DT cho luong
            ths[i]=new Thread(cal[i]);
            ths[i].start();//Bat dau luong
        }
        //Doi cho cac luong hoan thanh -> cong don
        for(int i=0; i<numThreads;i++)
        {
            try {
                ths[i].join();//cho cho luong ket thuc
            } catch (Exception e) {
            }
        }
        //Cong don
        int tong=0;
        for(LopTinhTong c: cal)
        {
            tong += c.getTongTungPhan();
        }
        //tin ket qua ra man hinh dang co
        System.out.println("Tong la"+tong);
    }
 
}
class LopTinhTong implements Runnable{
    private int[] array;
    private int startIndex;
    private int buocNhay;
    private int tongTungPhan=0;
            
    public LopTinhTong(int[] array, int startIndex, int buocNhay) {
        this.array = array;
        this.startIndex = startIndex;
        this.buocNhay = buocNhay;
        this.tongTungPhan=0;
    }
    
    @Override
    public void run(){
        for(int i=startIndex;i<array.length;i+=buocNhay)
        {
            tongTungPhan +=array[i];//tinh tong tung phan            
        }
        
    }

    public int getTongTungPhan() {
        return tongTungPhan;
    }
}
// Vũ Xuân Sơn CNTT 1502/ MSV 1571020227