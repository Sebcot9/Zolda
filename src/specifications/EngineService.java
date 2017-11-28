package specifications;

import tools.User;

public interface EngineService {

	void init();

	void start();

	void stop();

	void setHeroesCommand(User.COMMAND c);

	void releaseHeroesCommand(User.COMMAND c);
}
