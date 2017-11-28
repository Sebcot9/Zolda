package data;

import java.util.ArrayList;

import classes.Allies;
import classes.Enemies;
import classes.Heroes;
import classes.Maps;
import classes.Pets;
import classes.Position;
import specifications.DataService;

public class Data implements DataService {

	private Heroes lonk;
	private ArrayList <Enemies> enemies;
	private ArrayList <Allies> allies;
	private ArrayList <Pets> pets;
	private Maps maps;
	
	public Data(){}
	
	/*@Override
	public void init(){
		
	}*/
	@Override
	public void init(){
		lonk = new Heroes(new Position(512,512), "Lonk", 0);
		maps = new Maps(1024,876);
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
	public Maps getMaps() {
		return maps;
	}
	@Override
	public void setMaps(Maps maps) {
		this.maps = maps;
	}
	
	
}
