import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import acm.program.ConsoleProgram;

/**
 * MIT License (http://choosealicense.com/licenses/mit/)
 * 
 * Stocks: Trains
 * 
 * Shows the train connections between major German cities, and allows you to
 * plan a trip. Try to get from Hamburg to Frankfurt.
 * 
 * @see http://www.VariationenZumThema.de/
 * @author Khen Brian N. K. Quartey
 */
public class Trains extends ConsoleProgram {

	private HashMap<String, ArrayList<String>> connections = new HashMap<String, ArrayList<String>>();
	private ArrayList<String> cities = new ArrayList<>();
	String departure;

	public void run() {
		setup();

		// program loop
		while (true) {
			getNextDestination();
		}
	}

	// method to get the destination from the user and initiate a game loop
	private void getNextDestination() {
		String nextDeparture = readLine("Enter next destination: ");
		if (nextDeparture.isEmpty()) {
			exit();
		} else if (connections.get(departure).contains(nextDeparture)) {
			println("From " + nextDeparture + " you can go to:");
			println(connections.get(nextDeparture));
		} else {
			println("You can not go to destination from this station");
		}
		departure = nextDeparture;
	}

	// method for displaying cities
	private void printCities(ArrayList<String> cities) {
		println(" ");
		for (String city : cities) {
			print(city + ", ");
		}
		println();
	}

	// program logic and flow 
	private void setup() {
		readConnections();
		printCities(cities);
		departure = readLine("Where do you want to start: ");
		if (departure.isEmpty()) {
			exit();
		} else if (connections.containsKey(departure)) {
			println("From " + departure + " you can go to:");
			println(connections.get(departure));
		} else {
			println("No available connection from departure location");
		}
	}

	// Separate each line by source and destination, then save in corresponding
	// HashMap
	private void stationMap(String info) {
		StringTokenizer st = new StringTokenizer(info, ">");
		while (st.hasMoreTokens()) {
			String source = st.nextToken().trim();
			String destination = st.nextToken().trim();

			if (!cities.contains(source)) {
				cities.add(source);
				connections.put(source, new ArrayList<String>());
			}
			ArrayList<String> cits = connections.get(source);
			cits.add(destination);
		}
	}

	// Reads the text file and stores in the hashmap
	private void readConnections() {
		String line = "";
		try {
			FileReader trains = new FileReader("Trains.txt");
			BufferedReader read = new BufferedReader(trains);

			while (true) {
				line = read.readLine();
				if (line == null)
					break;
				stationMap(line);
			}
			trains.close();
			read.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
