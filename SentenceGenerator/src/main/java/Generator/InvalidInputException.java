package Generator;

/**
 * Throws exception if user inputs invalid input
 *
 * @author vrindabisani
 */
public class InvalidInputException extends RuntimeException {

  /**
   * constructor
   *
   * @param errorMessage error message
   */
  public InvalidInputException(String errorMessage) {
    super(errorMessage);
  }
}
