package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.*;

import pt.iscte.poo.gui.ImageTile;


public abstract class GameElement implements ImageTile{

	private int priority = 0;
	private Point2D position;
	private String name;
	private int layer;
	
	public GameElement(Point2D position) {
		this.position = position;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public int getLayer() {
		return layer;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setPosition(Point2D position) {
		this.position=position;
	}

	
	public void setLayer(int x){
		this.layer = x;
	}
    public void setPriority(int priority) {
        this.priority = priority;
    }
    
    public int getPriority() {
        return priority;
    }
	
	public boolean interact(GameElement other) {return false;}

	public void move(Point2D position2){};
	

}