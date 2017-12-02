package classes;

public class Weapon{
    private Position pos;
    public Weapon(Position pos) {
        this.pos = pos;
    }

    public Position getPosition(){
        return this.pos;
    }

    public void setPosition(Position pos) {
        this.pos = pos;
    }
}
