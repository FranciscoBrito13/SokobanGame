package pt.iscte.poo.sokoban;

import java.util.List;

import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Explosive extends GameElement implements Movable{

	/* [EXPLOSIVE CONSTRUCTOR] */
	public Explosive(Point2D position) {
		super(position);
		setName("Explosivo");
		setPriority(1);
	}
	
	/* [LAYER GETTER] */
	@Override
	public int getLayer() {
		return 3;
	}
	
	/* [EXPLOSIVE INTERACT FUNCTION] */
	@Override
	public boolean interact(GameElement other) {
		Point2D otherPosition = other.getPosition();
		Point2D initialPosition = getPosition();
		Vector2D moveVector = Vector2D.movementVector(otherPosition, initialPosition);
		Point2D newBoxPoint = initialPosition.plus(moveVector);
		
		if(other instanceof Bobcat){
			boolean canMove = true;
			List<GameElement> elements = GameEngine.getInstance().getGameElement(newBoxPoint);
			elements.sort((g1, g2) -> g2.getPriority() - g1.getPriority());
			for(GameElement g : elements){
				canMove = canMove && g.interact(this);	

			}
			if(canMove)
				move(newBoxPoint);
			return canMove || initialPosition != getPosition();
		}
		return false;
	}

}
