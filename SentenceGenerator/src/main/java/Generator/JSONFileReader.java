package Generator;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Generator.JSONFileReader is used to read grammar json file at given path
 *
 * @author vrindabisani
 */
public class JSONFileReader {

  /**
   * Empty constructor
   */
  public JSONFileReader() {}

  /**
   * This function is used to read json file from directory at given path in parameter
   *
   * @param jsonFileName Represented as String, path of json file
   * @return Represented as Map, where key is element in String and Value is JsonElement
   */
  public Map<String, JsonElement> readFile(String jsonFileName) {
    Map<String, JsonElement> grammarJson;
    try (Reader fileReader = Files.newBufferedReader(Paths.get(jsonFileName))) {
      JsonElement jsonElement = JsonParser.parseReader(fileReader);
      JsonObject jsonRootObject = jsonElement.getAsJsonObject();
      grammarJson = jsonRootObject.asMap();
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
    return grammarJson;
  }
}
