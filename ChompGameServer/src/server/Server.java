package server;

import chompgame.Chocolate;
import chompgame.Game;
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

    private ServerSocket sv;
    public int countOfClient = 0;
    private int listeningPort = 0;
    private ServerThread serverT;
    private ArrayList<Client> Clients = new ArrayList<>();
    private Game newGame = new Game();

    public void start(int inComingPort) {
        newGame.start();
        newGame.rapor();
        //newGame.eatChocolate(10);
        newGame.eatChocolate(new Chocolate(0, 1, 4, 0));
        newGame.eatChocolate(new Chocolate(0, 2, 2, 0));
        newGame.rapor();
        try {
            this.listeningPort = inComingPort;
            this.sv = new ServerSocket(this.listeningPort);
            this.serverT = new ServerThread();
            this.serverT.start();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Print to screen
    public void Display(String msg) {
        System.out.println(msg);
    }

    //Server listens port here.
    class ServerThread extends Thread {
        public void run() {
            while (true) {
                try {
                    System.out.println("Client Bekleniyor...");
                    Socket clientSocket = sv.accept();
                    System.out.println("Client Geldi...");
                    Client nclient = new Client(clientSocket, countOfClient);
                    countOfClient++;
                    Clients.add(nclient);

                } catch (IOException ex) {
                    Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
