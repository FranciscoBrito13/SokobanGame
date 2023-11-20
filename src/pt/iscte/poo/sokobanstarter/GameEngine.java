package pt.iscte.poo.sokobanstarter;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.stream.Collectors;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class GameEngine implements Observer {

	// Dimensoes da grelha de jogo
	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;

	private static GameEngine INSTANCE; // Referencia para o unico objeto GameEngine (singleton)
	
    private Level level = new Level();
	private ImageMatrixGUI gui = level.getGui();  		// Referencia para ImageMatrixGUI (janela de interface com o utilizador) 
	private Empilhadora bobcat;
	
	private GameEngine() {
		gui.registerObserver(this);            // 3. registar o objeto ativo GameEngine como observador da GUI
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
		gui.setStatusMessage("Nivel:" + level.getLevel() + " Bateria:" + bobcat.getBateria());

	}
	
	private boolean isArrows(int key){
		if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_LEFT ) {
			return true;
		}
		return false;
	}
	
	
	@Override
	public void update(Observed source) {
	    int key = gui.keyPressed();

	    if(isArrows(key)) {
		    Direction d = Direction.directionFor(key);
		    
		    // Verifica se o bobcat não tem uma parede naquela posição
		    Point2D nextP = bobcat.getPosition().plus(d.asVector());
		    GameElement g = getGameElement(nextP);
		    //Se nao
		    boolean canMove = g == null ? true :  g.interact(bobcat);
		    
		    //interactWithObjects(key);
		    if(canMove)bobcat.move(nextP);
		    bobcat.changeDirection(key);
	    }  
	    
	    if (isGameOver()) {
	        displayGameOverPanel();
	        level.restartLevel();
	    }
	        
	        
	    if(boxInPlace()){
	    	level.increaseLevel();
	    }
	        

	    // O jogo é resetado ao carregar na tecla R
	    else if (key == KeyEvent.VK_R) {
	    	level.restartLevel();
	        
	        
	    } else if(key == KeyEvent.VK_N){
	    	level.increaseLevel();
	    }
	    gui.setStatusMessage("Level: " + level.getLevel() + " Energy:" + bobcat.getBateria() + " Moves: " + bobcat.getMoves());
	    gui.update(); // Update the GUI after the movement
	}

	//Verifica se as caixas estão nos alvos
    private boolean boxInPlace() { 

        List<Caixote> caixotes = level.getTileMap().values()
        	    .stream()
        	    .filter(ge -> ge instanceof Caixote)
        	    .map(ge -> (Caixote) ge)
        	    .collect(Collectors.toList());

        int qntAlvosAtivos = 0;

        for (Alvo a : level.getAlvos()) {
            boolean isTargetActive = false;
            for (Caixote c : caixotes) {
                if (c.getPosition().equals(a.getPosition())) {
                    isTargetActive = true;
                    break;
                }
            }
            if (isTargetActive) {
                qntAlvosAtivos++;
            }
        }
        
        return level.getAlvos().size() == qntAlvosAtivos;
    }

	private void displayGameOverPanel() {
		System.out.println("Ainda nao estou implementado");
	}
	

	public boolean isGameOver(){
		return bobcat.getBateria() <= 0;
	}
	
	
	public void relocateObject(Point2D old, Point2D newP, GameElement g) {
		level.getTileMap().remove(old); // Clear the old position
		level.getTileMap().put(newP, g); // Update the tileMap with the existing Caixote in the new position
	}
	

	public GameElement getGameElement(Point2D p) {
		return level.getTileMap().get(p);
	}
	
	public List<Teleporte> getTeleportes(){
		return level.getTeleportes();
	}

	public void removeElement(Point2D p) {
		level.getTileMap().remove(p);
	}

}