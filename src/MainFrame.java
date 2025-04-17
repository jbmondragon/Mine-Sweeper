import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

public class MainFrame extends JFrame implements MouseListener {

    JPanel mainPanel = new JPanel(new GridBagLayout());
    JPanel upperPanel = new JPanel();
    JPanel lowerPanel = new JPanel();
    JPanel boardPanel, main;

    JLabel imagelbl, imagelbl1, imagelbl2;
    JLabel label1 = new JLabel();
    JLabel label2 = new JLabel();

    JButton[][] square = new JButton[16][16];
    JButton nextbtn, playbtn, exitbtn, okbtn, newGamebtn, homebtn, resumebtn, easybtn, mediumbtn, hardbtn;

    ImageIcon happyIcon, flagIcon, question_markIcon;
    MatteBorder backgroundSpaceFlag, backgroundSpaceQuestionMark;
    GridBagConstraints gbc;
    CardLayout cardLayout;

    int[][] adjacentMineCount = new int[16][16];
    int[][] rightClickState = new int[16][16];
    int r, c;
    int NUM_MINES = 15;
    int currentPanel = 1;
    int time = 0;
    String name;

    boolean stop = false;
    boolean restart = false;
    boolean ispaused = false;
    boolean flag = false;
    boolean question_mark = false;
    boolean clickNa = false;
    boolean gameOver = false;
    boolean[][] isFlag = new boolean[16][16];
    boolean[][] isQuesMark = new boolean[16][16];

    Timer date = new Timer();

    MineList mines = new MineList();
    User user = new User();
    Opening open = new Opening();
    Instruction instruction = new Instruction();
    Menu menu = new Menu();
    Level level = new Level();
    Hash mapFlag = new Hash();
    Hash mapQues = new Hash();
    GameOver gameover;
    Win win;
    Sound sound = new Sound();

    public MainFrame() {
        this.setTitle("Board yarn");
        this.setSize(1000, 800);
        this.setBackground(Color.decode("#3CB371"));

        // creating objects from classes and adding listeners to the buttons
        cardLayout = new CardLayout();
        main = new JPanel(cardLayout);

        sound.playIntroSound();

        open.createOpening();
        open.nextButtonOpening.addMouseListener(this);

        user.askUserDetails();
        user.okbtn.addMouseListener(this);

        instruction.createInstruction();
        instruction.playbtn.addMouseListener(this);

        menu.createMenu();
        menu.newGamebtn.addMouseListener(this);
        menu.homebtn.addMouseListener(this);
        menu.resumebtn.addMouseListener(this);
        menu.exitbtn.addMouseListener(this);

        level.createLevel();
        level.easybtn.addMouseListener(this);
        level.mediumbtn.addMouseListener(this);
        level.hardbtn.addMouseListener(this);

        // panels for winning and losing the game
        gameover = new GameOver();
        gameover.createGameOver();
        gameover.backtogame.addMouseListener(this);

        win = new Win();
        win.createWin();
        win.backtogame.addMouseListener(this);

        // card layout to change panels
        main.add(open.openingPanel, "1");
        main.add(user.userPanel, "2");
        main.add(instruction.instructionPanel, "3");
        main.add(level.levelPanel, "4");
        main.add(mainPanel, "5");
        main.add(menu.menuPanel, "6");
        main.add(gameover.gameOverPanel, "7");
        main.add(win.winPanel, "8");
        this.add(main);

        cardLayout.show(main, "1");

        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void mainInterfaceForGame() {

        createMainPanel();
        createButton();
        createBoard();
        placeMines();
        calculateAdjacentMineCounts();
        try {
            date.scheduleAtFixedRate(task, 0, 1000); // timer updates every second
        } catch (Exception ee) {
            System.out.println("Timer error.");
        }
    }

    // GUI lang to for Panel for the Game
    public void createMainPanel() {

        upperPanel.setBackground(new Color(0x5a8d03));
        lowerPanel.setBackground(new Color(0x5a8d03));

        mainPanel.setBorder(BorderFactory.createMatteBorder(20, 20, 20, 20, Color.DARK_GRAY));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;

        gbc.weighty = 0.05;
        Color darkGray = Color.DARK_GRAY;
        upperPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, darkGray, darkGray, darkGray, darkGray));
        mainPanel.add(upperPanel, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.95;
        lowerPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, darkGray, darkGray, darkGray, darkGray));
        mainPanel.add(lowerPanel, gbc);
    }

    // Code Implementation for the creation of Board Using 2D array
    public void createBoard() {
        lowerPanel.setLayout(new GridLayout(16, 16));
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                lowerPanel.add(square[i][j]);
            }
        }
    }

    /*
     * This is the 2D array implementation, where in we are
     * initializing all the buttons or the square that we
     * will be clicking to reveal either number,
     * empty cell or mines
     */
    public void createButton() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                square[i][j] = new JButton();
                final int r = i;
                final int c = j;
                square[i][j].addActionListener(e -> onButtonClick(r, c));
                square[i][j].setActionCommand(r + "," + c);
                square[i][j].addMouseListener(this);
                square[i][j].setBackground(Color.DARK_GRAY); // Reset background color
            }
        }
    }

    // Condition to para malaman if na click si question mark or flag
    private void onButtonClick(int row, int col) {
        if (gameOver) {
            return;
        }
        if (flag && !question_mark) {
            toggleFlag(row, col);
        } else if (question_mark && !flag) {
            imagelbl2.setBackground(Color.blue);
            toggleQuestionMark(row, col);
        } else if (!question_mark && !flag) {
            revealCellContent(row, col);
        }
        flag = false;
        question_mark = false;
    }

    private void toggleFlag(int row, int col) {

        // Cell is flagged, so remove the flag
        if (mapFlag.getValue(row, col)) {
            square[row][col].setIcon(null);
            mapFlag.removeValue(row, col);
        }
        // Cell is unflagged, so add the flag
        else {
            if (flagIcon == null) {
                flagIcon = new ImageIcon("C:\\Users\\Geralyn\\Desktop\\MineSweeper\\src\\resources\\flag.jpg");
            }
            square[row][col].setIcon(flagIcon);
            mapFlag.setValue(row, col, true);
        }

        // for estitik lang to
        backgroundSpaceFlag = new MatteBorder(5, 5, 5, 5, Color.BLUE);
        Border innerPadding = ((CompoundBorder) imagelbl1.getBorder()).getInsideBorder();
        CompoundBorder combinedBorder = new CompoundBorder(backgroundSpaceFlag, innerPadding);
        imagelbl1.setBorder(combinedBorder);
        imagelbl1.repaint();
    }

    private void toggleQuestionMark(int row, int col) {
        // Here we are using hash map to track the location of the question mark
        // Cell has question mark, so remove it
        if (mapQues.getValue(row, col)) {
            square[row][col].setIcon(null);
            mapQues.removeValue(row, col);
        }

        // Cell does not have question mark, so add one
        else {
            if (question_markIcon == null) {
                question_markIcon = new ImageIcon("C:\\Users\\Geralyn\\Desktop\\MineSweeper\\src\\resources\\question_mark.jfif");
            }
            square[row][col].setIcon(question_markIcon);
            mapQues.setValue(row, col, true);
        }

        // for estitik lang to
        backgroundSpaceQuestionMark = new MatteBorder(5, 5, 5, 5, Color.BLUE); // Change to red when
        Border innerPadding = ((CompoundBorder) imagelbl2.getBorder()).getInsideBorder();
        CompoundBorder combinedBorder = new CompoundBorder(backgroundSpaceQuestionMark, innerPadding);
        imagelbl2.setBorder(combinedBorder);
        imagelbl2.repaint();
    }

    private void revealCellContent(int row, int col) {

        // to stop the content from revealing once it is marked
        if (mapFlag.getValue(row, col) || mapQues.getValue(row, col)) {
            return;
        }

        // this demostrates how will the content of the cell will be revealed
        if (mines.containMines(row, col)) {
            if (mines.bombIcon == null) {
                mines.bombIcon = new ImageIcon("C:\\Users\\Geralyn\\Desktop\\MineSweeper\\src\\resources\\Bomb.jfif");
            }
            square[row][col].setIcon(mines.bombIcon);
            square[row][col].setBackground(Color.red);
            stop = true;
            gameOver = true;
            sound.playBogshSound();
            cardLayout.show(main, "7");
            revealAllMines();
            disableAllButtons();
        } else {
            sound.playClickSound();
            revealCell(row, col);
            if (checkWin()) {
                stop = true;
                gameOver = true;
                cardLayout.show(main, "8");
                revealAllMines();
                disableAllButtons();
            }
        }
    }

    private void revealAllMines() {
        /*
         * The function of this code is to reveal all mines
         * Here we are using 2D arrays to access all of the unclicked cells
         * On the other hand we are using singly linked list to reveal all the mines
         */

        for (int row = 0; row < 16; row++) {
            for (int col = 0; col < 16; col++) {
                if (mines.containMines(row, col)) {
                    imagelbl = new JLabel(mines.bombIcon);
                    square[row][col].setIcon(mines.bombIcon);
                }
            }
        }
    }

    private void disableAllButtons() {
        // We are using 2D array here to disable all the cell once the game is over
        for (int row = 0; row < 16; row++) {
            for (int col = 0; col < 16; col++) {
                square[row][col].setEnabled(false);
            }
        }
    }

    private void revealCell(int row, int col) {
        IntArrayQueue<int[]> queue = new IntArrayQueue<>();
        queue.add(new int[] { row, col });

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int currentRow = cell[0];
            int currentCol = cell[1];

            // Skip if cell is already disabled
            if (!square[currentRow][currentCol].isEnabled()) {
                continue;
            }

            // Get the icon of the current cell
            ImageIcon icon = (ImageIcon) square[currentRow][currentCol].getIcon();

            // Skip flagged or question-marked cells to retain their icons
            if (icon == flagIcon || icon == question_markIcon) {
                continue;
            }

            // Disable the cell to mark it as revealed
            square[currentRow][currentCol].setEnabled(false);

            int count = adjacentMineCount[currentRow][currentCol];

            if (count > 0) {
                // Only set the text if there are adjacent mines
                square[currentRow][currentCol].setText(String.valueOf(count));
            } else {
                // Set background to light gray for empty cells only
                square[currentRow][currentCol].setBackground(Color.LIGHT_GRAY);
                square[currentRow][currentCol].setIcon(null); // Remove icon for empty cells only

                // Add neighboring cells to the queue if they are within bounds
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        int newRow = currentRow + i;
                        int newCol = currentCol + j;

                        // Check bounds and add only enabled, non-flagged cells to the queue
                        if (newRow >= 0 && newRow < 16 && newCol >= 0 && newCol < 16) {
                            if (square[newRow][newCol].isEnabled()) {
                                // Get the icon of the neighboring cell
                                ImageIcon neighborIcon = (ImageIcon) square[newRow][newCol].getIcon();

                                // Skip adding flagged or question-marked neighbors to the queue
                                if (neighborIcon != flagIcon && neighborIcon != question_markIcon) {
                                    queue.add(new int[] { newRow, newCol });
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void calculateAdjacentMineCounts() {
        // Calculate the number of adjacent mines for each cell
        for (int row = 0; row < 16; row++) {
            for (int col = 0; col < 16; col++) {
                if (!mines.containMines(row, col)) {
                    int count = 0;
                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            int newRow = row + i;
                            int newCol = col + j;
                            if (newRow >= 0 && newRow < 16 && newCol >= 0 && newCol < 16
                                    && mines.containMines(newRow, newCol)) {
                                count++;
                            }
                        }
                    }
                    adjacentMineCount[row][col] = count;
                }
            }
        }
    }

    private boolean checkWin() {
        // Check if the player has won by revealing all non-mine cells
        for (int row = 0; row < 16; row++) {
            for (int col = 0; col < 16; col++) {
                if (!mines.containMines(row, col) && square[row][col].isEnabled()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void placeMines() {
        // Randomly place mines on the grid
        Random rand = new Random();
        int minesPlaced = 0;
        while (minesPlaced < NUM_MINES) {
            int row = rand.nextInt(16);
            int col = rand.nextInt(16);

            if (!mines.containMines(row, col)) {
                mines.addMine(row, col);
                minesPlaced++;
            }
        }
    }

    // GUI lang to for icons: question mark and flag
    public void createTimerAndMenu() {
        upperPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        if (name != null && !name.isEmpty()) {
            label2.setText("Player: " + name);
        } else {
            label2.setText("Player: Guest");
        }

        label2.setForeground(new Color(0x5a8d03));
        label2.setBackground(Color.BLACK);
        label2.setOpaque(true);
        label2.setFont(new Font("Serif", Font.BOLD, 15));

        // happy icon
        happyIcon = new ImageIcon("C:\\Users\\Geralyn\\Desktop\\MineSweeper\\src\\resources\\happy.png");
        imagelbl = new JLabel(happyIcon);
        imagelbl.addMouseListener(this);
        flagIcon = new ImageIcon("C:\\Users\\Geralyn\\Desktop\\MineSweeper\\src\\resources\\flag.jpg");
        imagelbl1 = new JLabel(flagIcon);
        imagelbl1.addMouseListener(this);
        EmptyBorder padding = new EmptyBorder(10, 10, 10, 10); // Adjust as needed
        backgroundSpaceFlag = new MatteBorder(5, 5, 5, 5, Color.BLUE); // Adjust as needed
        imagelbl1.setBorder(new CompoundBorder(backgroundSpaceFlag, padding));

        // question mark icon
        question_markIcon = new ImageIcon("C:\\Users\\Geralyn\\Desktop\\MineSweeper\\src\\resources\\question_mark.jfif");
        imagelbl2 = new JLabel(question_markIcon);
        imagelbl2.setPreferredSize(new Dimension(60, 60)); // Add some extra space for background visibility
        imagelbl2.setHorizontalAlignment(SwingConstants.CENTER); // Center the icon within the label
        imagelbl2.setVerticalAlignment(SwingConstants.CENTER);
        imagelbl2.addMouseListener(this);
        backgroundSpaceQuestionMark = new MatteBorder(5, 5, 5, 5, Color.BLUE); // Adjust as needed
        imagelbl2.setBorder(new CompoundBorder(backgroundSpaceQuestionMark, padding));

        // Adding components
        gbc.gridx = 0;
        gbc.gridy = 0;
        upperPanel.add(imagelbl1, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        upperPanel.add(label1, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        upperPanel.add(imagelbl, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        upperPanel.add(label2, gbc);

        gbc.gridx = 4;
        gbc.gridy = 0;
        upperPanel.add(imagelbl2, gbc);
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if (!stop) {
                int minutes = time / 60;
                int seconds = time % 60;
                label1.setText(String.format("Timer: %02d:%02d", minutes, seconds));
                label1.setForeground(new Color(0x5a8d03));
                label1.setBackground(Color.BLACK);
                label1.setOpaque(true);
                label1.setFont(new Font("Serif", Font.BOLD, 15));
                if (!(ispaused)) {
                    time++;
                }
            }
        }
    };

    public void restartTheGame() {
        // this is to reset everything to way back from the start
        stop = true;
        gameOver = false;
        question_mark = false;
        flag = false;
        label1.setText("Timer: 00:00");
        mines = new MineList();
        adjacentMineCount = new int[16][16];

        // Re-enable the button, Remove any icons, Clear any text, Reset background
        // color
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                square[i][j].setEnabled(true);
                square[i][j].setIcon(null);
                square[i][j].setText("");
                square[i][j].setBackground(Color.DARK_GRAY);
            }
        }

        // Place mines and calculate adjacent mine counts again
        placeMines();
        calculateAdjacentMineCounts();
        time = 0;

        // Restart the timer
        stop = false;
        try {
            date.scheduleAtFixedRate(task, 0, 1000); // timer updates every second
        } catch (Exception ee) {
            System.out.println("Timer error.");
        }

        // Reset the game display, Show the game panel
        cardLayout.show(main, "5");
        sound.clip.stop();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            if (e.getSource() instanceof JButton) {
                JButton clickedButton = (JButton) e.getComponent();
                String buttonText = clickedButton.getText();

                switch (buttonText) {
                    case "Play":
                        showPanel(4);
                        break;
                    case "Resume":
                        ispaused = false;
                        showPanel(5);
                        break;
                    case "Home":
                        sound.playIntroSound();
                        ispaused = false;
                        restart = true;
                        showPanel(4);
                        break;
                    case "Next":
                        currentPanel++;
                        showPanel(currentPanel);
                        break;
                    case "Ok":
                        this.name = user.nameField.getText();
                        createTimerAndMenu();
                        currentPanel++;
                        showPanel(currentPanel);
                        break;
                    case "Exit":
                        System.exit(0);
                        break;
                    case "Easy":
                        NUM_MINES = 10;
                        if (restart) {
                            restartTheGame();
                            restart = false;
                        } else {
                            startNewGame();
                        }

                        break;
                    case "Medium":
                        NUM_MINES = 17;
                        if (restart) {
                            restartTheGame();
                            restart = false;
                        } else {
                            startNewGame();
                        }

                        break;
                    case "Hard":
                        NUM_MINES = 25;
                        if (restart) {
                            restartTheGame();
                            restart = false;
                        } else {
                            startNewGame();
                        }
                        break;
                    case "Restart":
                        ispaused = false;
                        restartTheGame();
                        break;
                    case "Back to Game":
                        cardLayout.show(main, "5");
                        sound.clip.stop();
                        break;
                    default:
                        break;
                }
            } else if (e.getSource() instanceof JLabel) {
                JLabel label = (JLabel) e.getSource();
                ImageIcon icon = (ImageIcon) label.getIcon();

                if (icon == happyIcon) {
                    ispaused = true;
                    showPanel(6);
                } else if (icon == flagIcon && stop == false && question_mark == false) {
                    flag = true;
                    changeflagColor();
                } else if (icon == question_markIcon && stop == false && flag == false) {
                    question_mark = true;
                    changeQuestionMarkColor();

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void showPanel(int panelNumber) {
        currentPanel = panelNumber;
        cardLayout.show(main, Integer.toString(currentPanel));
    }

    private void startNewGame() {
        mainInterfaceForGame();
        currentPanel++;
        sound.clip.stop();
        showPanel(currentPanel);

    }

    // for estitik lang to
    private void changeQuestionMarkColor() {
        backgroundSpaceQuestionMark = new MatteBorder(5, 5, 5, 5, Color.RED); // Change to red when
        Border innerPadding = ((CompoundBorder) imagelbl2.getBorder()).getInsideBorder();
        CompoundBorder combinedBorder = new CompoundBorder(backgroundSpaceQuestionMark, innerPadding);
        imagelbl2.setBorder(combinedBorder);
        imagelbl2.repaint();
    }

    // for estitik lang to
    private void changeflagColor() {
        backgroundSpaceFlag = new MatteBorder(5, 5, 5, 5, Color.RED); // Change to red when
        Border innerPadding = ((CompoundBorder) imagelbl1.getBorder()).getInsideBorder();
        CompoundBorder combinedBorder = new CompoundBorder(backgroundSpaceFlag, innerPadding);
        imagelbl1.setBorder(combinedBorder);
        imagelbl1.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        setBackground(Color.RED);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        setBackground(Color.YELLOW);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setBackground(Color.LIGHT_GRAY);
    }
}