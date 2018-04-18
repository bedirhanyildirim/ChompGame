package server;

import chompgame.Chocolate;
import chompgame.Game;
import chompgame.Message;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Bedirhan YILDIRIM
 */
//Server listens port here.
class ServerThread extends Thread {

    public void run() {
        while (!Server.sv.isClosed()) {
            try {
                System.out.println("Client Bekleniyor...");
                Socket clientSocket = Server.sv.accept();
                System.out.println("Client Geldi...");
                Client nclient = new Client(clientSocket, Server.countOfClient);
                Server.countOfClient++;
                Server.Clients.add(nclient);
                int count = Server.Clients.size();
                System.out.println("Client sayısı: " + count);
                if (count == 2) {
                    System.out.println("eşleştiriliyor...");
                    if (Server.Clients.get(0).isPaired == false && Server.Clients.get(1).isPaired == false) {
                        Server.competitorClients[0] = Server.Clients.get(0);
                        Server.competitorClients[1] = Server.Clients.get(1);

                        Server.competitorClients[0].competitor = Server.competitorClients[1];
                        Server.competitorClients[0].isPaired = true;

                        Server.competitorClients[1].competitor = Server.competitorClients[0];
                        Server.competitorClients[1].isPaired = true;

                        Message msg0 = new Message(Message.Message_Type.CompetitorConnected);
                        msg0.content = "ready";
                        Server.Send(Server.competitorClients[0], msg0);

                        Message msg1 = new Message(Message.Message_Type.CompetitorConnected);
                        msg1.content = "ready";
                        Server.Send(Server.competitorClients[1], msg1);

                        System.out.println("eşleştirildi");

                        Message turn = new Message(Message.Message_Type.Turn);
                        turn.content = "your";
                        Server.Send(Server.competitorClients[0], turn);

                        Message noTurn = new Message(Message.Message_Type.Turn);
                        noTurn.content = "competitor";
                        Server.Send(Server.competitorClients[1], noTurn);
                    }
                }

            } catch (IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

public class Server {

    public static ServerSocket sv;
    public static int countOfClient = 0;
    public static int listeningPort = 0;
    public static ServerThread serverT;
    public static ArrayList<Client> Clients = new ArrayList<>();
    public static Client[] competitorClients = new Client[2];
    //public static Game newGame = new Game();

    public static void start(int inComingPort) {
        //newGame.start();
        //newGame.rapor();
        //newGame.eatChocolate(10);
        //newGame.eatChocolate(new Chocolate(0, 1, 4, 0));
        //newGame.eatChocolate(new Chocolate(0, 2, 2, 0));
        //newGame.rapor();
        try {
            Server.listeningPort = inComingPort;
            Server.sv = new ServerSocket(Server.listeningPort);
            Server.serverT = new ServerThread();
            Server.serverT.start();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void Send(Client cl, Message msg) {
        try {
            cl.oos.writeObject(msg);
            //newGame.rapor();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Client findClient(int findId) {
        for (int i = 0; i < Clients.size(); i++) {
            if (Clients.get(i).id == findId) {
                return Clients.get(i);
            }
        }

        return null;
    }

    //Print to screen
    public static void Display(String msg) {
        System.out.println(msg);
    }
}
