package Generator;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Generator.JSONFileParser is class which parses grammar json file and return grammar instance of Generator.Grammar class
 *
 * @author vrindabisani
 */
public class JSONFileParser {

  /**
   * Constant used for json extension
   */
  private static final String JSON_EXTENSION = ".json";
  /**
   * Constant for element grammarTitle in json file
   */
  private static final String GRAMMAR_TITLE = "grammarTitle";

  /**
   * Constant for element grammarDesc in json file
   */
  private static final String GRAMMAR_DESC = "grammarDesc";
  /**
   * Constant for empty string
   */
  private static final String EMPTY_STRING = "";
  private String jsonFileName;

  /**
   * Constructor of Generator.JSONFileParser which validates JSON File, if not valid then throws Generator.InvalidJSONFileException exception
   *
   * @param jsonFileName Represented as String, name of json File
   * @throws InvalidJSONFileException if json file is not valid as conditions like:
   *                                  file extension is not json, file does not exist, is not a file or can not be read
   */
  public JSONFileParser(String jsonFileName) throws InvalidJSONFileException {
    if(validateFile(jsonFileName)){
      this.jsonFileName = jsonFileName;
    }
    else {
      throw new InvalidJSONFileException("Invalid " + jsonFileName + " json file !");
    }
  }

  /**
   * This private method reads json file at given path and returns grammar instance
   *
   * @return Represented as Generator.Grammar, provides information like grammar title, grammar desc, and other elements in map
   */
  public Grammar processJsonFile() {
    JSONFileReader jsonReader = new JSONFileReader();
    Map<String, JsonElement> rawGrammarJson = jsonReader.readFile(this.jsonFileName);
    return this.parseJsonContent(rawGrammarJson);
  }

  /**
   * This private method removes GRAMMAR_TITLE and GRAMMAR_DESC from json and creates grammar instance
   *
   * @param rawGrammarJson Map of grammar json elements represented as JsonElement from json file
   * @return Represented as Generator.Grammar, Generator.Grammar instance
   */
  private Grammar parseJsonContent(Map<String, JsonElement> rawGrammarJson) {
    String grammarTitle = this.getGrammarTitleFromJson(rawGrammarJson);
    String grammarDesc = this.getGrammarDescFromJson(rawGrammarJson);
    //The names of non-terminals should be considered case-insensitively, matching Adj and adj, for example
    Map<String, List<String>> nonTerminalDefinitions = this.getNonTerminalDefinitions(rawGrammarJson);

    return new Grammar(grammarTitle, grammarDesc, nonTerminalDefinitions);
  }

  /**
   * This helper method iterates on Map of json Elements of json file.
   * This method converts them into Map of non-terminal name and value as List of productions
   *
   * @param rawGrammarJson Map of grammar json elements represented as JsonElement from json file
   * @return Map of non-terminal definitions with list of productions
   */
  private Map<String, List<String>> getNonTerminalDefinitions(Map<String, JsonElement> rawGrammarJson) {
    Map<String, List<String>> nonTerminalMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    rawGrammarJson.forEach((nonTerminal, rawJson) -> {
      JsonArray productionsArray = rawJson.getAsJsonArray();
      List<String> productions = new ArrayList<>();

      if(productionsArray != null) {
        for (int index = 0; index < productionsArray.size(); index++) {
          productions.add(productionsArray.get(index).getAsString());
        }
      }
      nonTerminalMap.put(nonTerminal, productions);
    });
    return nonTerminalMap;
  }

  /**
   * This private method gets value of grammarDesc element from map of grammar elements
   *
   * @param rawGrammarJson Map of grammar elements, represented as JsonElement from json file
   * @return Represented as String
   */
  private String getGrammarDescFromJson(Map<String, JsonElement> rawGrammarJson) {
    String grammarDesc = EMPTY_STRING;
    if(rawGrammarJson.containsKey(GRAMMAR_DESC)) {
      grammarDesc = rawGrammarJson.get(GRAMMAR_DESC).getAsString();
      rawGrammarJson.remove(GRAMMAR_DESC);
    }
    return grammarDesc;
  }

  /**
   * This private method gets value of grammarTitle element from map of grammar elements
   *
   * @param rawGrammarJson Map of grammar elements, represented as JsonElement from json file
   * @return Represented as String
   */
  private String getGrammarTitleFromJson(Map<String, JsonElement> rawGrammarJson) {
    String grammarTitle = EMPTY_STRING;
    if(rawGrammarJson.containsKey(GRAMMAR_TITLE)){
      grammarTitle = rawGrammarJson.get(GRAMMAR_TITLE).getAsString();
      rawGrammarJson.remove(GRAMMAR_TITLE);
    }
    return grammarTitle;
  }

  /**
   * Validates if file is valid or not :
   * conditions are valid json extension, file exists, file object is file and file can be read
   *
   * @param jsonFileName Represented as String, json file path
   * @return represented as boolean
   */
  private boolean validateFile(String jsonFileName) {
    File jsonFile = new File(jsonFileName);
    return this.validateExtension(jsonFileName) && jsonFile.exists() && jsonFile.isFile() && jsonFile.canRead();
  }

  /**
   * Validates if file has valid json extension
   *
   * @param jsonFileName Represented as String, json file path
   * @return represented as boolean
   */
  private boolean validateExtension(String jsonFileName) {
    return jsonFileName.endsWith(JSON_EXTENSION);
  }
}
