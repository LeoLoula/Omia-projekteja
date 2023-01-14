package fi.utu.tech.telephonegame.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Do not edit this file. Älä muokkaa tätä tiedostoa.
 * 
 * This class is used by the template to determine IP addresses and ports
 */

public class Resolver extends Thread {

	private DatagramSocket serverSocket;
	private byte[] buf = new byte[256];
	private int port;
	private boolean serverMode = false;
	private NetworkType netType;
	private AtomicInteger serverPort;
	private String serverIPAddress;
	private ArrayDeque<Node> ipTable = new ArrayDeque<Node>();

	public enum NetworkType {LOCALHOST, PUBLIC};

	public Resolver(int resolverPort, NetworkType netType, int rootServerPort, String rootIPAddress) {
		this.port = resolverPort;
		this.netType = netType;
		this.serverPort = new AtomicInteger(rootServerPort);
		this.serverIPAddress = rootIPAddress;
	}

	public Resolver(NetworkType netType, int rootServerPort, String rootIPAddress) {
		this(4445, netType, rootServerPort, rootIPAddress);
	}

	public void startResolverServer() {
		this.serverMode = true;
		try {
			this.serverSocket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		this.start();
	}

	public PeerConfiguration resolve() throws UnknownHostException, NumberFormatException {
		String[] addresses = { "", "", "" };
		if (!serverMode) {
			try {
				InetAddress address = InetAddress.getByName("255.255.255.255");
				DatagramSocket socket = new DatagramSocket();
				socket.setBroadcast(true);

				String broadcastMessage = "getAddress";
				byte[] buffer = broadcastMessage.getBytes();

				System.out.printf("Sending peer discovery broadcast message using port %d%n", port);

				DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
				socket.send(packet);
				packet = new DatagramPacket(buf, buf.length);
				socket.setSoTimeout(2000);
				socket.receive(packet);
				addresses = (new String(packet.getData(), 0, packet.getLength())).split("/");
				socket.close();
			} catch (SocketException | SocketTimeoutException ex) {
				throw new UnknownHostException();
			}
			catch (IOException e) {
				e.printStackTrace();
			}

		}

		PeerConfiguration pc = new PeerConfiguration(Integer.parseInt(addresses[0]), addresses[1], Integer.parseInt(addresses[2]));
		return pc;
	}

	public void run() {
		System.out.printf("Starting resolver, waiting for peer discovery messages on port %d%n", this.port);
		while (true) {
			try {
				ipTable.add(new Node(this.serverIPAddress, Integer.valueOf(0)));
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				serverSocket.receive(packet);

				InetAddress address = packet.getAddress();
				int port = packet.getPort();
				if (new String(packet.getData(), 0, packet.getLength()).equals("getAddress")) {
					String broadcastMessage = "//";
					switch (netType) {
					case LOCALHOST:
						String clientPortString = serverPort.toString();
						serverPort.addAndGet(1);
						String serverPortString = serverPort.toString();
						broadcastMessage = serverPortString + "/localhost/" + clientPortString;
						System.out.printf("Peer discovery message received, sending information for peer %s:%s%n", "localhost", clientPortString);
						break;
					case PUBLIC:
						Node node = ipTable.peekFirst();
						String clientIP = node.ip;
						node.clients += 1;
						if (node.clients > 1) {
							ipTable.removeFirst();
						}
						ipTable.addLast(new Node(address.getHostAddress(), 0));
						broadcastMessage = serverPort.toString() + "/" + clientIP + "/" + serverPort.toString();
						System.out.printf("Peer discovery message received, sending information for peer %s:%s%n", clientIP, serverPort.toString());
						break;
					}

					byte[] buffer = broadcastMessage.getBytes();
					packet = new DatagramPacket(buffer, buffer.length, address, port);
					serverSocket.send(packet);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * A class representing peer configuration data
	 */
	public class PeerConfiguration {
		public final int listeningPort;
		public final String peerAddr;
		public final int peerPort;

		/**
		 * Create a new peer configuration object
		 * 
		 * @param listeningPort The port where the peer should start listening for new peers
		 * @param peerAddr The IP address of a peer that our peer should connect itself to
		 * @param peerPort The port of a peer that our peer should connect itself to
		 */
		PeerConfiguration(int listeningPort, String peerAddr, int peerPort) {
			this.listeningPort = listeningPort;
			this.peerAddr = peerAddr;
			this.peerPort = peerPort;

		}

	}

	private class Node {
		public String ip;
		public int clients;

		public Node(String ip, Integer clients) {
			this.ip = ip;
			this.clients = clients;
		}

		public String toString() {
			return "IP: " + ip + " clients: " + clients;
		}
	}
}
