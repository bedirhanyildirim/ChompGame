package chompgame;

/**
 * @author Bedirhan YILDIRIM
 */
public class Chocolate implements java.io.Serializable {

    public static int id;
    public static int x;
    public static int y;
    public static boolean isEaten;
    public static boolean isPoisoned;
    public static int user;

    public Chocolate(int id, int x, int y, int user) {
        Chocolate.id = id;
        Chocolate.x = x;
        Chocolate.y = y;
        Chocolate.user = user;
        Chocolate.isEaten = false;
        Chocolate.isPoisoned = false;
    }

    public void poison() {
        Chocolate.isPoisoned = true;
    }

    public void eatChocolate(int us) {
        Chocolate.isEaten = true;
        Chocolate.user = us;
    }

    public boolean isEaten() {
        return Chocolate.isEaten;
    }

    public boolean isPoisoned() {
        return Chocolate.isPoisoned;
    }

    public int getChocolateID() {
        return Chocolate.id;
    }

    public int getXcoordinate() {
        return Chocolate.x;
    }

    public int getYcoordinate() {
        return Chocolate.y;
    }
    
    public int getUser(){
        return Chocolate.user;
    }
    
    public void setMe(){
        Chocolate.user = 0;
    }
    
    public void setCompetitor(){
        Chocolate.user = 1;
    }
}
