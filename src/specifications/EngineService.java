package specifications;

import tools.User;

public interface EngineService {

	void init();

	void start();

	void stop();

	void setHeroesCommand(User.COMMAND c);

	void releaseHeroesCommand(User.COMMAND c);
	boolean getMoveLeft();
	boolean getmoveRight();
	boolean getmoveUp();
	boolean getmoveDown();

	boolean isPushSpace();
	void isGameOver();
	void setPushSpace(boolean bool);

//	void resume();
}
