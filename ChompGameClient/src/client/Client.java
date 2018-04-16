package client;

import chompgame.*;
import ui.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import static client.Client.sInput;

/**
 * @author Bedirhan YILDIRIM
 */
class Listen extends Thread {

    public void run() {
        while (Client.socket.isConnected()) {
            try {
                //Client.Display(sInput.readObject().toString());
                Message read = (Message) (Client.sInput.readObject());
                Client.Display(read.content.toString());
                switch (read.type) {
                    case CompetitorConnected:
                        if(read.content.equals("ready")){
                            ui.WaitCompetitor.wait.setVisible(false);
                            ui.GameInterface.gameInterface.setVisible(true);
                        }
                        break;

                    case GameOver:
                        Client.Display(read.content.toString());
                        break;

                    case Turn:
                        if(read.content.equals("your")){
                            ui.GameInterface.myTurn = true;
                        }else{
                            ui.GameInterface.myTurn = false;
                        }
                        break;
                        
                    case Warning:
                        Client.Display(read.content.toString());
                        break;

                    case Bar:
                        Bar syncBar = (Bar)read.content;
                        ui.GameInterface.board.syncBar(syncBar);
                        ui.GameInterface.board.durumRapor();
                        System.out.println("buradayım !!!!!!");
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

public class Client {

    public static Socket socket;
    public static ObjectInputStream sInput;
    public static ObjectOutputStream sOutput;
    public static Listen listenMe;

    public static void Start(String ip, int port) {
        try {
            Client.socket = new Socket(ip, port);
            Client.Display("Servera bağlandı");
            Client.sInput = new ObjectInputStream(Client.socket.getInputStream());
            Client.sOutput = new ObjectOutputStream(Client.socket.getOutputStream());
            Client.listenMe = new Listen();
            Client.listenMe.start();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void Stop() {
        try {
            if (Client.socket != null) {
                Client.listenMe.stop();
                Client.socket.close();
                Client.sOutput.flush();
                Client.sOutput.close();
                Client.sInput.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void Display(String msg) {
        System.out.println(msg);
    }

    public static void Send(Message msg) {
        try {
            Client.sOutput.writeObject(msg);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
