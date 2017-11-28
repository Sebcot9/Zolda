package classes;

public abstract class Character {

	Position position;
	String name;
	int hp;
	
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
	
}
