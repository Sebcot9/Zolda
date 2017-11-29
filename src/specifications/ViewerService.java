package specifications;

import javafx.scene.Parent;
import ui.HeroSprites;

public interface ViewerService {

	void init();

	Parent getPanel();

	void setMainWindowWidth(double w);

	void setMainWindowHeight(double h);

	HeroSprites getHeroSprites();

}
