package pt.iscte.poo.sokoban;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import pt.iscte.poo.utils.Point2D;

public class Bobcat extends GameElement implements Movable {

	/* [BOTCAT INSTANCE] */
    private static Bobcat INSTANCE; 
    /* [BOBCAT IMAGE NAME] */
    private String imageName = "Empilhadora_U";
    /* [BOBCAT INITIAL POSITION] */
    private Point2D initialPosition;
    /* [BOBCAT MOVE COUNTER] */
    private int moves;
    /* [BOBCAT BATTERY] */
    private int maxBattery = 100;
    private int curBattery;
    /*[BOBCAT INVENTORY]*/
    private List<Item> inventory = new ArrayList<>();
    
    /* [BOBCAT CONSTRUCTOR] */
    private Bobcat(Point2D position) {
        super(position);
        initialPosition = position;
        curBattery = maxBattery;
    }

    /* [BOBCAT INSTANCE GETTER] */
    public static Bobcat getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Bobcat(new Point2D(0, 0));
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
    	inventory.clear();
    	moves = 0;
    }
    
    /* [INITIAL POSITION SETTER] */
    public void setInitialPosition(Point2D position){
    	initialPosition = position;
    }

    /* [BOBCAT'S BATTERY GETTER] */
    public int getBattery() {
        return curBattery;
    }
    
    /* [BOBCAT'S BATTERY SETTER] */
    private void setBattery(int batteryAmount) {
        curBattery = batteryAmount;
    }

    /* [FUNCTION THAT DECREASES BOBCAT'S BATTERY] */
    public void decBattery() {
        curBattery--;
    }

    /* [FUNCTION THAT SUMS AN AMMOUNT TO THE CURRENT BATTERY AND CAPS IT AT 100] */
    public void consumeBattery(Battery b) {
    	setBattery(Math.min(getBattery() + b.getBattery(), maxBattery + 1));
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
	    decBattery();
        moves++;
	}
	
	/* [FUNCTION THAT ADDS AN ITEM TO THE INVENTORY] */
	public void addItem(Item item) {
		inventory.add(item);
	}
	
	/*[REMOVES ALL INSTANCES OF THE MUDBUCKET IN THE INVENTORY (Limit should always be 1)]*/
	public void removeMudBucket(){
		inventory.removeIf(ge -> ge instanceof MudBucket);
	}
	
	/* [FUNCTION THT CHECKS IF A GIVEN ITEM IN A PREDICATE FORM IS IN THE INVENTORY] */
	public boolean checkPredicateInventory(Predicate<Item> p){
		for(Item i : inventory){
			if(p.test(i)){
				return true;
			}
		}
		return false;
	}
}
