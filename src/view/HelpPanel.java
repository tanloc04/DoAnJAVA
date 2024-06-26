/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author ADMIN
 */
public class HelpPanel extends MenuPanel{
    private JButton backButton;
    private JTextArea textArea;
    
    public HelpPanel(CardLayout cardLayout, JPanel cardPanel) {
        super(cardLayout, cardPanel);

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 150, 0, 0 };
        gridBagLayout.rowHeights = new int[] { 0, 65, 0 };
        gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
        setLayout(gridBagLayout);
        
        textArea = new JTextArea("Dùng chuột để di chuyển con cá. \n"
                + "Ăn các con cá có kích cỡ bé hơn để ghi điểm và cố gắng đừng để mất chuỗi frenzy");
        textArea.setFont(new Font("Tahoma", Font.BOLD, 12));
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setBackground(new Color(255, 255, 255, 150));
        GridBagConstraints gbc_textArea = new GridBagConstraints();
        gbc_textArea.gridwidth = 2;
        gbc_textArea.insets = new Insets(15, 15, 15, 15);
        gbc_textArea.fill = GridBagConstraints.BOTH;
        gbc_textArea.gridx = 0;
        gbc_textArea.gridy = 0;
        add(textArea, gbc_textArea);

        backButton = new JButton("Back");
        GridBagConstraints gbc_backButton = new GridBagConstraints();
        gbc_backButton.insets = new Insets(0, 15, 15, 15);
        gbc_backButton.fill = GridBagConstraints.BOTH;
        gbc_backButton.gridx = 0;
        gbc_backButton.gridy = 1;
        add(backButton, gbc_backButton);

        addActionListenres();
    }
    
    private void addActionListenres() {
        backButton.addActionListener(ae -> {
            cardLayout.show(parentPanel, MainMenuPanel.class.getName());
        });
    }
}
