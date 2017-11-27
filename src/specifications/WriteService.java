package specifications;

import java.util.ArrayList;

import classes.Allies;
import classes.Enemies;
import classes.Heroes;
import classes.Maps;
import classes.Pets;

public interface WriteService {

	void setLonk(Heroes lonk);

	void setEnemies(ArrayList<Enemies> enemies);

	void setAllies(ArrayList<Allies> allies);

	void setPets(ArrayList<Pets> pets);

	void setMaps(Maps maps);

}
