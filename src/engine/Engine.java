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
import java.awt.Rectangle;
import specifications.DataService;
import specifications.EngineService;
import specifications.RequireDataService;
import tools.HardCodedParameters;
import tools.User;

public class Engine implements RequireDataService, EngineService {

	public Engine(){}
	private DataService data;
	private Timer timer;
	private boolean moveLeft, moveRight, moveUp, moveDown, collision;
	//private User.COMMAND command
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
		collision = false;
		heroesVX =0;
		heroesVY=0;
		gen = new Random();
		for(int i=0; i<5;i++)
		{
			spawnEnemies();
			fillSet();
		}
		for(int i=0; i<25;i++)
		{
			obstacleGeneration();
			fillSet();
		}
		for(int i=0; i<5;i++)
		{	
			holesGeneration();
			fillSet();
		}
		allPos = new HashSet<Position>();
	}

	@Override
	public void start(){
		timer.schedule(new TimerTask(){
			public void run(){

				fillSet();
				ArrayList<Obstacle> obstacles = data.getMap().getObstacles();
				Obstacle o;
				ArrayList <Enemies> enemies = data.getEnemies();
				Enemies e;
				for(int i =0;i<obstacles.size();i++)
				{
					o = obstacles.get(i);
					if(collisionObstacles(o))
					{	
						data.getLonk().setPosition(new Position(data.getLonk().getPosition().x-heroesVX,
								data.getLonk().getPosition().y-heroesVY));					
						System.out.println("Kek");
						heroesVX =0;heroesVY =0;
					}
					for(int j =0;j<enemies.size();j++)
					{
						e = enemies.get(j);
						if((o.getPosition().x-e.getPosition().x)*(o.getPosition().x-e.getPosition().x)+(o.getPosition().y-e.getPosition().y)*(o.getPosition().y-e.getPosition().y) > 500)
						{
							
							System.out.println("Collision ennemie avec un obstacle");
						}
					}
				}
				updateEnemiesPosition();
				updateSpeedHeroes();
				updateCommandHeroes();
				updatePositionHeroes();


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
		boolean cont = true;
		while(cont){
			x = (int) ((gen.nextInt((int) ((HardCodedParameters.maxX-HardCodedParameters.minX))))+HardCodedParameters.minX);
			y = (int) ((gen.nextInt((int) ((HardCodedParameters.maxY-HardCodedParameters.minY))))+HardCodedParameters.minY);
			cont =false;
			for(Position p : allPos)
			{
				if(Math.abs((p.x-x)*(p.x-x)+(p.y-y)*(p.y-y)) < 400)
					cont= true;
			}
		}
		data.getMap().getObstacles().add(new Obstacle(new Position(x,y)));

	}

	private void holesGeneration(){
		int x=0;
		int y=0;

		boolean cont = true;
		while(cont){
			x = (int) ((gen.nextInt((int) ((HardCodedParameters.maxX-HardCodedParameters.minX))))+HardCodedParameters.minX);
			y = (int) ((gen.nextInt((int) ((HardCodedParameters.maxY-HardCodedParameters.minY))))+HardCodedParameters.minY);
			cont =false;
			for(Position p : allPos)
			{
				if(Math.abs((p.x-x)*(p.x-x)+(p.y-y)*(p.y-y)) < 400)
					cont= true;
			}
		}
		data.getMap().getHoles().add(new Holes(new Position(x,y)));

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

		if (moveLeft)
			heroesVX-=10;
		if (moveRight) 
			heroesVX+=10;
		if (moveUp) 
			heroesVY-=10;
		if (moveDown) 
			heroesVY+=10;
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
			x = (int) ((gen.nextInt((int) ((HardCodedParameters.maxX-HardCodedParameters.minX)))+HardCodedParameters.minX));
			y = (int) ((gen.nextInt((int) ((HardCodedParameters.maxY-HardCodedParameters.minY)))+HardCodedParameters.minY));
			cont =false;
			for(Position p : allPos)
			{
				if(((p.x-x)*(p.x-x)+(p.y-y)*(p.y-y)) < 500)
					cont= true;
			}
		}
		data.getEnemies().add(new Enemies(new Position(x,y),"Enemy",5));
	}

	private void updateEnemiesPosition()
	{
		int rng = (int)(Math.random()*2);
		for(Enemies e : data.getEnemies())
		{

				if(rng==0)
				{
					if(e.getPosition().x > data.getLonk().getPosition().x)
					{
						e.getPosition().x =e.getPosition().x -1;
					}
					else if(e.getPosition().x < data.getLonk().getPosition().x)
					{
						e.getPosition().x =e.getPosition().x +1;
					}
				}
				else
				{
					if(e.getPosition().y > data.getLonk().getPosition().y)
						e.getPosition().y =e.getPosition().y -1;
					else if(e.getPosition().y < data.getLonk().getPosition().y)
						e.getPosition().y =e.getPosition().y +1;
				}	
			}
		}
	

	private boolean collisionObstacles(Obstacle o){

		return((data.getLonk().getPosition().x <= o.getPosition().x + 30 &&
				data.getLonk().getPosition().x + 30 >= o.getPosition().x &&
				data.getLonk().getPosition().y <= o.getPosition().y + 30 &&
				30 + data.getLonk().getPosition().y >=  o.getPosition().y)
				);
	}
	private boolean collisionHoles(Holes h){
		return(
				(data.getLonk().getPosition().x-h.getPosition().x)*(data.getLonk().getPosition().x-h.getPosition().x)
				+
				(data.getLonk().getPosition().y-h.getPosition().y)*(data.getLonk().getPosition().y-h.getPosition().y)
				<
				0.25*20*100
				);
	}
	private boolean collisionEnemies(Enemies e){
		return(
				(data.getLonk().getPosition().x-e.getPosition().x)*(data.getLonk().getPosition().x-e.getPosition().x)
				+
				(data.getLonk().getPosition().y-e.getPosition().y)*(data.getLonk().getPosition().y-e.getPosition().y)
				<=
				0.25*20*100
				);
	}

	/*private boolean collisionAll(Position p)
	{

		return ((p.x-data.getLonk().getPosition().x)*(p.x-data.getLonk().getPosition().x)+(p.y-data.getLonk().getPosition().y)*(p.y-data.getLonk().getPosition().y) < 0.25*20*100);
	}*/
}
