package chompgame;

/**
 * @author Bedirhan YILDIRIM
 */
public class Bar {

    public Chocolate[][] table;

    public Bar() {
        this.table = new Chocolate[4][7];
        this.table = getReadyBar();
    }

    private Chocolate[][] getReadyBar() {
        System.out.println("barın yüksekliği: " + this.table.length);
        System.out.println("barın genişliği: " + this.table[0].length);
        int id = 0;
        for (int i = 0; i < this.table.length; i++) {
            for (int j = 0; j < this.table[0].length; j++) {
                this.table[i][j] = new Chocolate(id,i,j,0);
                id++;
            }
        }
        System.out.println("Son çikolatanın id'si: " + (this.table[3][6].id+1));
        return null;
    }
}
