package pt.iscte.poo.sokobanstarter;

import java.util.List;

import pt.iscte.poo.utils.Point2D;

public class Teleporte extends GameElement{

	public Teleporte(Point2D position) {
		super(position);
	}
	
	@Override
	public String getName() {
		return "Teleporte";
	}

	@Override
	public int getLayer() {
		return 1;
	}
	
	private Teleporte getPair(){
		GameEngine gEngine = GameEngine.getInstance();
		List<Teleporte> teleportes = gEngine.getTeleportes();
		for(Teleporte t : teleportes){
			if(!(t.getPosition().equals(position))){
				return t;
			}
		}
		return null;
	}
	
	
	public boolean interact(GameElement other) {
		Point2D oldPosition = other.getPosition();
		other.setPosition(getPair().getPosition());
		GameEngine.getInstance().relocateObject(oldPosition, other.getPosition(), other);
		return false;
	} 


}
