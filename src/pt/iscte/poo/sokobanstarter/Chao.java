package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Chao extends GameElement {

	/* [FLOOR CONSTRUCTOR] */
	public Chao(Point2D position){
		super(position);
		setName("Chao");
	}
	
	/* [LAYER GETTER] */
	@Override
	public int getLayer() {
		return 0;
	}

}
