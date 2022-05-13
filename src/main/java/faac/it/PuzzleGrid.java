package faac.it;

import java.util.Locale;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class PuzzleGrid {

	boolean isPuzzleResolved = false;
	int[][] puzzleCurrentState;

	public static int[] findEmptyCell(int[][] arr, int target) {
		for (int row = 0; row < arr.length; row++) {
			for (int col = 0; col < arr[row].length; col++) {
				if (arr[row][col] == target) {
					return new int[] { row, col };
				}
			}
		}
		return new int[] { -1, -1 };
	}

	public void moveEmptyCell(int[] emptyCellLocation, String directionToMove) {
		int[] newEmptyCellLocation = emptyCellLocation.clone();
		int row = emptyCellLocation[0];
		int col = emptyCellLocation[1];
		int[][] newPuzzleState = this.getPuzzleCurrentState();
		int swappedCellValue = 0;

		switch (directionToMove.toUpperCase(Locale.ROOT)) {
			case "UP":
				if (emptyCellLocation[0] != 0) {
					swappedCellValue = this.getPuzzleCurrentState()[row - 1][col];
					newEmptyCellLocation[0] = row - 1;
				}
				break;
			case "DOWN":
				if (emptyCellLocation[0] != 3) {
					swappedCellValue = this.getPuzzleCurrentState()[row + 1][col];
					newEmptyCellLocation[0] = row + 1;
				}
				break;
			case "LEFT":
				if (emptyCellLocation[1] != 0) {
					swappedCellValue = this.getPuzzleCurrentState()[row][col - 1];
					newEmptyCellLocation[1] = col - 1;
				}
				break;
			case "RIGHT":
				if (emptyCellLocation[1] != 3) {
					swappedCellValue = this.getPuzzleCurrentState()[row][col + 1];
					newEmptyCellLocation[1] = col + 1;
				}
				break;
			default:
				swappedCellValue = -1;
				log.warn(directionToMove + " is invalid direction. No move action has been performed. Correct directions: up, down, left, right");
		}

		if (swappedCellValue > 0) {
			newPuzzleState[emptyCellLocation[0]][emptyCellLocation[1]] = swappedCellValue;
			newPuzzleState[newEmptyCellLocation[0]][newEmptyCellLocation[1]] = 0;
			this.setPuzzleCurrentState(newPuzzleState);
		} else if (swappedCellValue == 0) {
			log.warn(directionToMove + " is out of boundaries, please try another direction.");
		}
	}

	public void printPuzzleState() {
		int[][] arr = this.getPuzzleCurrentState();
		for (int[] ints : arr) {
			for (int anInt : ints) {
				System.out.print(String.format("(" + "%02d", anInt) + ") ");
			}
			System.out.print("\n");
		}
	}

	public boolean isPuzzleSolved() {
		this.isPuzzleResolved = false;
		int[] array = Stream.of(this.getPuzzleCurrentState())
				.flatMapToInt(IntStream::of)
				.toArray();
		for (int i = 0; i < array.length; i++) {
			if (i == array.length - 1) {
				this.isPuzzleResolved = true;
				log.info("\u001B[32m" + "Congratz, Your Puzzle is Solved!!!" + "\u001B[0m");
				return true;
			}
			if (i != array[i] - 1) {
				break;
			}
		}
		return false;
	}

}
