package fi.utu.tech.assignment3;

import java.io.*;
import java.net.*;


public class Server {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        try {
            ServerSocket server = new ServerSocket(34567);
            int counter = 0;
            System.out.println("Server Started ....");
            while (true) {
                counter++;
                Socket serverClient = server.accept();  //server accept the client connection request
                System.out.println("Client No: " + counter + " started!");
                ClientHandler thread = new ClientHandler(serverClient, counter); //send  the request to a separate thread
                thread.start();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

