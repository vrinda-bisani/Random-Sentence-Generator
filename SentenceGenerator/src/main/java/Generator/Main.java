package Generator;

/**
 * Generator.Main class
 *
 * @author vrindabisani
 */
public class Main {

  /**
   * Generator.Main method
   *
   * @param args path to grammar directory
   * @throws InvalidInputException if user does not input directory path
   * @throws EmptyDirectoryException if user gives directory path which does not have grammar files
   * @throws DirectoryDoesNotExistException if directory does not exist
   */
  public static void main(String[] args)
      throws InvalidInputException, EmptyDirectoryException, DirectoryDoesNotExistException {
    if(args.length == 0){
      throw new InvalidInputException("Directory path not given");
    }
    CommandLineParser commandLineParser = new CommandLineParser();
    commandLineParser.start(args[0]);
  }
}
