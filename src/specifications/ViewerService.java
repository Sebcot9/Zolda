package specifications;

import javafx.scene.Parent;

public interface ViewerService {

	void init();

	Parent getPanel();
	Parent getGameOverPanel();

	void setMainWindowWidth(double w);

	void setMainWindowHeight(double h);
	void bindEngineService(EngineService service);

}
