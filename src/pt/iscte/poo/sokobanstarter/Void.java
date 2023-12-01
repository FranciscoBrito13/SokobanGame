package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Void extends GameElement {

	/* [VOID CONSTRUCTOR] */
	public Void(Point2D position) {
		super(position);
		setName("Vazio");
	}

	/* [LAYER GETTER] */
	@Override
	public int getLayer() {
		return 0;
	}

}