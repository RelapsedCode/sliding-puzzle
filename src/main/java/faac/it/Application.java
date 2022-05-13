package faac.it;

import static faac.it.PuzzleGrid.findEmptyCell;

import java.util.Locale;
import java.util.Scanner;

import faac.it.dataProvider.ConfigFileReader;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application {

	public static void main(String args[]) {
		PuzzleGrid puzzleGrid = new PuzzleGrid();

		int[][] initialState = ConfigFileReader.loadInitialState(false);
		puzzleGrid.setPuzzleCurrentState(initialState);
		puzzleGrid.printPuzzleState();
		Scanner sc = new Scanner(System.in);

		while (true) {
			if (puzzleGrid.isPuzzleSolved()) {
				log.info("\nWould you like to start a new game? yes/no");
				String answer = sc.nextLine();
				switch (answer.toLowerCase(Locale.ROOT)) {
					case "yes" -> {
						puzzleGrid.setPuzzleCurrentState(ConfigFileReader.loadInitialState(true));
						log.info("\nNew game has been started.");
						puzzleGrid.printPuzzleState();
					}
					case "no" -> System.exit(0);
					default -> log.warn("PLease select a valid option. yes/no");
				}
			}
			log.info("\nYour next move: ");
			String dir = sc.nextLine();
			puzzleGrid.moveEmptyCell(findEmptyCell(puzzleGrid.getPuzzleCurrentState(), 0), dir);
			puzzleGrid.printPuzzleState();
			ConfigFileReader.savePuzzleStateToCSV(puzzleGrid.getPuzzleCurrentState());
		}
	}
}
