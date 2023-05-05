package Generator;

/**
 * Generator.MissingDefinitionException is a custom exception class which extends RuntimeException.
 * This exception is thrown when there is no value provided in grammar for element to create a sentence.
 *
 * @author vrindabisani
 */
public class MissingDefinitionException extends RuntimeException {
  /**
   * Constructor
   *
   * @param errorMessage error message specified while throwing exception
   */
  public MissingDefinitionException(String errorMessage) {
    super(errorMessage);
  }
}
