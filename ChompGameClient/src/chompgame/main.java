package chompgame;

import client.Client;

/**
 * @author Bedirhan YILDIRIM
 */
public class main {

    public static void main(String[] args) {
        System.out.println("Hello");
        
        Client cl = new Client();
        cl.Start("127.0.0.1", 2000);
    }
}
