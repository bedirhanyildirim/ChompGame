package server;

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

    public Client(Socket connectionSocket, int id){
        this.clientSocket = connectionSocket;
        this.id = id;
        try {
            OutputStream os = clientSocket.getOutputStream();
            oos = new ObjectOutputStream(os);
            InputStream is = clientSocket.getInputStream();
            ois = new ObjectInputStream(is);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        listenMe = new Listen();
        listenMe.start();
    }

    public void sendObject(Object message) {
        try {
            this.oos.writeObject(message.toString());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    class Listen extends Thread {

        public void run() {
            while (true) {
                try {

                    sendObject("Naber?");
                    Server.Display(id + " -> " + ois.readObject().toString());

                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
