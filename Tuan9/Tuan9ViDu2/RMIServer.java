/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tuan9ViDu2;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RMIServer {
    public static void main(String[] args) {
        try {
            //khoi tao may chu
            LocateRegistry.createRegistry(1097);
            //tao doi tuong cua lop Remote
            StudentService obj=new StudentServiceImp();
            //dang ky
            Naming.rebind("//localhost:1097/StudentService", obj);
            System.out.println("Server da san sang");
        } catch (Exception e) {
            System.err.println("Loi: "+e.toString());
            e.printStackTrace();
        }
    }
 
}