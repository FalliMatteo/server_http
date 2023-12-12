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
            File file = new File("src/files" + name);
            System.out.println("-----" + name);
            String type[] = name.split("\\.");
            Scanner scanner = new Scanner(file);
            out.println("HTTP/1.1 200 OK");
            System.out.println("LENGTH: " + file.length());
            out.println("Content-Length: " + file.length());
            out.println("Server: Falli's Java HTTP Server");
            out.println("Date: " + new Date());
            switch(type[1]){
                case "html":
                    out.println("Content-Type: text/html; charset=utf-8");
                    break;
                case "css":
                    out.println("Content-Type: text/css; charset=utf-8");
                    break;
                case "js":
                    out.println("Content-Type: text/javascript; charset=utf-8");
                    break;
                case "png":
                    out.println("Content-Type: image/png; charset=utf-8");
                    break;
                case "jpg":
                    out.println("Content-Type: image/jpeg; charset=utf-8");
                    break;
                default:
                    out.println("Content-Type: text; charset=utf-8");
            }
            out.println();
            while(scanner.hasNextLine()){
                String data = scanner.nextLine();
                out.println(data);
            }
            out.close();
            scanner.close();
        }catch(FileNotFoundException e){
            out.println("HTTP/1.1 404 NOT FOUND");
            System.out.println("File not found");
        }
    }

    public static void main( String[] args )
    {
        try {
            ServerSocket server= new ServerSocket(3000);
            boolean loop = true;
            while(loop){
                Socket socket = server.accept();
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
