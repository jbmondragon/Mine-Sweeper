import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.*;

public class GameOver {
    JPanel gameOverPanel;

    JLabel gamelbl, overlbl;

    JTextField nameField;
    GridBagConstraints gbc;
    JButton backtogame;

    public void createGameOver() {
        gameOverPanel = new JPanel();
        gameOverPanel.setLayout(new GridBagLayout());
        gameOverPanel.setBackground(Color.BLACK);

        gamelbl = new JLabel("Game");
        gamelbl.setFont(new Font("Serif", Font.BOLD, 80));
        gamelbl.setForeground(Color.red);
        gamelbl.setBackground(Color.BLACK);
        gamelbl.setOpaque(true);

        overlbl = new JLabel("Over!");
        overlbl.setFont(new Font("Serif", Font.BOLD, 50));
        overlbl.setForeground(Color.red);
        overlbl.setBackground(Color.BLACK);
        overlbl.setOpaque(true);

        backtogame = new JButton("Back to Game");
        backtogame.setFont(new Font("Serif", Font.BOLD, 15));
        backtogame.setForeground(new Color(0xB7C9E2));
        backtogame.setPreferredSize(new Dimension(100, 40));
        backtogame.setBackground(Color.red);
        backtogame.setOpaque(true);
        backtogame.setFocusPainted(false);

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.gridx = 0;

        gbc.gridy = 0;
        gameOverPanel.add(gamelbl, gbc);

        gbc.gridy = 1;
        gameOverPanel.add(overlbl, gbc);

        gbc.gridy = 3;
        gbc.ipadx = 200;
        gbc.ipady = 20;
        gameOverPanel.add(backtogame, gbc);
    }
}
