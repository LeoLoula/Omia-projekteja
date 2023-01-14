package fi.utu.tech.telephonegame.network;
import java.io.*;
import java.net.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.TransferQueue;


import fi.utu.tech.telephonegame.Message;
import fi.utu.tech.telephonegame.MessageBroker;


public class ClientHandler extends Thread {
    Socket socket;
    int serverPort;
    TransferQueue<Object> inQueue;
    TransferQueue<Serializable> outQueue;
    ArrayList<ObjectOutputStream> oosList;
    
    private ArrayList<Integer> ports  = new ArrayList<>();
    ArrayList<Socket> sockets = new ArrayList<>();
    

    public ClientHandler(int serverPort, TransferQueue inQueue, TransferQueue outQueue, ArrayList<ObjectOutputStream> oosList, ArrayList<Integer> ports, ArrayList<Socket> sockets) {
        this.serverPort = serverPort;
        this.inQueue = inQueue;
        this.outQueue = outQueue;
        this.oosList = oosList;
        this.ports = ports;
        this.sockets = sockets;
    }


    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(serverPort);){
                System.out.println("This is my port: " +serverPort);
                socket = serverSocket.accept();
                System.out.println(socket);
                ports.add(serverPort+1);
                sockets.add(socket);
                System.out.println("New client connected");
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                oosList.add(objectOutputStream);
                System.out.println("Odottaa viestiä");
                System.out.println(oosList);
            while(true) {
                try {
                    inQueue.add(objectInputStream.readObject());
                    
                    //Message message = (Message) inQueue.peek();
                    //System.out.println(message);
                    
                } catch (EOFException e) {
                    System.out.println("Viesti oli tyhjä");
                }
                try {
                    if (objectInputStream.readObject() != null) {
                        //outQueue.add((Serializable) objectInputStream.readObject());
                        
                    }
                }
                finally {
                }
            }
        } catch (IOException ex) {
            System.out.println("Asiakas: " + socket +  " lähti");
            run();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
