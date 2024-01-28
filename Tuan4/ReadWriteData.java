package com.mycompany.javaapplication4.Tuan4;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class ReadWriteData {
    public static void main(String[] args) {
        try {
            // tao doi tuong tu dia chi URL
            URL url=new URL("htpps://www.google.com");
            URLConnection uRLConnection=url.openConnection();
            BufferedReader reader=new BufferedReader(
            new InputStreamReader(uRLConnection.getInputStream()));
            FileWriter fileWriter=new FileWriter("output.html");
            String line="";
            while((line=reader.readLine())!=null){
                fileWriter.write(line+"\n");
            }
            fileWriter.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
