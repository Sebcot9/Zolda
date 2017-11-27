package data;

import java.util.ArrayList;

import classes.Allies;
import classes.Enemies;
import classes.Heroes;
import classes.Maps;
import classes.Pets;

public class Data {

	Heroes lonk;
	ArrayList <Enemies> enemies;
	ArrayList <Allies> allies;
	ArrayList <Pets> pets;
	Maps maps;
	
	public Data(){};
	
	/*@Override
	public void init(){
		
	}*/
	
	public Heroes getLonk() {
		return lonk;
	}
	public void setLonk(Heroes lonk) {
		this.lonk = lonk;
	}
	public ArrayList<Enemies> getEnemies() {
		return enemies;
	}
	public void setEnemies(ArrayList<Enemies> enemies) {
		this.enemies = enemies;
	}
	public ArrayList<Allies> getAllies() {
		return allies;
	}
	public void setAllies(ArrayList<Allies> allies) {
		this.allies = allies;
	}
	public ArrayList<Pets> getPets() {
		return pets;
	}
	public void setPets(ArrayList<Pets> pets) {
		this.pets = pets;
	}
	public Maps getMaps() {
		return maps;
	}
	public void setMaps(Maps maps) {
		this.maps = maps;
	}
	
	
}
