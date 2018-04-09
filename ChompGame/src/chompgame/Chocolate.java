package chompgame;

/**
 * @author Bedirhan YILDIRIM
 */
public class Chocolate{

    private int id;
    private int x;
    private int y;
    private boolean isEaten;
    private boolean isPoisoned;
    private int user;

    public Chocolate(int id, int x, int y, int user) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.user = user;
        isEaten = false;
        isPoisoned = false;
    }
    
    public void eatChocolate(){
        this.isEaten = true;
    }
    
    public boolean isEaten(){
        return this.isEaten;
    }
    public boolean isPoisoned(){
        return this.isPoisoned;
    }
}
