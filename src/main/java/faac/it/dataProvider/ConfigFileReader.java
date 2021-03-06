package faac.it.dataProvider;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConfigFileReader {

	private static final String PROPERTY_FILE_PATH = "src/main/java/faac/it/configurations/puzzleLastKnownState.csv";

	@SneakyThrows
	public static int[][] loadInitialState(boolean enableShuffle) {
		byte gridSize = 4;
		List<int[]> arr = new ArrayList<>();

		FileReader filereader = new FileReader(PROPERTY_FILE_PATH);
		CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
		CSVReader csvReader = new CSVReaderBuilder(filereader)
				.withCSVParser(parser)
				.build();
		List<String[]> allData = csvReader.readAll();
		for (String[] allDatum : allData) {
			arr.add(Arrays.stream(allDatum)
					.mapToInt(Integer::parseInt)
					.toArray());
		}

		if (enableShuffle) {
			Collections.shuffle(arr);
		}
		return arr.toArray(new int[gridSize][gridSize]);
	}

	@SneakyThrows
	public static void savePuzzleStateToCSV(int[][] puzzleState) {
			FileWriter outputFile = new FileWriter(PROPERTY_FILE_PATH);
			CSVWriter writer = new CSVWriter(outputFile, ';',
					CSVWriter.NO_QUOTE_CHARACTER,
					CSVWriter.DEFAULT_ESCAPE_CHARACTER,
					CSVWriter.DEFAULT_LINE_END);
			for (int[] ints : puzzleState) {
				writer.writeNext(Arrays.stream(ints)
						.mapToObj(String::valueOf)
						.toArray(String[]::new));
			}
			writer.close();
	}

}
