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
public class Message implements java.io.Serializable {

    public static enum Message_Type {
        Disconnect, CompetitorConnected, GameOver, Turn, EatChocolate, Warning
    }
    public Message_Type type;
    public Object content;

    public Message(Message_Type t) {
        this.type = t;
    }
}
