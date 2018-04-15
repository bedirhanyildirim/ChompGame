/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bedirhan
 */
public class Client {
    //her clientın bir soketi olmalı
    public Socket socket;

    //verileri almak için gerekli nesne
    public ObjectInputStream sInput;
    //verileri göndermek için gerekli nesne
    public ObjectOutputStream sOutput;
    public Listen listenMe;

    public void Start(String ip, int port) {
        try {
            // Client Soket nesnesi
            this.socket = new Socket(ip, port);
            this.Display("Servera bağlandı");
            // input stream
            this.sInput = new ObjectInputStream(this.socket.getInputStream());
            // output stream
            this.sOutput = new ObjectOutputStream(this.socket.getOutputStream());
            this.listenMe = new Listen();
            this.listenMe.start();

        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Display(String msg) {

        System.out.println(msg);

    }

    public void Send(Object message) {
        try {
            this.sOutput.writeObject(message.toString());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    class Listen extends Thread {

    public void run() {

        while (true) {
            try {

                Display(sInput.readObject().toString());
                Send("İyiyim!");
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
}

