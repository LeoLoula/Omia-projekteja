package fi.utu.tech.assignment3;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException {
        try {
            Socket socket = new Socket("127.0.0.1", 34567);
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream=new DataOutputStream(socket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String clientMessage = "";
            String serverMessage;
            while (!clientMessage.equals("leave")) {
                System.out.println("Kirjoita jotain: ");
                clientMessage = reader.readLine();
                outputStream.writeUTF(clientMessage);
                outputStream.flush();
                serverMessage= inputStream.readUTF();
                System.out.println(serverMessage);
            }
            outputStream.close();
            inputStream.close();
            socket.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}


