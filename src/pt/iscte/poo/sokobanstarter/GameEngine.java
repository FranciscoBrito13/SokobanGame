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

	/* [GRID DIMENTIONS] */
	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;

	/*[GAMEENGINE INSTANCE]*/
	private static GameEngine INSTANCE;

	/*[LEVEL/BOBCAT/GUI]*/
	private Level level = new Level();
	private ImageMatrixGUI gui = level.getGui();  	
	private Bobcat bobcat;

	/*[USER]*/
	private User user; // Add this field

	/*[CONSTANTS]*/
	private static final double POINTS_MULTIPLIER = 15.0;

	/*[SINGLETON CONSTRUCTOR]*/
	private GameEngine() {
		gui.registerObserver(this);           
		gui.go();
	}

	/*[RETURN REFERENCE TO SINGLTON IF IT EXISTS]*/
	public static GameEngine getInstance() {
		if (INSTANCE==null)
			return INSTANCE = new GameEngine();
		return INSTANCE;
	}

	/*[FUNCION TO START THE GAME]*/
	public void start(){
		level.createGame(); 
		bobcat = level.getBobcat();
		gui.update();
		gui.setStatusMessage("NickName: " + "'" + user.getUsername()+ "'" +  " LEVEL:" + level.getLevel() + " BATTERY:" + bobcat.getBateria());

	}

	/*[FUNCION THAT HANDLES GAME UPDATE THROUGH KEY INPUT AND GAME STATE VALIDATION]*/
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


		gui.setStatusMessage("NickName: '" + user.getUsername() + "'" +  " LEVEL:" + level.getLevel() + " BATTERY:" + bobcat.getBateria());
		gui.update(); 
	}

	/*[CHECK IF THE GIVEN INPUT IS KEYS]*/
	private boolean isArrows(int key) {
		return key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_LEFT;
	}

	/*[HANDLES THE INTERACTION BETWEEN THE BOBCAT AND THE GAME ELEMENTS]*/
	private void handleArrowMovement(int key) {
		Direction d = Direction.directionFor(key);
		Point2D nextP = bobcat.getPosition().plus(d.asVector());
		List<GameElement> g = getGameElement(nextP);
		g.sort((g1, g2) -> g2.getPriority() - g1.getPriority());
		boolean canMove = g.isEmpty() || g.stream().allMatch(ge -> ge.interact(bobcat));

		if (canMove) bobcat.move(nextP);
		bobcat.changeDirection(key);
	}

	/*[RESETS THE USER POINTS FOR ANOTHER RUN AND RESETS THE LEVEL]*/
	public void handleGameOver() {
		user.resetPoints();
		level.resetGame();
	}

	/*[CHECKS IF THE GAME IS OVER]*/
	private boolean isGameOver(){
		if(bobcat.getBateria() <= 0){
			gui.setMessage("The bobcat ran out of battery, went back to the start to recharge :(");
			return true;
		}
		if(level.getCaixotes().size() < level.getAlvos().size()){
			gui.setMessage("You destroyed too many boxes, the bobcat had to go back to the beginning to get more");
			return true;
		};
		return false;
	}

	/*[HANDLES THE LEVEL COMPLETION AND GAME COMPLETION]*/
	private void handleLevelCompletion() {
		int currentLevel = level.getLevel();
		double points = POINTS_MULTIPLIER * Math.log(currentLevel + 1) * bobcat.getBateria();
		user.setPointsForLevel(currentLevel, (int) points);

		if (currentLevel == Level.maxLevel) handleMaxLevelCompletion();
		else level.increaseLevel();

	}

	/*[HANDLES THE GAME COMPLETION]*/
	private void handleMaxLevelCompletion() {
		if (user.getPreviousTopScore() < user.getTotalPoints()) {
			user.writeScore();
			gui.setMessage("You finished the game with a new record!!");
			LeaderBoard.updateLeaderBoard(user.getUsername(), user.getTotalPoints());
		} else {
			gui.setMessage("The game is over!!");
		}
		user.resetPoints();
		level.resetGame();
	}

	/*[HANDLES DIFFERENT KEYS FROM MOVEMENT]*/
	private void handleOtherKeys(int key) {
		if(key == KeyEvent.VK_R){
			level.restartLevel();
		} else if(key == KeyEvent.VK_N){
			level.increaseLevel();
		}
	}

	/*[CHECKS IF EACH TARGET HAS A BOX IN THE SAME POSITION, IF ALL MATCH THAT STATMENT IT RETRUNS TRUE]*/
	private boolean boxInPlace() {
		return level.getAlvos().stream()
				.allMatch(alvo -> level.getTileMap().stream()
						.filter(ge -> ge instanceof Box)
						.map(ge -> (Box) ge)
						.anyMatch(caixote -> caixote.getPosition().equals(alvo.getPosition())));
	}



	/*[RETURNS ALL GAME ELEMENTS IN A GIVEN POSITION]*/
	public List<GameElement> getGameElement(Point2D p) {
		List<GameElement> elementsAtPoint = new ArrayList<>(level.getTileMap());
		elementsAtPoint.removeIf(gameElement -> !gameElement.getPosition().equals(p));
		return elementsAtPoint;
	}

	/*[RETURNS THE TELEPORT PAIR]*/
	public List<Teleport> getTeleportes(){
		return level.getTeleportes();
	}


	/*[REMOVES A GIVEN GAME ELEMENT FROM THE TILEMAP]*/
	public void removeElement(GameElement p){
		level.getTileMap().remove(p);
		level.updateLists();
	}

	/*[SETS THE USER FOR THE GAME]*/
	public void setCurrentUser(User user) {
		this.user = user;
	}


}
