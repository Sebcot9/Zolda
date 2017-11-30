package ui;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import specifications.EngineService;
import specifications.ReadService;
import specifications.RequireReadService;
import specifications.ViewerService;
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
    private int avatarIndex;
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
    }

    @Override
    public Parent getPanel() {
        Group panel = new Group();
        shrink = Math.min(xShrink, yShrink);
        xModifier = .01 * shrink * data.getMap().getHeight();
        yModifier = .01 * shrink * data.getMap().getWidth();
        double radius = .5 * Math.min(shrink * 20, shrink * 20);

        //Vue Utilisateur
        Rectangle userView = new Rectangle(-2 * xModifier + shrink * 800,
                -.2 * shrink * 600 + shrink * 600);
        userView.setFill(Color.WHITE);
        userView.setStroke(Color.DIMGRAY);
        userView.setStrokeWidth(.01 * shrink * 600);
        userView.setArcWidth(.04 * shrink * 600);
        userView.setArcHeight(.04 * shrink * 600);
        userView.setTranslateX(xModifier);
        userView.setTranslateY(yModifier);
//		panel.getChildren().add(userView);

        //Vue des Statistiques
        Rectangle statView = new Rectangle(-2 * xModifier + shrink * 224,
                -.2 * shrink * 600 + shrink * 600);
        statView.setFill(Color.WHITE);
        statView.setStroke(Color.DIMGRAY);
        statView.setStrokeWidth(.01 * shrink * 600);
        statView.setArcWidth(.04 * shrink * 600);
        statView.setArcHeight(.04 * shrink * 600);
        statView.setTranslateX(xModifier * 92);
        statView.setTranslateY(yModifier);
        panel.getChildren().add(statView);


        //Vue Console
        Rectangle consoleView = new Rectangle(-2 * xModifier + shrink * 1024,
                -.2 * shrink * 400 + shrink * 400);
        consoleView.setFill(Color.WHITE);
        consoleView.setStroke(Color.DIMGRAY);
        consoleView.setStrokeWidth(.01 * shrink * 600);
        consoleView.setArcWidth(.04 * shrink * 600);
        consoleView.setArcHeight(.04 * shrink * 600);
        consoleView.setTranslateX(xModifier);
        consoleView.setTranslateY(yModifier * 50);
        panel.getChildren().add(consoleView);

        int index = avatarIndex/7;

        if (engine.getMoveLeft()) {
            defaultAvatarList = left;
            if (avatarIndex+1 > defaultAvatarList.size()-1)
                avatarIndex = 0;
            else {
                if (data.getStepNumber()%5 == 0) {
                    index += 1;
                    avatarIndex = (avatarIndex + 1);
                }
            }
        } else if (engine.getmoveRight()) {
            defaultAvatarList = right;
            if (avatarIndex+1 > defaultAvatarList.size()-1)
                avatarIndex = 0;
            else {
                index += 1;
                avatarIndex += 1;
            }
        } else if(engine.getmoveUp()){
            defaultAvatarList =up;
            if (avatarIndex+1 > defaultAvatarList.size()-1)
                avatarIndex = 0;
            else {
                index += 1;
                avatarIndex += 1;
            }
        }else if(engine.getmoveDown()) {
             defaultAvatarList =down;
            if (avatarIndex+1 > defaultAvatarList.size()-1)
                avatarIndex = 0;
            else {
                index += 1;
                avatarIndex += 1;
            }
        }else {
            index = 0;
            avatarIndex = 0;
		}
		linkAvatarImageView.setFitHeight(5*10);
		linkAvatarImageView.setPreserveRatio(true);

		linkAvatarImageView.setViewport(defaultAvatarList.get(index));
        linkAvatarImageView.setTranslateX(shrink*data.getLonk().getPosition().x+shrink*xModifier-radius);
		linkAvatarImageView.setTranslateY(shrink*data.getLonk().getPosition().y+shrink*yModifier-radius);
        System.out.print(avatarIndex);
		avatarIndex = (avatarIndex) % (defaultAvatarList.size() * 7);
		panel.getChildren().addAll(userView,linkAvatarImageView);



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

    @Override
    public HeroSprites getHeroSprites() {
        return null;
    }
}
