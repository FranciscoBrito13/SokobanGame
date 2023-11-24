package pt.iscte.poo.sokobanstarter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

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
    
    public void writeScore() {
        String folderPath = "score/";
        String fileName = folderPath + username + "_scores.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Username: " + username + "\n");

            for (int i : levelPoints.keySet()) {
                int level = i;
                int points = levelPoints.get(i);
                writer.write("Level " + level + ": " + points + " points\n");
            }
            writer.write("O jogador '" + username + "' tem um total de " + totalPoints + " pontos");
            System.out.println("Score written to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception according to your application's requirements
        }
    }
    
    
}
