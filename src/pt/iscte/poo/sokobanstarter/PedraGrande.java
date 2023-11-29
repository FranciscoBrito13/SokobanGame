package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.utils.Point2D;

public class PedraGrande extends GameElement{

	/* [BOULDER CONSTRUCTOR] */
	public PedraGrande(Point2D position) {
		super(position);
		setName("BigStone2");
	}
	
	/* [LAYER GETTER] */
	@Override
	public int getLayer(){
		return 3;
	}
	
	/* [BOULDER INTERACT FUNCTION] */
	@Override
	public boolean interact(GameElement other){
		if(other instanceof Explosivo){
			GameEngine.getInstance().removeElement(this);
			ImageMatrixGUI.getInstance().removeImage(this);
			GameEngine.getInstance().removeElement(other);
			ImageMatrixGUI.getInstance().removeImage(other);
			return true;
		}
		return false;
	}

}
