package specifications;

import java.util.ArrayList;

import classes.*;

public interface WriteService {

	void setLonk(Heroes lonk);

	void setEnemies(ArrayList<Enemies> enemies);

	void setAllies(ArrayList<Allies> allies);

	void setPets(ArrayList<Pets> pets);

	void setMaps(Map map);

	void setStepNumber(int stepNumber);

	void setWeaponPosition(Position pos);

}
