/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chompgame;

import server.Client;
import stack.Stack;

/**
 *
 * @author bedirhan
 */
public class Game {

    public Bar board;
    public Stack<Chocolate> moves;

    public Game() {
        
    }

    public void start() {
        this.board = new Bar();
        this.moves = new Stack();
    }

    public void rapor() {
        this.board.durumRapor();
    }

    public void eatChocolate(Chocolate eatThisOne) {
        if (this.board.isChocolateEaten(eatThisOne) == true) {
            //this move made before, you can not
            Message warning = new Message(Message.Message_Type.Warning);
            warning.content = "You can not make this move. This move made before!";
            Client targetCl = server.Server.findClient(eatThisOne.user);
            server.Server.Send(targetCl, warning);
            
        } else if (this.board.isChocolatePoisoned(eatThisOne) == true) {
            //this move makes game over
            Message warning = new Message(Message.Message_Type.Warning);
            warning.content = "You ate the poisened chocolate! You lost!";
            Client targetCl = server.Server.findClient(eatThisOne.user);
            server.Server.Send(targetCl, warning);
            this.moves.push(eatThisOne); //add moves
            
            Message gameOver = new Message(Message.Message_Type.GameOver);
            gameOver.content = "Loser!";
            Client loserCl = server.Server.findClient(eatThisOne.user);
            server.Server.Send(loserCl, gameOver);
        } else {
            this.board.eatChocolate(eatThisOne);
            this.moves.push(eatThisOne); //add moves
        }
    }
    
    
    
    public void eatChocolate(int id) {
        eatChocolate(this.board.findChocolate(id));
    }

}
