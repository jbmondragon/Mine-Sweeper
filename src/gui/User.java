import javax.swing.*;
import java.awt.*;
import javax.swing.text.*;

public class User {
    JPanel userPanel;
    JLabel userlbl;

    JButton okbtn;

    String name;
    JTextField nameField;
    GridBagConstraints gbc;

    public void askUserDetails() {
        userPanel = new JPanel();
        userPanel.setLayout(new GridBagLayout());
        userPanel.setBackground(Color.BLACK);

        userlbl = new JLabel("Enter your name:");
        userlbl.setFont(new Font("Serif", Font.BOLD, 50));
        userlbl.setForeground(new Color(0x5a8d03));
        userlbl.setBackground(Color.BLACK);
        userlbl.setOpaque(true);

        // Create a text field for user input
        nameField = new JTextField(15); // You can adjust the width here
        nameField.setFont(new Font("Serif", Font.PLAIN, 20));
        nameField.setForeground(Color.BLACK);
        nameField.setBackground(new Color(0xFFFFFF));
        nameField.setPreferredSize(new Dimension(200, 30));

        okbtn = new JButton("Ok");
        okbtn.setFont(new Font("Serif", Font.BOLD, 15));
        okbtn.setForeground(new Color(0xB7C9E2));
        okbtn.setPreferredSize(new Dimension(100, 40));
        okbtn.setBackground(new Color(0x5a8d03));
        okbtn.setOpaque(true);
        okbtn.setFocusPainted(false);
        // okbtn.addMouseListener(this);

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        userPanel.add(userlbl, gbc);

        gbc.gridy = 1;
        userPanel.add(nameField, gbc); // Add the text field to the panel

        gbc.gridy = 2;
        userPanel.add(okbtn, gbc);
    }
}
