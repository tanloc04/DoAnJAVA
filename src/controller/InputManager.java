/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import model.Direction;

//Kế thừa MouseAdapter để xử lý sự kiện chuột và triển khai KeyListener để xử lý sự kiện bàn phím.
public class InputManager extends MouseAdapter implements KeyListener {

    private static InputManager instance = null;
    
//getInstance(): Sử dụng mẫu Singleton để đảm bảo chỉ có một thể hiện của InputManager trong suốt vòng đời của ứng dụng.
    public static synchronized InputManager getInstance() {
        if (instance == null) {
            instance = new InputManager();
        }
        return instance;
    }

    
    private Point mousePoint;
    private Direction changeDirection;

    public InputManager() {
        mousePoint = new Point();
    }

    public Point getMousePoint() {
        return mousePoint;
    }

    public Direction getChangeDirection() {
        return changeDirection;
    }
    
//Phương thức mouseMoved được gọi mỗi khi con trỏ chuột di chuyển.
    @Override
    public void mouseMoved( MouseEvent e) {
//        Kiểm tra xem tọa độ x của con trỏ chuột trước khi di chuyển (mousePoint.x) 
//        có lớn hơn tọa độ x mới của con trỏ chuột (e.getPoint().x) hay không.
        if (mousePoint.x > e.getPoint().x) {
            changeDirection = Direction.LEFT;
        } else {
            changeDirection = Direction.RIGHT;
        }
//        Cập nhật tọa độ chuột
        mousePoint = e.getPoint();
    }

    @Override
    public void keyTyped( KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed( KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyReleased( KeyEvent e) {
        // TODO Auto-generated method stub

    }

}