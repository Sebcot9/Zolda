package ui;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import specifications.EngineService;
import specifications.ReadService;
import specifications.RequireReadService;
import specifications.ViewerService;

public class ViewerEngine implements ViewerService, RequireReadService{

	private ReadService data;
	private EngineService engine;
	private double xShrink, yShrink,shrink,xModifier, yModifier;

	@Override
	public void bindReadService(ReadService service)
	{
		data=service;
	}

	@java.lang.Override
	public void bindEngineService(EngineService service) {
		engine = service;
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

	@java.lang.Override
	public HeroSprites getHeroSprites() {
		return null;
	}
}
