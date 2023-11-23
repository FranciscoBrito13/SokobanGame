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
	
	
	public boolean interact(GameElement other){
		Point2D bobcatPosition = other.getPosition();
		Point2D boxPosition = getPosition();
		Vector2D moveVector = Vector2D.movementVector(bobcatPosition, boxPosition);
		Point2D newBoxPoint = boxPosition.plus(moveVector);
		
		if(other instanceof Empilhadora){
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