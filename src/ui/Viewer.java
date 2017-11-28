package ui;

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
