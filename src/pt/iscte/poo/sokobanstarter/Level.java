package pt.iscte.poo.sokobanstarter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Level {

	private int level = 0;
	private HashMap<Point2D, GameElement> tileMap;
	private Empilhadora bobcat;
	List<Alvo> alvos;
	List<Teleporte> teleportes;
	private ImageMatrixGUI gui;
	
	public Level(){
		tileMap = new HashMap<>();
		
		gui = ImageMatrixGUI.getInstance();
		gui.setSize(GameEngine.GRID_HEIGHT, GameEngine.GRID_WIDTH);
	}
	
	public void createGame() {
		
	    try {
	    	List<ImageTile> tileList = new ArrayList<>();
	        Scanner s = new Scanner(new File("levels/level" + level + ".txt"));
	        int y = 0;

	        while (s.hasNext()) {
	            String line = s.nextLine();
	            for (int x = 0; x < line.length(); x++) {
	                Point2D ponto = new Point2D(x, y);

	                char ch = line.charAt(x);

	                switch (ch) {
	                    case '#':
	                    	tileMap.put(ponto, new Parede(ponto));
	                        tileList.add(new Parede(ponto));
	                        break;
	                    case 'E':
	                    	tileList.add(new Chao(ponto));
	                        bobcat = Empilhadora.getInstance(ponto);
	                        bobcat.setInitialPosition(ponto);
	                    	tileMap.put(ponto, bobcat);
	                        tileList.add(bobcat);
	                        break;
	                    case 'C':
	                    	Caixote caixote = new Caixote(ponto);
	                        tileMap.put(ponto, caixote);
	                        tileList.add(caixote);
	                        tileList.add(new Chao(ponto));
	                        
	                        break;
	                    case '=':
	                        tileList.add(new Vazio(ponto));
	                        break;
	                    case 'B':
	                    	Bateria b = new Bateria(ponto);
	                    	tileMap.put(ponto, b);
	                        tileList.add(b);
	                        break;
	                    case 'X':
	                    	Alvo a = new Alvo(ponto);
	                    	tileMap.put(ponto, a);
	                        tileList.add(a);
	                        break;
	                    case 'T':
	                    	Teleporte t = new Teleporte(ponto);
	                    	tileMap.put(ponto, t);
	                    	tileList.add(t);
	                    default:
	                        tileList.add(new Chao(ponto));
	                        break;
	                }
	            }
	            y++;
	            
	        }
	        alvos = loadAlvos();
	        teleportes = loadTeleportes();
	        gui.addImages(tileList);
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}

	private List<Alvo> loadAlvos() {
	    alvos = new ArrayList<>();
	    for (GameElement ge : tileMap.values()) {
	        if (ge instanceof Alvo) {
	            alvos.add((Alvo) ge);
	        }
	    }
	    return alvos;
	}
	
	private List<Teleporte> loadTeleportes() {
	    teleportes = new ArrayList<>();
	    for (GameElement ge : tileMap.values()) {
	        if (ge instanceof Teleporte) {
	            teleportes.add((Teleporte) ge);
	        }
	    }
	    return teleportes;
	}
	

	public int getLevel() {
		return level;
	}

	public void increaseLevel() {
		level++;
	    tileMap.clear();
	    gui.clearImages();
	    createGame(); 
	    bobcat.resetEmpilhadora();
	    gui.update();
	}
	
	public void restartLevel() {
	    tileMap.clear();
	    gui.clearImages();
	    createGame();
	    bobcat.resetEmpilhadora();
	}


	public HashMap<Point2D, GameElement> getTileMap() {
		return tileMap;
	}

	public ImageMatrixGUI getGui() {
		return gui;
	}


	public List<Alvo> getAlvos(){
		return alvos;
	}
	public Empilhadora getBobcat() {
		return bobcat;
	}
	
	public List<Teleporte> getTeleportes(){
		return teleportes;
	}
	
	
}
