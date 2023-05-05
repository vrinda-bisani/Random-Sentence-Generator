import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import Generator.Grammar;
import Generator.InvalidJSONFileException;
import Generator.JSONFileParser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JSONFileParserTest {
  private JSONFileParser testParser;
  private Grammar testGrammar;
  private String testGrammarTitle;
  private String testGrammarDesc;
  private Map<String, List<String>> nonTerminalMap;

  @BeforeEach
  void setUp() {
    testGrammarTitle = "";
    testGrammarDesc = "";
    nonTerminalMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    List<String> startValues = new ArrayList<>(Arrays.asList("The <object> <verb>."));
    nonTerminalMap.put("Start", startValues);

    List<String> objectValues = new ArrayList<>(Arrays.asList("test"));
    nonTerminalMap.put("Object", objectValues);

    List<String> verbValues = new ArrayList<>(Arrays.asList("failed", "passed", "works <adverb>"));
    nonTerminalMap.put("Verb", verbValues);

    List<String> adverbValues = new ArrayList<>(Arrays.asList("fine", "badly"));
    nonTerminalMap.put("Adverb", adverbValues);

    testGrammar = new Grammar(testGrammarTitle, testGrammarDesc, nonTerminalMap);
    testParser = new JSONFileParser("src/test/resources/jsonReaderGrammar.json");
  }

  @Test
  void processJsonFile() {
    Grammar grammar = testParser.processJsonFile();
    assertEquals(testGrammar, grammar);
  }

  @Test
  void invalidJSONFileException_FileNotExist() {
    assertThrows(InvalidJSONFileException.class, () -> {
      testParser = new JSONFileParser("jsonReaderGrammar.json");
    });
  }
}