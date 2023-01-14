

package fi.utu.tech.assignment3;

import java.io.*;
import java.net.*;

public class ClientHandler extends Thread {
    Socket serverClient;
    int clientNo;

    ClientHandler(Socket inSocket, int counter) {
        serverClient = inSocket;
        clientNo = counter;
    }

    public void run() {
        try {
            DataInputStream inputStream = new DataInputStream(serverClient.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(serverClient.getOutputStream());
            String message = "";
            String reset = "";
            while (!message.equals("leave")) {
                message = inputStream.readUTF();
                System.out.println("From Client "+ clientNo + ":typed: :" + message);
                outputStream.writeUTF(reset);
                outputStream.flush();
            }
            inputStream.close();
            outputStream.close();
            serverClient.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("Client " + clientNo + " left. ");
        }
    }
}


