package view;

import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.GameManager;
import java.awt.Color;

/**
 *
 * @author ADMIN
 */
public class MainMenuPanel extends MenuPanel {
    private JButton startNewGameButton;
    private JButton highScoresButton;
    private JButton helpButton;
    private JButton settingsButton;
    private JButton aboutButton;
    private JButton creditsButton;
    private JButton exitButton;
    private GameManager gameManager;
    private GamePanel gamePanel;

    public MainMenuPanel(CardLayout cardLayout, JPanel cardPanel, GameManager gameManager, GamePanel gamePanel) {
        super(cardLayout, cardPanel);
        this.gameManager = gameManager;
        this.gamePanel = gamePanel;

        try {
            InputStream is = getClass().getResourceAsStream("/resources/backgrounds/main_menu_background.jpg");
            if (is == null) {
                System.err.println("File not found in classpath: /resources/backgrounds/main_menu_background.jpg");
                return;
            } else {
                System.out.println("File found in classpath: /resources/backgrounds/main_menu_background.jpg");
            }
            backGroundImage = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 0, 150, 50 };
        gridBagLayout.rowHeights = new int[] { 0, 65, 65, 65, 65, 65, 65, 65, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 1.0, 0.0 };
        gridBagLayout.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
        setLayout(gridBagLayout);

        startNewGameButton = new JButton("New Game");
        GridBagConstraints gbc_newGameButton = new GridBagConstraints();
        gbc_newGameButton.fill = GridBagConstraints.BOTH;
        gbc_newGameButton.insets = new Insets(0, 0, 15, 0);
        gbc_newGameButton.gridx = 1;
        gbc_newGameButton.gridy = 1;
        add(startNewGameButton, gbc_newGameButton);
        startNewGameButton.setBackground(Color.BLUE);
        startNewGameButton.setForeground(Color.WHITE);

        highScoresButton = new JButton("High Scores");
        GridBagConstraints gbc_highScoresButton = new GridBagConstraints();
        gbc_highScoresButton.fill = GridBagConstraints.BOTH;
        gbc_highScoresButton.insets = new Insets(0, 0, 15, 0);
        gbc_highScoresButton.gridx = 1;
        gbc_highScoresButton.gridy = 2;
        add(highScoresButton, gbc_highScoresButton);
        highScoresButton.setBackground(Color.BLUE);
        highScoresButton.setForeground(Color.WHITE);

        helpButton = new JButton("Help");
        GridBagConstraints gbc_helpButton = new GridBagConstraints();
        gbc_helpButton.fill = GridBagConstraints.BOTH;
        gbc_helpButton.insets = new Insets(0, 0, 15, 0);
        gbc_helpButton.gridx = 1;
        gbc_helpButton.gridy = 3;
        add(helpButton, gbc_helpButton);
        helpButton.setBackground(Color.BLUE);
        helpButton.setForeground(Color.WHITE);

        settingsButton = new JButton("Settings");
        GridBagConstraints gbc_settingsButton = new GridBagConstraints();
        gbc_settingsButton.fill = GridBagConstraints.BOTH;
        gbc_settingsButton.insets = new Insets(0, 0, 15, 0);
        gbc_settingsButton.gridx = 1;
        gbc_settingsButton.gridy = 4;
        add(settingsButton, gbc_settingsButton);
        settingsButton.setBackground(Color.BLUE);
        settingsButton.setForeground(Color.WHITE);

        aboutButton = new JButton("About");
        GridBagConstraints gbc_aboutButton = new GridBagConstraints();
        gbc_aboutButton.fill = GridBagConstraints.BOTH;
        gbc_aboutButton.insets = new Insets(0, 0, 15, 0);
        gbc_aboutButton.gridx = 1;
        gbc_aboutButton.gridy = 5;
        add(aboutButton, gbc_aboutButton);
        aboutButton.setBackground(Color.BLUE);
        aboutButton.setForeground(Color.WHITE);

        creditsButton = new JButton("Credits");
        GridBagConstraints gbc_creditsButton = new GridBagConstraints();
        gbc_creditsButton.fill = GridBagConstraints.BOTH;
        gbc_creditsButton.insets = new Insets(0, 0, 15, 0);
        gbc_creditsButton.gridx = 1;
        gbc_creditsButton.gridy = 6;
        add(creditsButton, gbc_creditsButton);
        creditsButton.setBackground(Color.BLUE);
        creditsButton.setForeground(Color.WHITE);

        exitButton = new JButton("Exit");
        GridBagConstraints gbc_exitButton = new GridBagConstraints();
        gbc_exitButton.fill = GridBagConstraints.BOTH;
        gbc_exitButton.insets = new Insets(0, 0, 15, 0);
        gbc_exitButton.gridx = 1;
        gbc_exitButton.gridy = 7;
        add(exitButton, gbc_exitButton);
        exitButton.setBackground(Color.red);
        exitButton.setForeground(Color.WHITE);

        setActionListeners();
    }

    private void setActionListeners() {
        startNewGameButton.addActionListener(ae -> {
            cardLayout.show(parentPanel, GamePanel.class.getName());
            gameManager.startGame(true);
        });
        highScoresButton.addActionListener(ae -> {
            cardLayout.show(parentPanel, HighScorePanel.class.getName());
        });
        helpButton.addActionListener(ae -> {
            cardLayout.show(parentPanel, HelpPanel.class.getName());
        });
        settingsButton.addActionListener(ae -> {
            cardLayout.show(parentPanel, ChangeSettingsPanel.class.getName());
        });
        aboutButton.addActionListener(ae -> {
            cardLayout.show(parentPanel, AboutPanel.class.getName());
        });
        creditsButton.addActionListener(ae -> {
            cardLayout.show(parentPanel, CreditsPanel.class.getName());
        });
        exitButton.addActionListener(ae -> {
            int result = JOptionPane.showConfirmDialog(MainMenuPanel.this, "Are you sure you want to exit?",
                    "Exit Game?", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }
}
