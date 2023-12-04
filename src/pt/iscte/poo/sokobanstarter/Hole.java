package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.utils.Point2D;

public class Hole extends GameElement{

	/* [HOLE CONSTRUCTOR] */
	public Hole(Point2D position) {
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
		if(other instanceof Bobcat){
			if(((Bobcat) other).checkPredicateInventory(i -> i instanceof MudBucket)){
				GameEngine.getInstance().removeElement(this);
				ImageMatrixGUI.getInstance().removeImage(this);
				((Bobcat) other).removeMudBucket();
				ImageMatrixGUI.getInstance().addImage(new Floor(getPosition()));
				return true;
			}
			GameEngine.getInstance().handleGameOver();
			ImageMatrixGUI.getInstance().setMessage("The bobcat fell into the hole and turned back to go to the workshop");
			return false;
		}
		//INTERACTION WITH PALLET, REMOVES THE OTHER ELEMENT AND THE HOLE, CHANGES THE HOLE IMAGE
		if(other instanceof Pallet){
			setName("Buraco_Palete");
			GameEngine.getInstance().removeElement(this);
			
		}
		GameEngine.getInstance().removeElement(other);
		ImageMatrixGUI.getInstance().removeImage(other);
		return true;
	}

}
