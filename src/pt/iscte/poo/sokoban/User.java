package pt.iscte.poo.sokoban;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class User {
	
	/* [USER VARIABLES] */
	private String username;
	private String password;
	private HashMap<Integer, Integer> levelPoints;
	private int totalPoints = 0;

	/* [USER CONSTRUCTOR] */
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.levelPoints = new HashMap<>();
	}
	
	/* [TOTAL POINTS GETTER] */
	public int getTotalPoints(){
		return totalPoints;
	}

	/* [USERNAME GETTER] */
	public String getUsername() {
		return username;
	}

	/* [PASSWORD GETTER] */
	public String getPassword() {
		return password;
	}

	/* [LEVELPOINTS GETTER] */
	public HashMap<Integer, Integer> getLevelPoints() {
		return levelPoints;
	}
	
	/* [FUNCTION THAT RESETS POINTS] */
	public void resetPoints(){
		totalPoints = 0;
		levelPoints = new HashMap<>();
	}

	/* [FUNCTION THAT RETURNS THE POINTS OF A GIVEN LEVEL] */
	public int getPointsForLevel(int level) {
		return levelPoints.getOrDefault(level, 0);
	}

	/* [FUNCTION THAT SETS THE POINTS FOR THE GIVEN LEVEL] */
	public void setPointsForLevel(int level, int points) {
		levelPoints.put(level, points);
		totalPoints += points;
	}

	/* [FUNCTION THAT RETURNS THE PREVIOUS TOP SCORE] */
	//IF THERES AN ADDED LEVEL THAN THE PREVIOUS SCORE WILL ALWAYS UPDATE
	public int getPreviousTopScore() {
		
		String filePath = "score/" + username.toLowerCase() + "_score.txt";

		try (Scanner scanner = new Scanner(new File(filePath))) {

			for (int i = 0; i < Level.maxLevel + 1 && scanner.hasNextLine(); i++) {
				scanner.nextLine();
			}

			if (scanner.hasNextLine()) {
				String line = scanner.nextLine();


                String[] words = line.split(" ");
                System.out.println(words[1]);
                return Integer.parseInt(words[1]);
			}
		} catch (IOException e) {
			
		}

		return 0;
	}
	
	/* [FUNCTION THAT WRITES THE SCORES IN THE USER'S SCORE FILE] */
	public void writeScore() {
		System.out.println("Writing user' score... ");
		String fileName = "score/" + username.toLowerCase() + "_score.txt";

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
			writer.write("Username: " + username.toLowerCase() + System.lineSeparator());

			for (int i : levelPoints.keySet()) {
				int level = i;
				int points = levelPoints.get(i);
				writer.write("Level " + level + ": " + points + " points\n");
			}
			writer.write("Total: " + totalPoints + " pontos");
			System.out.println("Score saved in " + fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
