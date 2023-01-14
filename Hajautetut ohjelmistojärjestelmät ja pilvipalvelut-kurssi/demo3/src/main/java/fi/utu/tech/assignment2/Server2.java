package fi.utu.tech.assignment2;

import java.io.*;
import java.net.*;

public class Server2 {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int port2 = 23456;
        ServerSocket serverSocket = new ServerSocket(port2);
        while (true) {
            System.out.println("Server started and waiting for a client");
            Socket socket = serverSocket.accept();
            System.out.println("Client accepted");
            //luetaan soketista ObjectInputStream objektiin.
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            //muunnetaan ObjectInputStream-objekti merkkijonoksi
            String message = (String) input.readObject();
            //luodaan ObjectOutputStream objekti
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            //kirjoitetaan socket
            output.writeObject("Teht2-Hello" + message);
            //suljetaan resurssit
            input.close();
            output.close();
            socket.close();
            break;
        }
        System.out.println("Shutting down Socket server!!");
        //suljetaan server
        serverSocket.close();
    }

}

