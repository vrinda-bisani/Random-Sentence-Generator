package Generator;

import java.io.File;

/**
 * Class to parse directory path from command line
 *
 * @author vrindabisani
 */
public class CommandLineParser {
  private UserInterface userInterface;

  /**
   * Constructor
   */
  public CommandLineParser() {
    this.userInterface = new UserInterface(System.in, System.out);
  }

  /**
   * Initiates the program
   *
   * @param directoryPath contains path to grammar directory
   * @throws EmptyDirectoryException if directory has no json files
   * @throws DirectoryDoesNotExistException if directory does not exist
   */
  public void start(String directoryPath)
      throws EmptyDirectoryException, DirectoryDoesNotExistException {

    final File grammarDirectory = new File(directoryPath);
    if(!grammarDirectory.exists()) {
      throw new DirectoryDoesNotExistException("Directory does not exist");
    }
    System.out.println("Loading grammars...");
    this.userInterface.loadGrammars(grammarDirectory);
    this.userInterface.generateSentence();
  }
}

