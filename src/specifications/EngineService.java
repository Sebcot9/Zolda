package specifications;

import tools.User;

public interface EngineService {

	void init();

	void start();

	void stop();
	  public void setHeroesCommand(User.COMMAND c);
	  public void releaseHeroesCommand(User.COMMAND c);
}
