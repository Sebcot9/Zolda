package data;

import java.util.ArrayList;
import java.util.Random;

import classes.Allies;
import classes.Enemies;
import classes.Heroes;
import classes.Map;
import classes.Pets;
import classes.Position;
import specifications.DataService;
import tools.HardCodedParameters;

public class Data implements DataService {

	private Heroes lonk;
	private ArrayList <Enemies> enemies;
	private ArrayList <Allies> allies;
	private ArrayList <Pets> pets;
	private Map maps;
	private int minX, maxX, minY, maxY;



	public void setStepNumber(int stepNumber) {
		this.stepNumber = stepNumber;
	}

	private int stepNumber;

	public Data(){}

	/*@Override
	public void init(){

	}*/
	@Override
	public void init(){
		lonk = new Heroes(new Position(512,450), "Lonk", 0);
		maps = new Map(1024,876);
		enemies = new ArrayList<Enemies>();
		Random gen= new Random();
		int x = (int)(gen.nextInt((int)(getMap().getWidth()*.6))+getMap().getWidth()*.1);
		int y = (int)(gen.nextInt((int)(getMap().getHeight()*.6))+getMap().getHeight()*.1);
		Enemies enemy = new Enemies(new Position(x,y),"Bakemono", 2);
		enemies.add(enemy);

		allies = new ArrayList<Allies>();
		pets = new ArrayList<Pets>();
		minX = HardCodedParameters.minX;
		maxX = HardCodedParameters.maxX;
		minY = HardCodedParameters.minY;
		maxY = HardCodedParameters.maxY;
		stepNumber = 0;

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
	public Map getMap() {
		return maps;
	}
	@Override
	public void setMaps(Map maps) {
		this.maps = maps;
	}

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
}
