package classes;

public class Weapon{
    private Position pos;
    private int height;
    private int width;

    public Weapon(Position pos, int height, int width) {
        this.pos = pos;
        this.height = height;
        this.width = width;
    }

    public Position getPosition(){
        return this.pos;
    }

    public void setPosition(Position pos) {
        this.pos = pos;
    }

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
