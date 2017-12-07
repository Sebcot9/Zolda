package specifications;

import java.util.ArrayList;

import classes.*;

public interface ReadService {

	Heroes getLonk();
	ArrayList<Enemies> getEnemies();
	ArrayList<Allies> getAllies();
	ArrayList<Pets> getPets();
	Map getMap();
	int getMinX();
	int getMaxX();
	int getMinY();
	int getMaxY();

	int getLinkHeight();
	int getLinkWidth();
	int getStepNumber();
	Position getWeaponPosition();
	int getStepAttack();



}
