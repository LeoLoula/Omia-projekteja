package fi.utu.tech.assignment1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("Server3 started and waiting for a client");
        Socket socket = serverSocket.accept();
        System.out.println("Client accepted");
        serverSocket.close();
    }

}
