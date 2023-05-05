package Generator;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Generator.Grammar class represents the grammar json file with information
 * like grammar title, grammar desc, and grammar definitions and its one or more productions
 *
 * @author vrindabisani
 */
public class Grammar {
  private String grammarTitle;
  private String grammarDesc;
  private Map<String, List<String>> grammarDefinition;

  /**
   * Constructor of Generator.Grammar
   *
   * @param grammarTitle Represented as String, grammar title in json file
   * @param grammarDesc Represented as String, grammar description in json file
   * @param grammarDefinition Represented as String, grammar non-terminal and its definitions in json file
   */
  public Grammar(String grammarTitle, String grammarDesc, Map<String, List<String>> grammarDefinition) {
    this.grammarTitle = grammarTitle;
    this.grammarDesc = grammarDesc;
    this.grammarDefinition = grammarDefinition;
  }

  /**
   * Getter of grammar title
   *
   * @return Represented as String, this.grammarTitle
   */
  public String getGrammarTitle() {
    return this.grammarTitle;
  }

  /**
   * Getter of grammar description
   *
   * @return Represented as String, this.grammarDesc
   */
  public String getGrammarDesc() {
    return this.grammarDesc;
  }

  /**
   * This method provides list of productions of given element from grammarDefinition
   *
   * @param element Represented as String, name of element in grammar
   * @return Represented as List of String
   */
  public List<String> getProductions(String element) {
    return this.grammarDefinition.get(element);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Grammar grammar = (Grammar) obj;
    return Objects.equals(this.getGrammarTitle(), grammar.getGrammarTitle())
        && Objects.equals(this.getGrammarDesc(), grammar.getGrammarDesc())
        && Objects.equals(this.grammarDefinition, grammar.grammarDefinition);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getGrammarTitle(), this.getGrammarDesc(), this.grammarDefinition);
  }

  @Override
  public String toString() {
    return "Generator.Grammar{" +
        "grammarTitle='" + this.getGrammarTitle() + '\'' +
        ", grammarDesc='" + this.getGrammarDesc() + '\'' +
        ", grammarDefinition=" + this.grammarDefinition +
        '}';
  }
}
