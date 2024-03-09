package Tuan5;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
//VIET UNG DUNG CLIENT/ SERVER BANG SOCKET
public class Client {
    public static void main(String[] args) {
        final String SERVER_ADDRESS="192.168.31.59";
        final int PORT=12345;
        try {
            Socket socket=new Socket(SERVER_ADDRESS,PORT);
            BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out= new PrintWriter(socket.getOutputStream(),true);
            BufferedReader userInput=new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Client da ket noi voi Server");
            String userInputLine="";
            while ((userInputLine=userInput.readLine())!=null) {                
                out.println(userInputLine);//gui du lieu tu client den server
                String response=in.readLine();//phan hoi tu server
                System.out.println("Server tra ve: "+response);
            }
           
        } catch (Exception e) {
        }
    }
}