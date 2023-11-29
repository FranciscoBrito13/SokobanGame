package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

/*[INTERFACE THAT CLASSES WHICH HAVE MOVEMENT IMPLEMENT]*/
public interface Movable {

	/* [DEFAULT MOVE FUNCTION] */
	default void move(Point2D p) {setPosition(p);}
	
	/* [ASSIGNS SET POSITION FUNCTION] */
	void setPosition(Point2D p);
	
}