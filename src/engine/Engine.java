package engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

import classes.*;

import java.awt.Rectangle;
import specifications.DataService;
import specifications.EngineService;
import specifications.RequireDataService;
import tools.Direction;
import tools.HardCodedParameters;
import tools.User;

public class Engine implements RequireDataService, EngineService {


	private int i;

	public Engine(){}
	private DataService data;
	private Timer timer;
	private User.COMMAND oldDirection;
	private boolean moveLeft, moveRight, moveUp, moveDown, collision;
	private boolean pushSpace;
	private double friction = 0.2;
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
		gen = new Random();
		i = 0;

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

						{
							data.getLonk().setPosition(new Position(data.getLonk().getPosition().x-data.getLonk().getVelocityX(),
									data.getLonk().getPosition().y-data.getLonk().getVelocityY()));
							System.out.println("Kek");
							data.getLonk().setVelocityX(0);
							data.getLonk().setVelocityX(0);
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
				}

				for(int j =0;j<enemies.size();j++)
				{
					e=enemies.get(j);
					if(collisionEnemies(e))
					{

					}
				}


				for(Holes h : data.getMap().getHoles()){
					if(collisionHoles(h)){
						data.getLonk().setHp(data.getLonk().getHp() - 1);
						data.getLonk().setPosition(new Position(30,
								30));
					}
				}
				collisionWeaponEnnemies();
				updateEnemiesPosition();
				updateSpeedHeroes();
				updateCommandHeroes();
				updatePositionHeroes();
				updateWeaponPosition();
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
				if((p.x-x)*(p.x-x)+(p.y-y)*(p.y-y) < 400)
					cont= true;
			}
		}

		if(x > 40 && y > 40){
			data.getMap().getObstacles().add(new Obstacle(new Position(x,y)));
			System.out.println("x : " + x + " y : " + y);
		}
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
				if((p.x-x)*(p.x-x)+(p.y-y)*(p.y-y) < 400)
					cont= true;
			}
		}

		if(x > 40 && y > 40) {
			data.getMap().getHoles().add(new Holes(new Position(x, y)));
		}
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

		data.getLonk().setPosition(new Position(data.getLonk().getPosition().x+data.getLonk().getVelocityX(),
				data.getLonk().getPosition().y+data.getLonk().getVelocityY()));
		data.getLonk().setStep(data.getLonk().getStep()+1);
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

	private void collisionWeaponEnnemies(){
		if (isPushSpace()) {
			Weapon weapon = data.getLonk().getWeapon();

			for (Enemies e : data.getEnemies()) {
//					100+15 - 100

				if ( Math.abs((weapon.getPosition().x+weapon.getHeight()/2) - (e.getPosition().x+e.getHeight()/2) ) < 25  && Math.abs((weapon.getPosition().y+weapon.getWidth()/2) - (e.getPosition().y+e.getWidth()/2) ) < 25  ){
					e.setHp(e.getHp()-1);
								System.out.println("touché "+i);
					i++;

				}
			}
		}
	}

	private void updateCommandHeroes() {
		double lonkVelocityX =  data.getLonk().getVelocityX();
		double lonkVelocityY = data.getLonk().getVelocityY();

		if (moveLeft) {
			data.getLonk().setVelocityX(lonkVelocityX-5);
			data.getLonk().setDirection(Direction.LEFT);
		}
		if (moveRight){
			data.getLonk().setVelocityX(lonkVelocityX+5);
			data.getLonk().setDirection(Direction.RIGHT);
		}
		if (moveUp) {
			data.getLonk().setVelocityY(lonkVelocityY-5);
			data.getLonk().setDirection(Direction.UP);
		}
		if (moveDown){
			data.getLonk().setVelocityY(lonkVelocityY+5);
			data.getLonk().setDirection(Direction.DOWN);
		}
	}

	private void updateSpeedHeroes() {
		data.getLonk().setVelocityX(data.getLonk().getVelocityX()*friction);
		data.getLonk().setVelocityY(data.getLonk().getVelocityY()*friction);

	}

	private void updateWeaponPosition(){
		    Heroes lonk = data.getLonk();
			Position linkPos = data.getLonk().getPosition();
			Weapon saber =  data.getLonk().getWeapon();
			if (lonk.getDirection().equals(Direction.LEFT))
				saber.setPosition(new Position(linkPos.x+lonk.getWidth(),linkPos.y));
			if (lonk.getDirection().equals(Direction.RIGHT))
				saber.setPosition(new Position(linkPos.x+25,linkPos.y));
			if (lonk.getDirection().equals(Direction.UP))
				saber.setPosition(new Position(linkPos.x,linkPos.y-25));
			if (lonk.getDirection().equals(Direction.DOWN))
				saber.setPosition(new Position(linkPos.x,linkPos.y+25));
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
				if(((p.x-x)*(p.x-x)+(p.y-y)*(p.y-y)) < 500)
					cont= true;
			}
		}
		data.getEnemies().add(new Enemies(new Position(x,y),"Enemy",5));
	}

	private void updateEnemiesPosition()
	{

		ArrayList<Enemies> enemies = new ArrayList<>();
		enemies = data.getEnemies();

		for (int i = 0 ; i < data.getEnemies().size() ; i++)
		{
			Enemies e = data.getEnemies().get(i);
			if (e.getHp()>0) {
				if (e.getPosition().x > data.getLonk().getPosition().x)
					e.getPosition().x = e.getPosition().x - 1;
				else if (e.getPosition().x < data.getLonk().getPosition().x)
					e.getPosition().x = e.getPosition().x + 1;
				if (e.getPosition().y > data.getLonk().getPosition().y)
					e.getPosition().y = e.getPosition().y - 1;
				else if (e.getPosition().y < data.getLonk().getPosition().y)
					e.getPosition().y = e.getPosition().y + 1;
			}
			else {
				enemies.remove(e);

			}
		}
		data.setEnemies(enemies);
	}


	private boolean collisionObstacles(Obstacle o){

		return((data.getLonk().getPosition().x <= o.getPosition().x + HardCodedParameters.obsWidth &&
				data.getLonk().getPosition().x + HardCodedParameters.obsWidth >= o.getPosition().x &&
				data.getLonk().getPosition().y <= o.getPosition().y + HardCodedParameters.obsHeight &&
				HardCodedParameters.obsHeight + data.getLonk().getPosition().y >=  o.getPosition().y)

				);

        /*return((data.getLonk().getPosition().x <= o.getPosition().x + 30 &&
                data.getLonk().getPosition().x + 30 >= o.getPosition().x &&
                data.getLonk().getPosition().y <= o.getPosition().y + 30 &&
                30 + data.getLonk().getPosition().y >=  o.getPosition().y)	
        );*/
	}
	private boolean collisionHoles(Holes h){
		return(
				(data.getLonk().getPosition().x-h.getPosition().x)*(data.getLonk().getPosition().x-h.getPosition().x)
				+
				(data.getLonk().getPosition().y-h.getPosition().y)*(data.getLonk().getPosition().y-h.getPosition().y)
				<
				500
				);
	}
	private boolean collisionEnemies(Enemies e){
		return(
				(data.getLonk().getPosition().x-e.getPosition().x)*(data.getLonk().getPosition().x-e.getPosition().x)
				+
				(data.getLonk().getPosition().y-e.getPosition().y)*(data.getLonk().getPosition().y-e.getPosition().y)
				<=
				500
				);
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
