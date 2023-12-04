package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.utils.Point2D;

public class FracturedWall extends GameElement{

	/* [CRACKED WALL CONSTRUCTOR] */
	public FracturedWall(Point2D position) {
		super(position);
		setName("ParedeRachada");
	}
	
	/* [LAYER GETTER] */
	@Override
	public int getLayer(){
		return 4;
	}
	
	/* [FRACTURED WALL INTERACT FUNCTION] */
	@Override
	public boolean interact(GameElement other){
		if(other instanceof Bobcat){
			if(((Bobcat) other).checkPredicateInventory(i -> i instanceof Hammer)){
			GameEngine.getInstance().removeElement(this);
			ImageMatrixGUI.getInstance().removeImage(this);
			return true;
			}
		}
		return false;
	}

}
