/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import chompgame.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Bedirhan
 */



public class GameInterface extends javax.swing.JFrame implements ActionListener  {
    
    class gameThread extends Thread{

        public void run(){
            while(true){
                try {
                    if(client.Client.whosTurn == true){
                        turnText.setText("Your Turn!");
                        myTurn = true;
                    }else{
                        turnText.setText("Your Competition's Turn!");
                        myTurn = false;
                    }
                    if(Game.isGameOver == true && Game.amIWinner == 1){
                        enableButtons(false);
                        turnText.setText("You won!");
                    }else if(Game.isGameOver == true && Game.amIWinner == 2){
                        turnText.setText("You Lost!");
                    }
                    sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GameInterface.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    /**
     * Creates new form GameInterface
     */
    public static GameInterface gameInterface;
    //public static Bar board;
    public static javax.swing.JButton[][] board;
    public static boolean myTurn;
    public static gameThread gameT;
    
    public GameInterface() {
        initComponents();
        gameInterface = this;
        myTurn = false;
        this.setLocation(250, 250);
        this.setResizable(false);
        client.Client.newGame.board = new Bar();
        client.Client.newGame.board.durumRapor();
        gameT = new gameThread();
        gameT.start();
        if(client.Client.whosTurn){
            turnText.setText("Your Turn!");
        }else{
            turnText.setText("Your Competition's Turn!");
        }
        
        board = new javax.swing.JButton[4][7];
        board[0][0] = choco00;board[0][1] = choco01;board[0][2] = choco02;board[0][3] = choco03;board[0][4] = choco04;board[0][5] = choco05;board[0][6] = choco06;
        board[1][0] = choco10;board[1][1] = choco11;board[1][2] = choco12;board[1][3] = choco13;board[1][4] = choco14;board[1][5] = choco15;board[1][6] = choco16;
        board[2][0] = choco20;board[2][1] = choco21;board[2][2] = choco22;board[2][3] = choco23;board[2][4] = choco24;board[2][5] = choco25;board[2][6] = choco26;
        board[3][0] = choco30;board[3][1] = choco31;board[3][2] = choco32;board[3][3] = choco33;board[3][4] = choco34;board[3][5] = choco35;board[3][6] = choco36;
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int exitGame = JOptionPane.showConfirmDialog(gameInterface, "Leaving game..", "Are you sure to leave game?", JOptionPane.YES_NO_OPTION);
                if (exitGame == 0) {
                    client.Client.Stop();//DISCONNECT
                    System.exit(1);
                    gameInterface.dispose();
                    Message dsgMsg = new Message(Message.Message_Type.Disconnect);
                    dsgMsg.content = client.Client.socket;
                    client.Client.Send(dsgMsg);
                }else if (exitGame == 1) {//If clicked Cancel
                    System.out.println("Canceled.");
                }
            }
        });
        enableButtons(false);
        focusButtons(false);
        enableButtons(true);
        
        
        //Naming all buttons for actionperformed actions
        choco00.setActionCommand("00"); choco00.addActionListener(this);
        choco01.setActionCommand("01"); choco01.addActionListener(this);
        choco02.setActionCommand("02"); choco02.addActionListener(this);
        choco03.setActionCommand("03"); choco03.addActionListener(this);
        choco04.setActionCommand("04"); choco04.addActionListener(this);
        choco05.setActionCommand("05"); choco05.addActionListener(this);
        choco06.setActionCommand("06"); choco06.addActionListener(this);
        choco10.setActionCommand("10"); choco10.addActionListener(this);
        choco11.setActionCommand("11"); choco11.addActionListener(this);
        choco12.setActionCommand("12"); choco12.addActionListener(this);
        choco13.setActionCommand("13"); choco13.addActionListener(this);
        choco14.setActionCommand("14"); choco14.addActionListener(this);
        choco15.setActionCommand("15"); choco15.addActionListener(this);
        choco16.setActionCommand("16"); choco16.addActionListener(this);
        choco20.setActionCommand("20"); choco20.addActionListener(this);
        choco21.setActionCommand("21"); choco21.addActionListener(this);
        choco22.setActionCommand("22"); choco22.addActionListener(this);
        choco23.setActionCommand("23"); choco23.addActionListener(this);
        choco24.setActionCommand("24"); choco24.addActionListener(this);
        choco25.setActionCommand("25"); choco25.addActionListener(this);
        choco26.setActionCommand("26"); choco26.addActionListener(this);
        choco30.setActionCommand("30"); choco30.addActionListener(this);
        choco31.setActionCommand("31"); choco31.addActionListener(this);
        choco32.setActionCommand("32"); choco32.addActionListener(this);
        choco33.setActionCommand("33"); choco33.addActionListener(this);
        choco34.setActionCommand("34"); choco34.addActionListener(this);
        choco35.setActionCommand("35"); choco35.addActionListener(this);
        choco36.setActionCommand("36"); choco36.addActionListener(this);
        
    }
    
    public void syncBar(){
        for (int i = 0; i < GameInterface.board.length; i++) {
            for (int j = 0; j < GameInterface.board[0].length; j++) {
                if(client.Client.newGame.board.table[i][j].isEaten() == true){
                    GameInterface.board[i][j].setEnabled(false);
                }
            }
        }
    }
    
    public static void setTurn(boolean b){
        //turnText.setText(s);
        myTurn = b;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();
        if(myTurn){
            turnText.setText("Your Turn!");
        }else{
            turnText.setText("Your Competition's Turn!");
        }
            Message eatThis = new Message(Message.Message_Type.EatChocolate);
            Chocolate letsEat = new Chocolate(100,100,100,100);
            switch (action) {
                case "00":
                    System.out.println("choco00 tıkladın!!!");
                    if (client.Client.whosTurn == false) {
                        JOptionPane.showMessageDialog(this, "Wait your turn!", "It is not your turn..", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    letsEat = client.Client.newGame.board.table[0][0];
                    eatThis.content = (Chocolate)letsEat;
                    client.Client.Send(eatThis);
                    letsEat.setMe();
                    client.Client.newGame.eatChocolate(letsEat);
                    
                    choco00.setEnabled(false);
                    break;

                case "01":
                    System.out.println("choco01 tıkladın!!!");
                    if (client.Client.whosTurn == false) {
                        JOptionPane.showMessageDialog(this, "Wait your turn!", "It is not your turn..", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    System.out.println(client.Client.newGame.board.table[0][1].getChocolateID());
                    letsEat = client.Client.newGame.board.table[0][1];
                    System.out.println(letsEat.getChocolateID());
                    eatThis.content = (Chocolate)letsEat;
                    client.Client.Send(eatThis);
                    letsEat.setMe();
                    client.Client.newGame.eatChocolate(letsEat);
                    choco01.setEnabled(false);
                    break;

                case "02":
                    System.out.println("choco02 tıkladın!!!");
                    if (client.Client.whosTurn == false) {
                        JOptionPane.showMessageDialog(this, "Wait your turn!", "It is not your turn..", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    System.out.println(client.Client.newGame.board.table[0][2].getChocolateID());
                    letsEat = client.Client.newGame.board.table[0][2];
                    System.out.println(letsEat.getChocolateID());
                    eatThis.content = (Chocolate)letsEat;
                    client.Client.Send(eatThis);
                    letsEat.setMe();
                    client.Client.newGame.eatChocolate(letsEat);
                    choco02.setEnabled(false);
                    break;

                case "03":
                    System.out.println("choco03 tıkladın!!!");
                    if (client.Client.whosTurn == false) {
                        JOptionPane.showMessageDialog(this, "Wait your turn!", "It is not your turn..", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    letsEat = client.Client.newGame.board.table[0][3];
                    eatThis.content = (Chocolate)letsEat;
                    client.Client.Send(eatThis);
                    letsEat.setMe();
                    client.Client.newGame.eatChocolate(letsEat);
                    choco03.setEnabled(false);
                    break;

                case "04":
                    System.out.println("choco04 tıkladın!!!");
                    if (client.Client.whosTurn == false) {
                        JOptionPane.showMessageDialog(this, "Wait your turn!", "It is not your turn..", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    letsEat = client.Client.newGame.board.table[0][4];
                    eatThis.content = (Chocolate)letsEat;
                    client.Client.Send(eatThis);
                    letsEat.setMe();
                    client.Client.newGame.eatChocolate(letsEat);
                    choco04.setEnabled(false);
                    break;

                case "05":
                    System.out.println("choco05 tıkladın!!!");
                    if (client.Client.whosTurn == false) {
                        JOptionPane.showMessageDialog(this, "Wait your turn!", "It is not your turn..", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    letsEat = client.Client.newGame.board.table[0][5];
                    eatThis.content = (Chocolate)letsEat;
                    client.Client.Send(eatThis);
                    letsEat.setMe();
                    client.Client.newGame.eatChocolate(letsEat);
                    choco05.setEnabled(false);
                    break;

                case "06":
                    System.out.println("choco06 tıkladın!!!");
                    if (client.Client.whosTurn == false) {
                        JOptionPane.showMessageDialog(this, "Wait your turn!", "It is not your turn..", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    letsEat = client.Client.newGame.board.table[0][6];
                    eatThis.content = (Chocolate)letsEat;
                    client.Client.Send(eatThis);
                    letsEat.setMe();
                    client.Client.newGame.eatChocolate(letsEat);
                    choco06.setEnabled(false);
                    break;

                case "10":
                    System.out.println("choco10 tıkladın!!!");
                    if (client.Client.whosTurn == false) {
                        JOptionPane.showMessageDialog(this, "Wait your turn!", "It is not your turn..", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    letsEat = client.Client.newGame.board.table[1][0];
                    eatThis.content = (Chocolate)letsEat;
                    client.Client.Send(eatThis);
                    letsEat.setMe();
                    client.Client.newGame.eatChocolate(letsEat);
                    choco10.setEnabled(false);
                    break;

                case "11":
                    System.out.println("choco11 tıkladın!!!");
                    if (client.Client.whosTurn == false) {
                        JOptionPane.showMessageDialog(this, "Wait your turn!", "It is not your turn..", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    letsEat = client.Client.newGame.board.table[1][1];
                    eatThis.content = (Chocolate)letsEat;
                    client.Client.Send(eatThis);
                    letsEat.setMe();
                    client.Client.newGame.eatChocolate(letsEat);
                    choco11.setEnabled(false);
                    break;

                case "12":
                    System.out.println("choco12 tıkladın!!!");
                    if (client.Client.whosTurn == false) {
                        JOptionPane.showMessageDialog(this, "Wait your turn!", "It is not your turn..", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    letsEat = client.Client.newGame.board.table[1][2];
                    eatThis.content = (Chocolate)letsEat;
                    client.Client.Send(eatThis);
                    letsEat.setMe();
                    client.Client.newGame.eatChocolate(letsEat);
                    choco12.setEnabled(false);
                    break;

                case "13":
                    System.out.println("choco13 tıkladın!!!");
                    if (client.Client.whosTurn == false) {
                        JOptionPane.showMessageDialog(this, "Wait your turn!", "It is not your turn..", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    letsEat = client.Client.newGame.board.table[1][3];
                    eatThis.content = (Chocolate)letsEat;
                    client.Client.Send(eatThis);
                    letsEat.setMe();
                    client.Client.newGame.eatChocolate(letsEat);
                    choco13.setEnabled(false);
                    break;

                case "14":
                    System.out.println("choco14 tıkladın!!!");
                    if (client.Client.whosTurn == false) {
                        JOptionPane.showMessageDialog(this, "Wait your turn!", "It is not your turn..", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    letsEat = client.Client.newGame.board.table[1][4];
                    eatThis.content = (Chocolate)letsEat;
                    client.Client.Send(eatThis);
                    letsEat.setMe();
                    client.Client.newGame.eatChocolate(letsEat);
                    choco14.setEnabled(false);
                    break;

                case "15":
                    System.out.println("choco15 tıkladın!!!");
                    if (client.Client.whosTurn == false) {
                        JOptionPane.showMessageDialog(this, "Wait your turn!", "It is not your turn..", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    letsEat = client.Client.newGame.board.table[1][5];
                    eatThis.content = (Chocolate)letsEat;
                    client.Client.Send(eatThis);
                    letsEat.setMe();
                    client.Client.newGame.eatChocolate(letsEat);
                    choco15.setEnabled(false);
                    break;

                case "16":
                    System.out.println("choco16 tıkladın!!!");
                    if (client.Client.whosTurn == false) {
                        JOptionPane.showMessageDialog(this, "Wait your turn!", "It is not your turn..", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    letsEat = client.Client.newGame.board.table[1][6];
                    eatThis.content = (Chocolate)letsEat;
                    client.Client.Send(eatThis);
                    letsEat.setMe();
                    client.Client.newGame.eatChocolate(letsEat);
                    choco16.setEnabled(false);
                    break;

                case "20":
                    System.out.println("choco20 tıkladın!!!");
                    if (client.Client.whosTurn == false) {
                        JOptionPane.showMessageDialog(this, "Wait your turn!", "It is not your turn..", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    letsEat = client.Client.newGame.board.table[2][0];
                    eatThis.content = (Chocolate)letsEat;
                    client.Client.Send(eatThis);
                    letsEat.setMe();
                    client.Client.newGame.eatChocolate(letsEat);
                    choco20.setEnabled(false);
                    break;

                case "21":
                    System.out.println("choco21 tıkladın!!!");
                    if (client.Client.whosTurn == false) {
                        JOptionPane.showMessageDialog(this, "Wait your turn!", "It is not your turn..", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    letsEat = client.Client.newGame.board.table[2][1];
                    eatThis.content = (Chocolate)letsEat;
                    client.Client.Send(eatThis);
                    letsEat.setMe();
                    client.Client.newGame.eatChocolate(letsEat);
                    choco21.setEnabled(false);
                    break;

                case "22":
                    System.out.println("choco22 tıkladın!!!");
                    if (client.Client.whosTurn == false) {
                        JOptionPane.showMessageDialog(this, "Wait your turn!", "It is not your turn..", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    letsEat = client.Client.newGame.board.table[2][2];
                    eatThis.content = (Chocolate)letsEat;
                    client.Client.Send(eatThis);
                    letsEat.setMe();
                    client.Client.newGame.eatChocolate(letsEat);
                    choco22.setEnabled(false);
                    break;

                case "23":
                    System.out.println("choco23 tıkladın!!!");
                    if (client.Client.whosTurn == false) {
                        JOptionPane.showMessageDialog(this, "Wait your turn!", "It is not your turn..", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    letsEat = client.Client.newGame.board.table[2][3];
                    eatThis.content = (Chocolate)letsEat;
                    client.Client.Send(eatThis);
                    letsEat.setMe();
                    client.Client.newGame.eatChocolate(letsEat);
                    choco23.setEnabled(false);
                    break;

                case "24":
                    System.out.println("choco24 tıkladın!!!");
                    if (client.Client.whosTurn == false) {
                        JOptionPane.showMessageDialog(this, "Wait your turn!", "It is not your turn..", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    letsEat = client.Client.newGame.board.table[2][4];
                    eatThis.content = (Chocolate)letsEat;
                    client.Client.Send(eatThis);
                    letsEat.setMe();
                    client.Client.newGame.eatChocolate(letsEat);
                    choco24.setEnabled(false);
                    break;

                case "25":
                    System.out.println("choco25 tıkladın!!!");
                    if (client.Client.whosTurn == false) {
                        JOptionPane.showMessageDialog(this, "Wait your turn!", "It is not your turn..", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    letsEat = client.Client.newGame.board.table[2][5];
                    eatThis.content = (Chocolate)letsEat;
                    client.Client.Send(eatThis);
                    letsEat.setMe();
                    client.Client.newGame.eatChocolate(letsEat);
                    choco25.setEnabled(false);
                    break;

                case "26":
                    System.out.println("choco26 tıkladın!!!");
                    if (client.Client.whosTurn == false) {
                        JOptionPane.showMessageDialog(this, "Wait your turn!", "It is not your turn..", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    letsEat = client.Client.newGame.board.table[2][6];
                    eatThis.content = (Chocolate)letsEat;
                    client.Client.Send(eatThis);
                    letsEat.setMe();
                    client.Client.newGame.eatChocolate(letsEat);
                    choco26.setEnabled(false);
                    break;

                case "30":
                    System.out.println("choco30 tıkladın!!!");
                    if (client.Client.whosTurn == false) {
                        JOptionPane.showMessageDialog(this, "Wait your turn!", "It is not your turn..", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    //letsEat = client.Client.newGame.board.table[3][0];
                    //eatThis.content = (Chocolate)letsEat;
                    //client.Client.Send(eatThis);
                    //letsEat.setMe();
                    //client.Client.newGame.eatChocolate(letsEat);
                    
                    JOptionPane.showMessageDialog(this, "Game Over!", "You Lost!", JOptionPane.ERROR_MESSAGE);
                    Message go = new Message(Message.Message_Type.GameOver);
                    go.content = "you";
                    client.Client.Send(go);
                    enableButtons(false);
                    Game.isGameOver = true;
                    Game.amIWinner = 2;
                    //choco30.setEnabled(false);
                    break;

                case "31":
                    System.out.println("choco31 tıkladın!!!");
                    if (client.Client.whosTurn == false) {
                        JOptionPane.showMessageDialog(this, "Wait your turn!", "It is not your turn..", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    letsEat = client.Client.newGame.board.table[3][1];
                    eatThis.content = (Chocolate)letsEat;
                    client.Client.Send(eatThis);
                    letsEat.setMe();
                    client.Client.newGame.eatChocolate(letsEat);
                    choco31.setEnabled(false);
                    break;

                case "32":
                    System.out.println("choco32 tıkladın!!!");
                    if (client.Client.whosTurn == false) {
                        JOptionPane.showMessageDialog(this, "Wait your turn!", "It is not your turn..", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    letsEat = client.Client.newGame.board.table[3][2];
                    eatThis.content = (Chocolate)letsEat;
                    client.Client.Send(eatThis);
                    letsEat.setMe();
                    client.Client.newGame.eatChocolate(letsEat);
                    choco32.setEnabled(false);
                    break;

                case "33":
                    System.out.println("choco33 tıkladın!!!");
                    if (client.Client.whosTurn == false) {
                        JOptionPane.showMessageDialog(this, "Wait your turn!", "It is not your turn..", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    letsEat = client.Client.newGame.board.table[3][3];
                    eatThis.content = (Chocolate)letsEat;
                    client.Client.Send(eatThis);
                    letsEat.setMe();
                    client.Client.newGame.eatChocolate(letsEat);
                    choco33.setEnabled(false);
                    break;

                case "34":
                    System.out.println("choco34 tıkladın!!!");
                    if (client.Client.whosTurn == false) {
                        JOptionPane.showMessageDialog(this, "Wait your turn!", "It is not your turn..", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    letsEat = client.Client.newGame.board.table[3][4];
                    eatThis.content = (Chocolate)letsEat;
                    client.Client.Send(eatThis);
                    letsEat.setMe();
                    client.Client.newGame.eatChocolate(letsEat);
                    choco34.setEnabled(false);
                    break;

                case "35":
                    System.out.println("choco35 tıkladın!!!");
                    if (client.Client.whosTurn == false) {
                        JOptionPane.showMessageDialog(this, "Wait your turn!", "It is not your turn..", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    letsEat = client.Client.newGame.board.table[3][5];
                    eatThis.content = (Chocolate)letsEat;
                    client.Client.Send(eatThis);
                    letsEat.setMe();
                    client.Client.newGame.eatChocolate(letsEat);
                    choco35.setEnabled(false);
                    break;

                case "36":
                    System.out.println("choco36 tıkladın!!!");
                    if (client.Client.whosTurn == false) {
                        JOptionPane.showMessageDialog(this, "Wait your turn!", "It is not your turn..", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    letsEat = client.Client.newGame.board.table[3][6];
                    eatThis.content = (Chocolate)letsEat;
                    client.Client.Send(eatThis);
                    letsEat.setMe();
                    client.Client.newGame.eatChocolate(letsEat);
                    choco36.setEnabled(false);
                    break;
            }
        
    }
    
    public void rapor() {
        Game.board.durumRapor();
    }
    
    public void enableButtons(boolean b){
        choco00.setEnabled(b);choco01.setEnabled(b);choco02.setEnabled(b);choco03.setEnabled(b);choco04.setEnabled(b);choco05.setEnabled(b);choco06.setEnabled(b);
        choco10.setEnabled(b);choco11.setEnabled(b);choco12.setEnabled(b);choco13.setEnabled(b);choco14.setEnabled(b);choco15.setEnabled(b);choco16.setEnabled(b);
        choco20.setEnabled(b);choco21.setEnabled(b);choco22.setEnabled(b);choco23.setEnabled(b);choco24.setEnabled(b);choco25.setEnabled(b);choco26.setEnabled(b);
        choco30.setEnabled(b);choco31.setEnabled(b);choco32.setEnabled(b);choco33.setEnabled(b);choco34.setEnabled(b);choco35.setEnabled(b);choco36.setEnabled(b);
    }
    
    public void focusButtons(boolean b){
        choco00.setFocusable(b);choco01.setFocusable(b);choco02.setFocusable(b);choco03.setFocusable(b);choco04.setFocusable(b);choco05.setFocusable(b);choco06.setFocusable(b);
        choco10.setFocusable(b);choco11.setFocusable(b);choco12.setFocusable(b);choco13.setFocusable(b);choco14.setFocusable(b);choco15.setFocusable(b);choco16.setFocusable(b);
        choco20.setFocusable(b);choco21.setFocusable(b);choco22.setFocusable(b);choco23.setFocusable(b);choco24.setFocusable(b);choco25.setFocusable(b);choco26.setFocusable(b);
        choco30.setFocusable(b);choco31.setFocusable(b);choco32.setFocusable(b);choco33.setFocusable(b);choco34.setFocusable(b);choco35.setFocusable(b);choco36.setFocusable(b);
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        title = new javax.swing.JLabel();
        choco00 = new javax.swing.JButton();
        choco01 = new javax.swing.JButton();
        choco02 = new javax.swing.JButton();
        choco03 = new javax.swing.JButton();
        choco04 = new javax.swing.JButton();
        choco05 = new javax.swing.JButton();
        choco06 = new javax.swing.JButton();
        choco10 = new javax.swing.JButton();
        choco11 = new javax.swing.JButton();
        choco12 = new javax.swing.JButton();
        choco13 = new javax.swing.JButton();
        choco14 = new javax.swing.JButton();
        choco15 = new javax.swing.JButton();
        choco16 = new javax.swing.JButton();
        choco20 = new javax.swing.JButton();
        choco21 = new javax.swing.JButton();
        choco22 = new javax.swing.JButton();
        choco23 = new javax.swing.JButton();
        choco24 = new javax.swing.JButton();
        choco25 = new javax.swing.JButton();
        choco26 = new javax.swing.JButton();
        choco30 = new javax.swing.JButton();
        choco31 = new javax.swing.JButton();
        choco32 = new javax.swing.JButton();
        choco33 = new javax.swing.JButton();
        choco34 = new javax.swing.JButton();
        choco35 = new javax.swing.JButton();
        choco36 = new javax.swing.JButton();
        turnText = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        title.setText("Welcome!");

        choco00.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single.png"))); // NOI18N
        choco00.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        choco00.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single-eaten.png"))); // NOI18N
        choco00.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                choco00MouseClicked(evt);
            }
        });
        choco00.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                choco00ActionPerformed(evt);
            }
        });

        choco01.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single.png"))); // NOI18N
        choco01.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        choco01.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single-eaten.png"))); // NOI18N
        choco01.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                choco01MouseClicked(evt);
            }
        });

        choco02.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single.png"))); // NOI18N
        choco02.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        choco02.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single-eaten.png"))); // NOI18N
        choco02.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                choco02MouseClicked(evt);
            }
        });

        choco03.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single.png"))); // NOI18N
        choco03.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        choco03.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single-eaten.png"))); // NOI18N
        choco03.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                choco03MouseClicked(evt);
            }
        });

        choco04.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single.png"))); // NOI18N
        choco04.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        choco04.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single-eaten.png"))); // NOI18N
        choco04.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                choco04MouseClicked(evt);
            }
        });

        choco05.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single.png"))); // NOI18N
        choco05.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        choco05.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single-eaten.png"))); // NOI18N
        choco05.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                choco05MouseClicked(evt);
            }
        });

        choco06.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single.png"))); // NOI18N
        choco06.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        choco06.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single-eaten.png"))); // NOI18N
        choco06.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                choco06MouseClicked(evt);
            }
        });

        choco10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single.png"))); // NOI18N
        choco10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        choco10.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single-eaten.png"))); // NOI18N
        choco10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                choco10MouseClicked(evt);
            }
        });

        choco11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single.png"))); // NOI18N
        choco11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        choco11.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single-eaten.png"))); // NOI18N
        choco11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                choco11MouseClicked(evt);
            }
        });

        choco12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single.png"))); // NOI18N
        choco12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        choco12.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single-eaten.png"))); // NOI18N
        choco12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                choco12MouseClicked(evt);
            }
        });

        choco13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single.png"))); // NOI18N
        choco13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        choco13.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single-eaten.png"))); // NOI18N
        choco13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                choco13MouseClicked(evt);
            }
        });

        choco14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single.png"))); // NOI18N
        choco14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        choco14.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single-eaten.png"))); // NOI18N
        choco14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                choco14MouseClicked(evt);
            }
        });

        choco15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single.png"))); // NOI18N
        choco15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        choco15.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single-eaten.png"))); // NOI18N
        choco15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                choco15MouseClicked(evt);
            }
        });

        choco16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single.png"))); // NOI18N
        choco16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        choco16.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single-eaten.png"))); // NOI18N
        choco16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                choco16MouseClicked(evt);
            }
        });

        choco20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single.png"))); // NOI18N
        choco20.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        choco20.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single-eaten.png"))); // NOI18N
        choco20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                choco20MouseClicked(evt);
            }
        });

        choco21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single.png"))); // NOI18N
        choco21.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        choco21.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single-eaten.png"))); // NOI18N
        choco21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                choco21MouseClicked(evt);
            }
        });

        choco22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single.png"))); // NOI18N
        choco22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        choco22.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single-eaten.png"))); // NOI18N
        choco22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                choco22MouseClicked(evt);
            }
        });

        choco23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single.png"))); // NOI18N
        choco23.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        choco23.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single-eaten.png"))); // NOI18N
        choco23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                choco23MouseClicked(evt);
            }
        });

        choco24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single.png"))); // NOI18N
        choco24.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        choco24.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single-eaten.png"))); // NOI18N
        choco24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                choco24MouseClicked(evt);
            }
        });

        choco25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single.png"))); // NOI18N
        choco25.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        choco25.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single-eaten.png"))); // NOI18N
        choco25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                choco25MouseClicked(evt);
            }
        });

        choco26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single.png"))); // NOI18N
        choco26.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        choco26.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single-eaten.png"))); // NOI18N
        choco26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                choco26MouseClicked(evt);
            }
        });

        choco30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single.png"))); // NOI18N
        choco30.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        choco30.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single-eaten.png"))); // NOI18N
        choco30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                choco30MouseClicked(evt);
            }
        });

        choco31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single.png"))); // NOI18N
        choco31.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        choco31.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single-eaten.png"))); // NOI18N
        choco31.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                choco31MouseClicked(evt);
            }
        });

        choco32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single.png"))); // NOI18N
        choco32.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        choco32.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single-eaten.png"))); // NOI18N
        choco32.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                choco32MouseClicked(evt);
            }
        });

        choco33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single.png"))); // NOI18N
        choco33.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        choco33.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single-eaten.png"))); // NOI18N
        choco33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                choco33MouseClicked(evt);
            }
        });

        choco34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single.png"))); // NOI18N
        choco34.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        choco34.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single-eaten.png"))); // NOI18N
        choco34.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                choco34MouseClicked(evt);
            }
        });

        choco35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single.png"))); // NOI18N
        choco35.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        choco35.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single-eaten.png"))); // NOI18N
        choco35.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                choco35MouseClicked(evt);
            }
        });

        choco36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single.png"))); // NOI18N
        choco36.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        choco36.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/img/coco-single-eaten.png"))); // NOI18N
        choco36.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                choco36MouseClicked(evt);
            }
        });

        turnText.setText("Who's turn?");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(choco10, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(choco11, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(choco12, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(choco13, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(choco14, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(choco15, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(choco16, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(choco20, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(choco21, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(choco22, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(choco23, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(choco24, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(choco25, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(choco26, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(choco30, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(choco31, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(choco32, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(choco33, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(choco34, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(choco35, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(choco36, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(choco00, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(choco01, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(title))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(turnText)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(choco02, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(choco03, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(choco04, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(choco05, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(choco06, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(title)
                    .addComponent(turnText))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(choco00, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(choco01, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(choco02, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(choco03, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(choco04, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(choco05, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(choco06, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(choco10, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(choco11, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(choco12, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(choco13, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(choco14, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(choco15, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(choco16, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(choco20, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(choco21, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(choco22, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(choco23, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(choco24, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(choco25, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(choco26, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(choco30, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(choco31, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(choco32, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(choco33, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(choco34, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(choco35, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(choco36, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void choco00MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_choco00MouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_choco00MouseClicked

    private void choco01MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_choco01MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_choco01MouseClicked

    private void choco02MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_choco02MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_choco02MouseClicked

    private void choco03MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_choco03MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_choco03MouseClicked

    private void choco04MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_choco04MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_choco04MouseClicked

    private void choco05MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_choco05MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_choco05MouseClicked

    private void choco06MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_choco06MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_choco06MouseClicked

    private void choco10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_choco10MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_choco10MouseClicked

    private void choco11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_choco11MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_choco11MouseClicked

    private void choco12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_choco12MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_choco12MouseClicked

    private void choco13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_choco13MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_choco13MouseClicked

    private void choco14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_choco14MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_choco14MouseClicked

    private void choco15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_choco15MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_choco15MouseClicked

    private void choco16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_choco16MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_choco16MouseClicked

    private void choco20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_choco20MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_choco20MouseClicked

    private void choco21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_choco21MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_choco21MouseClicked

    private void choco22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_choco22MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_choco22MouseClicked

    private void choco23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_choco23MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_choco23MouseClicked

    private void choco24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_choco24MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_choco24MouseClicked

    private void choco25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_choco25MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_choco25MouseClicked

    private void choco26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_choco26MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_choco26MouseClicked

    private void choco30MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_choco30MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_choco30MouseClicked

    private void choco31MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_choco31MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_choco31MouseClicked

    private void choco32MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_choco32MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_choco32MouseClicked

    private void choco33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_choco33MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_choco33MouseClicked

    private void choco34MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_choco34MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_choco34MouseClicked

    private void choco35MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_choco35MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_choco35MouseClicked

    private void choco36MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_choco36MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_choco36MouseClicked

    private void choco00ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_choco00ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_choco00ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GameInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameInterface().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton choco00;
    private javax.swing.JButton choco01;
    private javax.swing.JButton choco02;
    private javax.swing.JButton choco03;
    private javax.swing.JButton choco04;
    private javax.swing.JButton choco05;
    private javax.swing.JButton choco06;
    private javax.swing.JButton choco10;
    private javax.swing.JButton choco11;
    private javax.swing.JButton choco12;
    private javax.swing.JButton choco13;
    private javax.swing.JButton choco14;
    private javax.swing.JButton choco15;
    private javax.swing.JButton choco16;
    private javax.swing.JButton choco20;
    private javax.swing.JButton choco21;
    private javax.swing.JButton choco22;
    private javax.swing.JButton choco23;
    private javax.swing.JButton choco24;
    private javax.swing.JButton choco25;
    private javax.swing.JButton choco26;
    private javax.swing.JButton choco30;
    private javax.swing.JButton choco31;
    private javax.swing.JButton choco32;
    private javax.swing.JButton choco33;
    private javax.swing.JButton choco34;
    private javax.swing.JButton choco35;
    private javax.swing.JButton choco36;
    private javax.swing.JLabel title;
    private javax.swing.JLabel turnText;
    // End of variables declaration//GEN-END:variables

}
