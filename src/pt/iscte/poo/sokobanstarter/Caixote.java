package pt.iscte.poo.sokobanstarter;

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
		return 2;
	}
	
	
    public void setPosition(Point2D position) {
        this.position = position;
    }

    @Override
	public void move(Point2D p) {
	    Point2D oldPosition = getPosition();
	    setPosition(p);
	    GameEngine.getInstance().relocateObject(oldPosition, p, this);
	}
    
    public boolean isPositionValid(Point2D p) {
        GameElement elementAtNewPosition = GameEngine.getInstance().getGameElement(p);
        return elementAtNewPosition == null || elementAtNewPosition instanceof Bateria || elementAtNewPosition instanceof Alvo;
    }
	
    @Override
    public boolean interact(GameElement other) {
    	Vector2D move = Vector2D.movementVector(other.getPosition(), getPosition());
    	Point2D p = getPosition().plus(move);
    	if(isPositionValid(p)) {	
    		move(p);
    		return true;
    	}
    	return false;
    	
    	
    }
    
    
    
    
}