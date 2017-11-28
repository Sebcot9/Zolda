package ui;

import specifications.ReadService;
import specifications.RequireReadService;
import specifications.ViewerService;
import javafx.scene.Group;
import javafx.scene.Parent;

public class Viewer implements ViewerService, RequireReadService{

	private ReadService data;
	private double xShrink, yShrink, xModifier, yModifier;
	@Override
	public void bindReadService(ReadService service)
	{
		data=service;
	}
	
	@Override
	public void init(){
		
	}
	
	@Override
	public Parent getPanel(){
		Group panel = new Group();
		return panel;
		
	}
	
	@Override
	public void setMainWindowWidth(double w){}
	
	@Override
	public void setMainWindowHeight(double h){}
}
