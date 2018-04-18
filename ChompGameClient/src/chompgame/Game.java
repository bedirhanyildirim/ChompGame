/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chompgame;

/**
 *
 * @author Bedirhan
 */
public class Game {
    public static Bar board;
    
    public static void start() {
        Game.board = new Bar();
    }

    public static void rapor() {
        Game.board.durumRapor();
    }
    public static void eatChocolate(Chocolate eatThisOne) {
        Game.board.eatChocolate(eatThisOne);
    }
    
    public void eatChocolate(int id) {
        eatChocolate(this.board.findChocolate(id));
    }
    
}
