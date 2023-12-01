package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Wall extends GameElement {

	/* [WALL CONSTRUCTOR] */
	public Wall(Point2D position) {
		super(position);
		setName("Parede");
	}

	/* [LAYER GETTER] */
	@Override
	public int getLayer() {
		return 4;
	}

}