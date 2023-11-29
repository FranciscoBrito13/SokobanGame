package pt.iscte.poo.sokobanstarter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class LeaderBoard {

	/* [FUNCTION THAT RETURNS THE TOP SCORES] */
	public static LinkedHashMap<String,Integer> getLeaderBoard(){

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

	/* [FUNCTION THAT SORTS THE LEADERBOARD] */
	public static LinkedHashMap<String, Integer> sortByValueDescending(HashMap<String, Integer> unsorterBoard) {
		LinkedHashMap<String, Integer> sortedMap = unsorterBoard.entrySet()
				.stream()
				.sorted(HashMap.Entry.<String, Integer>comparingByValue().reversed())
				.collect(LinkedHashMap::new,
						(map, entry) -> map.put(entry.getKey(), entry.getValue()),
						LinkedHashMap::putAll);

		return sortedMap;
	}

	/* [] */               ////////////////////////////
	public static boolean isInLeaderBoard(LinkedHashMap<String, Integer> topScores, int totalPoints) {
		for (int value : topScores.values()) {
			if (value < totalPoints) {
				return true;
			}
		}
		return false;
	}


	/* [FUNCTION THAT UPDATES THE LEADERBOARD] */
	public static boolean updateLeaderBoard(String playerName, int totalPoints) {
		LinkedHashMap<String, Integer> topScores = getLeaderBoard();

		if (topScores.size() < 5 || isInLeaderBoard(topScores, totalPoints)) {

			topScores.put(playerName, totalPoints);

			topScores = sortByValueDescending(topScores);

			topScores = keepTop5Entries(topScores);

			updateLeaderBoardFile(topScores);

			return true;
		}

		return false;
	}

	/* [] */                               /////////
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
		String filePath = "score/top_score.txt";

		try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
			topScores.entrySet()
			.stream()
			.forEach(entry -> writer.println(entry.getKey() + ":" + entry.getValue()));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
