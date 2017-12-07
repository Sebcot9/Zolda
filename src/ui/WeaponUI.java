package ui;


import javafx.geometry.Rectangle2D;

import java.util.ArrayList;

public class WeaponUI {
    Rectangle2D left,right,up,down;
    ArrayList<Rectangle2D> spaceRight , spaceLeft , spaceUp , spaceDown;

    public WeaponUI() {
        left = new Rectangle2D(0,0,16,16);
        up = new Rectangle2D(17,0,16,16);
        right = new Rectangle2D(17,0,16,16);
        down = new Rectangle2D(34,0,16,16);

        spaceRight = new ArrayList<>();
        spaceRight.add(new Rectangle2D(99, 165, 32, 32));
        spaceRight.add(new Rectangle2D(198, 165, 32, 32));
        spaceRight.add(new Rectangle2D(246, 0, 32, 32));
        spaceRight.add(new Rectangle2D(231, 33, 32, 32));
        spaceRight.add(new Rectangle2D(40, 10, 32, 32));

        spaceLeft = new ArrayList<>();
        spaceLeft.add(new Rectangle2D(0, 183, 32, 32));
        spaceLeft.add(new Rectangle2D(66, 182, 32, 32));
        spaceLeft.add(new Rectangle2D(165, 99, 32, 32));
        spaceLeft.add(new Rectangle2D(132, 132, 32, 32));
        spaceLeft.add(new Rectangle2D(0, 39, 32, 32));


        spaceUp = new ArrayList<>();
        spaceUp.add(new Rectangle2D(297, 33, 32, 32));
        spaceUp.add(new Rectangle2D(297, 99, 32, 32));
        spaceUp.add(new Rectangle2D(33, 82, 32, 32));
        spaceUp.add(new Rectangle2D(0, 82, 32, 34));
        spaceUp.add(new Rectangle2D(66, 82, 32, 33));

        spaceDown = new ArrayList<>();
        spaceDown.add(new Rectangle2D(180, 0, 32, 32));
        spaceDown.add(new Rectangle2D(114, 0, 32, 32));
        spaceDown.add(new Rectangle2D(0, 33, 32, 48));
        spaceDown.add(new Rectangle2D(33, 33, 32, 48));
        spaceDown.add(new Rectangle2D(66, 33, 32, 32));

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

    public ArrayList<Rectangle2D> getSpaceRight() {
        return spaceRight;
    }

    public void setSpaceRight(ArrayList<Rectangle2D> spaceRight) {
        this.spaceRight = spaceRight;
    }

    public ArrayList<Rectangle2D> getSpaceLeft() {
        return spaceLeft;
    }

    public void setSpaceLeft(ArrayList<Rectangle2D> spaceLeft) {
        this.spaceLeft = spaceLeft;
    }

    public ArrayList<Rectangle2D> getSpaceUp() {
        return spaceUp;
    }

    public void setSpaceUp(ArrayList<Rectangle2D> spaceUp) {
        this.spaceUp = spaceUp;
    }

    public ArrayList<Rectangle2D> getSpaceDown() {
        return spaceDown;
    }

    public void setSpaceDown(ArrayList<Rectangle2D> spaceDown) {
        this.spaceDown = spaceDown;
    }
}
