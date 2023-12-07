package pt.iscte.poo.sokoban;

import java.util.List;

import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Box extends GameElement implements Movable, Placable{

	/* [BOX CONSTRUCTOR] */
	public Box(Point2D position){
		super(position);
		setPriority(1);
		setName("Caixote");
	}
	
	/* [LAYER GETTER] */
	@Override
	public int getLayer() {
		return 2;
	}
	
	/* [BOX INTERACT FUNCTION] */
	@Override
	public boolean interact(GameElement other){
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