package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.utils.Point2D;

public class Hammer extends GameElement implements Item {

	/* [HAMMER CONSTRUCTOR] */
	public Hammer(Point2D position) {
		super(position);
		setName("Martelo");
	}
	
	/* [LAYER GETTER] */
	@Override
	public int getLayer() {
		return 1;
	}
	
	/* [HAMMER INTERACT FUNCTION] */
	public boolean interact(GameElement other){
		if(other instanceof Bobcat){
			GameEngine.getInstance().removeElement(this);
			ImageMatrixGUI.getInstance().removeImage(this);
			((Bobcat) other).addItem(this);
			return true;
		}
		return false;
	}

}
