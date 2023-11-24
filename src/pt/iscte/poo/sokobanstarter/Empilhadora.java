package pt.iscte.poo.sokobanstarter;

import java.awt.event.KeyEvent;
import pt.iscte.poo.utils.Point2D;

public class Empilhadora extends GameElement implements Movable {

    private static Empilhadora INSTANCE; //Instancia da empilhadora
    private String imageName = "Empilhadora_U";
    private Point2D initialPosition;
    private int moves;
    private int maxBattery = 100;
    private int curBattery;
    

    private Empilhadora(Point2D position) {
        super(position);
        initialPosition = position;
        curBattery = maxBattery;
    }

    public static Empilhadora getInstance(Point2D initialPosition) {
        if (INSTANCE == null)
            INSTANCE = new Empilhadora(initialPosition);
        return INSTANCE;
    }

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
    
    public void resetEmpilhadora(){
    	curBattery = maxBattery;
    	setPosition(initialPosition);
    	imageName = "Empilhadora_U";
    	moves = 0;
    }
    
    public void setInitialPosition(Point2D position){
    	initialPosition = position;
    }

    public int getBateria() {
        return curBattery;
    }

    public void decBateria() {
        curBattery--;
    }

    public void addBattery(int batteryAmount) {
        setBateria(Math.min(getBateria() + batteryAmount, maxBattery + 1));
    }

    private void setBateria(int batteryAmount) {
        curBattery = batteryAmount;
    }

    public void consumeBattery(Bateria b) {
        addBattery(b.getBattery());
    }
    
    public int getMaxBattery(){
    	return maxBattery;
    }
    

	public int getMoves() {
		return moves;
	}
	
    @Override
    public String getName() {
        return imageName;
    }

    @Override
    public int getLayer() {
        return 2;
    }

	@Override
	public void move(Point2D p) {
	    //Point2D oldPosition = getPosition();
	    setPosition(p);
	    //GameEngine.getInstance().relocateObject(oldPosition, p, this);
	    decBateria();
        moves++;
	}
}
