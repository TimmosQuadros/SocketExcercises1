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
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TimmosQuadros
 */
public class socketTest {

	static String ip = "localhost";
	static int portNum = 8080;

	public static void handleClient(Socket s){
		try {
			Scanner scn = new Scanner(s.getInputStream());
			PrintWriter prnt = new PrintWriter(s.getOutputStream(), true);
			String msg = "";
			while(!msg.equals("STOP")){
				msg = scn.nextLine();
				prnt.println(msg.toUpperCase());
			}
			scn.close();
			prnt.close();
			s.close();
		} catch (IOException ex) {
			Logger.getLogger(socketTest.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static void main(String[] args) throws IOException {
		if(args.length==2){
			ip = args[0];
			portNum = Integer.parseInt(args[1]);
		}

		ServerSocket ss = new ServerSocket();
		ss.bind(new InetSocketAddress(ip, portNum));
		System.out.println("Server started - listening on port "+portNum+ "bound to ip "+ip);

		while(true){
			Socket link = ss.accept();
			System.out.println("New client connection");
			handleClient(link);
		}

	}
}
