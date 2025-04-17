import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Menu {
    JPanel upperPanel = new JPanel();
    JPanel lowerPanel = new JPanel();
    JPanel main;
    JPanel boardPanel, openingPanel, userPanel, menuPanel, levelPanel;

    JLabel welcomelbl, tolbl, damath53lbl, imagelbl, imagelbl1, imagelbl2, userlbl;
    JLabel label1 = new JLabel();
    JLabel label2 = new JLabel();

    JButton[][] square = new JButton[16][16]; // Use JButton here
    JButton nextbtn, playbtn, exitbtn, okbtn, newGamebtn, homebtn, resumebtn, easybtn, mediumbtn, hardbtn;

    int[][] adjacentMineCount = new int[16][16];
    int[][] rightClickState = new int[16][16];
    int r, c;
    int NUM_MINES = 15;
    int currentPanel = 1;

    boolean stop = false;
    boolean restart = false;
    boolean ispaused = false;
    boolean flag = false;
    boolean question_mark = false;
    boolean clickNa = false;
    boolean[][] isFlag = new boolean[16][16];
    boolean[][] isQuesMark = new boolean[16][16];

    HashMap<Point, Boolean> flagMap = new HashMap<>();
    HashMap<Point, Boolean> quesMarkMap = new HashMap<>();
    boolean gameOver = false;

    String name;

    Timer date = new Timer();

    GridBagConstraints gbc;
    int time = 0;

    MineList mines = new MineList();
    ImageIcon happyIcon, flagIcon, question_markIcon;
    MatteBorder backgroundSpaceFlag, backgroundSpaceQuestionMark;
    Clip clip;

    public void createMenu() {
        Dimension buttonSize = new Dimension(70, 20);
        menuPanel = new JPanel();
        menuPanel.setLayout(new GridBagLayout());
        menuPanel.setBackground(Color.BLACK);

        newGamebtn = new JButton("Restart");
        newGamebtn.setPreferredSize(buttonSize); // Set preferred size
        newGamebtn.setFont(new Font("Restart", Font.BOLD, 15));
        newGamebtn.setForeground(new Color(0xB7C9E2));
        newGamebtn.setBackground(new Color(0x5a8d03));
        newGamebtn.setFocusPainted(false);
        newGamebtn.setOpaque(true);
        newGamebtn.setActionCommand("Restart"); // Set action command for the restart button
        // newGamebtn.addMouseListener(this); // Add an action listener

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 2, 2, 2);
        gbc.gridx = 0;

        gbc.gridy = 0;
        gbc.ipadx = 100;
        gbc.ipady = 10;
        menuPanel.add(newGamebtn, gbc);

        homebtn = new JButton("Home");
        homebtn.setPreferredSize(buttonSize); // Set preferred size
        homebtn.setFont(new Font("Home", Font.BOLD, 15));
        homebtn.setForeground(new Color(0xB7C9E2));
        homebtn.setBackground(new Color(0x5a8d03));
        homebtn.setFocusPainted(false);
        homebtn.setOpaque(true);
        homebtn.setActionCommand("Home"); // Set action command for the restart button
        // homebtn.addMouseListener(this); // Add an action listener

        gbc.gridy = 1;
        menuPanel.add(homebtn, gbc);

        resumebtn = new JButton("Resume");
        resumebtn.setPreferredSize(buttonSize); // Set preferred size
        resumebtn.setFont(new Font("Resume", Font.BOLD, 15));
        resumebtn.setForeground(new Color(0xB7C9E2));
        resumebtn.setBackground(new Color(0x5a8d03));
        resumebtn.setFocusPainted(false);
        resumebtn.setOpaque(true);
        resumebtn.setActionCommand("Resume"); // Set action command for the restart button
        // resumebtn.addMouseListener(this); // Add an action listener

        gbc.gridy = 2;
        menuPanel.add(resumebtn, gbc);

        exitbtn = new JButton("Exit");
        exitbtn.setPreferredSize(buttonSize); // Set preferred size
        exitbtn.setFont(new Font("Serif", Font.BOLD, 15));
        exitbtn.setForeground(new Color(0xB7C9E2));
        exitbtn.setBackground(new Color(0x5a8d03));
        exitbtn.setFocusPainted(false);
        exitbtn.setOpaque(true);
        exitbtn.setActionCommand("Exit");
        // exitbtn.addMouseListener(this);

        gbc.gridy = 3;
        menuPanel.add(exitbtn, gbc);
    }
}
