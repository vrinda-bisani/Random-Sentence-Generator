package Generator;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class to process user input
 *
 * @author vrindabisani
 */
public class UserInterface {

  /**
   * Constant for q character
   */
  private static final String QUIT = "q";

  /**
   * Constant for y character
   */
  private static final String YES = "y";

  /**
   * Constant for n character
   */
  private static final String NO_INPUT = "n";

  /**
   * Regex for number
   */
  private static final String NUMBER_REGEX = "[0-9]+";
  private static final String JSON_EXTENSION = ".json";
  private static final String HELP_NOTE = "\nNote: If you want to generate a random sentence, you can enter any number written before grammar name or press 'q' to quit from program." ;
  private static final String DOT_SPACE = ". ";

  private Scanner scanner;
  private PrintStream printStream;
  private List<Grammar> grammars;

  private boolean foundError;

  /**
   * Constructs userInterface
   * @param inputStream input stream
   * @param printStream print stream
   */
  public UserInterface(InputStream inputStream, PrintStream printStream) {
    this.scanner = new Scanner(inputStream);
    this.printStream = printStream;
    this.grammars = new ArrayList<>();
    this.foundError = Boolean.FALSE;
  }

  /**
   * Loads grammar files from the directory
   * @param grammarDirectory path to grammar directory
   * @throws EmptyDirectoryException if directory has no json grammar files
   */
  public void loadGrammars(File grammarDirectory) throws EmptyDirectoryException {
    File[] directoryFiles = grammarDirectory.listFiles();

    if(directoryFiles != null) {
      for(File path: directoryFiles) {
        if(path.getAbsolutePath().endsWith(JSON_EXTENSION)) {
          JSONFileParser jsonFileParser = new JSONFileParser(path.getAbsolutePath());
          this.grammars.add(jsonFileParser.processJsonFile());
        }
      }
    }

    if(this.grammars.isEmpty()) {
      throw new EmptyDirectoryException("Generator.Grammar files are not available in directory");
    }
  }

  /**
   * Generates sentence using grammar chosen by the user
   */
  public void generateSentence() {
    while (Boolean.TRUE) {
      this.displayGrammar();
      String userInput = this.getInput();
      if (QUIT.equalsIgnoreCase(userInput)) {
        printStream.println("Exiting...");
        break;
      }
      while(Boolean.TRUE) {
        try{
          String sentenceMade = this.getSentence(userInput);
          if(sentenceMade.isEmpty() || this.foundError) {
            printStream.println("Note: Press 'q' to quit program to fix grammar or choose from other grammar.");
            break;
          }
          else {
            printStream.println(sentenceMade);
          }
          printStream.println("\nWould you like another? (enter 'y' for yes or 'n' for no)");
          String anotherInput = getInput();
          if (this.doAgain(anotherInput)) {
            continue;
          }
          break;
        }
        catch (InvalidInputException e) {
          printStream.println(e.getMessage());
          break;
        }

      }
    }
  }

  /**
   * Uses the same grammar chosen by the user until he wants to change it
   * @return true if user wants to use same grammar else returns false
   * @throws InvalidInputException if invalid input is given by the user
   */
  private boolean doAgain(String userInput) throws InvalidInputException {
    String inputLowerCase = userInput.toLowerCase();
    return switch (inputLowerCase) {
      case YES -> Boolean.TRUE;
      case NO_INPUT -> Boolean.FALSE;
      default -> throw new InvalidInputException("Invalid input");
    };
  }

  /**
   * Generates sentence using the grammar chosen by user
   * @param input grammar chosen by user
   * @return sentence generated using the grammar chosen by user
   * @throws InvalidInputException if invalid input is given by user
   */
  private String getSentence(String input) throws InvalidInputException {
    if(!input.matches(NUMBER_REGEX)) {
        throw new InvalidInputException("Invalid input");
    }
    int grammarChoice = Integer.parseInt(input);
    if(grammarChoice > this.grammars.size()) {
      throw new InvalidInputException("Invalid input");
    }
    SentenceGenerator sentenceGenerator = new SentenceGenerator(this.grammars.get(grammarChoice - 1), null);
    String foundSentence = sentenceGenerator.generateRandomSentence();
    this.foundError = sentenceGenerator.isErrorEncountered();
    return foundSentence;
  }


  /**
   * Displays list of available grammars to user
   */

  private void displayGrammar(){
    int counter = 1;
    this.printStream.println("The following grammars are available:");
    for(Grammar grammar : this.grammars){
      printStream.println(counter + DOT_SPACE + grammar.getGrammarTitle());
      counter++;
    }
    printStream.println(HELP_NOTE + "\nWhich would you like to use? (q to quit)");

  }

  /**
   * Takes user input
   *
   * @return user input
   */
  private String getInput(){
    return scanner.next();
  }

}
