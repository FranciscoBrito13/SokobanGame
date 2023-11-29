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
		
		//INTERACTION WITH BOBCAT, GAME OVER
		if(other instanceof Empilhadora){
			GameEngine.getInstance().handleGameOver();
			ImageMatrixGUI.getInstance().setMessage("The bobcat fell into the hole and turned back to go to the workshop");
			return false;
		}
		//INTERACTION WITH CAIXOTE, REMOVES IMAGE AND ELEMENT
		if(other instanceof Caixote){
			GameEngine.getInstance().removeElement(other);
			ImageMatrixGUI.getInstance().removeImage(other);
			return true;
		}
		//INTERACTION WITH EXPLOSIVE, REMOVES ELEMENT AND IMAGE
		if(other instanceof Explosivo){
			GameEngine.getInstance().removeElement(other);
			ImageMatrixGUI.getInstance().removeImage(other);
			return true;
		}
		//INTERACTION WITH PALLET, REMOVES THE OTHER ELEMENT AND THE HOLE, CHANGES THE HOLE IMAGE
		if(other instanceof Palete){
			setName("Buraco_Palete");
			ImageMatrixGUI.getInstance().removeImage(other);
			GameEngine.getInstance().removeElement(other);
			GameEngine.getInstance().removeElement(this);
			
			
		}
		return true;
	}

}
