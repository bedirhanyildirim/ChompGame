/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chompgame;

import stack.Stack;

/**
 *
 * @author bedirhan
 */
public class Game {

    private Bar board;
    private Stack<Chocolate> moves;

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
        } else if (this.board.isChocolatePoisoned(eatThisOne) == true) {
            //this move makes game over
        } else {
            this.board.eatChocolate(eatThisOne);
            this.moves.push(eatThisOne); //add moves
        }
    }
    
    public void eatChocolate(int id) {
        Chocolate eatThis = this.board.findChocolate(id);
        if (this.board.isChocolateEaten(eatThis) == true) {
            //this move made before, you can not
        } else if (this.board.isChocolatePoisoned(eatThis) == true) {
            //this move makes game over
        } else {
            //this.board.eatChocolate(eatThis);
            this.board.eatChocolate(eatThis.getXcoordinate(), eatThis.getYcoordinate());
            this.moves.push(eatThis); //add moves
        }
    }

}
