/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tuan9ViDu2;
import java.rmi.Naming;
import java.util.List;

public class RMIClient {
    public static void main(String[] args) {
        try {
            //tim doi tuong
            StudentService obj=(StudentService)
                    Naming.lookup("//localhost:1097/StudentService");
            //goi cac phuong thuc tu xa
            obj.addStudent("Nguyen Van A", 20);
            obj.addStudent("Tran Van B", 21);
            List<Student> students=obj.getAllStudents();
            //in ra ket qua
            System.out.println("List sinh vien");
            for(Student std:students)
            {
                System.out.println("Name: "+std.getName()+
                        " - Age: "+std.getAge());
            }
            //xoa ket qua
            System.out.println("Xoa ket qua");
            obj.removeStudent("Tran Van B");
            System.out.println("Sau khi xoa");
            for(Student std:students)
            {
                System.out.println("Name: "+std.getName()+
                        " - Age: "+std.getAge());
            }
        } catch (Exception e) {
            System.err.println("Loi: "+e.toString());
            e.printStackTrace();
        }
    }
}