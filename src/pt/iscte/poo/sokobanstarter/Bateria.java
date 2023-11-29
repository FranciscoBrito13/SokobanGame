package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.utils.Point2D;

public class Bateria extends GameElement{
	
	/*[BATTERY AMMOUT]*/
	private int batteryAmount = 50;

	/*[BATTERY CONSTRUCTOR]*/
	public Bateria(Point2D position) {
		super(position);
		setName("Bateria");
	}

	/* [LAYER GETTER] */
	@Override
	public int getLayer() {
		return 1;
	}
	
	/* [BATTERY AMOUNT GETTER] */
	public int getBattery(){
		return batteryAmount;

	}
	
	/* [BATTERY INTERACT FUNCTION] */
	@Override
	public boolean interact(GameElement other) {
		if(other instanceof Empilhadora){
			Empilhadora.getInstance().consumeBattery(this);
			ImageMatrixGUI.getInstance().removeImage(this);
			GameEngine.getInstance().removeElement(this);
			ImageMatrixGUI.getInstance().addImage(new Chao(getPosition()));
			return true;
		} 
		return false;
		
	}

}