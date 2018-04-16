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
    public Bar board;
    public boolean turn;
    
    public void start() {
        this.board = new Bar();
        turn = false;
    }

    public void rapor() {
        this.board.durumRapor();
    }
    
}
