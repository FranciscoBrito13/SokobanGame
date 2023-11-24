package pt.iscte.poo.sokobanstarter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class LeaderBoard {
	
	 public static boolean updateLeaderBoard(String playerName, int totalPoints) {
	        HashMap<String, Integer> topScores = getLeaderBoard();

	        if (topScores.size() < 5 || isInLeaderBoard(topScores, totalPoints)) {
	        	
	            topScores.put(playerName, totalPoints);

	            topScores = sortByValueDescending(topScores);

	            topScores = keepTop5Entries(topScores);

	            updateLeaderBoardFile(topScores);

	            return true;
	        }

	        return false;
	    }
	 
		public static HashMap<String,Integer> getLeaderBoard(){
			
			LinkedHashMap<String, Integer> topScores = new LinkedHashMap<>();
			String filePath = "score/top_score.txt";

			try (Scanner scanner = new Scanner(new File(filePath))) {
				while(scanner.hasNextLine()){
					String line = scanner.nextLine();
					topScores.put(line.split(":")[0],Integer.parseInt(line.split(":")[1]));
				}
					
			} catch (IOException e) {
				e.printStackTrace();
			}

			return topScores;
		}
		
		
	    public static HashMap<String, Integer> sortByValueDescending(HashMap<String, Integer> unsortedMap) {
	        return unsortedMap.entrySet()
	                .stream()
	                .sorted(HashMap.Entry.<String, Integer>comparingByValue().reversed())
	                .collect(HashMap::new, (map, entry) -> map.put(entry.getKey(), entry.getValue()), HashMap::putAll);
	    }
	    
	    public static boolean isInLeaderBoard(HashMap<String, Integer> topScores, int totalPoints) {
	        for (int value : topScores.values()) {
	            if (value < totalPoints) {
	                return true;
	            }
	        }
	        return false;
	    }
	    
	    private static HashMap<String, Integer> keepTop5Entries(HashMap<String, Integer> topScores) {
	        int count = 0;
	        HashMap<String, Integer> result = new HashMap<>();

	        for (HashMap.Entry<String, Integer> entry : topScores.entrySet()) {
	            if (count >= 5) {
	                break;
	            }

	            result.put(entry.getKey(), entry.getValue());
	            count++;
	        }

	        return result;
	    }
	    
	    
	    private static void updateLeaderBoardFile(HashMap<String, Integer> topScores) {
	        String filePath = "score/top_score.txt";

	        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
	            for (HashMap.Entry<String, Integer> entry : topScores.entrySet()) {
	                writer.println(entry.getKey() + ":" + entry.getValue());
	            }
	        } catch (IOException e) {
	            
	        }
	    }

}
