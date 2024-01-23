package javaapplication3;
public class BT4 {
    private final Object lc=new Object();
    //Pthuc duoc dong bo
    public synchronized void phuongThuc1()
    {
        
    }
    public synchronized void phuongThuc2()
    {
        synchronized (lc)
        {
            
        }    
    }
     public static void main(String[] args ){
        Thread th=new Thread(()->{
            //--
        });
        th.start(); // bat dau
        try{
            ; // cho tien trinh
        } catch (Exception e) {
            
        }
    }
}
