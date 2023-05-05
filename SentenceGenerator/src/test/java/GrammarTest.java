import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import Generator.Grammar;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GrammarTest {
  private Grammar testGrammar;
  private String testGrammarTitle;
  private String testGrammarDesc;
  private Map<String, List<String>> nonTerminalMap;
  @BeforeEach
  void setUp() {
    testGrammarTitle = "Test Generator";
    testGrammarDesc = "Generates test sentence.";
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
  }

  @Test
  void getGrammarTitle() {
    assertEquals("Test Generator", testGrammar.getGrammarTitle());
  }

  @Test
  void getGrammarDesc() {
    assertEquals("Generates test sentence.", testGrammar.getGrammarDesc());
  }
  @Test
  void getValueFromJson() {
    assertEquals(Arrays.asList("test"), testGrammar.getProductions("object"));
  }

  @Test
  void testEquals_Self() {
    assertTrue(testGrammar.equals(testGrammar));
  }

  @Test
  void testEquals_null() {
    assertFalse(testGrammar.equals(null));
  }

  @Test
  void testEquals_differentType() {
    assertFalse(testGrammar.equals(new String("test")));
  }

  @Test
  void testEquals_SimilarType() {
    Grammar dupGrammar = new Grammar(testGrammarTitle, testGrammarDesc, nonTerminalMap);
    assertTrue(testGrammar.equals(dupGrammar));
  }

  @Test
  void testEquals_TitleDiff() {
    Grammar dupGrammar = new Grammar(null, testGrammarDesc, nonTerminalMap);
    assertFalse(testGrammar.equals(dupGrammar));
  }

  @Test
  void testEquals_DescDiff() {
    Grammar dupGrammar = new Grammar(testGrammarTitle, null, nonTerminalMap);
    assertFalse(testGrammar.equals(dupGrammar));
  }

  @Test
  void testEquals_JsonDiff() {
    Grammar dupGrammar = new Grammar(testGrammarTitle, testGrammarDesc, null);
    assertFalse(testGrammar.equals(dupGrammar));
  }

  @Test
  void testHashCode() {
    int testHash = Objects.hash(testGrammarTitle, testGrammarDesc, nonTerminalMap);
    assertEquals(testHash, testGrammar.hashCode());
  }

  @Test
  void testToString() {
    String expString = "Generator.Grammar{" +
        "grammarTitle='" + testGrammarTitle + '\'' +
        ", grammarDesc='" + testGrammarDesc + '\'' +
        ", grammarDefinition=" + nonTerminalMap +
        '}';
    assertEquals(expString, testGrammar.toString());
  }
}