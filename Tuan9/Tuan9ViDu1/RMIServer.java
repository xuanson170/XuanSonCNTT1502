/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tuan9ViDu1;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RMIServer {
    public static void main(String[] args) {
        try {
            //khoi tao may chu
            LocateRegistry.createRegistry(1099);
            //tao doi tuong cua lop Remote
            RemoteInterface obj=new RemoteInterfaceImp();
            //dang ky
            Naming.rebind("//localhost:1099/RemoteObject", obj);
            System.out.println("Server da san sang");
        } catch (Exception e) {
            System.err.println("Loi: "+e.toString());
            e.printStackTrace();
        }
    }
 
}