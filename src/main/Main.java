package main;

import data.Data;
import engine.Engine;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import specifications.DataService;
import specifications.EngineService;
import specifications.ViewerService;
import tools.User;
import ui.Viewer;
//import ui.ViewerData;
//import ui.ViewerEngine;

public class Main extends Application{

	  private static DataService data;
	  private static EngineService engine;
	  private static ViewerService viewer;
	  private static ViewerService dataViewer;
	  private static AnimationTimer timer;
	  private static boolean onStop;

	  public static void main(String args[]){
		data = new Data();
		engine = new Engine();
		viewer = new Viewer();
		//dataViewer = new ViewerData();

		((Engine) engine).bindDataService(data);
		((Viewer) viewer).bindReadService(data);
		//((ViewerData) dataViewer).bindReadService(data);
		(viewer).bindEngineService(engine);

//		((ViewerData) dataViewer).bindReadService(data);

		data.init();
		engine.init();
		viewer.init();
		//dataViewer.init();
//		dataViewer.init();

		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub

		Group root = new Group();
		Scene scene = new Scene(root, 500, 500, Color.WHITE);


		root.getChildren().add(viewer.getPanel());


		scene.setFill(Color.ANTIQUEWHITE);
		scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
				public void handle(KeyEvent event) {
					if (!onStop){
						if (event.getCode() == KeyCode.LEFT) engine.setHeroesCommand(User.COMMAND.LEFT);
						if (event.getCode() == KeyCode.RIGHT) engine.setHeroesCommand(User.COMMAND.RIGHT);
						if (event.getCode() == KeyCode.UP) engine.setHeroesCommand(User.COMMAND.UP);
						if (event.getCode() == KeyCode.DOWN) engine.setHeroesCommand(User.COMMAND.DOWN);
						if (event.getCode() == KeyCode.SPACE) engine.setHeroesCommand(User.COMMAND.SPACE);
					}
				if (event.getCode()==KeyCode.R) {
					data.init();
					engine.init();
					viewer.init();
				}

					if (event.getCode()==KeyCode.P) {
					  engine.setHeroesCommand(User.COMMAND.P);
		          	if(!onStop){
		          		timer.stop();
						onStop = true;
					}else{
						timer.start();
						onStop = false;
					}
				  }

		          event.consume();
			}
		});

		scene.setOnKeyReleased(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent event){
		          if (event.getCode()==KeyCode.LEFT) engine.releaseHeroesCommand(User.COMMAND.LEFT);
		          if (event.getCode()==KeyCode.RIGHT) engine.releaseHeroesCommand(User.COMMAND.RIGHT);
		          if (event.getCode()==KeyCode.UP) engine.releaseHeroesCommand(User.COMMAND.UP);
		          if (event.getCode()==KeyCode.DOWN) engine.releaseHeroesCommand(User.COMMAND.DOWN);
		          if (event.getCode()==KeyCode.SPACE) engine.releaseHeroesCommand(User.COMMAND.SPACE);
		          if (event.getCode()==KeyCode.P) engine.releaseHeroesCommand(User.COMMAND.P);
		          event.consume();
			}
		});
	    scene.widthProperty().addListener(new ChangeListener<Number>() {
	    	public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
				viewer.setMainWindowWidth(newSceneWidth.doubleValue());
	        }
	    });
	    scene.heightProperty().addListener(new ChangeListener<Number>() {
	        @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
				viewer.setMainWindowHeight(newSceneHeight.doubleValue());
	        }
	    });

	    stage.setScene(scene);
	    stage.setWidth(data.getMap().getWidth());
	    stage.setHeight(data.getMap().getHeight());
	    stage.setOnShown(new EventHandler<WindowEvent>() {
	        @Override public void handle(WindowEvent event) {
	          engine.start();
	        }
	      });
	      stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	        @Override public void handle(WindowEvent event) {
	          engine.stop();
	        }
	      });
	      stage.show();


			timer = new AnimationTimer() {
				@Override public void handle(long l) {
					if(data.getLonk().getHp() > 0)
				  		scene.setRoot(viewer.getPanel());
					else
						scene.setRoot(viewer.getGameOverPanel());


				};
			};

	 /* 	timer = new AnimationTimer() {
	        @Override public void handle(long l) {
	          scene.setRoot(viewer.getPanel());
	        }
	    };*/
	    timer.start();
	    onStop = false;
	}

	/*public static void gameOver(boolean isGameOver){
		System.out.print("GameOver");

		Group root = new Group();

		Scene scene = new Scene(root, 500, 500, Color.WHITE);

		if(isGameOver){
			System.out.print("Game");
			timer = new AnimationTimer() {
				@Override public void handle(long l) {
					scene.setRoot(viewer.getPanel());
				}
			};
		}
	}*/

}
