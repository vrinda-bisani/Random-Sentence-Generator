package Generator;

/**
 * Throws exception if directory does not exist
 *
 * @author vrindabisani
 */
public class DirectoryDoesNotExistException extends RuntimeException {

  /**
   * constructor
   *
   * @param errorMessage error message
   */
  public DirectoryDoesNotExistException(String errorMessage) {
    super(errorMessage);
  }
}
