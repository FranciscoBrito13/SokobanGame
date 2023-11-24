package pt.iscte.poo.sokobanstarter;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class GameEngine implements Observer {

	// Dimensoes da grelha de jogo
	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;


	private static GameEngine INSTANCE;
	private Nivel level = new Nivel();
	private ImageMatrixGUI gui = level.getGui();  	
	private Empilhadora bobcat;

	//USER
	private Utilizador user; // Add this field

	private GameEngine() {
		gui.registerObserver(this);           
		gui.go();
	}

	public static GameEngine getInstance() {
		if (INSTANCE==null)
			return INSTANCE = new GameEngine();
		return INSTANCE;
	}

	public void start(){
		level.createGame(); 
		bobcat = level.getBobcat();
		gui.update();
		gui.setStatusMessage("Nivel:" + level.getLevel() + " Bateria:" + bobcat.getBateria() + " NickName:" + user.getUsername());

	}
	
	@Override
	public void update(Observed source) {
		int key = gui.keyPressed();

		if (isArrows(key)) {
			handleArrowMovement(key);
			
			if (isGameOver()) handleGameOver();
			
			if (boxInPlace()){
				handleLevelCompletion();
			}
			
		} else handleOtherKeys(key);


		gui.setStatusMessage("Nivel:" + level.getLevel() + " Bateria:" + bobcat.getBateria() + " NickName:" + user.getUsername());
		gui.update(); 
	}
	
	//Verifica se o input dado são setas
	private boolean isArrows(int key) {
		return key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_LEFT;
	}
	
	//Trata do movimento segundo as setas
	private void handleArrowMovement(int key) {
		Direction d = Direction.directionFor(key);
		Point2D nextP = bobcat.getPosition().plus(d.asVector());
		List<GameElement> g = getGameElement(nextP);
		g.sort((g1, g2) -> g2.getPriority() - g1.getPriority());
		boolean canMove = g.isEmpty() || g.stream().allMatch(ge -> ge.interact(bobcat));

		if (canMove) bobcat.move(nextP);
		bobcat.changeDirection(key);
	}
	
	//Mostra o display de GameOver e reinicia o nivel
	private void handleGameOver() {
		displayGameOverPanel();
		level.restartLevel();
	}

	//Atualiza o scoreMap caso seja necessário
	private void handleLevelCompletion() {
		user.setPointsForLevel(level.getLevel(), (int) ((bobcat.getMaxBattery() - bobcat.getMoves()) * (level.getLevel()* 1.10)));
		if(level.getLevel() == 6){
			user.writeScore();
		}
		level.increaseLevel();
	}
	
	//Verifica, caso nao seja dada um key de movimento, que operação deve ser executada
	private void handleOtherKeys(int key) {
		if(key == KeyEvent.VK_R)
			level.restartLevel();
	}

	//Verifica se as caixas estão nos alvos
	private boolean boxInPlace() {
	    return level.getAlvos().stream().allMatch(alvo ->
	            level.getTileMap().stream()
	                    .filter(ge -> ge instanceof Caixote)
	                    .map(ge -> (Caixote) ge)
	                    .anyMatch(caixote -> caixote.getPosition().equals(alvo.getPosition()))
	    );
	}


	//Mostra um Display de GameOver
	private void displayGameOverPanel() {
		System.out.println("Ainda nao estou implementado");
	}


	//Verifica se a Empilhadora já não se pode mexer
	public boolean isGameOver(){
		return bobcat.getBateria() <= 0;
	}

	//Devolve todos os GameElements numa dada posição
	public List<GameElement> getGameElement(Point2D p) {
		List<GameElement> elementsAtPoint = new ArrayList<>();
		for(GameElement gameElement : level.getTileMap()){
			if(gameElement.getPosition().equals(p))
				elementsAtPoint.add(gameElement);
		}
		return elementsAtPoint;
	}
	
	//Devolve o par de teleportes
	public List<Teleporte> getTeleportes(){
		return level.getTeleportes();
	}
	
	
	//Remove um GameElement da List de elementos
	public void removeElement(GameElement p){
		level.getTileMap().remove(p);
	}
	//setter do user
    public void setCurrentUser(Utilizador user) {
        this.user = user;
    }
	

}
