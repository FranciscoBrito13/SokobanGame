package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Parede extends GameElement {

	public Parede(Point2D position) {
		super(position);
		setName("Parede");
	}

	@Override
	public int getLayer() {
		return 4;
	}

	public void interact() {

	}

}