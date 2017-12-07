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
import specifications.ViewerService;
import tools.Direction;
import tools.HardCodedParameters;
import tools.User;
import ui.Viewer;

public class Engine implements RequireDataService, EngineService {


	private int i;

	public Engine(){}
	private DataService data;
	private ViewerService viewer;
	private Timer timer;
	private User.COMMAND oldDirection;
	private boolean moveLeft, moveRight, moveUp, moveDown, collision;
	//private User.COMMAND command
	private int heroesVX;
	private int heroesVY;
	private double friction = 0.2;
	private Random gen;
	private boolean pushSpace;
	private boolean gameOver;
	private volatile boolean isPaused = false;
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
		gameOver = false;
		gen = new Random();
		i = 0;


		for(int i=0; i<1;i++)
		{
			spawnEnemies();
			fillSet();
		}
		for(int i=0; i<25;i++)
		{
			obstacleGeneration();
			fillSet();
		}
		for(int i=0; i<25;i++)
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
				ArrayList <Holes> holes = data.getMap().getHoles();
				ArrayList <Stairs> stairs = data.getMap().getStairs();
				Stairs st;

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


					}

					for(int j =0;j<enemies.size();j++)
					{
						e = enemies.get(j);

						if (collisionEnemiesObstacles(o, e)) {

							if ((Math.abs(o.getPosition().x + 20 - e.getPosition().x)) < 20){
								e.setPosition(new Position(e.getPosition().x-data.getLonk().getVelocityX(), e.getPosition().y-data.getLonk().getVelocityY()));
								updateEnemiesPosition();
							}

							if (((Math.abs(o.getPosition().y  + 20 - e.getPosition().y)) < 20)){
								e.setPosition(new Position(e.getPosition().x-heroesVX, e.getPosition().y -heroesVY));
								updateEnemiesPosition();
							}


						/*	if(o.getPosition().x + HardCodedParameters.obsWidth >= e.getPosition().x - 30 ){
								e.setPosition(new Position(e.getPosition().x-5,
										e.getPosition().y));
							}

							if(o.getPosition().x + HardCodedParameters.obsWidth <= e.getPosition().x - 30 ){
								e.setPosition(new Position(e.getPosition().x-5,
										e.getPosition().y));
							}

							if(o.getPosition().y + HardCodedParameters.obsWidth <= e.getPosition().y - 30 ){
								e.setPosition(new Position(e.getPosition().x,
										e.getPosition().y-5));
							}

							if(o.getPosition().y + HardCodedParameters.obsWidth >= e.getPosition().y - 30 ){
								e.se	tPosition(new Position(e.getPosition().x,
										e.getPosition().y-5));
							}
*/
						}
							/*if((o.getPosition().x-e.getPosition().x)*(o.getPosition().x-e.getPosition().x)+(o.getPosition().y-e.getPosition().y)*(o.getPosition().y-e.getPosition().y) < 500)
						{
							System.out.println("Collision ennemie avec un obstacle");
							e.getPosition().x = e.getPosition().x - heroesVX;
							e.getPosition().y =e.getPosition().y - heroesVY;

							System.out.println(o.getPosition().x);
							System.out.println(e.getPosition().x);
						}*/
					}

				}

				for (int j = 0; j < enemies.size(); j++) {
					e = enemies.get(j);
					if (collisionEnemies(e)) {

					}
				}


				for (Holes h : data.getMap().getHoles()) {
					if (collisionHoles(h)) {
						data.getLonk().setHp(data.getLonk().getHp() - data.getLonk().getHp());
						data.getLonk().setPosition(new Position(30,
								30));
					}
				}

				if (!isPaused) {
					collisionWeaponEnnemies();
					updateEnemiesPosition();
					updateSpeedHeroes();
					updateCommandHeroes();
					updatePositionHeroes();
					updateWeaponPosition();
					data.setStepNumber(data.getStepNumber()+1);
				}

				if(enemies.size() == 0){

					for(int i = 0; i < data.getMap().getHoles().size(); i++){
						Holes hl = data.getMap().getHoles().get(i);
						holes.remove(hl);
					}

					data.getMap().getStairs().add(new Stairs(new Position(763, 400)));

					for(int i = 0; i < data.getMap().getObstacles().size(); i++){
						Obstacle ob = data.getMap().getObstacles().get(i);
						obstacles.remove(ob);
						System.out.print("ko");
					}

					for(int i =0;i<stairs.size();i++){
						st = stairs.get(i);

						if (collisionStairs(st)) {
							data.getLonk().setPosition(new Position(750,
									350));
							init();
							start();
						}
					}
					/*for(int i=0; i< 3;i++)
					{
						int x = (int) ((gen.nextInt((int) ((HardCodedParameters.maxX-HardCodedParameters.minX))))+HardCodedParameters.minX);
						int y = (int) ((gen.nextInt((int) ((HardCodedParameters.maxY-HardCodedParameters.minY))))+HardCodedParameters.minY);

						data.getMap().getStairs().add(new Stairs(new Position(x, y)));
						fillSet();
					}*/
				}


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

		allPos.add(data.getLonk().getPosition());
		allPos.add(data.getWeaponPosition());

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
				if((p.x-x)*(p.x-x)+(p.y-y)*(p.y-y) < 1500)
					cont= true;
			}
		}



//		if((x > data.getLonk().getPosition().x || x < data.getLonk().getPosition().x) && y > data.getLonk().getPosition().y || (y > data.getLonk().getPosition().y || y < data.getLonk().getPosition().y) && x > data.getLonk().getPosition().x ) {
			data.getMap().getObstacles().add(new Obstacle(new Position(x,y)));
//		}


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
				if((p.x-x)*(p.x-x)+(p.y-y)*(p.y-y) < 900)
					cont= true;
			}
		}

		//if(x > 40 && y > 40) {
			data.getMap().getHoles().add(new Holes(new Position(x, y)));
		//}
	}
	@Override
	public void stop()
	{
		timer.cancel();
	}

	@Override
	public void setHeroesCommand(User.COMMAND c) {
		oldDirection = c;
		if (c == User.COMMAND.LEFT) moveLeft = true;
		if (c == User.COMMAND.RIGHT) moveRight = true;
		if (c == User.COMMAND.UP) moveUp = true;
		if (c == User.COMMAND.DOWN) moveDown = true;
		if (c == User.COMMAND.SPACE) {
			if (data.getStepAttack() == 0)
				pushSpace = true;
		}
		if (c==User.COMMAND.P) isPaused=true;
	}

	@Override
	public void releaseHeroesCommand(User.COMMAND c)

	{
		oldDirection = c;
		if (c==User.COMMAND.LEFT) moveLeft=false;
		if (c==User.COMMAND.RIGHT) moveRight=false;
		if (c==User.COMMAND.UP) moveUp=false;
		if (c==User.COMMAND.DOWN) moveDown=false;
		if (c==User.COMMAND.SPACE) {

		}
		if (c==User.COMMAND.P) isPaused=false;

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
		if (isPushSpace()){
			int stepAttack = data.getStepAttack();
			if (stepAttack+1 < 8) {
				data.setStepAttack(stepAttack+1);
				if (stepAttack == 1) {
					Weapon weapon = data.getLonk().getWeapon();

					for (Enemies e : data.getEnemies()) {

						if ( Math.abs((weapon.getPosition().x+weapon.getHeight()/2) - (e.getPosition().x+e.getHeight()/2) ) < 35  && Math.abs((weapon.getPosition().y+weapon.getWidth()/2) - (e.getPosition().y+e.getWidth()/2) ) < 35  ){
							e.setHp(e.getHp()-1);
							System.out.println("touché "+i);
							i++;
						}
					}
				}
			}
			else {
				pushSpace = false;
				data.setStepAttack(0);
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
//		if (isPushSpace())
			Heroes lonk = data.getLonk();

			Position linkPos = data.getLonk().getPosition();
			Weapon saber = data.getLonk().getWeapon();
			if (lonk.getDirection().equals(Direction.LEFT))
				saber.setPosition(new Position(linkPos.x - lonk.getWidth()/2, linkPos.y));
			if (lonk.getDirection().equals(Direction.RIGHT))
				saber.setPosition(new Position(linkPos.x + lonk.getWidth()/2, linkPos.y));
			if (lonk.getDirection().equals(Direction.UP))
				saber.setPosition(new Position(linkPos.x, linkPos.y - lonk.getHeight()));
			if (lonk.getDirection().equals(Direction.DOWN))
				saber.setPosition(new Position(linkPos.x, linkPos.y + lonk.getHeight()));
		}
//	}

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

		Enemies en = new Enemies(new Position(x,y),"Enemy",5);
		en.setHeight(20);
		en.setWidth(20);

		data.getEnemies().add(en);
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
				data.getLonk().getPosition().y <= o.getPosition().y + HardCodedParameters.obsHeight + 7 &&
				 data.getLonk().getPosition().y >=  o.getPosition().y)

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

	private boolean collisionEnemiesObstacles(Obstacle o, Enemies e){

		return((e.getPosition().x  <= o.getPosition().x + 20 &&
				e.getPosition().x + 20 >= o.getPosition().x &&
				e.getPosition().y  <= o.getPosition().y + 20&&
				20 + e.getPosition().y >=  o.getPosition().y)

		);

        /*return((data.getLonk().getPosition().x <= o.getPosition().x + 30 &&
                data.getLonk().getPosition().x + 30 >= o.getPosition().x &&
                data.getLonk().getPosition().y <= o.getPosition().y + 30 &&
                30 + data.getLonk().getPosition().y >=  o.getPosition().y)
        );*/
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
	public void isGameOver(){
		if(data.getLonk().getHp() == 0){
			gameOver = true;
		}
	}

	@Override
	public boolean isPushSpace() {
		return pushSpace;
	}

	@Override
	public void setPushSpace(boolean pushSpace) {
		this.pushSpace = pushSpace;
	}


	private boolean collisionStairs(Stairs st){

		return((data.getLonk().getPosition().x <= st.getPosition().x + HardCodedParameters.obsWidth &&
				data.getLonk().getPosition().x + HardCodedParameters.obsWidth >= st.getPosition().x &&
				data.getLonk().getPosition().y <= st.getPosition().y + HardCodedParameters.obsHeight &&
				data.getLonk().getPosition().y >=  st.getPosition().y )

		);
	}


}
