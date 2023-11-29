package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Alvo extends GameElement{
	
	/* [TARGET CONSTRUCTOR]*/
	public Alvo(Point2D position) {
		super(position);
		setName("Alvo2");
	}
	
	/*[LAYER GETTER]*/
	@Override
	public int getLayer(){
		return 1;
	}
	
	/*[TARGET INTERACT FUNCTION, ALLOWS STEPPING OVER THE TARGET] */
	@Override
	public boolean interact(GameElement g) {
		return true;
	}

}