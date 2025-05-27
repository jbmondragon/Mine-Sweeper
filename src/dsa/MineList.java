import javax.swing.ImageIcon;

public class MineList {

    Mine head;
    ImageIcon bombIcon = new ImageIcon(getClass().getResource("/resources/Bomb.jfif"));

    public MineList() {

    }

    // method to add mine
    public void addMine(int row, int col) {
        Mine newMine = new Mine(row, col);
        if (head == null) {
            head = newMine;
        } else {
            Mine current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newMine;
        }
    }

    // method to check if a mine exist in the specific location
    public boolean containMines(int row, int col) {
        Mine current = head;
        while (current != null) {
            if (current.row == row && current.col == col) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

}
