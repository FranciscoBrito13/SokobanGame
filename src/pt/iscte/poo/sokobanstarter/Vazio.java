package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Vazio extends GameElement {

	public Vazio(Point2D position) {
		super(position);
		setName("Vazio");
	}

	@Override
	public int getLayer() {
		// TODO Auto-generated method stub
		return 0;
	}

}