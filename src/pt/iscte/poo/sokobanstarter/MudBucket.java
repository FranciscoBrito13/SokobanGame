package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.utils.Point2D;

public class MudBucket extends GameElement implements Item{

	
	/*[MUDBUCKET CONSTRUCTOR]*/
	public MudBucket(Point2D position) {
		super(position);
		setName("BaldeLama");
	}
	
	@Override
	public int getLayer(){
		return 1;
	}
	
	/*[MUDBUCKET INTERACT FUNCION]*/
	@Override 
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
