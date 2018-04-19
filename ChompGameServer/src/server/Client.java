package server;

import chompgame.Chocolate;
import chompgame.Message;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Bedirhan YILDIRIM
 */
public class Client {

    int id;
    Socket clientSocket;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    Listen listenMe;
    public Client competitor;
    public boolean isPaired = false;

    public Client(Socket connectionSocket, int id) {
        this.clientSocket = connectionSocket;
        this.id = id;
        try {
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            ois = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        listenMe = new Listen(this);
        listenMe.start();
    }

    public void sendObject(Message msg) {
        try {
            this.oos.writeObject(msg);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    class Listen extends Thread {
        Client theClient;
        Listen(Client theClient){
            this.theClient = theClient;
        }
        public void run() {
            while (theClient.clientSocket.isConnected()) {
                try {
                    Message read = (Message) (theClient.ois.readObject());
                    switch(read.type){
                        case Disconnect:
                            theClient.oos.close();
                            theClient.ois.close();
                            theClient.clientSocket.close();
                            break;
                            
                        case GameOver:
                            if(read.content.equals("you")){
                                Message go = new Message(Message.Message_Type.GameOver);
                                go.content = "You won!";
                                Server.Send(theClient.competitor, go);
                            }
                            break;
                            
                        case EatChocolate:
                            Chocolate eatThis = (Chocolate)read.content;
                            Message competitorEat = new Message(Message.Message_Type.EatChocolate);
                            competitorEat.content = (Chocolate) eatThis;
                            Server.Send(theClient.competitor, competitorEat);
                            
                            Server.Display(theClient.id + " ate the chocolate: " + eatThis.getXcoordinate() + " : " + eatThis.getYcoordinate());
                            
                            Message turn = new Message(Message.Message_Type.Turn);
                            turn.content = "your";
                            Server.Send(theClient.competitor, turn);

                            Message noTurn = new Message(Message.Message_Type.Turn);
                            noTurn.content = "competitor";
                            Server.Send(theClient, noTurn);
                            break;
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
