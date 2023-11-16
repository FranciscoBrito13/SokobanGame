package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.utils.Point2D;

public class Bateria extends GameElement{
	
	private int batteryAmount = 50;

	public Bateria(Point2D position) {
		super(position);
	}


	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Bateria";
	}


	@Override
	public int getLayer() {
		// TODO Auto-generated method stub
		return 1;
	}
	
	public int getBattery(){
		return batteryAmount;

	}
	
	@Override
	public boolean interact(GameElement e) {
		Empilhadora.getInstance(e.getPosition()).consumeBattery(this);
		ImageMatrixGUI.getInstance().removeImage(this);
		GameEngine.getInstance().removeElement(getPosition());
		ImageMatrixGUI.getInstance().addImage(new Chao(getPosition()));
		return true;
	}

}