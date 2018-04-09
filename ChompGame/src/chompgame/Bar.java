package chompgame;

/**
 * @author Bedirhan YILDIRIM
 */
public class Bar {

    public Chocolate[][] table;

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
                this.table[i][j] = new Chocolate(id, i, j, 0);
                id++;
            }
        }
        //Sol alt köşedekini zehirle
        this.table[3][0].eatChocolate();
        System.out.println("Toplam çikolata sayısı: " + (this.table.length * this.table[0].length));
    }

    public void eatChocolate(int x, int y) {
        if (this.table[x][y].isEaten() == true) {
            //bu hamleyi yapamazsın, daha önce bu hamle oynandı.
        } else {
            if (this.table[x][y].isPoisoned() == true) {
                //oyunu bitir - kaybettin.
            } else {
                for (int i = x; 0 <= i; i--) {
                    for (int j = y; j < this.table[x].length; j++) {
                        if(this.table[i][j].isEaten() == false){
                            this.table[i][j].eatChocolate(); // Yenildi olarak işaretle
                            //this.table[x][y].user = 0; // Kim tarafından yenildi
                        }
                    }
                }
            }
        }
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
