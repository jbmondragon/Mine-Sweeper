import javax.swing.*;
import java.awt.*;
import javax.swing.text.*;

public class Instruction {
    JPanel instructionPanel;
    JButton playbtn;

    public void createInstruction() {
        instructionPanel = new JPanel();
        instructionPanel.setLayout(new GridBagLayout());
        instructionPanel.setBackground(Color.BLACK);

        // Create a JTextPane for centered alignment
        JTextPane instructionPane = new JTextPane();
        instructionPane.setFont(new Font("Serif", Font.BOLD, 15));
        instructionPane.setForeground(new Color(0xB7C9E2));
        instructionPane.setBackground(new Color(0x5a8d03));
        instructionPane.setOpaque(true);
        instructionPane.setEditable(false);

        // Define the instruction text
        String instructions = "About the Game:\n" +
                "The main goal of the game is to overcome all the squares\n" +
                "and to complete the board without detonating any mines.\n" +
                "If there is no more available square on the board\n" +
                "that you haven't clicked, this means that you have\n" +
                "completed the game. On the other hand, once a mine appears\n" +
                "on your screen, this means that you have lost the game.";

        // Center the text
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);

        // Insert the text and apply the center alignment
        StyledDocument doc = instructionPane.getStyledDocument();
        try {
            doc.insertString(doc.getLength(), instructions, null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        // Add the JTextPane to the instruction panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        instructionPanel.add(instructionPane, gbc);

        // Play button setup
        playbtn = new JButton("Play");
        playbtn.setFont(new Font("Serif", Font.BOLD, 15));
        playbtn.setForeground(new Color(0xB7C9E2));
        playbtn.setBackground(new Color(0x5a8d03));
        playbtn.setFocusPainted(false);
        playbtn.setOpaque(true);
        playbtn.setActionCommand("Play");
        // playbtn.addMouseListener(this);

        // Add the play button to the panel
        gbc.gridy = 1;
        instructionPanel.add(playbtn, gbc);
    }
}
