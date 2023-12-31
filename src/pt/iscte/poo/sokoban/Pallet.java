package pt.iscte.poo.sokoban;

import java.util.List;

import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Pallet extends GameElement implements Movable {

	/* [PALLET CONSTRUCTOR] */
	public Pallet(Point2D position) {
		super(position);
		setName("Palete");
	}
	
	/* [LAYER GETTER] */
	@Override
	public int getLayer() {
		return 2;
	}
	
	/* [PALLET INTERACT FUNCTION] */
	public boolean interact(GameElement other){
		Point2D bobcatPosition = other.getPosition();
		Point2D boxPosition = getPosition();
		Vector2D moveVector = Vector2D.movementVector(bobcatPosition, boxPosition);
		Point2D newBoxPoint = boxPosition.plus(moveVector);
		
		if(other instanceof Bobcat){
			boolean canMove = true;
			List<GameElement> elements = GameEngine.getInstance().getGameElement(newBoxPoint);
			for(GameElement g : elements){
				canMove = canMove && g.interact(this);	
				//if(initialPosition.equals(finalPosition)) return true;

			}
			if(canMove)
				move(newBoxPoint);
			return canMove || boxPosition != getPosition();
		}
		return false;
	}


}
