package ui;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import specifications.EngineService;
import specifications.ReadService;
import specifications.RequireReadService;
import specifications.ViewerService;
import tools.Direction;
import tools.HardCodedParameters;

import java.util.ArrayList;

import classes.Enemies;
import classes.Holes;
import classes.Obstacle;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

import javafx.scene.text.Text;

public class Viewer implements ViewerService, RequireReadService {

    private ReadService data;
    private EngineService engine;
    private double xShrink, yShrink, shrink, xModifier, yModifier;
    private ArrayList<Rectangle2D> left;
    private ArrayList<Rectangle2D> right;
    private ArrayList<Rectangle2D> up;
    private ArrayList<Rectangle2D> down;
    private ArrayList<Rectangle2D> defaultAvatarList;
    private Image linkAvatarSpriteSheet;
    private ImageView linkAvatarImageView;
    private Rectangle2D oldSpriteDirection;
    private boolean isUp, isDown, isRight, isLeft;
    private int avatarIndex;
    private ImageView swordAvatarImageView;
    private WeaponUI sword;
//    private int index;

    @Override
    public void bindReadService(ReadService service) {
        data = service;
    }

    @Override
    public void bindEngineService(EngineService service) {
        engine = service;
    }

    @Override
    public void init() {
        xShrink = 1;
        yShrink = 1;
        xModifier = 0;
        yModifier = 0;
        avatarIndex = 0;
//        index = 0;

        linkAvatarSpriteSheet = new Image("File:src/images/red_lonk.png");
        linkAvatarImageView = new ImageView(linkAvatarSpriteSheet);
        oldSpriteDirection = new Rectangle2D(33, 0, 32, 32);
        defaultAvatarList = new ArrayList<>();
        defaultAvatarList.add(new Rectangle2D(33, 0, 32, 32));

        right = new ArrayList<>();
        right.add(new Rectangle2D(66, 33, 32, 32));

        right.add(new Rectangle2D(66, 66, 32, 32));
//        right.add(new Rectangle2D(66, 33, 32, 32));
        right.add(new Rectangle2D(33, 66, 32, 32));

        left = new ArrayList<>();
        left.add(new Rectangle2D(33, 33, 32, 32));
        left.add(new Rectangle2D(0, 33, 32, 32));
//        left.add(new Rectangle2D(33, 66, 32, 32));
        left.add(new Rectangle2D(0, 66, 32, 32));

        up = new ArrayList<>();
        up.add(new Rectangle2D(99, 33, 32, 32));
        up.add(new Rectangle2D(99, 0, 32, 32));
        up.add(new Rectangle2D(132, 0, 32, 32));

        down = new ArrayList<>();
        down.add(new Rectangle2D(33, 0, 32, 32));
        down.add(new Rectangle2D(0, 0, 32, 32));
        down.add(new Rectangle2D(66, 0, 32, 32));
        swordAvatarImageView = new ImageView(new Image("File:src/images/sword1SpriteSheet.png"));
        sword = new WeaponUI();


    }

    @Override
    public Parent getPanel() {
        Group panel = new Group();
        shrink = Math.min(xShrink, yShrink);
        xModifier = .01 * shrink * data.getMap().getHeight();
        yModifier = .01 * shrink * data.getMap().getWidth();
        double radius = Math.min(shrink * 25, shrink * 25);

        //Vue Utilisateur
        Rectangle userView = new Rectangle(-2 * xModifier + shrink * 800,
                -.2 * shrink * 600 + shrink * 600);
        userView.setFill(new ImagePattern(new Image("File:src/images/terrain1.png")));
        userView.setStroke(Color.DIMGRAY);
        userView.setStrokeWidth(.01 * shrink * 600);
        userView.setArcWidth(.04 * shrink * 600);
        userView.setArcHeight(.04 * shrink * 600);
        userView.setTranslateX(xModifier);
        userView.setTranslateY(yModifier);
//		panel.getChildren().add(userView);

		//Vue de la Console
		Rectangle consoleView = new Rectangle(-2*xModifier+shrink*224,
				-.2*shrink*600+shrink*600);
		consoleView.setFill(Color.WHITE);
		consoleView.setStroke(Color.DIMGRAY);
		consoleView.setStrokeWidth(.01*shrink*600);
		consoleView.setArcWidth(.04*shrink*600);
		consoleView.setArcHeight(.04*shrink*600);
		consoleView.setTranslateX(xModifier*92);
		consoleView.setTranslateY(yModifier);

		panel.getChildren().add(consoleView);

        Text console = new Text(0.5*shrink*800+.5*shrink*900,
                -0.5*shrink*300+shrink*200,"Console : ");
        console.setFont(new Font(.05*shrink*600));
        panel.getChildren().add(console);

        //Vue Statistique
		Rectangle statView = new Rectangle(-2*xModifier+shrink*1024,
				-.2*shrink*400+shrink*400);
		statView.setFill(Color.WHITE);
		statView.setStroke(Color.DIMGRAY);
		statView.setStrokeWidth(.01*shrink*600);
		statView.setArcWidth(.04*shrink*600);
		statView.setArcHeight(.04*shrink*600);
		statView.setTranslateX(xModifier);
		statView.setTranslateY(yModifier*50);
		panel.getChildren().add(statView);

//        int index = avatarIndex/7;

        if (engine.getMoveLeft()) {
            defaultAvatarList = left;
//            isLeft = true; isDown = false; isRight = false; isUp = false;
//            if (avatarIndex+1 > defaultAvatarList.size()-1)
//                avatarIndex = 0;
//            else {
//                    index += 1;
                avatarIndex = data.getLonk().getStepDivided()%3;
//            }
        } else if (engine.getmoveRight()) {
            defaultAvatarList = right;
            if (avatarIndex+1 > defaultAvatarList.size()-1)
                avatarIndex = 0;
            else {
//                index += 1;

                avatarIndex = data.getLonk().getStepDivided()%3;
            }
        } else if(engine.getmoveUp()){
//            isLeft = false; isDown = false; isRight = false; isUp = true;
            defaultAvatarList =up;
            if (avatarIndex+1 > defaultAvatarList.size()-1)
                avatarIndex = 0;
            else {
//                index += 1;
                avatarIndex = data.getLonk().getStepDivided()%3;
            }
        }else if(engine.getmoveDown()) {
//            isLeft = false; isDown = true; isRight = false; isUp = false;
             defaultAvatarList =down;
            if (avatarIndex+1 > defaultAvatarList.size()-1)
                avatarIndex = 0;
            else {
//                index += 1;
                avatarIndex = data.getLonk().getStepDivided()%3;
            }
        }else {
//            index = 0;
            avatarIndex = 0;
		}
//		linkAvatarImageView.setFitHeight(5*10);
		linkAvatarImageView.setPreserveRatio(true);

        linkAvatarImageView.setTranslateX(shrink*data.getLonk().getPosition().x+shrink*xModifier-radius);
		linkAvatarImageView.setTranslateY(shrink*data.getLonk().getPosition().y+shrink*yModifier-radius);
		linkAvatarImageView.setFitWidth(shrink*data.getLonk().getWidth());
		linkAvatarImageView.setFitHeight(shrink*data.getLonk().getHeight());
		linkAvatarImageView.setViewport(defaultAvatarList.get(avatarIndex));
		//avatarIndex = (avatarIndex) % (defaultAvatarList.size() * 7);
		panel.getChildren().addAll(userView,linkAvatarImageView);

	  /*  Text t = new Text(-0.1*shrink*600+.5*shrink*800,
				-0.1*shrink*800+shrink*600,
				"Po : " + data.getLonk().getPosition().x + " " + data.getLonk().getPosition().y);
*/
		Text hp = new Text(-0.1*shrink*600+.5*shrink*500,
				-0.1*shrink*800+shrink*700,"HP : " + data.getLonk().getHp());
        hp.setFont(new Font(.05*shrink*600));
		panel.getChildren().add(hp);


		//panel.getChildren().add(t);
        if (engine.isPushSpace()) {
            Direction linkDirection = data.getLonk().getDirection();
            if (linkDirection.equals(Direction.LEFT)){
                swordAvatarImageView.setViewport(sword.getLeft());
            }
            if (Direction.RIGHT.equals(linkDirection)) {
                swordAvatarImageView.setViewport(sword.getRight());
            }
            if ( linkDirection.equals(Direction.UP)){
                swordAvatarImageView.setViewport(sword.getUp());
            }
            if (linkDirection.equals(Direction.DOWN)){
                swordAvatarImageView.setViewport(sword.getDown());
            }

            swordAvatarImageView.setTranslateX(shrink* data.getWeaponPosition().x + shrink*xModifier-radius);
            swordAvatarImageView.setTranslateY(shrink* data.getWeaponPosition().y + shrink*xModifier-radius);
            swordAvatarImageView.setFitWidth(shrink*data.getLonk().getWeapon().getWidth());
            swordAvatarImageView.setFitHeight(shrink*data.getLonk().getWeapon().getHeight());

            panel.getChildren().add(swordAvatarImageView);

//            Rectangle sword = new Rectangle(radius, radius);
//            sword.setFill(new ImagePattern(new Image("File:src/images/sword1.png")));
//            sword.setTranslateX(shrink * data.getWeaponPosition().x + shrink);
//            sword.setTranslateY(shrink * data.getWeaponPosition().y + shrink);
//
//            panel.getChildren().add(sword);
    }
//		Rectangle heroes = new Rectangle(500,500);
//		heroes.setFill(new ImagePattern(new Image("File:src/images/red_lonk.png")));
//		heroes.setEffect(new Lighting());
//		heroes.setTranslateX(shrink*data.getLonk().getPosition().x+shrink*xModifier-radius);
//		heroes.setTranslateY(shrink*data.getLonk().getPosition().y+shrink*yModifier-radius);
//	    panel.getChildren().add(heroes);


	    /* Cr�ation des ennemies*/
	    ArrayList<Enemies> enemies = data.getEnemies();
	    Enemies e;
	    for(int i=0; i<enemies.size();i++)
	    {
	    	e = enemies.get(i);
	    	double rad=Math.min(shrink*20,shrink*20);
			Rectangle enemy_c = new Rectangle(rad,rad);
			enemy_c.setFill(Color.RED);
			enemy_c.setEffect(new Lighting());
			enemy_c.setTranslateX(shrink*e.getPosition().x+shrink*xModifier-radius);
			enemy_c.setTranslateY(shrink*e.getPosition().y+shrink*yModifier-radius);
			//System.out.println("Ennemi en x :"+e.getPosition().x+", y"+e.getPosition().y);
		    panel.getChildren().add(enemy_c);
	    }

	    /* Cr�ation des obstacles */
	    ArrayList<Obstacle> obstacles = data.getMap().getObstacles();
	    Obstacle o;
	    for(int i=0; i<obstacles.size();i++)
	    {
	    	o = obstacles.get(i);
            double rad=Math.min(shrink*40,shrink*40);
            Rectangle obs = new Rectangle(rad,rad);
			obs.setFill(new ImagePattern(new Image("File:src/images/sapin.png")));
			obs.setEffect(new Lighting());
			obs.setTranslateX(shrink*o.getPosition().x+shrink*xModifier-radius);
			obs.setTranslateY(shrink*o.getPosition().y+shrink*yModifier-radius);
			//System.out.println("Ennemi en x :"+e.getPosition().x+", y"+e.getPosition().y);
		    panel.getChildren().add(obs);
	    }
	    /* Cr�ation des trous */
	    ArrayList<Holes> holes = data.getMap().getHoles();
	    Holes hole;
	    for(int i=0; i<holes.size();i++)
	    {
	    	hole = holes.get(i);
            double rad=Math.min(shrink*20,shrink*20);
            Rectangle hol = new Rectangle(rad,rad);
			hol.setFill(new ImagePattern(new Image("File:src/images/hole.png")));
			hol.setEffect(new Lighting());
			hol.setTranslateX(shrink*hole.getPosition().x+shrink*xModifier-radius);
			hol.setTranslateY(shrink*hole.getPosition().y+shrink*yModifier-radius);
			//System.out.println("Ennemi en x :"+e.getPosition().x+", y"+e.getPosition().y);
		    panel.getChildren().add(hol);
	    }

		return panel;

	}

	@Override
	public void setMainWindowWidth(double w){
		xShrink = w/data.getMap().getWidth();
	}

	@Override
	public void setMainWindowHeight(double h){
		yShrink = h/data.getMap().getHeight();
	}
}
