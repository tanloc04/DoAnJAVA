/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import controller.GameManager;
import controller.GameMapManager;

/**
 *
 * @author ADMIN
 */
public abstract class GameObject {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected BufferedImage image;
    
    protected Direction lookingDirection;

    protected boolean isControlledByMouse;
    protected boolean isControlledByKeyBoard;
    protected boolean isControlledByAi;
    
    private boolean isMarkedForDestroying;

    public GameObject() {
        isControlledByMouse = false;
        isControlledByKeyBoard = false;
        isControlledByAi = false;
        isMarkedForDestroying = false;
    }
    
    //Trả về hình chữ nhật bao quanh đối tượng dựa trên tọa độ và kích thước của nó
    public Rectangle getBoundingBox() {
        return new Rectangle(x, y, width, height);
    }
    
    //Kiểm tra xem đối tượng có giao nhau với hình chữ nhật được truyền vào hay không
    public boolean intersects( Rectangle rectangle) {
        return getBoundingBox().intersects(rectangle);
    }
    
    //Thiết lập hướng di chuyển của đối tượng và lật hình ảnh nếu cần thiết
    public void setDirection( Direction changeDirection) {
        if ((changeDirection == Direction.LEFT && lookingDirection == Direction.RIGHT)
                || (changeDirection == Direction.RIGHT && lookingDirection == Direction.LEFT)) {
            flipHorizontally();
            lookingDirection = changeDirection;
        }

        if ((changeDirection == Direction.UP && lookingDirection == Direction.DOWN)
                || (changeDirection == Direction.DOWN && lookingDirection == Direction.UP)) {
            flipVertically();
            lookingDirection = changeDirection;
        }
    }
    
    //Lật hình ảnh theo chiều dọc
    public void flipVertically() {
        AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
        tx.translate(0, -image.getHeight(null));
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        image = op.filter(image, null);
        if (lookingDirection == Direction.LEFT) {
            lookingDirection = Direction.RIGHT;
        } else if (lookingDirection == Direction.RIGHT) {
            lookingDirection = Direction.LEFT;
        }
    }
    
    //Lật hình ảnh theo chiều ngang
    public void flipHorizontally() {
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-image.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        image = op.filter(image, null);
        if (lookingDirection == Direction.UP) {
            lookingDirection = Direction.DOWN;
        } else if (lookingDirection == Direction.DOWN) {
            lookingDirection = Direction.UP;
        }
    }
    
    public abstract void move();

    public abstract void updateState( GameManager gameManager, GameMapManager gameMapManager, PlayerFish playerFish);

    public void setPositon( int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Thiết lập vị trí của một đối tượng theo một điểm
    public void setPosition( Point point) {
        this.x = point.x;
        this.y = point.y;
    }

    public int getX() {
        return x;
    }

    public void setX( int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY( int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth( int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight( int height) {
        this.height = height;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage( BufferedImage image) {
        this.image = image;
    }

    public boolean isControlledByMouse() {
        return isControlledByMouse;
    }

    public void setControlledByMouse( boolean isControlledByMouse) {
        this.isControlledByMouse = isControlledByMouse;
    }

    public boolean isControlledByKeyBoard() {
        return isControlledByKeyBoard;
    }

    public void setControlledByKeyBoard( boolean isControlledByKeyBoard) {
        this.isControlledByKeyBoard = isControlledByKeyBoard;
    }

    public boolean isControlledByAi() {
        return isControlledByAi;
    }

    public void setControlledByAi( boolean isControlledByAi) {
        this.isControlledByAi = isControlledByAi;
    }

    public boolean isMarkedForDestroying() {
        return isMarkedForDestroying;
    }

    public void setMarkedForDestroying( boolean isMarkedForDestroying) {
        this.isMarkedForDestroying = isMarkedForDestroying;
    }
}
