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
	
	
	private Teleporte getPair() {
	    GameEngine gEngine = GameEngine.getInstance();
	    List<Teleporte> teleportes = gEngine.getTeleportes();
	    
	    for (Teleporte t : teleportes)
	    	if (!t.getPosition().equals(position)) return t;
	    return null;
	}

	private boolean isPairFree() {
	    Teleporte pair = getPair();
	    
	    if (pair != null) {
	        List<GameElement> elementos = GameEngine.getInstance().getGameElement(pair.getPosition());
	        return elementos.size() <= 1;
	    }
	    
	    return false;
	}
	
	private boolean isThisFree(){
		List<GameElement> elementos = GameEngine.getInstance().getGameElement(getPosition());
		if(elementos.size() > 1){
			return false;
		}
		return true;
	}

	
	@Override
	public boolean interact(GameElement other){
		//Point2D initialPosition = other.getPosition();
		System.out.println(getPair().getPosition() + " " + isPairFree() + " " + other.getName());
		if(isPairFree()){
			other.move(getPair().getPosition());
			return false;
		}
		return true;
	}

}
