import javax.swing.*;
import java.awt.*;

public class Level {
    JPanel levelPanel;
    JButton easybtn, mediumbtn, hardbtn;
    GridBagConstraints gbc;

    public void createLevel() {

        if (levelPanel == null) {
            levelPanel = new JPanel();
            levelPanel.setLayout(new GridBagLayout());
            levelPanel.setBackground(Color.BLACK);

            Dimension buttonSize = new Dimension(100, 40); // Set preferred width and height for buttons

            easybtn = new JButton("Easy");
            easybtn.setFont(new Font("Serif", Font.BOLD, 15));
            easybtn.setForeground(new Color(0xB7C9E2));
            easybtn.setBackground(new Color(0x5a8d03));
            easybtn.setFocusPainted(false);
            easybtn.setOpaque(true);
            easybtn.setPreferredSize(buttonSize);
            // easybtn.addMouseListener(this);

            gbc = new GridBagConstraints();
            gbc.insets = new Insets(4, 2, 2, 2);
            gbc.gridx = 0;

            gbc.gridy = 0;
            levelPanel.add(easybtn, gbc);

            mediumbtn = new JButton("Medium");
            mediumbtn.setFont(new Font("Serif", Font.BOLD, 15));
            mediumbtn.setForeground(new Color(0xB7C9E2));
            mediumbtn.setBackground(new Color(0x5a8d03));
            mediumbtn.setFocusPainted(false);
            mediumbtn.setOpaque(true);
            mediumbtn.setPreferredSize(buttonSize);
            // mediumbtn.addMouseListener(this);

            gbc.gridy = 1;
            levelPanel.add(mediumbtn, gbc);

            hardbtn = new JButton("Hard");
            hardbtn.setFont(new Font("Serif", Font.BOLD, 15));
            hardbtn.setForeground(new Color(0xB7C9E2));
            hardbtn.setBackground(new Color(0x5a8d03));
            hardbtn.setFocusPainted(false);
            hardbtn.setOpaque(true);
            hardbtn.setPreferredSize(buttonSize);
            hardbtn.setActionCommand("Exit");
            // hardbtn.addMouseListener(this);

            gbc.gridy = 2;
            levelPanel.add(hardbtn, gbc);

        }
    }
}
