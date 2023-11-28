package pt.iscte.poo.sokobanstarter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Nivel {

	private int level = 1;
	private int maxLevel = 6;
	private List<GameElement> tileMap;
	private Empilhadora bobcat;
	List<Alvo> alvos;
	List<Teleporte> teleportes;
	List<Buraco> buracos;
	List<Caixote> caixotes;
	private ImageMatrixGUI gui;

	public Nivel(){
		tileMap = new ArrayList<>();

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
						tileMap.add(new Parede(ponto));
						tileList.add(new Parede(ponto));
						break;
					case 'E':
						tileList.add(new Chao(ponto));
						bobcat = Empilhadora.getInstance();
						bobcat.setInitialPosition(ponto);
						bobcat.resetEmpilhadora();
						tileMap.add(bobcat);
						tileList.add(bobcat);
						break;
					case 'C':
						Caixote caixote = new Caixote(ponto);
						tileMap.add(caixote);
						tileList.add(caixote);
						tileList.add(new Chao(ponto));

						break;
					case '=':
						tileList.add(new Vazio(ponto));
						break;
					case 'B':
						Bateria b = new Bateria(ponto);
						tileMap.add(b);
						tileList.add(b);
						break;
					case 'X':
						Alvo a = new Alvo(ponto);
						tileMap.add(a);
						tileList.add(a);
						break;
					case 'T':
						Teleporte t = new Teleporte(ponto);
						tileMap.add(t);
						tileList.add(t);
						break;
					case 'O':
						Buraco buraco = new Buraco(ponto);
						tileMap.add(buraco);
						tileList.add(buraco);
						break;
					case 'P':
						Palete p = new Palete(ponto);
						tileMap.add(p);
						tileList.add(p);
						tileList.add(new Chao(ponto));
						break;
					case 'M':
						Martelo m = new Martelo(ponto);
						tileMap.add(m);
						tileList.add(m);
						tileList.add(new Chao(ponto));
						break;
					case '%':
						ParedeRachada paredeRachada = new ParedeRachada(ponto);
						tileMap.add(paredeRachada);
						tileList.add(paredeRachada);
						tileList.add(new Chao(ponto));
					default:
						tileList.add(new Chao(ponto));
						break;
					}
				}
				y++;

			}
			alvos = loadAlvos();
			teleportes = loadTeleportes();
			buracos = loadBuracos();
			caixotes = loadCaixotes();
			gui.addImages(tileList);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private List<Caixote> loadCaixotes() {
		caixotes = new ArrayList<>();
		for (GameElement ge : tileMap) {
			if (ge instanceof Caixote) {
				caixotes.add((Caixote) ge);
			}
		}
		return caixotes;
	}

	private List<Alvo> loadAlvos() {
		alvos = new ArrayList<>();
		for (GameElement ge : tileMap) {
			if (ge instanceof Alvo) {
				alvos.add((Alvo) ge);
			}
		}
		return alvos;
	}

	private List<Teleporte> loadTeleportes() {
		teleportes = new ArrayList<>();
		for (GameElement ge : tileMap) {
			if (ge instanceof Teleporte) {
				teleportes.add((Teleporte) ge);
			}
		}
		return teleportes;
	}
	
	private List<Buraco> loadBuracos(){
		buracos = new ArrayList<>();
		for (GameElement ge : tileMap) {
			if (ge instanceof Buraco) {
				buracos.add((Buraco) ge);
			}
		}
		return buracos;
	}
	
	public void updateLists(){
		alvos = loadAlvos();
		buracos = loadBuracos();
		teleportes = loadTeleportes();
		caixotes = loadCaixotes();
	}


	public int getLevel() {
		return level;
	}

	public void increaseLevel() {
		if(level < maxLevel){
			level++;
			restartLevel();
			gui.update();
		}
	}
	

	public void restartLevel() {
		tileMap.clear();
		gui.clearImages();
		createGame();
		bobcat.resetEmpilhadora();
	}

	public void resetGame() {
		level = 1;
		restartLevel();
		gui.update();	
	}

	public List<GameElement> getTileMap() {
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
	public List<Caixote> getCaixotes(){
		return caixotes;
	}


}
