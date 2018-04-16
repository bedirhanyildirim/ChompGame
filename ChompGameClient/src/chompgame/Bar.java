package chompgame;

/**
 * @author Bedirhan YILDIRIM
 */
public class Bar implements java.io.Serializable {

    private Chocolate[][] table;

    public Bar() {
        this.table = new Chocolate[4][7];
        getReadyBar();
    }

    private void getReadyBar() {
        System.out.println("Çikolata barının yüksekliği: " + this.table.length);
        System.out.println("Çikolata barının genişliği: " + this.table[0].length);
        //Çikolata id sayacını başlat
        int id = 0;
        //Barı doldurmaya başla
        for (int i = 0; i < this.table.length; i++) {
            for (int j = 0; j < this.table[0].length; j++) {
                this.table[i][j] = new Chocolate(id, i, j, 100);
                id++;
            }
        }
        //Sol alt köşedekini zehirle
        this.table[table.length - 1][0].poison();
        System.out.println("Toplam çikolata sayısı: " + (this.table.length * this.table[0].length));
    }
    
    public void syncBar(Bar sync) {
        for (int i = 0; i < this.table.length; i++) {
            for (int j = 0; j < this.table[0].length; j++) {
                this.table[i][j].isEaten = sync.table[i][j].isEaten;
                this.table[i][j].user = sync.table[i][j].user;
            }
        }
    }

    public void eatChocolate(Chocolate eat) {
        for (int i = eat.getXcoordinate(); 0 <= i; i--) {
            for (int j = eat.getYcoordinate(); j < this.table[0].length; j++) {
                if (this.table[i][j].isEaten() == false) {
                    this.table[i][j].eatChocolate();
                }
            }
        }
    }

    public void eatChocolate(int x, int y) {
        for (int i = x; 0 <= i; i--) {
            for (int j = y; j < this.table[0].length; j++) {
                if (this.table[i][j].isEaten() == false) {
                    this.table[i][j].eatChocolate();
                }
            }
        }
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

    public void durumRapor() {
        System.out.println("#############################################################################################################");
        for (int i = 0; i < this.table.length; i++) {
            for (int j = 0; j < this.table[0].length; j++) {
                System.out.print(this.table[i][j].isEaten() + "\t");
            }
            System.out.println("");
        }
        System.out.println("#############################################################################################################");
    }
}
