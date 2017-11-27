package engine;

import java.util.Timer;
import java.util.TimerTask;

import specifications.DataService;
import specifications.EngineService;
import specifications.RequireDataService;

public class Engine implements RequireDataService, EngineService {

	public Engine(){}
	private DataService data;
	private Timer timer;
	private boolean moveLeft, moveRight, moveUp, moveDown;
	//private User.COMMAND command;


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
	}

	@Override
	public void start(){
		timer.schedule(new TimerTask(){
			public void run(){

				
			}
		},0, 100);
	}

	@Override
	public void stop()
	{
		timer.cancel();
	}
}
