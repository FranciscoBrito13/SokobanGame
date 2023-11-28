
//ADICIONAR UM FICHEIRO COM HIGHSCORE

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
	
	public void resetGameInstance(){
		INSTANCE = null;
		start();
	}

	public void start(){
		level.createGame(); 
		bobcat = level.getBobcat();
		gui.update();
		gui.setStatusMessage("NickName: " + user.getUsername() +  " Nivel:" + level.getLevel() + " Bateria:" + bobcat.getBateria());

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


		gui.setStatusMessage("NickName: '" + user.getUsername() + "'" +  " Nivel:" + level.getLevel() + " Bateria:" + bobcat.getBateria());
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
	public void handleGameOver() {
		user.resetPoints();
		level.resetGame();
	}
	
	//Verifica se a Empilhadora ficou sem bateria ou o jogo tem menos caixotes que alvos
	private boolean isGameOver(){
		if(bobcat.getBateria() <= 0){
			gui.setMessage("A empilhadora ficou sem bateria, voltou ao início para recarregar :(");
			return true;
		}
		if(level.getCaixotes().size() < level.getAlvos().size()){
			gui.setMessage("Destruiste caixotes a mais, a emplhadora teve que voltar ao início para ir buscar mais");
			return true;
		};
		
		return false;
	}

	//trata da finalização de cada nível
	private void handleLevelCompletion() {
		user.setPointsForLevel(level.getLevel(), (int) (15 * Math.log(level.getLevel() + 1) * bobcat.getBateria()));
		if(level.getLevel() == 6){
			if(user.getPreviousTopScore() < user.getTotalPoints()){
				LeaderBoard.updateLeaderBoard(user.getUsername(),user.getTotalPoints());
				user.writeScore();
				gui.setMessage("Acabaste o jogo com um novo recorde!!");
				user.resetPoints();
				level.resetGame();
				return;
			}
			LeaderBoard.updateLeaderBoard(user.getUsername(),user.getTotalPoints());
			gui.setMessage("Acabaste o jogo!!");
			user.resetPoints();
			level.resetGame();
			return;
		} 
		level.increaseLevel();
	}

	//Verifica, caso nao seja dada um key de movimento, que operação deve ser executada
	private void handleOtherKeys(int key) {
		if(key == KeyEvent.VK_R){
			level.restartLevel();
		} else if(key == KeyEvent.VK_N){
			level.increaseLevel();
		}
	}

	//Verifica se as caixas estão nos alvos usando uma expressão lambda para transformar os alvos numa stream
	//verifica então se todos os elementos têm um caixote na mesma posição
	private boolean boxInPlace() {
	    return level.getAlvos().stream()
	            .allMatch(alvo -> level.getTileMap().stream()
	                    .filter(ge -> ge instanceof Caixote)
	                    .map(ge -> (Caixote) ge)
	                    .anyMatch(caixote -> caixote.getPosition().equals(alvo.getPosition())));
	}
	


	//Devolve todos os GameElements numa dada posição
	public List<GameElement> getGameElement(Point2D p) {
	    List<GameElement> elementsAtPoint = new ArrayList<>(level.getTileMap());
	    elementsAtPoint.removeIf(gameElement -> !gameElement.getPosition().equals(p));
	    return elementsAtPoint;
	}

	//Devolve o par de teleportes
	public List<Teleporte> getTeleportes(){
		return level.getTeleportes();
	}


	//Remove um GameElement da List de elementos
	public void removeElement(GameElement p){
		level.getTileMap().remove(p);
		level.updateLists();
	}

	//setter do user
	public void setCurrentUser(Utilizador user) {
		this.user = user;
	}

	public ImageMatrixGUI getGui(){
		return gui;
	}

}
