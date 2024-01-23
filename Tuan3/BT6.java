package javaapplication3;
public class BT6 {
    private final Object monitor=new Object();
    private boolean isDataReady=false; 
    public void producData() throws InterruptedException
    {
        synchronized (monitor) 
        {
         //chi co 1 tien trinh dc thuc hien
         // neu tin hieu ss
         while(isDataReady){
             monitor.wait();
         }
        }
            // lap lai du lieu
    System.out.println("San xuat du lieu...");
    Thread.sleep(2000);
    // Sau khi sxuat du lieu xong =>> ss
    isDataReady=true;
    // thong bao do tien trinh
    monitor.notify();
    }
    // su dung du lieu
    public void useData() throws InterruptedException
    {
        synchronized (monitor) 
        {
            while(!isDataReady) {
            monitor.wait();}
        }
        System.out.println("Su dung du lieu...");
        Thread.sleep(3000);
        // danh dau dlieu chua ss
        isDataReady=false;
        monitor.notify();
        }
    public static void main(String[] args) {
        BT6 ex=new BT6();
        // tao tien trinh Sxuat
        Thread thSanXuat = new Thread(()->{
            try {
                while (true){
                    ex.producData();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        // Tao tien trinh SuDung
        Thread thSuDung = new Thread(()->{
            try {
                while (true){
                    ex.useData();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        // Start
        thSanXuat.start();
        thSuDung.start();
    }
}
// Vũ Xuân Sơn CNTT1502 - 1571020227
