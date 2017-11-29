package ui;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import specifications.EngineService;
import specifications.ReadService;
import specifications.RequireReadService;
import specifications.ViewerService;

public class ViewerData implements ViewerService, RequireReadService{

	private ReadService data;
	private EngineService engine;
	private double xShrink, yShrink,shrink,xModifier, yModifier;
	@Override
	public void bindReadService(ReadService service)
	{
		data=service;
	}

	@Override
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

		Text t = new Text(-0.5*shrink*600+.5*shrink*800,
				-0.5*shrink*800+shrink*600, "shrink data : " + shrink);
		panel.getChildren().add(t);

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
