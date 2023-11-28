package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.utils.Point2D;

public class ParedeRachada extends GameElement{

	public ParedeRachada(Point2D position) {
		super(position);
		setName("ParedeRachada");
	}
	@Override
	public int getLayer(){
		return 4;
	}
	
	@Override
	public boolean interact(GameElement other){
		if(other instanceof Empilhadora){
			if(((Empilhadora) other).hasHammer()){
			GameEngine.getInstance().removeElement(this);
			ImageMatrixGUI.getInstance().removeImage(this);
			return true;
			}
		}
		return false;
	}

}
