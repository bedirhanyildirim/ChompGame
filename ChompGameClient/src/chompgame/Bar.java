package chompgame;

import javax.swing.JOptionPane;

/**
 * @author Bedirhan YILDIRIM
 */
public class Bar {

    public static Chocolate[][] table;

    public Bar() {
        Bar.table = new Chocolate[4][7];
        getReadyBar();
    }

    public void getReadyBar() {
        System.out.println("Çikolata barının yüksekliği: " + Bar.table.length);
        System.out.println("Çikolata barının genişliği: " + Bar.table[0].length);
        //Çikolata id sayacını başlat
        int id = 0;
        //Barı doldurmaya başla
        for (int i = 0; i < Bar.table.length; i++) {
            for (int j = 0; j < Bar.table[0].length; j++) {
                Bar.table[i][j] = new Chocolate(id, i, j, 100);
                id++;
            }
        }
        //Sol alt köşedekini zehirle
        Bar.table[3][0].poison();
        System.out.println("Toplam çikolata sayısı: " + (Bar.table.length * Bar.table[0].length));
    }

    public void eatChocolate(Chocolate eat) {
        client.Client.Display(""+eat.user);
        client.Client.Display(""+eat.isPoisoned);
        //if (eat.isPoisoned() == true) {
            //JOptionPane.showMessageDialog(ui.GameInterface.gameInterface, "Game Over", "You lost!", JOptionPane.ERROR_MESSAGE);
        //} else {
            for (int i = eat.getXcoordinate(); 0 <= i; i--) {
                for (int j = eat.getYcoordinate(); j < Bar.table[0].length; j++) {
                    if (Bar.table[i][j].isEaten() == false) {
                        Bar.table[i][j].eatChocolate(eat.getUser());
                        ui.GameInterface.board[i][j].setEnabled(false);
                    }
                }
            }
        //}
        Game.rapor();
    }

    public Chocolate findChocolate(int findID) {
        for (int i = 0; i < this.table.length; i++) {
            for (int j = 0; j < this.table[0].length; j++) {
                if (this.table[i][j].getChocolateID() == findID) {
                    return this.table[i][j];
                }
            }
        }
        return null;
    }

    public boolean isChocolatePoisoned(int findID) {
        return findChocolate(findID).isPoisoned();
    }

    public boolean isChocolatePoisoned(Chocolate find) {
        return isChocolatePoisoned(find.getChocolateID());
    }

    public boolean isChocolateEaten(int findID) {
        return findChocolate(findID).isEaten();
    }

    public boolean isChocolateEaten(Chocolate find) {
        return isChocolateEaten(find.getChocolateID());
    }

    public static void durumRapor() {
        System.out.println("#############################################################################################################");
        for (int i = 0; i < Bar.table.length; i++) {
            for (int j = 0; j < Bar.table[0].length; j++) {
                System.out.print(Bar.table[i][j].isEaten() + "\t");
            }
            System.out.println("");
        }
        System.out.println("#############################################################################################################");
    }
}
