package classes;

public class Stairs {
    Position position;
    private static int height = 20;
    private static int width = 20;
    private boolean lvl;

    public Stairs(Position position){
        this.position = position;
        this.lvl = false;
    }

    public boolean isLvl() {
        return lvl;
    }

    public void setLvl(boolean lvl) {
        this.lvl = lvl;
    }

    public Position getPosition() {
        // TODO Auto-generated method stub
        return position;
    }
}

