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
            // Đọc và tải hình ảnh của "bubble" từ file
            image = ImageIO.read(getClass().getResource("/resources/bubble.png"));
        } catch (IOException e) {
            // In lỗi ra màn hình nếu có vấn đề khi tải hình ản
            e.printStackTrace();
        }

        random = new Random();
        isControlledByAi = true;
    }

    @Override
    public void move() {
        // Kiểm tra nếu bubble đã di chuyển ra ngoài phía trên màn hình
        if (y + height < 0) {
            // Đặt lại vị trí y của bubble ở phía dưới màn hình
            y = GamePanel.RESOLUTION.height + height;
            // Đặt lại vị trí x của bubble một cách ngẫu nhiên
            x = random.nextInt((GamePanel.RESOLUTION.width - width * 2)) + width / 2;
            return;
        }

        // Di chuyển bubble lên trên theo hướng y với khoảng cách ngẫu nhiên
        y -= random.nextInt(1) + 1;

        // Di chuyển bubble theo hướng x ngẫu nhiên
        if (random.nextInt(5) == 0) {
            if (random.nextBoolean()) {
                x++;
            } else {
                x--;
            }
        }

        // Kiểm tra và điều chỉnh vị trí x để đảm bảo bubble không ra ngoài màn hình
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
