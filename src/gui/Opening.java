import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.*;

public class Opening {
    JPanel instructionPanel;
    JPanel openingPanel;

    JLabel welcomelbl, tolbl, damath53lbl;

    JTextField nameField;
    GridBagConstraints gbc;
    JButton nextButtonOpening;

    public void createOpening() {
        openingPanel = new JPanel();
        openingPanel.setLayout(new GridBagLayout());
        openingPanel.setBackground(Color.BLACK);

        welcomelbl = new JLabel("WELCOME");
        welcomelbl.setFont(new Font("Serif", Font.BOLD, 80));
        welcomelbl.setForeground(new Color(0x5a8d03));
        welcomelbl.setBackground(Color.BLACK);
        welcomelbl.setOpaque(true);

        tolbl = new JLabel("TO");
        tolbl.setFont(new Font("Serif", Font.BOLD, 50));
        tolbl.setForeground(new Color(0x5a8d03));
        tolbl.setBackground(Color.BLACK);
        tolbl.setOpaque(true);

        damath53lbl = new JLabel("Mine Sweeper");
        damath53lbl.setFont(new Font("Serif", Font.BOLD, 80));
        damath53lbl.setForeground(new Color(0x5a8d03));
        damath53lbl.setBackground(Color.BLACK);
        damath53lbl.setOpaque(true);

        nextButtonOpening = new JButton("Next");
        nextButtonOpening.setFont(new Font("Serif", Font.BOLD, 15));
        nextButtonOpening.setForeground(new Color(0xB7C9E2));
        nextButtonOpening.setPreferredSize(new Dimension(100, 40));
        nextButtonOpening.setBackground(new Color(0x5a8d03));
        nextButtonOpening.setOpaque(true);
        nextButtonOpening.setFocusPainted(false);

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.gridx = 0;

        gbc.gridy = 0;
        openingPanel.add(welcomelbl, gbc);

        gbc.gridy = 1;
        openingPanel.add(tolbl, gbc);

        gbc.gridy = 2;
        openingPanel.add(damath53lbl, gbc);

        gbc.gridy = 3;
        gbc.ipadx = 200;
        gbc.ipady = 20;
        openingPanel.add(nextButtonOpening, gbc);
    }
}
