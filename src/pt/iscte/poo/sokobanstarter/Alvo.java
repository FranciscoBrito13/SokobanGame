package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Alvo extends GameElement{
	

	public Alvo(Point2D position) {
		super(position);
		setName("Alvo2");
	}
	
	
	@Override
	public int getLayer(){
		return 1;
	}
	
	@Override
	public boolean interact(GameElement g) {
		return true;
	}

}