/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tuan9ViDu1;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteInterface extends Remote{
    String sayHello() throws RemoteException;
}
