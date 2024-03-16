package Tuan8;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Demo81 {
    public static void main(String[] args) {
        Student student=new Student ("Vu Xuan Son", 20);
        try {
            FileOutputStream fileOut
                    =new FileOutputStream("student.txt");
            ObjectOutputStream out
                    =new ObjectOutputStream(fileOut);
            out.writeObject(student);
            out.close();
            fileOut.close();
            System.out.print("Da tuan tu hoa va ghi doi tuong ao file");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            FileInputStream fileIn=new FileInputStream("student.txt");
            ObjectInputStream in=new ObjectInputStream(fileIn);
            Student stdFromFile=(Student)in.readObject();
            in.close();
            fileIn.close();
            System.out.print("Da doc doi tuong tu file");
            System.err.println("Ten: "+ stdFromFile.getName());
            System.err.println("Tuoi:"+ stdFromFile.getAge());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
