package chompgame;

/**
 * @author Bedirhan YILDIRIM
 */
public class Chocolate {

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
        isEaten = false;
        isPoisoned = false;
    }
}
