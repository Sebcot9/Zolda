package engine;

import java.util.HashSet;
import java.util.Random;
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
	private boolean moveLeft, moveRight, moveUp, moveDown;
	private User.COMMAND oldDirection;
	//private User.COMMAND command;
	private boolean moveLeft, moveRight, moveUp, moveDown, collision;
	//private User.COMMAND command
	private int heroesVX;
	private int heroesVY;
	private double friction = 0.5;
	private Random gen;
	private boolean pushSpace;


	private HashSet<Position> allPos = new HashSet<Position>();

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
				for(Obstacle o : data.getMap().getObstacles())
				{
					if(collisionObstacles(o))
					{

						data.getLonk().setPosition(new Position(data.getLonk().getPosition().x-heroesVX,
								data.getLonk().getPosition().y-heroesVY));
						//;
						//;
						System.out.println("Kek");
						heroesVX =0;heroesVY =0;

						//collision = false;
						//Rectangle overlap = r1.intersection(r2);
						/*if (overlap.getHeight() >= overlap.getWidth())
					    {
					    	data.getLonk().setPosition(new Position(data.getLonk().getPosition().x-heroesVX,
					    			data.getLonk().getPosition().y+heroesVY));

					       System.out.println("Collision");
					    }
					    if (overlap.getWidth() >= overlap.getHeight())
					    {
					    	data.getLonk().setPosition(new Position(data.getLonk().getPosition().x+heroesVX,
					    			data.getLonk().getPosition().y-heroesVY));

					        System.out.println("Collision");
					    }*/

					}
				}
				updateWeaponPosition();
				updateSpeedHeroes();
				updateCommandHeroes();
				updatePositionHeroes();
				data.setStepNumber(data.getStepNumber()+1);

			}
		},0, 80);
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
				if(Math.abs((p.x-x)*(p.x-x)+(p.y-y)*(p.y-y)) < 0.25*20*100)
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
				if(Math.abs((p.x-x)*(p.x-x)+(p.y-y)*(p.y-y)) < 0.25*20*100)
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
		oldDirection = c;
		if (c==User.COMMAND.LEFT) moveLeft=true;
		if (c==User.COMMAND.RIGHT) moveRight=true;
		if (c==User.COMMAND.UP) moveUp=true;
		if (c==User.COMMAND.DOWN) moveDown=true;
		if (c==User.COMMAND.SPACE) pushSpace= true;
	}

	@Override
	public void releaseHeroesCommand(User.COMMAND c)

	{
		oldDirection = c;
		if (c==User.COMMAND.LEFT) moveLeft=false;
		if (c==User.COMMAND.RIGHT) moveRight=false;
		if (c==User.COMMAND.UP) moveUp=false;
		if (c==User.COMMAND.DOWN) moveDown=false;
		if (c==User.COMMAND.SPACE) pushSpace=false;
	}

	@Override
	public boolean getMoveLeft() {
		return moveLeft;
	}

	@Override
	public boolean getmoveRight() {
		return moveRight;
	}

	@Override
	public boolean getmoveUp() {
		return moveUp;
	}

	@Override
	public boolean getmoveDown() {
		return moveDown;
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
		if (moveLeft) heroesVX-=5;
		if (moveRight) heroesVX+=5;
		if (moveUp) heroesVY-=5;
		if (moveDown) heroesVY+=5;
	}

	private void updateSpeedHeroes() {
		// TODO Auto-generated method stub
		heroesVX*=friction;
		heroesVY*=friction;
	}

	private void updateWeaponPosition(){
		if (isPushSpace()){
			Position linkPos = data.getLonk().getPosition();
			Weapon saber =  data.getLonk().getWeapon();
			if (oldDirection.equals(User.COMMAND.LEFT))
				saber.setPosition(new Position(linkPos.x-30,linkPos.y));
			if (oldDirection.equals(User.COMMAND.RIGHT))
				saber.setPosition(new Position(linkPos.x+30,linkPos.y));
			if (oldDirection.equals(User.COMMAND.UP))
				saber.setPosition(new Position(linkPos.x,linkPos.y-30));
			if (oldDirection.equals(User.COMMAND.DOWN))
				saber.setPosition(new Position(linkPos.x,linkPos.y+30));
		}

	}

	private void spawnEnemies(){
		int x=0;
		int y=0;
		boolean cont = true;
		while(cont){
			x = (int) ((gen.nextInt((int) ((HardCodedParameters.maxX-HardCodedParameters.minX)))));
			y = (int) ((gen.nextInt((int) ((HardCodedParameters.maxY-HardCodedParameters.minY)))));
			cont =false;
			for(Position p : allPos)
			{
				if(Math.abs((p.x-x)*(p.x-x)+(p.y-y)*(p.y-y)) < 0.25*20*100)
					cont= true;
			}
		}
		data.getEnemies().add(new Enemies(new Position(x,y),"Enemy",5));
	}

	private void updateEnemiesPosition()
	{
		for(Enemies e : data.getEnemies())
		{

		}
	}
	private boolean collisionObstacles(Obstacle o){

		return((data.getLonk().getPosition().x <= o.getPosition().x + 25 &&
				data.getLonk().getPosition().x + 25 >= o.getPosition().x &&
				data.getLonk().getPosition().y <= o.getPosition().y + 25 &&
				25 + data.getLonk().getPosition().y >=  o.getPosition().y)

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
	private boolean collisionAll(Position p)
	{

		return ((p.x-data.getLonk().getPosition().x)*(p.x-data.getLonk().getPosition().x)+(p.y-data.getLonk().getPosition().y)*(p.y-data.getLonk().getPosition().y) < 0.25*20*100);
	}

	@Override
	public boolean isPushSpace() {
		return pushSpace;
	}

	@Override
	public void setPushSpace(boolean pushSpace) {
		this.pushSpace = pushSpace;
	}


}
