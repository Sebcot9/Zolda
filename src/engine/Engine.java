package engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

import classes.Allies;
import classes.Enemies;
import classes.Holes;
import classes.Obstacle;
import classes.Pets;
import classes.Position;
import specifications.DataService;
import specifications.EngineService;
import specifications.RequireDataService;
import tools.User;

public class Engine implements RequireDataService, EngineService {

	public Engine(){}
	private DataService data;
	private Timer timer;
	private boolean moveLeft, moveRight, moveUp, moveDown;
	//private User.COMMAND command;
	private int heroesVX;
	private int heroesVY;
	private double friction = 0.5;
	private Random gen;
	private HashSet<Position> allPos = new HashSet<Position>(); 
	
	@Override
	public void bindDataService(DataService service){
		data = service;
	}

	@Override
	public void init(){
		timer = new Timer();
		moveLeft = false;
		moveRight = false;
		moveUp = false;
		moveDown = false;
		heroesVX =0;
		heroesVY=0;
		gen = new Random();
		for(int i=0; i<5;i++)
			spawnEnemies();
		for(int i=0; i<50;i++)
			obstacleGeneration();
		for(int i=0; i<15;i++)
			holesGeneration();
		allPos = new HashSet<Position>();
	}

	@Override
	public void start(){
		timer.schedule(new TimerTask(){
			public void run(){
				
				fillSet();
				updateSpeedHeroes();
				updateCommandHeroes();
				updatePositionHeroes();
				//ArrayList<Enemies> enemies =  new ArrayList<Enemies>();
				
				/*HashSet<Position> allpos = allPos;
				allpos.toString();*/
			}
		},0, 100);
	}

	private HashSet<Position> fillSet()
	{
		for(Allies a : data.getAllies())
			allPos.add(a.getPosition());
		for(Enemies e : data.getEnemies())
			allPos.add(e.getPosition());
		for(Obstacle o : data.getMap().getObstacles())
			allPos.add(o.getPosition());
		for(Holes h : data.getMap().getHoles())
			allPos.add(h.getPosition());
		for(Pets p : data.getPets())
			allPos.add(p.getPosition());
		return allPos;
	}
	
	private void obstacleGeneration(){
		int x=0;
		int y=0;
		//ArrayList<Obstacle> obs = new ArrayList<Obstacle>();
		boolean cont = true;
		while(cont){
			x = (int)(gen.nextInt((int)(data.getMap().getWidth()*.6))+data.getMap().getWidth()*.1);
			y = (int)(gen.nextInt((int)(data.getMap().getHeight()*.6))+data.getMap().getHeight()*.1);
			cont =false;
			for(Position p : allPos)
			{
				if((p.equals(new Position(x,y))))
					cont= true;
			}
		}
		data.getMap().getObstacles().add(new Obstacle(new Position(x,y)));
		//return obs;
	}
	
	private void holesGeneration(){
		int x=0;
		int y=0;
		//ArrayList<Holes> hol = new ArrayList<Holes>();
		boolean cont = true;
		while(cont){
			x = (int)(gen.nextInt((int)(data.getMap().getWidth()*.6))+data.getMap().getWidth()*.1);
			y = (int)(gen.nextInt((int)(data.getMap().getHeight()*.6))+data.getMap().getHeight()*.1);
			cont =false;
			for(Position p : allPos)
			{
				if((p.equals(new Position(x,y))))
					cont= true;
			}
		}
		data.getMap().getHoles().add(new Holes(new Position(x,y)));
		//return hol;
	}
	@Override
	public void stop()
	{
		timer.cancel();
	}

	@Override
	public void setHeroesCommand(User.COMMAND c)
	{
		if (c==User.COMMAND.LEFT) moveLeft=true;
		if (c==User.COMMAND.RIGHT) moveRight=true;
		if (c==User.COMMAND.UP) moveUp=true;
		if (c==User.COMMAND.DOWN) moveDown=true;
	}

	@Override
	public void releaseHeroesCommand(User.COMMAND c)
	{
		if (c==User.COMMAND.LEFT) moveLeft=false;
		if (c==User.COMMAND.RIGHT) moveRight=false;
		if (c==User.COMMAND.UP) moveUp=false;
		if (c==User.COMMAND.DOWN) moveDown=false;
	}

	private void updatePositionHeroes() {
		// TODO Auto-generated method stub
		data.getLonk().setPosition(new Position(data.getLonk().getPosition().x+heroesVX,
				data.getLonk().getPosition().y+heroesVY));

		if(data.getLonk().getPosition().x > data.getMaxX()){
			data.getLonk().setPosition(new Position(data.getMaxX(), data.getLonk().getPosition().y));
		}

		if(data.getLonk().getPosition().x < data.getMinX()){
			data.getLonk().setPosition(new Position(data.getMinX(), data.getLonk().getPosition().y));
		}

		if(data.getLonk().getPosition().y < data.getMinY()){
			data.getLonk().setPosition(new Position(data.getLonk().getPosition().x, data.getMinY()));
		}

		if(data.getLonk().getPosition().y > data.getMaxY()){
			data.getLonk().setPosition(new Position(data.getLonk().getPosition().x, data.getMaxY()));
		}
	}

	private void updateCommandHeroes() {
		// TODO Auto-generated method stub
		if (moveLeft) heroesVX-=10;
		if (moveRight) heroesVX+=10;
		if (moveUp) heroesVY-=10;
		if (moveDown) heroesVY+=10;
	}

	private void updateSpeedHeroes() {
		// TODO Auto-generated method stub
		heroesVX*=friction;
		heroesVY*=friction;
	}

	private void spawnEnemies(){
		int x=0;
		int y=0;
		boolean cont = true;
		while(cont){
			x = (int)(gen.nextInt((int)(data.getMap().getWidth()*.6))+data.getMap().getWidth()*.1);
			y = (int)(gen.nextInt((int)(data.getMap().getHeight()*.6))+data.getMap().getHeight()*.1);
			cont =false;
			for(Position p : allPos)
			{
				if((p.equals(new Position(x,y))))
					cont= true;
			}
		}
		data.getEnemies().add(new Enemies(new Position(x,y),"Enemy",5));
	}

}
