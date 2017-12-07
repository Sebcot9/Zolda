package classes;

import java.util.ArrayList;

public class Map {
	private int width ;
	private int height ;
	private ArrayList <Obstacle> obstacles;
	private ArrayList <Holes> holes;
	private ArrayList <Stairs> stairs;

	public Map(int width, int height){
		this.width = width;
		this.height = height;
		this.obstacles = new ArrayList<>();
		this.holes = new ArrayList<>();
		this.stairs = new ArrayList<>();
	}

	public Map(int width, int height, ArrayList<Obstacle> os, ArrayList<Holes> hs, ArrayList<Stairs> st){
		this.width = width;
		this.height = height;
		this.obstacles = os;
		this.holes = hs;
		this.stairs = st;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public ArrayList<Obstacle> getObstacles() {
		return obstacles;
	}

	public void setObstacles(ArrayList<Obstacle> obstacles) {
		this.obstacles = obstacles;
	}

	public ArrayList<Holes> getHoles() {
		return holes;
	}

	public void setHoles(ArrayList<Holes> holes) {
		this.holes = holes;
	}

	public ArrayList<Stairs> getStairs() {
		return stairs;
	}

	public void setStairs(ArrayList<Stairs> stairs) {
		this.stairs = stairs;
	}

}
