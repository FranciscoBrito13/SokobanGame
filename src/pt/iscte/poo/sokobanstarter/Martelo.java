package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.utils.Point2D;

public class Martelo extends GameElement {

	/* [HAMMER CONSTRUCTOR] */
	public Martelo(Point2D position) {
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
		if(other instanceof Empilhadora){
			GameEngine.getInstance().removeElement(this);
			ImageMatrixGUI.getInstance().removeImage(this);
			((Empilhadora) other).pickHammer();
			return true;
		}
		return false;
	}

}
