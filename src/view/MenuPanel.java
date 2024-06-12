package view;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author ADMIN
 */
public class MenuPanel extends JPanel {
    private static final Dimension RESOLUTION = new Dimension(800, 600);
    protected Image backGroundImage;
    protected CardLayout cardLayout;
    protected JPanel parentPanel;

    public MenuPanel(CardLayout cardLayout, JPanel parentPanel) {
        setDoubleBuffered(true);
        this.cardLayout = cardLayout;
        this.parentPanel = parentPanel;
        
        try {
            InputStream is = getClass().getResourceAsStream("/resources/backgrounds/level_background_1.png");
            if (is == null) {
                System.err.println("File not found in classpath: /resources/backgrounds/level_background_1.png");
                return;
            }
            backGroundImage = ImageIO.read(is);
        
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        setMinimumSize(RESOLUTION);
        setMaximumSize(RESOLUTION);
        setPreferredSize(RESOLUTION);
        
        addComponentListener(new ComponentListener() {
            @Override
            public void componentShown(ComponentEvent e) {
                repaint();
            }
            
            @Override
            public void componentResized(ComponentEvent e) {}
            
            @Override
            public void componentMoved(ComponentEvent e) {}
            
            @Override
            public void componentHidden(ComponentEvent e) {
                repaint();
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2d = (Graphics2D) g;
        graphics2d.drawImage(backGroundImage, 0, 0, null);
    }
}
