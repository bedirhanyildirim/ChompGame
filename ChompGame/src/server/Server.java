package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Bedirhan YILDIRIM
 */

public class Server {

    public static ServerSocket sv;
    public static int countOfClient = 0;
    public static int listeningPort = 0;
    public static ServerThread serverT;
    public static ArrayList<Client> Clients = new ArrayList<>();

    public static void start(int inComingPort) {
        try {
            Server.listeningPort = inComingPort;
            Server.sv = new ServerSocket(Server.listeningPort);
            Server.serverT = new ServerThread();
            Server.serverT.start();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Print to screen
    public static void Display(String msg) {
        System.out.println(msg);
    }
}

//Server listens port here.
class ServerThread extends Thread {

    public void run() {
        while (true) {
            try {
                Server.Display("Client Bekleniyor...");
                Socket clientSocket = Server.sv.accept();
                Server.Display("Client Geldi...");
                Client nclient = new Client(clientSocket, Server.countOfClient);
                Server.countOfClient++;
                Server.Clients.add(nclient);

            } catch (IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}