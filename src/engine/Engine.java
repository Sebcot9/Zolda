package engine;

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
	}

	@Override
	public void start(){
		timer.schedule(new TimerTask(){
			public void run(){
				updateSpeedHeroes();
				updateCommandHeroes();
				updatePositionHeroes();
				
			}

			
		},0, 100);
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


}
