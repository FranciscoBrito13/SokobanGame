package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.*;

import pt.iscte.poo.gui.ImageTile;


public abstract class GameElement implements ImageTile{

	/* [GAME ELEMENT BASE VARIABLES] */
	private int priority = 0;
	private Point2D position;
	private String name;
	private int layer;
	
	/* [GAME ELEMENT CONSTRUCTOR] */
	public GameElement(Point2D position) {
		this.position = position;
	}
	
	/* [NAME GETTER] */
	@Override
	public String getName() {
		return name;
	}

	/* [POSITION GETTER] */
	@Override
	public Point2D getPosition() {
		return position;
	}

	/* [LAYER GETTER] */
	@Override
	public int getLayer() {
		return layer;
	}
	
	/* [PRIORITY GETTER] */
    public int getPriority() {
        return priority;
    }
	
	/* [NAME SETTER] */
	public void setName(String name){
		this.name = name;
	}
	
	/* [POSITION SETTER] */
	public void setPosition(Point2D position) {
		this.position=position;
	}

	/* [LAYER SETTER] */
	public void setLayer(int layer){
		this.layer = layer;
	}

	/* [PRIORITY SETTER] */
    public void setPriority(int priority) {
        this.priority = priority;
    }
    
    /* [BASE GAME ELEMENT INTERACT FUNCTION] */
	public boolean interact(GameElement other) {return false;}

	/* [FUNCTION THAT TELEPORTS THE GAME ELEMENT TO A GIVEN POSITION] */
	public void teleport(Point2D position) {
		setPosition(position);
		
	}
	

}