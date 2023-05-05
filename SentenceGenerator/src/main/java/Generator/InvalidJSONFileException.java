package Generator;

/**
 * Generator.InvalidJSONFileException is thrown when a file is invalid according to given conditions:
 * if json file is not valid as conditions like, file extension is not json, file does not exist, is not a file or can not be read
 * Generator.InvalidJSONFileException is a custom RuntimeException.
 *
 * @author vrindabisani
 */
public class InvalidJSONFileException extends RuntimeException {

  /**
   * Constructor of Generator.InvalidJSONFileException
   *
   * @param errorMessage error message specified while throwing exception
   */
  public InvalidJSONFileException(String errorMessage) {
    super(errorMessage);
  }
}
