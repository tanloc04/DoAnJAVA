/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.GameManager;

/**
 *
 * @author ADMIN
 */
public class MainFrame extends JFrame {
    private JPanel contentPane;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    private MainMenuPanel mainMenuPanel;
    private PauseMenuPanel pauseMenuPanel;
    private HighScorePanel highScorePanel;
    private HelpPanel helpPanel;
    private ChangeSettingsPanel changeSettingsPanel;
    private AboutPanel aboutPanel;
    private CreditsPanel creditsPanel;

    private GamePanel gamePanel;
    private GameManager gameManager;
    
}
