/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import controller.GameManager;
import controller.GameMapManager;
import view.GamePanel;

/**
 *
 * @author ADMIN
 */
public class Bubble extends GameObject{
    private Random random;
    
    public Bubble() {
        try {
            image = ImageIO.read(getClass().getResource("/resources/bubble.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        random = new Random();
        isControlledByAi = true;
    }

    @Override
    public void move() {
        if (y + height < 0) {
            y = GamePanel.RESOLUTION.height + height;
            x = random.nextInt((GamePanel.RESOLUTION.width - width * 2)) + width / 2;
            return;
        }

        y -= random.nextInt(1) + 1;

        if (random.nextInt(5) == 0) {
            if (random.nextBoolean()) {
                x++;
            } else {
                x--;
            }
        }

        if (x < 5) {
            x = 5;
        }
        if (x + width > GamePanel.RESOLUTION.width - 5) {
            x = GamePanel.RESOLUTION.width - width - 5;
        }
    }

    @Override
    public void updateState( GameManager gameManager, GameMapManager gameMapManager, PlayerFish playerFish) {
    }
}
