package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.utils.Point2D;

public class Buraco extends GameElement{


	public Buraco(Point2D position) {
		super(position);
		setPriority(1);
		setName("Buraco");
	}


	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public boolean interact(GameElement other){
		if(other instanceof Empilhadora){
			GameEngine.getInstance().handleGameOver();
			ImageMatrixGUI.getInstance().setMessage("A empilhadora caiu no buraco e voltou para trás para ir à oficina");
			return false;
		}
		if(other instanceof Caixote){
			GameEngine.getInstance().removeElement(other);
			ImageMatrixGUI.getInstance().removeImage(other);
			return true;
		}
		if(other instanceof Palete){
			setName("Teleporte"); // MUDAR PARA OUTRA COISA
			ImageMatrixGUI.getInstance().removeImage(other);
			GameEngine.getInstance().removeElement(other);
			GameEngine.getInstance().removeElement(this);
			
			
		}
		return true;
	}

}
