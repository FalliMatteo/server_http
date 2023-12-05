package com.script;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class App 
{
    public static void sendFile(PrintWriter out, String name){
        try{
            File file = new File("files" + name);
            System.out.println(file);
            Scanner scanner = new Scanner(file);
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Length: " + file.length());
            out.println("Server: Falli's Java HTTP Server");
            out.println("Date: " + new Date());
            out.println("Content-Type: text; charset=utf-8");
            out.println();
            while(scanner.hasNextLine()){
                String data = scanner.nextLine();
                out.println(data);
            }
            scanner.close();
        }catch(FileNotFoundException e){
            out.println("HTTP/1.1 404 OK");
            System.out.println("File not found");
        }
    }

    public static void main( String[] args )
    {
        try {
            ServerSocket server= new ServerSocket(3000);
            boolean loop = true;
            while(loop){
                System.out.println("The server is waiting");
                Socket socket = server.accept();
                System.out.println("The server is connected");
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream());
                String message = input.readLine();
                String init[] = message.split(" ");
                do{
                    message = input.readLine();
                    System.out.println(message);
                }while(!message.equals("") || !message.isEmpty());
                sendFile(output, init[1]);
                output.flush();
                socket.close();
            }
            server.close();
            System.out.println("Server shutdown");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
