package ui;


import javafx.geometry.Rectangle2D;

public class WeaponUI {
    Rectangle2D left,right,up,down;

    public WeaponUI() {
        left = new Rectangle2D(0,0,16,16);
        up = new Rectangle2D(17,0,16,16);
        right = new Rectangle2D(17,0,16,16);
        down = new Rectangle2D(34,0,16,16);

    }

    public Rectangle2D getLeft() {
        return left;
    }

    public void setLeft(Rectangle2D left) {
        this.left = left;
    }

    public Rectangle2D getRight() {
        return right;
    }

    public void setRight(Rectangle2D right) {
        this.right = right;
    }

    public Rectangle2D getUp() {
        return up;
    }

    public void setUp(Rectangle2D up) {
        this.up = up;
    }

    public Rectangle2D getDown() {
        return down;
    }

    public void setDown(Rectangle2D down) {
        this.down = down;
    }
}
