package ui;

import javafx.scene.shape.Rectangle;
import specifications.ReadService;
import specifications.RequireReadService;
import specifications.ViewerService;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Viewer implements ViewerService, RequireReadService{

	private ReadService data;
	private double xShrink, yShrink,shrink,xModifier, yModifier;
	@Override
	public void bindReadService(ReadService service)
	{
		data=service;
	}

	@Override
	public void init(){
	    xShrink=1;
	    yShrink=1;
	    xModifier=0;
	    yModifier=0;
	}

	@Override
	public Parent getPanel(){
		Group panel = new Group();
		shrink = Math.min(xShrink, yShrink);
		xModifier = .01*shrink*data.getMap().getHeight();
		yModifier = .01*shrink*data.getMap().getWidth();
		double radius=.5*Math.min(shrink*20,shrink*20);

		//Vue Utilisateur
		Rectangle userView = new Rectangle(-2*xModifier+shrink*800,
				-.2*shrink*600+shrink*600);
		userView.setFill(Color.WHITE);
		userView.setStroke(Color.DIMGRAY);
		userView.setStrokeWidth(.01*shrink*600);
		userView.setArcWidth(.04*shrink*600);
		userView.setArcHeight(.04*shrink*600);
		userView.setTranslateX(xModifier);
		userView.setTranslateY(yModifier);
		panel.getChildren().add(userView);

		//Vue des Statistiques
		Rectangle statView = new Rectangle(-2*xModifier+shrink*224,
				-.2*shrink*600+shrink*600);
		statView.setFill(Color.WHITE);
		statView.setStroke(Color.DIMGRAY);
		statView.setStrokeWidth(.01*shrink*600);
		statView.setArcWidth(.04*shrink*600);
		statView.setArcHeight(.04*shrink*600);
		statView.setTranslateX(xModifier*92);
		statView.setTranslateY(yModifier);
		panel.getChildren().add(statView);

		//Vue Console
		Rectangle consoleView = new Rectangle(-2*xModifier+shrink*1024,
				-.2*shrink*400+shrink*400);
		consoleView.setFill(Color.WHITE);
		consoleView.setStroke(Color.DIMGRAY);
		consoleView.setStrokeWidth(.01*shrink*600);
		consoleView.setArcWidth(.04*shrink*600);
		consoleView.setArcHeight(.04*shrink*600);
		consoleView.setTranslateX(xModifier);
		consoleView.setTranslateY(yModifier*50);
		panel.getChildren().add(consoleView);

		Circle heroes = new Circle(radius);
		heroes.setFill(Color.DARKBLUE);
		heroes.setEffect(new Lighting());
		heroes.setTranslateX(shrink*data.getLonk().getPosition().x+shrink*xModifier-radius);
		heroes.setTranslateY(shrink*data.getLonk().getPosition().y+shrink*yModifier-radius);
	    panel.getChildren().add(heroes);


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
