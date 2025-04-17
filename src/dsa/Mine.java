public class Mine {

    int row;
    int col;
    Mine next;

    public Mine(int row, int col) {
        this.row = row;
        this.col = col;
        this.next = null;
    }
}