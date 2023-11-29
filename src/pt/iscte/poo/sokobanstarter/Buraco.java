package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.utils.Point2D;

public class Buraco extends GameElement{

	/* [HOLE CONSTRUCTOR] */
	public Buraco(Point2D position) {
		super(position);
		setPriority(1);
		setName("Buraco");
	}
	
	/* [LAYER GETTER] */
	@Override
	public int getLayer() {
		return 1;
	}

	/* [HOLE INTERACT FUNCTION] */
	@Override
	public boolean interact(GameElement other){
		if(other instanceof Empilhadora){
			GameEngine.getInstance().handleGameOver();
			ImageMatrixGUI.getInstance().setMessage("The bobcat fell into the hole and turned back to go to the workshop");
			return false;
		}
		if(other instanceof Caixote){
			GameEngine.getInstance().removeElement(other);
			ImageMatrixGUI.getInstance().removeImage(other);
			return true;
		}
		if(other instanceof Explosivo){
			GameEngine.getInstance().removeElement(other);
			ImageMatrixGUI.getInstance().removeImage(other);
			return true;
		}
		if(other instanceof Palete){
			setName("Buraco_Palete");
			ImageMatrixGUI.getInstance().removeImage(other);
			GameEngine.getInstance().removeElement(other);
			GameEngine.getInstance().removeElement(this);
			
			
		}
		return true;
	}

}
