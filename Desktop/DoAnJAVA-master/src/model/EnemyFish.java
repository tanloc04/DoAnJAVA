/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.awt.Point;
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
public class EnemyFish extends Fish{
    private int size;
    
    public EnemyFish(int size) {
        this.size = size;
        try {
            // Đọc và tải hình ảnh của cá địch từ file
            image = ImageIO.read(getClass().getResource("/resources/fishes/enemy_fish_" + size + ".png"));
            width = image.getWidth();
            height = image.getHeight();
        } catch (IOException e) {
            // In lỗi ra màn hình nếu có vấn đề khi tải hình ảnh
            e.printStackTrace();
        }

        lookingDirection = Direction.LEFT;
    }
    
    // Hàm khởi tạo với kích thước và vị trí của kẻ thù
    public EnemyFish(int size, Point position) {
        this(size);
        this.x = position.x;
        this.y = position.y;
    }
    
    // Phương thức di chuyển của kẻ thù
    public void move() {
        Random random = new Random();
        if (lookingDirection == Direction.LEFT) {
            // Di chuyển sang trái
            x -= random.nextInt(1) + 1;
        } else if (lookingDirection == Direction.RIGHT) {
            // Di chuyển sang phải
            x += random.nextInt(1) + 1;
        }

        // Di chuyển ngẫu nhiên theo hướng y
        if (random.nextInt(5) == 0) {
            if (random.nextBoolean()) {
                y++;
            } else {
                y--;
            }
        }

        // Nếu cá di chuyển ra ngoài phía phải màn hình, xuất hiện lại từ phía trái
        if (x > GamePanel.RESOLUTION.width) {
            x = -width;
        }
        // Nếu cá di chuyển ra ngoài phía trái màn hình, xuất hiện lại từ phía phải
        if (x + width < 0) {
            x = GamePanel.RESOLUTION.width;
        } else {
            // Nếu cá di chuyển ra ngoài phía trên hoặc dưới màn hình, đánh dấu để hủy
            if (y > GamePanel.RESOLUTION.height || y + height < 0) {
                setMarkedForDestroying(true);
            }
        }
    }
    
    public int getSize() {
        return size;
    }

    public void setSize( int size) {
        this.size = size;
    }

    // Phương thức cập nhật trạng thái của cá địch
    @Override
    public void updateState( GameManager gameManager, GameMapManager gameMapManager, PlayerFish playerFish) {
        // Nếu kích thước của kẻ thù nhỏ hơn cá người chơi hoặc cá người chơi đang ở trạng thái điên cuồng
        if (size < playerFish.getSize() || playerFish.getFrenzy() == 100) {
            // Tăng trưởng, điểm điên cuồng và điểm số của cá người chơi
            playerFish.setGrowth(playerFish.getGrowth() + (size + 1) * 5);
            playerFish.setFrenzy(playerFish.getFrenzy() + (size + 1) * 2);
            playerFish.setScore(playerFish.getScore() + (size + 1) * 5);
            // Đánh dấu kẻ thù để hủy
            setMarkedForDestroying(true);
        } else {
            // Giảm mạng sống của cá người chơi, đặt trạng thái bị hư hại và không hoạt động
            playerFish.setLives(playerFish.getLives() - 1);
            playerFish.setFrenzy(0);
            playerFish.setDamaged(true);
            playerFish.setCurrentlyActive(false);
        }
    }
}
