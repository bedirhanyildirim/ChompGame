package chompgame;

/**
 * @author Bedirhan YILDIRIM
 */
public class Chocolate implements java.io.Serializable {

    public int id;
    public int x;
    public int y;
    public boolean isEaten;
    public boolean isPoisoned;
    public int user;

    public Chocolate(int id, int x, int y, int user) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.user = user;
        this.isEaten = false;
        this.isPoisoned = false;
    }

    public void poison() {
        this.isPoisoned = true;
    }

    public void eatChocolate(int us) {
        this.isEaten = true;
        this.user = us;
    }

    public boolean isEaten() {
        return this.isEaten;
    }

    public boolean isPoisoned() {
        return this.isPoisoned;
    }

    public int getChocolateID() {
        return this.id;
    }

    public int getXcoordinate() {
        return this.x;
    }

    public int getYcoordinate() {
        return this.y;
    }
    
    public int getUser(){
        return this.user;
    }
    
    public void setMe(){
        this.user = 0;
    }
    
    public void setCompetitor(){
        this.user = 1;
    }
}
