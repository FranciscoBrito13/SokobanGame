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
	

	//Verifica se a posição do par tem algum elemento em cima, se tiver não d
	
	public boolean interact(GameElement other) {
		List<GameElement> ge = GameEngine.getInstance().getGameElement(getPair().getPosition());
		if(ge.size() > 1){
			return true;
		}
		other.setPosition(getPair().getPosition());
		return false;
	} 


}
