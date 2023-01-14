package fi.utu.tech.assignment1;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket s = new Socket("localhost", 12345);
    }

}