package pt.iscte.poo.sokoban;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Level {

	/* [LEVEL VARIABLES] */
	private int level = 0;
	public static final int maxLevel = 7;
	/* [TILEMAP/BOBCAT] */
	private List<GameElement> tileMap;
	private Bobcat bobcat;
	/* [GAME ELEMENT LISTS] */
	List<Target> alvos;
	List<Teleport> teleportes;
	List<Hole> buracos;
	List<Box> caixotes;
	List<Placeable> placableObjects;
	/* [GUI] */
	private ImageMatrixGUI gui;


	/* [LEVEL CONSTRUCTOR] */
	public Level(){
		tileMap = new ArrayList<>();

		gui = ImageMatrixGUI.getInstance();
		gui.setSize(GameEngine.GRID_HEIGHT, GameEngine.GRID_WIDTH);
	}


	/* [FUNCTION THAT CREATES THE GAME BY READING THE LEVEL FILES] */
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
						tileMap.add(new Wall(ponto));
						tileList.add(new Wall(ponto));
						break;
					case 'E':
						tileList.add(new Floor(ponto));
						bobcat = Bobcat.getInstance();
						bobcat.setInitialPosition(ponto);
						bobcat.resetEmpilhadora();
						tileMap.add(bobcat);
						tileList.add(bobcat);
						break;
					case 'C':
						Box caixote = new Box(ponto);
						tileMap.add(caixote);
						tileList.add(caixote);
						tileList.add(new Floor(ponto));

						break;
					case '=':
						tileList.add(new Void(ponto));
						break;
					case 'B':
						Battery b = new Battery(ponto);
						tileMap.add(b);
						tileList.add(b);
						tileList.add(new Floor(ponto));
						break;
					case 'X':
						Target a = new Target(ponto);
						tileMap.add(a);
						tileList.add(a);
						break;
					case 'T':
						Teleport t = new Teleport(ponto);
						tileMap.add(t);
						tileList.add(t);
						break;
					case 'O':
						Hole buraco = new Hole(ponto);
						tileMap.add(buraco);
						tileList.add(buraco);
						break;
					case 'P':
						Pallet p = new Pallet(ponto);
						tileMap.add(p);
						tileList.add(p);
						tileList.add(new Floor(ponto));
						break;
					case 'M':
						Hammer m = new Hammer(ponto);
						tileMap.add(m);
						tileList.add(m);
						tileList.add(new Floor(ponto));
						break;
					case '%':
						FracturedWall paredeRachada = new FracturedWall(ponto);
						tileMap.add(paredeRachada);
						tileList.add(paredeRachada);
						tileList.add(new Floor(ponto));
						break;
					case '&':
						Explosive e = new Explosive(ponto);
						tileMap.add(e);
						tileList.add(e);
						tileList.add(new Floor(ponto));
						break;
					case 'U':
						Boulder pg = new Boulder(ponto);
						tileMap.add(pg);
						tileList.add(pg);
						tileList.add(new Floor(ponto));
						break;
					case 'L':
						MudBucket mb = new MudBucket(ponto);
						tileMap.add(mb);
						tileList.add(mb);
						tileList.add(new Floor(ponto));
					default:
						tileList.add(new Floor(ponto));
						break;
					}
				}
				y++;

			}
			s.close();
			loadAlvos();
			loadTeleportes();
			loadBuracos();
			//loadCaixotes();
			loadPlacable();
			gui.addImages(tileList);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/* [FUNCTION THAT LOADS THE BOXES] */
//	private List<Box> loadCaixotes() {
//		caixotes = new ArrayList<>();
//		for (GameElement ge : tileMap) {
//			if (ge instanceof Box) {
//				caixotes.add((Box) ge);
//			}
//		}
//		return caixotes;
//	}

	/* [FUNCTION THAT LOADS THE TARGETS] */
	private List<Target> loadAlvos() {
		alvos = new ArrayList<>();
		for (GameElement ge : tileMap) {
			if (ge instanceof Target) {
				alvos.add((Target) ge);
			}
		}
		return alvos;
	}

	/* [FUNCTION THAT LOADS THE TELEPORTERS] */
	private List<Teleport> loadTeleportes() {
		teleportes = new ArrayList<>();
		for (GameElement ge : tileMap) {
			if (ge instanceof Teleport) {
				teleportes.add((Teleport) ge);
			}
		}
		return teleportes;
	}

	/* [FUNCTION THAT LOADS THE HOLES] */
	private List<Hole> loadBuracos(){
		buracos = new ArrayList<>();
		for (GameElement ge : tileMap) {
			if (ge instanceof Hole) {
				buracos.add((Hole) ge);
			}
		}
		return buracos;
	}

	private List<Placeable> loadPlacable(){
		placableObjects = new ArrayList<>();
		for (GameElement ge : tileMap) {
			if (ge instanceof Placeable) {
				placableObjects.add((Placeable) ge);
			}
		}
		return placableObjects;
	}

	/* [FUNCTION THAT UPDATES ALL GAME ELEMENT LISTS] */
	public void updateLists(){
		alvos = loadAlvos();
		buracos = loadBuracos();
		teleportes = loadTeleportes();
		//caixotes = loadCaixotes();
		placableObjects = loadPlacable();
	}

	/* [LEVEL GETTER] */
	public int getLevel() {
		return level;
	}

	/* [FUNCTION THAT INCREASES THE LEVEL] */
	public void increaseLevel() {
		if(level < maxLevel){
			level++;
			restartLevel();
			gui.update();
		}
	}

	/* [FUNCTION THAT RESETS THE LEVEL] */
	public void restartLevel() {
		tileMap.clear();
		gui.clearImages();
		createGame();
		bobcat.resetEmpilhadora();
	}

	/* [FUNCTION THAT RESETS THE GAME] */
	public void resetGame() {
		level = 1;
		restartLevel();
		gui.update();	
	}

	/* [TILEMAP GETTER] */
	public List<GameElement> getTileMap() {
		return tileMap;
	}

	/* [GUI GETTER] */
	public ImageMatrixGUI getGui() {
		return gui;
	}

	/* [LIST OF TARGETS GETTER] */
	public List<Target> getAlvos(){
		return alvos;
	}

	/* [BOBCAT GETTER] */
	public Bobcat getBobcat() {
		return bobcat;
	}

	/* [LIST OF TELEPORTER GETTER] */
	public List<Teleport> getTeleportes(){
		return teleportes;
	}

	/* [LIST OF BOXES GETTER] */
//	public List<Box> getCaixotes(){
//		return caixotes;
//	}

	public List<Placeable> getPlacable(){
		return placableObjects;
	}

}
