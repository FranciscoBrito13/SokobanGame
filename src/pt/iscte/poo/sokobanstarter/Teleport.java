package pt.iscte.poo.sokobanstarter;

import java.util.List;

import pt.iscte.poo.utils.Point2D;


public class Teleport extends GameElement{

	/* [TELEPORT CONSTRUCTOR] */
	public Teleport(Point2D position) {
		super(position);
		setName("Teleporte");
	}
	
	/* [LAYER GETTER] */
	@Override
	public int getLayer() {
		return 1;
	}
	
	/* [TELEPORT'S PAIR GETTER] */
	private Teleport getPair() {
	    GameEngine gEngine = GameEngine.getInstance();
	    List<Teleport> teleportes = gEngine.getTeleportes();
	    
	    for (Teleport t : teleportes)
	    	if (!t.getPosition().equals(getPosition())) return t;
	    return null;
	}

	/* [FUNCTION THAT CHECKS IF THE TELEPORT HAS SOMETHING ABOVE IT] */
	private boolean isPairFree() {
	    Teleport pair = getPair();
	    
	    if (pair != null) {
	        List<GameElement> elementos = GameEngine.getInstance().getGameElement(pair.getPosition());
	        return elementos.size() <= 1;
	    }
	    
	    return false;
	}
	
	/* [TELEPORT INTERACT FUNCTION] */
	@Override
	public boolean interact(GameElement other){
		if(isPairFree()){
			other.teleport(getPair().getPosition());
			return false;
		}
		return true;
	}

}
