/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author TimmosQuadros
 */
public class TCPTimerServer {

    static String ip = "localhost";
    static int portNum = 8080;

    public static void handleClient(Socket s) {
        try {
            Scanner scn = new Scanner(s.getInputStream());
            PrintWriter prnt = new PrintWriter(s.getOutputStream(), true);
            String msg = "";
            while (!msg.equals("STOP")) {
                msg = scn.nextLine();
                if (msg.equalsIgnoreCase("Time")) {
                    Date timeStamp = Calendar.getInstance().getTime();
                    prnt.println(timeStamp);
                } else if (msg.length()>6&&msg.substring(0, 6).equalsIgnoreCase("UPPER#")) {
                    prnt.println(msg.substring(6).toUpperCase());
                } else if (msg.length()>6&&msg.substring(0, 6).equalsIgnoreCase("LOWER#")) {
                    prnt.println(msg.substring(6).toLowerCase());
                } else if (msg.length()>8&&msg.substring(0, 8).equalsIgnoreCase("REVERSE#")) {
                    String output = "";
                    String input = msg.substring(8);
                    StringBuilder input1 = new StringBuilder();
                    input1.append(input);
                    input1 = input1.reverse();
                    for (int i = 0; i < input1.length(); i++) {
                        output += input1.charAt(i);
                    }
                    prnt.println(output);
                } else if (msg.length()>10&&msg.substring(0, 10).equalsIgnoreCase("TRANSLATE#")) {
                    if (msg.substring(10).equalsIgnoreCase("dog")) {
                        prnt.println("hund");
                    }
                }else{
                    msg="STOP";
                }
            }
            scn.close();
            prnt.close();
            s.close();
        } catch (IOException ex) {

        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 2) {
            ip = args[0];
            portNum = Integer.parseInt(args[1]);
        }

        ServerSocket ss = new ServerSocket();
        ss.bind(new InetSocketAddress(ip, portNum));
        System.out.println("Server started - listening on port " + portNum + "bound to ip " + ip);

        while (true) {
            Socket link = ss.accept();
            System.out.println("New client connection");
            handleClient(link);
        }

    }

}
