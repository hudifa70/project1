import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.*;

public class JsonToCsvConverter {

	public static void main(String[] args) {
		List<User> users = new ArrayList<>();

		// Generate 50 random objects
		for (int i = 0; i < 50; i++) {
			int id = i + 1;
			String name = "User" + id;
			String major = getRandomString();
			String extension = getRandomString();

			users.add(new User(id, name, major, extension));
		}
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(users);

		// Write JSON to a file
		try (FileWriter writer = new FileWriter("sample1.json")) {
			writer.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String jsonFilePath = "sample1.json";
		// Put your domain json file
		String csvFilePath = "file.csv";

		try {
			convertJsonToCsv(jsonFilePath, csvFilePath);
			System.out.println("Conversion completed successfully.");
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	private static String getRandomString() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		int length = random.nextInt(10) + 5; // Random length between 5 and 14 characters

		for (int i = 0; i < length; i++) {
			int index = random.nextInt(characters.length());
			sb.append(characters.charAt(index));
		}

		return sb.toString();
	}


	public static void convertJsonToCsv(String jsonFilePath, String csvFilePath) throws IOException {
		FileReader fileReader = new FileReader(jsonFilePath);
		JsonElement jsonElement = JsonParser.parseReader(fileReader);

		JsonArray jsonArray = jsonElement.getAsJsonArray();

		FileWriter csvWriter = new FileWriter(csvFilePath);

		JsonObject firstObject = jsonArray.get(0).getAsJsonObject();
		firstObject.keySet().forEach(key -> {
			try {
				csvWriter.append(key);
				csvWriter.append(',');
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		csvWriter.append('\n');

		for (JsonElement element : jsonArray) {
			JsonObject jsonObject = element.getAsJsonObject();
			jsonObject.entrySet().forEach(entry -> {
				try {
					csvWriter.append(entry.getValue().getAsString());
					csvWriter.append(',');
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			csvWriter.append('\n');
		}

		fileReader.close();
		csvWriter.flush();
		csvWriter.close();
	}
}
