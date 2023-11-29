package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Vazio extends GameElement {

	/* [VOID CONSTRUCTOR] */
	public Vazio(Point2D position) {
		super(position);
		setName("Vazio");
	}

	/* [LAYER GETTER] */
	@Override
	public int getLayer() {
		return 0;
	}

}