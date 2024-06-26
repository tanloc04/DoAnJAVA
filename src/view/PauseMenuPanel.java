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
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.GameManager;
import controller.HighScoreManager;
import model.HighScore;

/**
 *
 * @author ADMIN
 */
public class PauseMenuPanel extends MenuPanel implements ComponentListener{
    private JLabel gamePausedLabel;
    private JButton resumeButton;
    private JButton exitGameButton;
    private JTextField highsScoreNameTextField;
    private JLabel highScoreNameLabel;
    private JButton saveHighScoreButton;

    private GameManager gameManager;
    private GamePanel gamePanel;
    
    public PauseMenuPanel(CardLayout cardLayout, JPanel cardPanel, GameManager gameManager, GamePanel gamePanel) {
        super(cardLayout, cardPanel);
        this.gameManager = gameManager;
        this.gamePanel = gamePanel;

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 0, 150, 0, 0 };
        gridBagLayout.rowHeights = new int[] { 0, 0, 65, 65, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 1.0, 0.0, 1.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
        setLayout(gridBagLayout);

        gamePausedLabel = new JLabel("GAME IS PAUSED!");
        gamePausedLabel.setFont(new Font("Tahoma", Font.BOLD, 26));
        gamePausedLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gamePausedLabel.setForeground(Color.ORANGE);
        GridBagConstraints gbc_gamePausedLabel = new GridBagConstraints();
        gbc_gamePausedLabel.fill = GridBagConstraints.HORIZONTAL;
        gbc_gamePausedLabel.gridwidth = 3;
        gbc_gamePausedLabel.insets = new Insets(0, 0, 5, 0);
        gbc_gamePausedLabel.gridx = 0;
        gbc_gamePausedLabel.gridy = 0;
        add(gamePausedLabel, gbc_gamePausedLabel);

        highScoreNameLabel = new JLabel("Name for Highscore:");
        highScoreNameLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
        highScoreNameLabel.setForeground(Color.WHITE);
        GridBagConstraints gbc_highScoreNameLabel = new GridBagConstraints();
        gbc_highScoreNameLabel.insets = new Insets(0, 0, 5, 5);
        gbc_highScoreNameLabel.anchor = GridBagConstraints.EAST;
        gbc_highScoreNameLabel.gridx = 0;
        gbc_highScoreNameLabel.gridy = 1;
        add(highScoreNameLabel, gbc_highScoreNameLabel);

        highsScoreNameTextField = new JTextField();
        GridBagConstraints gbc_highsScoreNameTextField = new GridBagConstraints();
        gbc_highsScoreNameTextField.insets = new Insets(0, 0, 5, 5);
        gbc_highsScoreNameTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_highsScoreNameTextField.gridx = 1;
        gbc_highsScoreNameTextField.gridy = 1;
        add(highsScoreNameTextField, gbc_highsScoreNameTextField);
        highsScoreNameTextField.setColumns(10);

        saveHighScoreButton = new JButton("Save Highscore");
        GridBagConstraints gbc_saveHighScoreButton = new GridBagConstraints();
        gbc_saveHighScoreButton.fill = GridBagConstraints.VERTICAL;
        gbc_saveHighScoreButton.anchor = GridBagConstraints.WEST;
        gbc_saveHighScoreButton.insets = new Insets(0, 0, 5, 0);
        gbc_saveHighScoreButton.gridx = 2;
        gbc_saveHighScoreButton.gridy = 1;
        add(saveHighScoreButton, gbc_saveHighScoreButton);

        resumeButton = new JButton("Resume Game");
        GridBagConstraints gbc_resumeButton = new GridBagConstraints();
        gbc_resumeButton.fill = GridBagConstraints.BOTH;
        gbc_resumeButton.insets = new Insets(0, 0, 15, 5);
        gbc_resumeButton.gridx = 1;
        gbc_resumeButton.gridy = 2;
        add(resumeButton, gbc_resumeButton);

        exitGameButton = new JButton("Exit Game");
        GridBagConstraints gbc_exitGameButton = new GridBagConstraints();
        gbc_exitGameButton.fill = GridBagConstraints.BOTH;
        gbc_exitGameButton.insets = new Insets(0, 0, 15, 5);
        gbc_exitGameButton.gridx = 1;
        gbc_exitGameButton.gridy = 3;
        add(exitGameButton, gbc_exitGameButton);

        addActionListeners();
        addComponentListener(this);

        highScoreNameLabel.setVisible(false);
        highsScoreNameTextField.setVisible(false);
        saveHighScoreButton.setVisible(false);
    }
    
    private void addActionListeners() {
        resumeButton.addActionListener(ae -> {

            if (resumeButton.getText().equals("Resume Game")) {
                cardLayout.show(parentPanel, GamePanel.class.getName());
                gameManager.resumeGame();
            } else if (resumeButton.getText().equals("Restart Game")) {
                cardLayout.show(parentPanel, GamePanel.class.getName());
                gameManager.startGame(true);
            }
        });
        exitGameButton.addActionListener(ae -> {
            gameManager.stopGame();
            cardLayout.show(parentPanel, MainMenuPanel.class.getName());
            parentPanel.revalidate();
        });
        saveHighScoreButton.addActionListener(ae -> {
            HighScore highScore = new HighScore(highsScoreNameTextField.getText(), gameManager.getScore(),
                    new Date(System.currentTimeMillis()));
            HighScoreManager.getInstance().writeScoreToFile(highScore);
            highScoreNameLabel.setText("Saved!");
            highsScoreNameTextField.setVisible(false);
            saveHighScoreButton.setVisible(false);
            invalidate();
            revalidate();
            repaint();
        });
    }
    
    @Override
    public void componentResized( ComponentEvent e) {
    }

    @Override
    public void componentMoved( ComponentEvent e) {
    }

    @Override
    public void componentShown( ComponentEvent e) {
        if (gameManager.isGameOver()) {
            gamePausedLabel.setText("GAME OVER");
            gamePausedLabel.setForeground(Color.RED);
            resumeButton.setText("Restart Game");
            highScoreNameLabel.setText("Name for Highscore:");
            highScoreNameLabel.setVisible(true);
            highsScoreNameTextField.setVisible(true);
            saveHighScoreButton.setVisible(true);
        } else {
            gamePausedLabel.setText("GAME IS PAUSED!");
            gamePausedLabel.setForeground(Color.ORANGE);
            resumeButton.setText("Resume Game");
            highScoreNameLabel.setText("Name for Highscore:");
            highScoreNameLabel.setVisible(false);
            highsScoreNameTextField.setVisible(false);
            saveHighScoreButton.setVisible(false);
        }
    }

    @Override
    public void componentHidden( ComponentEvent e) {
    }
    
}
