import java.util.HashMap;

public class Hash {

    private HashMap<Integer, Boolean> map;

    // Constructor to initialize the HashMap
    public Hash() {
        map = new HashMap<>();
        initializeMap();
    }

    // Initialize the map with default values (false)
    private void initializeMap() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                int key = generateKey(i, j);
                map.put(key, false); // Default value is false
            }
        }
    }

    // Generate a unique key for each cell based on its row and column
    private int generateKey(int row, int col) {
        return row * 16 + col; // Unique key calculation (e.g., row-major order)
    }

    // Method to set a value
    public void setValue(int row, int col, boolean value) {
        if (isValid(row, col)) {
            int key = generateKey(row, col);
            map.put(key, value);
        } else {
            throw new IllegalArgumentException("Invalid row/column index.");
        }
    }

    // Method to get a value
    public boolean getValue(int row, int col) {
        if (isValid(row, col)) {
            int key = generateKey(row, col);
            return map.getOrDefault(key, false); // Default to false if not present
        } else {
            throw new IllegalArgumentException("Invalid row/column index.");
        }
    }

    // Method to remove a value (set it to false)
    public void removeValue(int row, int col) {
        if (isValid(row, col)) {
            int key = generateKey(row, col);
            map.put(key, false); // Set to false to represent removal
        } else {
            throw new IllegalArgumentException("Invalid row/column index.");
        }
    }

    // Validate if the row and column are within bounds
    public boolean isValid(int row, int col) {
        return row >= 0 && row < 16 && col >= 0 && col < 16;
    }
}
