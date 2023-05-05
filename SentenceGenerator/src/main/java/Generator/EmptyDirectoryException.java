package Generator;

/**
 * throws exception if directory is empty or does not have json grammar files
 *
 *  @author vrindabisani
 */
public class EmptyDirectoryException extends RuntimeException {

  /**
   * constructor
   *
   * @param errorMessage error message
   */
  public EmptyDirectoryException(String errorMessage) {
    super(errorMessage);
  }
}
