package fi.utu.tech.assignment2;

import java.io.*;
import java.net.*;

public class Client2 {
    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException {
        ObjectOutputStream outputstream;
        ObjectInputStream inputStream;
        //muodostetaan socket-yhteys palvelimeen
        Socket s = new Socket("localhost", 23456);
        //kirjoitetaan ObjectOutputStreamin avulla
        outputstream = new ObjectOutputStream(s.getOutputStream());
        System.out.println("Sending request to Socket Server");
        outputstream.writeObject("");
        //luetaan palvelimen viesti
        inputStream = new ObjectInputStream(s.getInputStream());
        String message = (String) inputStream.readObject();
        System.out.println("Viesti: " + message);
        inputStream.close();
        outputstream.close();

    }
}



