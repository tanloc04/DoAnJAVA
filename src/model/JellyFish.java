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
public class JellyFish extends SpecialFish{
    private static final int size = 5;

    public JellyFish() {
        try {
            image = ImageIO.read(getClass().getResource("/resources/fishes/jellyfish.png"));
            width = image.getWidth();
            height = image.getHeight();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Random random = new Random();
        x = random.nextInt((GamePanel.RESOLUTION.width - width * 2)) + width / 2;
        y = GamePanel.RESOLUTION.height + 1;
        lookingDirection = Direction.UP;
    }
    
    @Override
    public void move() {
        if (y + height < 0) {
            setMarkedForDestroying(true);
            return;
        }

        Random random = new Random();
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
        if (playerFish.getFrenzy() == 100) {
            playerFish.setScore(playerFish.getScore() + size * 5);
            setMarkedForDestroying(true);
        } else {
            playerFish.setLives(playerFish.getLives() - 1);
            playerFish.setFrenzy(0);
            playerFish.setDamaged(true);
            playerFish.setCurrentlyActive(false);
        }
    }
}
