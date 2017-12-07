package main;

import data.Data;
import engine.Engine;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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

	//Valeurs Tests
	private BorderPane panel1 = new BorderPane();
	private Text message1 = new Text("TOD");
	private Text message2 = new Text("The Origin of Destiny");
	private Button boutonJouer = new Button("Jouer");
	private static Button retour = new Button("Retour � l'�cran titre");
	
	public static void main(String args[]){
		data = new Data();
		engine = new Engine();
		viewer = new Viewer();
		//dataViewer = new ViewerData();

		((Engine) engine).bindDataService(data);
		((Viewer) viewer).bindReadService(data);
		//((ViewerData) dataViewer).bindReadService(data);
		((Viewer) viewer).bindEngineService(engine);

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
		//double shrink = Math.min(1, 1);
		//Panel Test
		boutonJouer.setPrefSize(295, 50);
		//retour.set
		message1.setFont(new Font(300));
		message2.setFont(new Font(100));
		BorderPane.setAlignment(boutonJouer, Pos.BASELINE_CENTER);
		BorderPane.setAlignment(message1, Pos.TOP_CENTER);
		BorderPane.setAlignment(message2, Pos.TOP_CENTER);
		

		Image imageTitre = new Image("File:src/images/ecrantitre.jpg");
		BackgroundSize sizeEcran = new BackgroundSize(100,100, true, true, true, true);
		BackgroundImage ecranTitre = new BackgroundImage(imageTitre, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, sizeEcran);
		message1.setFill(Color.BURLYWOOD);
		message2.setFill(Color.BURLYWOOD);
		panel1.setBackground(new Background(ecranTitre));		
		panel1.setTop(message1);
		panel1.setCenter(message2);
		panel1.setBottom(boutonJouer);
		//panel1.getChildren().addAll(message1, message2, boutonJouer);
		
		//boutonJouer.setAlignment(Pos.TOP_CENTER);
		//(boutonJouer); 
		Group root = new Group();
		Scene scene = new Scene(panel1, 500, 500, Color.WHITE);
		
		retour.setOnKeyPressed(e->{
			if(e.getCode() == KeyCode.SPACE){
				e.consume();
			}
		});
		
		viewer.setButton(retour);
		viewer.getButton().setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				timer.stop();
				engine.stop();
				//engine.init();
				//engine.stop();
				//engine.start();
				System.out.println("Nani ?");
				scene.setRoot(panel1);
			}

		});
		
		//root.getChildren().add(viewer.getPanel());
		root.getChildren().add(viewer.getPanel());
		//root.getChildren().add(retour);
		boutonJouer.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				
				data.init();
				engine.init();
				//viewer.init();
				timer = new AnimationTimer() {
					@Override public void handle(long l) {
						scene.setRoot(root);
						scene.setRoot(viewer.getPanel());
						
					}
				};
				timer.start();
			}

		});
		 
		

	
		//viewer.getButton();

		scene.setFill(Color.ANTIQUEWHITE);

		scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event){
				if (event.getCode()==KeyCode.LEFT) engine.setHeroesCommand(User.COMMAND.LEFT);
				if (event.getCode()==KeyCode.RIGHT) engine.setHeroesCommand(User.COMMAND.RIGHT);
				if (event.getCode()==KeyCode.UP) engine.setHeroesCommand(User.COMMAND.UP);
				if (event.getCode()==KeyCode.DOWN) engine.setHeroesCommand(User.COMMAND.DOWN);
				if (event.getCode()==KeyCode.SPACE) engine.setHeroesCommand(User.COMMAND.SPACE);
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
				event.consume();
			}
		});
		scene.widthProperty().addListener(new ChangeListener<Number>() {
			@Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
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



	}
}
