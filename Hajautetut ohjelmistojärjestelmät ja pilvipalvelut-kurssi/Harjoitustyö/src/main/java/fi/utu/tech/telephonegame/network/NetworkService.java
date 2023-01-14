package fi.utu.tech.telephonegame.network;
import fi.utu.tech.telephonegame.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;
import java.net.Socket;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.io.OutputStream;

public class NetworkService extends Thread implements Network {
	/*
	 * Do not change the existing class variables
	 * New variables can be added
	 */
	private TransferQueue<Object> inQueue = new LinkedTransferQueue<Object>(); // For messages incoming from network
	private TransferQueue<Serializable> outQueue = new LinkedTransferQueue<Serializable>(); // For messages outgoing to network
	private ArrayList<ObjectOutputStream> oosList  = new ArrayList<>();
	private ArrayList<Socket> sockets  = new ArrayList<>();
	private ArrayList<Integer> ports  = new ArrayList<>();
	ArrayList<Thread> threads = new ArrayList<>();
	


	/*
	 * No need to change the construtor
	 */
	public NetworkService() {
		this.start();
	}



	/**
	 * Creates a server instance and starts listening for new peers on specified port
	 * The port used to listen incoming connections is provided by the template
	 * 
	 * @param serverPort Which port should we start to listen to?
	 * 
	 */
	public synchronized void startListening(int serverPort) {
		System.out.printf("I should start listening for peers at port %d%n", serverPort);
		Thread myThread = new ClientHandler(serverPort, inQueue, outQueue, oosList, ports, sockets);
		threads.add(myThread);
		myThread.start();
		
		
		
		
 
	}

	/**
	 * This method will be called when connecting to a peer (other broken telephone
	 * instance)
	 * The IP address and port will be provided by the template (by the resolver)
	 * 
	 * @param peerIP   The IP address to connect to
	 * @param peerPort The TCP port to connect to
	 */
	public void connect(String peerIP, int peerPort) throws IOException, UnknownHostException {
		Socket socket = new Socket(peerIP, peerPort);
		ports.add(peerPort);
		sockets.add(socket);
		OutputStream outputStream = socket.getOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
		oosList.add(objectOutputStream);
		ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
	}

	

	/**
	 * This method is used to send the message to all connected neighbours (directly connected nodes)
	 * 
	 * @param out The serializable object to be sent to all the connected nodes
	 * @throws IOException
	 * 
	 */
	private void send(Serializable out) {
		// Send the object to all neighbouring nodes
		// TODO
		
		// Toimii vasemmalle
		for (int i =0; i<oosList.size(); i++) {
			try {
				oosList.get(i).writeObject(out);
				oosList.get(i).flush();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
	}


	/*
	 * Don't edit any methods below this comment
	 * Contains methods to move data between Network and 
	 * MessageBroker
	 * You might want to read still...
	 */

	/**
	 * Add an object to the queue for sending
	 * 
	 * @param outMessage The Serializable object to be sent
	 */
	public void postMessage(Serializable outMessage) {
		try {
			outQueue.offer(outMessage, 1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Get reference to the queue containing incoming messages from the network
	 * 
	 * @return Reference to the queue incoming messages queue
	 */
	public TransferQueue<Object> getInputQueue() {
		return this.inQueue;
	}

	/**
	 * Waits for messages from the core application and forwards them to the network
	 */
	public void run() {
		while (true) {
			try {
				send(outQueue.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
