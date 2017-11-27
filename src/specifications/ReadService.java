package specifications;

import java.util.ArrayList;

import classes.Allies;
import classes.Enemies;
import classes.Heroes;
import classes.Maps;
import classes.Pets;

public interface ReadService {

	Heroes getLonk();

	ArrayList<Enemies> getEnemies();

	ArrayList<Allies> getAllies();

	ArrayList<Pets> getPets();

	Maps getMaps();

}
