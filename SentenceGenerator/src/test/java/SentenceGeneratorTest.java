import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import Generator.Grammar;
import Generator.SentenceGenerator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SentenceGeneratorTest {
  private SentenceGenerator generator;
  private String testGrammarTitle;
  private String testGrammarDesc;
  private Map<String, List<String>> nonTerminalMap;
  private Grammar testGrammar;
  @BeforeEach
  void setUp() {
    testGrammarTitle = "";
    testGrammarDesc = "";
    nonTerminalMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    List<String> startValues = new ArrayList<>(Arrays.asList("The <object> <verb>.", "Sincerely , <sign>", "<object> <verb> ."));
    nonTerminalMap.put("Start", startValues);

    List<String> objectValues = new ArrayList<>(Arrays.asList("test"));
    nonTerminalMap.put("Object", objectValues);

    List<String> verbValues = new ArrayList<>(Arrays.asList("failed", "passed", "works <adverb>"));
    nonTerminalMap.put("Verb", verbValues);

    List<String> adverbValues = new ArrayList<>(Arrays.asList("fine", "badly"));
    nonTerminalMap.put("Adverb", adverbValues);

    testGrammar = new Grammar(testGrammarTitle, testGrammarDesc, nonTerminalMap);
    generator = new SentenceGenerator(testGrammar, 1L);
  }

  @Test
  void getGrammar() {
    Grammar dupGrammar = new Grammar(testGrammarTitle, testGrammarTitle, nonTerminalMap);
    assertEquals(dupGrammar, generator.getGrammar());
  }

  @Test
  void generateSentence() {
    assertEquals("The test failed", generator.generateRandomSentence());
  }

  @Test
  void generateSentence_innerNonTerminal() {
    SentenceGenerator dupGen = new SentenceGenerator(testGrammar, 3L);
    dupGen.generateRandomSentence();
    assertEquals("test works badly.", dupGen.generateRandomSentence());
  }

  @Test
  void generateSentence_innerNonTerminal_RandomNumber() {
    Map<String, List<String>> nonTerminalMapDup = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    List<String> startValues = new ArrayList<>(Arrays.asList("The <object>", "<object> <verb> ."));
    nonTerminalMapDup.put("Start", startValues);
    List<String> objectValues = new ArrayList<>(Arrays.asList("test"));
    nonTerminalMapDup.put("Object", objectValues);
    List<String> verbValues = new ArrayList<>(Arrays.asList("failed", "passed"));
    nonTerminalMapDup.put("Verb", verbValues);
    Grammar dupTestGrammar = new Grammar(testGrammarTitle, testGrammarDesc, nonTerminalMapDup);

    SentenceGenerator dupGen = new SentenceGenerator(dupTestGrammar, null);

    dupGen.generateRandomSentence();
  }

  @Test
  void InvalidElementException() {
    SentenceGenerator dupGen = new SentenceGenerator(testGrammar, 2L);
    assertEquals("Sincerely,", dupGen.generateRandomSentence());
  }

  @Test
  void testEquals() {
    assertTrue(generator.equals(generator));
  }

  @Test
  void testEquals_null() {
    assertFalse(generator.equals(null));
  }

  @Test
  void testEquals_diffType() {
    assertFalse(generator.equals(new String("test")));
  }

  @Test
  void testEquals_SimilarObj() {
    SentenceGenerator generator1 = new SentenceGenerator(testGrammar, 1L);
    assertTrue(generator.equals(generator1));
  }

  @Test
  void testHashCode() {
    int testHash = Objects.hash(testGrammar);
    assertEquals(testHash, generator.hashCode());
  }

  @Test
  void testToString() {
    String expString = "Generator.SentenceGenerator{" +
        "grammar=" + testGrammar +
        '}';
    assertEquals(expString, generator.toString());
  }
}