package javaapplication3;
public class BT3 {
    private int count=0;
    // phuong thuc dong bo hoa
    public synchronized void increment()
    {
        count++;
    }
    // phuong thuc 2 dong bo hoa
    public synchronized void decrement()
    {
        count-=2;
    }
    // phuong thuc 3 khong dong bo hoa
    public int getCount()
    {
        return count;
    }
    public static void main(String[] args) {
        BT3 counter = new BT3();
        // tao cac luong do tang va giam
        Thread incrementThread=new Thread(()->{
            for(int i=0;i<1000;i++)
            {
                counter.increment();
            }
        });
        // tao cac luong dem giam
        Thread decrementThread = new Thread(()->{
            for(int i=0;i<1000;i++)
            {
                counter.decrement();
            }
        });
        // khoi chay cac luong
        incrementThread.start();
        decrementThread.start();
        try {
            // tho doi cho den khi cac luong hthanh
            incrementThread.join();
            decrementThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Count cuoi cung:" +counter.getCount());
    }
}
