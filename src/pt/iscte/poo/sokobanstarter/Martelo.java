package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.utils.Point2D;

public class Martelo extends GameElement {

	public Martelo(Point2D position) {
		super(position);
		setName("Martelo");
	}
	
	@Override
	public int getLayer() {
		return 1;
	}
	
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
