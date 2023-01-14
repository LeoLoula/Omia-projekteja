package fi.utu.tech.assignment6;


import java.net.Socket;

public class ClientHandler extends Thread {

    Socket cs;
    Hub hub;

    // Pieni vinkki
    public ClientHandler(Socket cs, Hub h) {
        this.hub = h;
        this.cs = cs;
    }
    // TODO: Käytä edellisen tehtävän ratkaisua pohjana


}
