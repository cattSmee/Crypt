
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Main {
	public static void main(String[] args) {
		/*
		 * { if (args.length != 1) {
		 * System.err.println("Wrong format"); System.exit(0); } }
		 */

		Servant serv = new Servant();
		Builder builder = new Builder();
		KeyGen keygen = new KeyGen();
		StringBuilder data = serv.prepareData(serv.scanFile(serv.findFile("test.js")));
		System.out.println("Data read: " + data.toString());
		builder.append(data.toString());
		serv.articulate(builder.getBuilder(), keygen);
		keygen.displayCollection();
//		MockRunTime rt = new MockRunTime();
//		rt.pokeRunTime();
	}

}

class Servant {
	public File findFile(String path) {
		try {
			return new File(path);
		} catch (Exception e) {
			System.err.println("File not found!");
			e.printStackTrace();
		}
		return null;
	}

	public Scanner scanFile(File file) {
		try {
			if (file.canRead()) {
				return new Scanner(file);
			}
		} catch (FileNotFoundException e) {
			System.err.println("File not readable!");
			e.printStackTrace();
		}
		return null;
	}

	public StringBuilder prepareData(Scanner scanner) {
		if (scanner == null) {
			System.err.println("Preparing failed!");
			return null;
		} else {
			String rawData = "";
			while (scanner.hasNextLine()) {
				rawData += scanner.nextLine();
			}
			return new StringBuilder(rawData);
		}
	}

	public int articulate(StringBuilder stringBuilder, KeyGen kg) { // ADDS DIFFERENT LETTERS FROM DATA
		int spaces = 0;
		for (String s : stringBuilder.toString()
				.split(" ")) {
			for (char c : s.toCharArray()) {
				kg.add(c);
			}
			spaces++;
		}
		return spaces - 1;
	}
}

class Builder {
	private StringBuilder builder = new StringBuilder();

	public void append(String string) {
		builder.append(string);
	}

	public boolean contains() {
		return true;
	}

	public StringBuilder getBuilder() {
		return builder;
	}
}

class KeyGen {
	private final char token = ' ';
	private Set<Character> collection = new HashSet<>();
	private MapControl map = new MapControl();

	private boolean addToken() { // ADDS TOKEN, CHECKS IF NEEDED
		if (collection.size() > 0) {
			collection.add(token);
			return true;
		} else {
			return false;
		}
	}

	public void add(char c) { // ADDS CHAR TO COLLECTION
		collection.add(c);
	}

	private void mix() {
		List<Character> list = new ArrayList<>();
		list.addAll(collection);
		System.out.println("List before mix (set): " + collection.toString());
		System.out.println("List before mix (list): " + list.toString());
		list.set(list.indexOf(token), list.set(list.indexOf(getSubstitute(list)), token));
		System.out.println("Mixed list display " + list.toString());
	}

	private char getSubstitute(List<Character> list) { // GETS A RANDOM SUBSTITUTE FOR TOKEN, SUB CAN'T BE TOKEN
		Random random = new Random();
		char charTemp = list.get(random.nextInt(list.size()));
		while (charTemp == token) {
			charTemp = list.get(random.nextInt(list.size()));
		}
		return charTemp;
	}

	public void generate() { // ADDS TOKEN / MIXES
		if (addToken()) {
			mix();
		}
	}

	public void displayCollection() {
		generate(); // TEST PLACE
		System.out.println("Characters stored: " + collection.toString() + " [" + collection.size() + "]");
	}

}

class MapControl {
	private Map<Character, PositionMap> elementMap = new HashMap<>();
	private Map<Integer, Integer[]> positionMap = new PositionMap();

	public void addToElementMap(char character, PositionMap posmap) {
		elementMap.put(character, posmap);
	}

	public void addToPositionMap(int howMany, Integer[] where) {
		positionMap.put(howMany, where);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((elementMap == null) ? 0 : elementMap.hashCode());
		result = prime * result + ((positionMap == null) ? 0 : positionMap.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MapControl other = (MapControl) obj;
		if (elementMap == null) {
			if (other.elementMap != null)
				return false;
		} else if (!elementMap.equals(other.elementMap))
			return false;
		if (positionMap == null) {
			if (other.positionMap != null)
				return false;
		} else if (!positionMap.equals(other.positionMap))
			return false;
		return true;
	}
}

class PositionMap extends HashMap<Integer, Integer[]> {
	private static final long serialVersionUID = 1L;

}

class MockRunTime {
	public void pokeRunTime() {
		Runtime rt = Runtime.getRuntime();
		try {
			rt.exec(new String[] { "cmd.exe", "/k", "test.txt" });
			System.out.println("cmd started");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
