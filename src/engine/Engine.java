package engine;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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
	}

	@Override
	public void start(){
		timer.schedule(new TimerTask(){
			public void run(){
				updateSpeedHeroes();
				updateCommandHeroes();
				updatePositionHeroes();
				
			}

			
		},0, 80);
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

		if(data.getLonk().getPosition().x > data.getMap().getWidth()){

			System.out.println(data.getLonk().getPosition().x);

			data.getLonk().setPosition(new Position(data.getMap().getWidth(), data.getLonk().getPosition().y));
		}

		if(data.getLonk().getPosition().x < data.getMinX()){
			data.getLonk().setPosition(new Position(data.getMinX(), data.getLonk().getPosition().y));
		}

		if(data.getLonk().getPosition().y < data.getMinY()){
			data.getLonk().setPosition(new Position(data.getLonk().getPosition().x, data.getMinY()));
		}

		if(data.getLonk().getPosition().y > data.getMap().getHeight()){
			System.out.println(data.getLonk().getPosition().y);
			data.getLonk().setPosition(new Position(data.getLonk().getPosition().x, data.getMap().getHeight()));
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

	private void spawnEnemies(){
		int x=0;
		int y=0;
		x = (int)(gen.nextInt((int)(data.getMap().getWidth()*.6))+data.getMap().getWidth()*.1);
		y = (int)(gen.nextInt((int)(data.getMap().getHeight()*.6))+data.getMap().getHeight()*.1);
		
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

}
