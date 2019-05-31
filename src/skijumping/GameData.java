package skijumping;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

public class GameData {

	public static final int NUMBER_OF_JUMPHILL_PARAMS = 12;
	public static final String DATA_FOLDER_PATH = "data/jumpinghills.txt";
	public static final String JUMPER_DATA_PATH = "data/jumper.txt";

	private Vector<String> jumping_hills_data;

	GameData() {
		jumping_hills_data = getJumpingHills(DATA_FOLDER_PATH, "data/jumpinghills/");
	}

	public Jumper getSavedJumper() {
		int weight = 0;
		double skills = 0;
		String name = "";
		try {
			Scanner scanner = new Scanner(new File(JUMPER_DATA_PATH));
			name = scanner.nextLine().replaceFirst("[\\w]+ ", "");
			weight = Integer.parseInt(scanner.nextLine().replaceFirst("[\\w]+ ", ""));
			skills = Double.parseDouble(scanner.nextLine().replaceFirst("[\\w]+ ", ""));
			scanner.close();
		} catch (IOException e) {
		}
		Jumper new_jumper;
		try {
			new_jumper = new Jumper(name, weight, skills);
			return new_jumper;
		} catch (JumperException e) {
		}
		return null;
	}

	private Vector<String> getJumpingHills(String path, String hills_path_prefix) {
		Vector<String> hills_data = new Vector<String>();
		Vector<String> hill_data_lines = new Vector<String>();
		Vector<String> hills_list = new Vector<String>();
		Scanner scanner;
		try {
			scanner = new Scanner(new File(path));
			while (scanner.hasNext()) {
				hills_list.add(scanner.nextLine());
			}
			for (String hill : hills_list) {
				scanner = new Scanner(new File(hills_path_prefix + hill + ".txt"));
				while (scanner.hasNext()) {
					hill_data_lines.add(scanner.nextLine());
				}
				hills_data.add(hill);
				for (String line : hill_data_lines) {
					hills_data.addAll(Arrays.asList(line.replaceFirst("[\\w]+ ", "").split(" ")));
				}
				hill_data_lines.clear();
			}

		} catch (IOException e) {
		}
		return hills_data;
	}

	public Vector<String> getJumpingHillsData() {
		return jumping_hills_data;
	}

	public void save(Jumper jumper_to_save) {
		try {
			PrintWriter writer = new PrintWriter(JUMPER_DATA_PATH);
			writer.write("name " + jumper_to_save.getName() + "\n");
			writer.write("weight " + jumper_to_save.getWeight() + "\n");
			writer.write("skills " + jumper_to_save.getSkills() + "\n");
			writer.close();
		} catch (FileNotFoundException ex) {
		}
	}
}
