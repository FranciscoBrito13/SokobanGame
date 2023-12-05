package pt.iscte.poo.sokoban;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.utils.Point2D;

public class Battery extends GameElement{
	
	/*[BATTERY AMMOUT]*/
	private int batteryAmount = 50;

	/*[BATTERY CONSTRUCTOR]*/
	public Battery(Point2D position) {
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
		if(other instanceof Bobcat){
			Bobcat.getInstance().consumeBattery(this);
			ImageMatrixGUI.getInstance().removeImage(this);
			GameEngine.getInstance().removeElement(this);
			return true;
		} 
		return false;
		
	}

}