/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tuan9ViDu2;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface StudentService extends Remote{
    public void addStudent(String name, int age) throws RemoteException;
    public void removeStudent(String name) throws RemoteException;
    public List<Student> getAllStudents() throws RemoteException;
}
