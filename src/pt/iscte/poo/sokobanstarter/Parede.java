package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Parede extends GameElement {

	public Parede(Point2D position) {
		super(position);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Parede";
	}


	@Override
	public int getLayer() {
		// TODO Auto-generated method stub
		return 2;
	}

	public void interact() {

	}

}