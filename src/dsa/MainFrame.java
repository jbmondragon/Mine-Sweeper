import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
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
        setupFrame();
        setLogo();
        initializeComponents();
        registerListeners();
        setupPanels();
        showOpeningPanel();
    }

    private void setupFrame() {
        this.setTitle("Board yarn");
        this.setSize(1000, 800);
        this.setBackground(Color.decode("#3CB371"));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initializeComponents() {
        cardLayout = new CardLayout();
        main = new JPanel(cardLayout);
        sound.playIntroSound();

        open.createOpening();
        user.askUserDetails();
        instruction.createInstruction();
        menu.createMenu();
        level.createLevel();

        gameover = new GameOver();
        gameover.createGameOver();

        win = new Win();
        win.createWin();
    }

    private void setLogo(){
        Image icon = Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/resources/logo.png"));
        setIconImage(icon);
    }

    private void registerListeners() {
        open.nextButtonOpening.addMouseListener(this);
        user.okbtn.addMouseListener(this);

        instruction.playbtn.addMouseListener(this);

        menu.newGamebtn.addMouseListener(this);
        menu.homebtn.addMouseListener(this);
        menu.resumebtn.addMouseListener(this);
        menu.exitbtn.addMouseListener(this);

        level.easybtn.addMouseListener(this);
        level.mediumbtn.addMouseListener(this);
        level.hardbtn.addMouseListener(this);

        gameover.backtogame.addMouseListener(this);
        win.backtogame.addMouseListener(this);
    }

    private void setupPanels() {
        main.add(open.openingPanel, "1");
        main.add(user.userPanel, "2");
        main.add(instruction.instructionPanel, "3");
        main.add(level.levelPanel, "4");
        main.add(mainPanel, "5");
        main.add(menu.menuPanel, "6");
        main.add(gameover.gameOverPanel, "7");
        main.add(win.winPanel, "8");

        this.add(main);
    }

    private void showOpeningPanel() {
        cardLayout.show(main, "1");
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

    public void createBoard() {
        lowerPanel.setLayout(new GridLayout(16, 16));
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                lowerPanel.add(square[i][j]);
            }
        }
    }

    public void createButton() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                square[i][j] = new JButton();
                final int r = i;
                final int c = j;
                square[i][j].addActionListener(e -> onButtonClick(r, c));
                square[i][j].setActionCommand(r + "," + c);
                square[i][j].addMouseListener(this);
                square[i][j].setBackground(Color.DARK_GRAY);
            }
        }
    }

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
        if (mapFlag.getValue(row, col)) {
            square[row][col].setIcon(null);
            mapFlag.removeValue(row, col);
        } else {
            if (flagIcon == null) {
            flagIcon = new ImageIcon(getClass().getResource("/resources/flag.jpg"));
        }
        square[row][col].setIcon(flagIcon);
        mapFlag.setValue(row, col, true);
        }

        updateLabelBorder(imagelbl1, Color.BLUE);
    }

    private void toggleQuestionMark(int row, int col) {
        if (mapQues.getValue(row, col)) {
            square[row][col].setIcon(null);
            mapQues.removeValue(row, col);
        } else {
            question_markIcon = new ImageIcon(getClass().getResource("/resources/question_mark.jfif"));
            square[row][col].setIcon(question_markIcon);
            mapQues.setValue(row, col, true);
        }
    updateLabelBorder(imagelbl2, Color.BLUE);
    }

    private void updateLabelBorder(JLabel label, Color borderColor) {
        Border currentBorder = label.getBorder();
        Border innerPadding = (currentBorder instanceof CompoundBorder)
            ? ((CompoundBorder) currentBorder).getInsideBorder()
            : BorderFactory.createEmptyBorder();

        MatteBorder outerBorder = new MatteBorder(5, 5, 5, 5, borderColor);
        CompoundBorder combinedBorder = new CompoundBorder(outerBorder, innerPadding);

        label.setBorder(combinedBorder);
        label.repaint();
    }


    private void revealCellContent(int row, int col) {
        if (mapFlag.getValue(row, col) || mapQues.getValue(row, col)) {
            return;
        }

        if (mines.containMines(row, col)) {
            if (mines.bombIcon == null) {
                mines.bombIcon = new ImageIcon(getClass().getResource("/resources/Bomb.jfif"));
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

            if (isCellRevealedOrMarked(currentRow, currentCol)) {
                continue;
            }

            revealCurrentCell(currentRow, currentCol);

            if (adjacentMineCount[currentRow][currentCol] == 0) {
                addNeighborsToQueue(queue, currentRow, currentCol);
            }
        }
    }

    private boolean isCellRevealedOrMarked(int row, int col) {
        if (!square[row][col].isEnabled()) {
            return true;
        }
        ImageIcon icon = (ImageIcon) square[row][col].getIcon();
        return icon == flagIcon || icon == question_markIcon;
    }

    private void revealCurrentCell(int row, int col) {
        square[row][col].setEnabled(false);
        int count = adjacentMineCount[row][col];
        if (count > 0) {
            square[row][col].setText(String.valueOf(count));
        } else {
            square[row][col].setBackground(Color.LIGHT_GRAY);
            square[row][col].setIcon(null);
        }
    }

    private void addNeighborsToQueue(IntArrayQueue<int[]> queue, int row, int col) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j;
                if (isValidCell(newRow, newCol) && !isCellRevealedOrMarked(newRow, newCol)) {
                    queue.add(new int[] { newRow, newCol });
                }
            }
        }
    }

    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < 16 && col >= 0 && col < 16;
    }


    private void calculateAdjacentMineCounts() {
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

    public void createTimerAndMenu() {
        setupUpperPanelLayout();
        configurePlayerLabel();
        createIcons();
        addComponentsToUpperPanel();
    }

    private void setupUpperPanelLayout() {
        upperPanel.setLayout(new GridBagLayout());
    }

    private void configurePlayerLabel() {
        String playerName = (name != null && !name.isEmpty()) ? name : "Guest";
        label2.setText("Player: " + playerName);
        label2.setForeground(new Color(0x5a8d03));
        label2.setBackground(Color.BLACK);
        label2.setOpaque(true);
        label2.setFont(new Font("Serif", Font.BOLD, 15));
    }

    private void createIcons() {
        EmptyBorder padding = new EmptyBorder(10, 10, 10, 10);
        MatteBorder blueBorder = new MatteBorder(5, 5, 5, 5, Color.BLUE);
        CompoundBorder compoundBorder = new CompoundBorder(blueBorder, padding);

        flagIcon = loadIcon("/resources/flag.jpg");
        imagelbl1 = createIconLabel(flagIcon, compoundBorder);

        happyIcon = loadIcon("/resources/happy.png");
        imagelbl = createIconLabel(happyIcon, null);

        question_markIcon = loadIcon("/resources/question_mark.jfif");
        imagelbl2 = createIconLabel(question_markIcon, compoundBorder);
        imagelbl2.setPreferredSize(new Dimension(60, 60));
        imagelbl2.setHorizontalAlignment(SwingConstants.CENTER);
        imagelbl2.setVerticalAlignment(SwingConstants.CENTER);
    }

    private void addComponentsToUpperPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        addToUpperPanel(imagelbl1, gbc, 0);
        addToUpperPanel(label1, gbc, 1);
        addToUpperPanel(imagelbl, gbc, 2);
        addToUpperPanel(label2, gbc, 3);
        addToUpperPanel(imagelbl2, gbc, 4);
    }

    private ImageIcon loadIcon(String path) {
        return new ImageIcon(getClass().getResource(path));
    }

    private JLabel createIconLabel(ImageIcon icon, Border border) {
        JLabel label = new JLabel(icon);
        if (border != null) label.setBorder(border);
        label.addMouseListener(this);
        return label;
    }

    private void addToUpperPanel(Component component, GridBagConstraints gbc, int x) {
        gbc.gridx = x;
        gbc.gridy = 0;
        upperPanel.add(component, gbc);
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
        stop = true; gameOver = false; question_mark = false; flag = false;
        label1.setText("Timer: 00:00");
        mines = new MineList();
        adjacentMineCount = new int[16][16];

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                square[i][j].setEnabled(true);
                square[i][j].setIcon(null);
                square[i][j].setText("");
                square[i][j].setBackground(Color.DARK_GRAY);
            }
        }

        placeMines();
        calculateAdjacentMineCounts();
        time = 0;

        stop = false;
        try {
            date.scheduleAtFixedRate(task, 0, 1000);
        } catch (Exception ee) {
            System.out.println("Timer error.");
        }

        cardLayout.show(main, "5");
        sound.stopSound();
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
                        setUpBoard();
                        break;

                    case "Medium":
                        NUM_MINES = 17;
                        setUpBoard();

                        break;
                    case "Hard":
                        NUM_MINES = 25;
                        setUpBoard();
                        
                        break;
                    case "Restart":
                        ispaused = false;
                        restartTheGame();
                        break;
                    case "Back to Game":
                        cardLayout.show(main, "5");
                        sound.stopSound();;
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
        sound.stopSound();;
        showPanel(currentPanel);

    }

    private void setUpBoard(){
        if (restart) {
            restartTheGame();
            restart = false;
        } else {
            startNewGame();
        }
    }

    private void changeQuestionMarkColor() {
        backgroundSpaceQuestionMark = new MatteBorder(5, 5, 5, 5, Color.RED); // Change to red when
        Border innerPadding = ((CompoundBorder) imagelbl2.getBorder()).getInsideBorder();
        CompoundBorder combinedBorder = new CompoundBorder(backgroundSpaceQuestionMark, innerPadding);
        imagelbl2.setBorder(combinedBorder);
        imagelbl2.repaint();
    }

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