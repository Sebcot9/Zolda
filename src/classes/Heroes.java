package classes;

public class Heroes extends Character
{
	private Weapon weapon;
	public Heroes(Position position, String name, int hp) {
		super(position, name, hp);
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public Weapon getWeapon(){
		return this.weapon;
	}
}
