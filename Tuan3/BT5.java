package javaapplication3;
public class BT5 {
    private final Object monitor=new Object();
    public void waitForSignal() throws InterruptedException
    {
        synchronized (monitor) 
        {
            monitor.wait(); // tien trinh treo            
        }
    }
    public void notifySignal()
    {
        synchronized (monitor) 
        {
         monitor.notify();
        }
    }
}
