package classes;

public class Weapon{
    private Position pos;
    public Weapon(Position pos) {
        this.pos = pos;
    }

    Position getPosition(){
        return this.pos;
    }

    void setPosition(Position pos) {
        this.pos = pos;
    }
}
