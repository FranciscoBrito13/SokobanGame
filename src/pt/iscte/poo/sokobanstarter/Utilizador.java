package pt.iscte.poo.sokobanstarter;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Utilizador {
	private String username;
	private String password;
	private HashMap<Integer, Integer> levelPoints;
	private int totalPoints = 0;

	public Utilizador(String username, String password) {
		this.username = username;
		this.password = password;
		this.levelPoints = new HashMap<>();
	}
	
	public int getTotalPoints(){
		return totalPoints;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public HashMap<Integer, Integer> getLevelPoints() {
		return levelPoints;
	}

	public int getPointsForLevel(int level) {
		return levelPoints.getOrDefault(level, 0);
	}

	public void setPointsForLevel(int level, int points) {
		levelPoints.put(level, points);
		totalPoints += points;
	}

	public int getPreviousTopScore() {
		
		String filePath = "score/" + username + "_score.txt";

		try (Scanner scanner = new Scanner(new File(filePath))) {

			for (int i = 0; i < 7 && scanner.hasNextLine(); i++) {
				scanner.nextLine();
			}

			if (scanner.hasNextLine()) {
				String line = scanner.nextLine();


                String[] words = line.split(" ");
                return Integer.parseInt(words[1]);
			}
		} catch (IOException e) {
			
		}

		return 0;
	}
	

	public void writeScore() {
		System.out.println("A escrever o score do user...");
		String fileName = "score/" + username + "_score.txt";

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
			writer.write("Username: " + username + "\n");

			for (int i : levelPoints.keySet()) {
				int level = i;
				int points = levelPoints.get(i);
				writer.write("Level " + level + ": " + points + " points\n");
			}
			writer.write("Total: " + totalPoints + " pontos");
			System.out.println("Score guardado em " + fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
