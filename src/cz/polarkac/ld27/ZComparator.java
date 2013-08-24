package cz.polarkac.ld27;

import java.util.Comparator;

import cz.polarkac.ld27.entities.Entity;
import cz.polarkac.ld27.entities.Flower;

public class ZComparator implements Comparator<Entity> {

	@Override
	public int compare( Entity e1, Entity e2 ) {
		if ( e1 instanceof Flower ) {
			return -1;
		} else if ( e2 instanceof Flower ) {
			return 1;
		}
		return e1.getPosY() - e2.getPosY();
	}

}
