package data;

import java.util.ArrayList;
import java.util.Random;

import classes.*;
import specifications.DataService;
import tools.Direction;
import tools.HardCodedParameters;

public class Data implements DataService {

	private Heroes lonk;
	private ArrayList <Enemies> enemies;
	private ArrayList <Allies> allies;
	private ArrayList <Pets> pets; //Unused
	private Map map;
	private int minX, maxX, minY, maxY;
	private int stepNumber;
	private Random gen;

	public Data(){}



	@Override
	public void init(){
		lonk = new Heroes(new Position(30,30), "Lonk", 5);
		lonk.setWeapon(new Weapon(new Position(35,30),20,20));
		lonk.setHeight(30);
		lonk.setWidth(30);
		lonk.setDirection(Direction.RIGHT);
		lonk.setVelocityX(0);
		lonk.setVelocityY(0);
		map = new Map(1024,876);
		enemies = new ArrayList<>();
		gen= new Random();
		//Generate a random position for the 1st enemy
		int x = (int)(gen.nextInt((int)(getMap().getWidth()*.6))+getMap().getWidth()*.1);
		int y = (int)(gen.nextInt((int)(getMap().getHeight()*.6))+getMap().getHeight()*.1);
		Enemies enemy = new Enemies(new Position(x,y),"Bakemono", 2);

		enemies.add(enemy);
		allies = new ArrayList<>();
		minX = HardCodedParameters.minX;
		maxX = HardCodedParameters.maxX;
		minY = HardCodedParameters.minY;
		maxY = HardCodedParameters.maxY;
		stepNumber = 0;
		pets = new ArrayList<>(); //Unused

	}
	@Override
	public Heroes getLonk() {
		return lonk;
	}
	@Override
	public void setLonk(Heroes lonk) {
		this.lonk = lonk;
	}
	@Override
	public ArrayList<Enemies> getEnemies() {
		return enemies;
	}
	@Override
	public void setEnemies(ArrayList<Enemies> enemies) {
		this.enemies = enemies;
	}
	@Override
	public ArrayList<Allies> getAllies() {
		return allies;
	}
	@Override
	public void setAllies(ArrayList<Allies> allies) {
		this.allies = allies;
	}
	@Override
	public ArrayList<Pets> getPets() {
		return pets;
	}
	@Override
	public void setPets(ArrayList<Pets> pets) {
		this.pets = pets;
	}
	@Override
	public Map getMap() { return map; }
	@Override
	public void setMaps(Map map) { this.map = map; }
	@Override
	public int getMinX() {
		return minX;
	}
	@Override
	public int getMaxX() {
		return maxX;
	}
	@Override
	public int getMinY() {
		return minY;
	}
	@Override
	public int getMaxY() {
		return maxY;
	}
	@Override
	public int getLinkHeight() {
		return this.lonk.getHeight();
	}
	@Override
	public int getLinkWidth() {
		return this.lonk.getWidth();
	}
	@Override
	public int getStepNumber() {
		return stepNumber;
	}
	@Override
	public Position getWeaponPosition() {
		return this.lonk.getWeapon().getPosition();
	}
	@Override
	public void setWeaponPosition(Position pos) {
		this.lonk.getWeapon().setPosition(pos);
	}
	public void setStepNumber(int stepNumber) {
		this.stepNumber = stepNumber;
	}
}
