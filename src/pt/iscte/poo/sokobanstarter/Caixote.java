package pt.iscte.poo.sokobanstarter;

import java.util.List;

import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Caixote extends GameElement implements Movable{


	public Caixote(Point2D position){
		super(position);
	}

	@Override
	public String getName() {
		return "Caixote";
	}

	@Override
	public int getLayer() {
		return 3;
	}


	public void setPosition(Point2D position) {
		this.position = position;
	}

	@Override
	public void move(Point2D p) {
		setPosition(p);
	}

	public boolean isPositionValid(Point2D p) {
		List<GameElement> elementsAtNewPosition = GameEngine.getInstance().getGameElement(p);
		return elementsAtNewPosition == null || elementsAtNewPosition.isEmpty() ||
				elementsAtNewPosition.stream().anyMatch(ge -> ge instanceof Alvo || ge instanceof Teleporte);
	}
	
	
	@Override
	public boolean interact(GameElement other) {
		Vector2D move = Vector2D.movementVector(other.getPosition(), getPosition());
		Point2D p = getPosition().plus(move);
		List<GameElement> g = GameEngine.getInstance().getGameElement(p);
		if(isPositionValid(p)){
			for (GameElement ge : g) {
				if(ge instanceof Teleporte){
					ge.interact(this);
					return true;
				}
			}
			move(p);
			return true;
		}
		return false;
	

	}




}