package chompgame;

import server.Server;

/**
 * @author Bedirhan YILDIRIM
 */
public class main {

    public static void main(String[] args) {
        System.out.println("Hello");
        //Bar tahta = new Bar();
        //tahta.eatChocolate(1, 3);
        //tahta.durumRapor();
        
        Server sv = new Server();
        sv.start(2000);
    }
}