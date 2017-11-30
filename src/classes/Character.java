package classes;

public abstract class Character {

	private Position position;
	private String name;
	private int hp;
	private int width;
	private int height;

	
	public Character(Position position, String name, int hp) {
		//super();
		this.position = position;
		this.name = name;
		this.hp = hp;

	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public void setWidth(int width){this.width = width;}
	public void setHeight(int height){this.height = height;}
	public int getWidth(){ return this.width; }
	public int getHeight(){ return this.height; }
	
}
