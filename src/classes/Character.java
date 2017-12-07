package classes;

import tools.Direction;

public class Character {

	private Position position;
	private String name;
	private Direction direction;
	private int hp;
	private int width;
	private int height;
	private double velocityX;
	private double velocityY;
	private int step;

	public Character(Position position, String name, int hp) {
		this.position = position;
		this.name = name;
		this.hp = hp;
		this.step = 0;
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

	public double getVelocityX() {
		return velocityX;
	}

	public void setVelocityX(double velocityX) {
		this.velocityX = velocityX;
	}

	public double getVelocityY() {
		return velocityY;
	}

	public void setVelocityY(double velocityY) {
		this.velocityY = velocityY;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public int getStepDivided(){
		return this.step;
	}
}
