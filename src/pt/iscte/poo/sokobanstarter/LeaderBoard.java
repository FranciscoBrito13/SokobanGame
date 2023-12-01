package pt.iscte.poo.sokobanstarter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class LeaderBoard {
	
	
	private static final String SCORE_PATH = "score/top_scores.txt";

	/*[FUNCTION THAT RETURNS THE PREVIOUS TOP SCORES IN A LINKEDHASHMAP]*/
	public static LinkedHashMap<String,Integer> getLeaderBoard(){

		LinkedHashMap<String, Integer> topScores = new LinkedHashMap<>();
		//String filePath = "score/top_scores.txt";

		try (Scanner scanner = new Scanner(new File(SCORE_PATH))) {
			while(scanner.hasNextLine()){
				String line = scanner.nextLine();
				topScores.put(line.split(":")[0].toLowerCase(),Integer.parseInt(line.split(":")[1]));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return topScores;
	}

	/*[FUNCTION THAT SORTS A GIVEN LINKEDHASHMAP BY ITS KEYS VALUES VALUE]*/
	public static LinkedHashMap<String, Integer> sortByValueDescending(HashMap<String, Integer> unsorterBoard) {
		LinkedHashMap<String, Integer> sortedMap = unsorterBoard.entrySet()
				.stream()
				.sorted(HashMap.Entry.<String, Integer>comparingByValue().reversed())
				.collect(LinkedHashMap::new,
						(map, entry) -> map.put(entry.getKey(), entry.getValue()),
						LinkedHashMap::putAll);

		return sortedMap;
	}

	/* [CHECKS IF A GIVEN SCORE IS IN THE RANGE OF VALUES FOR THE TOPSCORE] */ 
	public static boolean isInLeaderBoard(LinkedHashMap<String, Integer> topScores, int totalPoints) {
		for (int value : topScores.values()) {
			if (value < totalPoints) {
				return true;
			}
		}
		return false;
	}


	/* [FUNCTION THAT UPDATES THE LEADERBOARD LINKEDHASHMAP] */
	public static boolean updateLeaderBoard(String playerName, int totalPoints) {
		LinkedHashMap<String, Integer> topScores = getLeaderBoard();

		if (topScores.size() < 5 || isInLeaderBoard(topScores, totalPoints)) {

			topScores.put(playerName.toLowerCase(), totalPoints);

			topScores = sortByValueDescending(topScores);

			topScores = keepTop5Entries(topScores);

			updateLeaderBoardFile(topScores);

			return true;
		}

		return false;
	}

	/*[FUNCION THAT KEEPS THE FIRST 5 KEYS OF A LINKEDHASHMAP, REMOVING THE OTHERS]*/
	private static LinkedHashMap<String, Integer> keepTop5Entries(HashMap<String, Integer> topScores) {
		int count = 0;
		LinkedHashMap<String, Integer> result = new LinkedHashMap<>();

		for (HashMap.Entry<String, Integer> entry : topScores.entrySet()) {
			if (count >= 5) {
				break;
			}

			result.put(entry.getKey(), entry.getValue());
			count++;
		}

		return result;
	}

	/* [FUNCTION THAT UPDATES THE LEADERBOARD FILE] */
	private static void updateLeaderBoardFile(HashMap<String, Integer> topScores) {

		try (PrintWriter writer = new PrintWriter(new FileWriter(SCORE_PATH))) {
			topScores.entrySet()
			.stream()
			.forEach(entry -> writer.println(entry.getKey() + ":" + entry.getValue()));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
