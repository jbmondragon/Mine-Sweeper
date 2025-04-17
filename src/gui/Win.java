import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.*;

public class Win {
    JPanel winPanel;

    JLabel youlbl, winlbl;

    JTextField nameField;
    GridBagConstraints gbc;
    JButton backtogame;

    public void createWin() {
        winPanel = new JPanel();
        winPanel.setLayout(new GridBagLayout());
        winPanel.setBackground(Color.BLACK);

        youlbl = new JLabel("You");
        youlbl.setFont(new Font("Serif", Font.BOLD, 80));
        youlbl.setForeground(new Color(0x5a8d03));
        youlbl.setBackground(Color.BLACK);
        youlbl.setOpaque(true);

        winlbl = new JLabel("Win!");
        winlbl.setFont(new Font("Serif", Font.BOLD, 50));
        winlbl.setForeground(new Color(0x5a8d03));
        winlbl.setBackground(Color.BLACK);
        winlbl.setOpaque(true);

        backtogame = new JButton("Back to Game");
        backtogame.setFont(new Font("Serif", Font.BOLD, 15));
        backtogame.setForeground(new Color(0xB7C9E2));
        backtogame.setPreferredSize(new Dimension(100, 40));
        backtogame.setBackground(new Color(0x5a8d03));
        backtogame.setOpaque(true);
        backtogame.setFocusPainted(false);

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.gridx = 0;

        gbc.gridy = 0;
        winPanel.add(youlbl, gbc);

        gbc.gridy = 1;
        winPanel.add(winlbl, gbc);

        gbc.gridy = 3;
        gbc.ipadx = 200;
        gbc.ipady = 20;
        winPanel.add(backtogame, gbc);
    }
}
