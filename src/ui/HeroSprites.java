package ui;

import java.awt.geom.Rectangle2D;

public class HeroSprites {
    private Rectangle2D[] left;
    private Rectangle2D[] right;
    private Rectangle2D[] up;
    private Rectangle2D[] down;

    public HeroSprites(Rectangle2D[] left,Rectangle2D[] right,Rectangle2D[] up,Rectangle2D[] down) {
        this.left = left;
        this.right = right;
        this.up = up;
        this.down = down;
    }

    public Rectangle2D[] getLeft() {
        return left;
    }

    public Rectangle2D[] getRight() {
        return right;
    }

    public Rectangle2D[] getUp() {
        return up;
    }

    public Rectangle2D[] getDown() {
        return down;
    }
}
