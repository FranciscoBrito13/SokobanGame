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
	
	
	public Teleporte getPair(){
		GameEngine gEngine = GameEngine.getInstance();
		List<Teleporte> teleportes = gEngine.getTeleportes();
		for(Teleporte t : teleportes){
			if(!(t.getPosition().equals(position))){
				return t;
			}
		}
		return null;
	}	
	
    public boolean isPositionValid(Point2D p) {
        List<GameElement> elementsAtNewPosition = GameEngine.getInstance().getGameElement(p);
        return elementsAtNewPosition == null || elementsAtNewPosition.isEmpty() ||
                elementsAtNewPosition.stream().anyMatch(ge -> ge instanceof Alvo || ge instanceof Teleporte);
    }
	

	//Verifica se a posição do par tem algum elemento em cima, se tiver não d
	
	public boolean interact(GameElement other) {
		List<GameElement> ge = GameEngine.getInstance().getGameElement(getPair().getPosition());
		if(ge.size() > 1){
			return false;
		}
		other.setPosition(getPair().getPosition());
		return true;
	} 


}
