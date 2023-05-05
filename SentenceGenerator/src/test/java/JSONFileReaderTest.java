import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import Generator.JSONFileReader;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JSONFileReaderTest {
  private JSONFileReader testReader;
  private Map<String, List<String>> nonTerminalMap;
  @BeforeEach
  void setUp() {
    testReader = new JSONFileReader();
    nonTerminalMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    List<String> startValues = new ArrayList<>(Arrays.asList("The <object> <verb>."));
    nonTerminalMap.put("start", startValues);

    List<String> objectValues = new ArrayList<>(Arrays.asList("test"));
    nonTerminalMap.put("object", objectValues);

    List<String> verbValues = new ArrayList<>(Arrays.asList("failed", "passed", "works <adverb>"));
    nonTerminalMap.put("verb", verbValues);

    List<String> adverbValues = new ArrayList<>(Arrays.asList("fine", "badly"));
    nonTerminalMap.put("adverb", adverbValues);
  }

  @Test
  void readFile_Exception() {
    assertThrows(RuntimeException.class, () -> {
      Map<String, JsonElement> jsonElementMap = testReader.readFile("jsonReaderGrammar.json");
    });
  }

  @Test
  void readFile() {
    Map<String, JsonElement> rawGrammarMap = testReader.readFile("src/test/resources/jsonReaderGrammar.json");
    Map<String, List<String>> resultantMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    rawGrammarMap.forEach((nonTerminal, jsonValue) -> {
      JsonArray productionArray = jsonValue.getAsJsonArray();
      List<String> productions = new ArrayList<>();
      if (productionArray != null) {
        for (int index = 0; index < productionArray.size(); index++) {
          productions.add(productionArray.get(index).getAsString());
        }
      }
      resultantMap.put(nonTerminal, productions);
    });

    assertEquals(nonTerminalMap, resultantMap);
  }
}
