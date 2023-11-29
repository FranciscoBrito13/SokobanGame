package pt.iscte.poo.sokobanstarter;

import java.awt.event.KeyEvent;
import pt.iscte.poo.utils.Point2D;

public class Empilhadora extends GameElement implements Movable {

	/* [BOTCAT INSTANCE] */
    private static Empilhadora INSTANCE; 
    /* [BOBCAT IMAGE NAME] */
    private String imageName = "Empilhadora_U";
    /* [BOBCAT INITIAL POSITION] */
    private Point2D initialPosition;
    /* [BOBCAT MOVE COUNTER] */
    private int moves;
    /* [BOBCAT BATTERY] */
    private int maxBattery = 100;
    private int curBattery;
    /* [BOBCAT VARIABLE THAT VERIFY IF IT HAS A HAMMER] */
    private boolean hasHammer = false;
    
    /* [BOBCAT CONSTRUCTOR] */
    private Empilhadora(Point2D position) {
        super(position);
        initialPosition = position;
        curBattery = maxBattery;
    }

    /* [BOBCAT INSTANCE GETTER] */
    public static Empilhadora getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Empilhadora(new Point2D(0, 0));
        return INSTANCE;
    }

    /* [FUNCTION THAT CHANGES THE IMAGE DIRECTION BASED ON THE KEY PRESSED] */
    public void changeDirection(int lastKeyPressed) {
        switch (lastKeyPressed) {
            case KeyEvent.VK_UP:
                imageName = "Empilhadora_U";
                break;
            case KeyEvent.VK_DOWN:
                imageName = "Empilhadora_D";
                break;
            case KeyEvent.VK_LEFT:
                imageName = "Empilhadora_L";
                break;
            case KeyEvent.VK_RIGHT:
                imageName = "Empilhadora_R";
                break;
        }
    }
    
    /* [FUNCTION THAT RESETS THE BOBCAT] */
    public void resetEmpilhadora(){
    	curBattery = maxBattery;
    	setPosition(initialPosition);
    	imageName = "Empilhadora_U";
    	hasHammer = false;
    	moves = 0;
    }
    
    /* [INITIAL POSITION SETTER] */
    public void setInitialPosition(Point2D position){
    	initialPosition = position;
    }

    /* [BOBCAT'S BATTERY GETTER] */
    public int getBateria() {
        return curBattery;
    }
    
    /* [BOBCAT'S BATTERY SETTER] */
    private void setBateria(int batteryAmount) {
        curBattery = batteryAmount;
    }

    /* [FUNCTION THAT DECREASES BOBCAT'S BATTERY] */
    public void decBateria() {
        curBattery--;
    }

    /* [FUNCTION THAT SUMS AN AMMOUNT TO THE CURRENT BATTERY AND CAPS IT AT 100] */
    public void consumeBattery(Bateria b) {
    	setBateria(Math.min(getBateria() + b.getBattery(), maxBattery + 1));
    }
    
    /* [BOBCAT'S MAX BATTERY GETTER] */
    public int getMaxBattery(){
    	return maxBattery;
    }
    
    /* [MOVES COUNTER GETTER] */
	public int getMoves() {
		return moves;
	}
	
	/* [IMAGE NAME GETTER] */
    @Override
    public String getName() {
        return imageName;
    }

    /* [LAYER GETTER] */
    @Override
    public int getLayer() {
        return 2;
    }

    /* [MOVE FUNCTION] */
	@Override
	public void move(Point2D p) {
	    setPosition(p);
	    decBateria();
        moves++;
	}

	/* [FUNCTION THAT SETS TRUE THE HASHAMMER VARIABLE] */
	public void pickHammer() {
		hasHammer = true;
	}

	/* [HASHAMMER GETTER] */
	public boolean hasHammer() {
		return hasHammer;
	}
}
