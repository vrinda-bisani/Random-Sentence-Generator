import static org.junit.jupiter.api.Assertions.assertEquals;

import Generator.EmptyDirectoryException;
import Generator.UserInterface;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserInterfaceTest {

  String testDirectory;

  String grammar = "1";
  String yes = "y";
  String no = "n";
  String quit = "q";
  String invalidInput = "a";
  String helpNote = "Note: If you want to generate a random sentence, you can enter any number written before grammar name or press 'q' to quit from program.";
  String displayGrammar = "The following grammars are available:\n"
      + "1. Test Generator\n\n"
      + helpNote
      + "\nWhich would you like to use? (q to quit)\n";
  String useSameGrammar = "\nWould you like another? (enter 'y' for yes or 'n' for no)\n";
  String exit = "Exiting...\n";
  String invalidStatement = "Invalid input\n";

  String testSentence;
  UserInterface ui;

  @BeforeEach
  void setUp() {
    testDirectory = System.getProperty("user.dir") + "/src/test/resources/TestDirectory";
    testSentence = "The test works fine\n";
  }

  @Test
  void generateSentence()
      throws EmptyDirectoryException {
    String outputText = getCLIOutput(grammar+"\n"+no+"\n"+quit+"\n");

    assertEquals(displayGrammar + testSentence + useSameGrammar + displayGrammar + exit, outputText);
  }

  @Test
  void generateSentenceInvalidGrammarInput1()
      throws EmptyDirectoryException{
    String outputText = getCLIOutput(invalidInput +"\n" + quit.toUpperCase()+ "\n");
    assertEquals(displayGrammar+invalidStatement+displayGrammar+exit, outputText);
  }

  @Test
  void generateSentenceInvalidGrammarInput2()
      throws EmptyDirectoryException {
    String outputText = getCLIOutput("2\n" + quit+ "\n");
    assertEquals(displayGrammar+invalidStatement+displayGrammar+exit, outputText);
  }

  @Test
  void generateSentenceInvalidDoAgainInput()
      throws EmptyDirectoryException{
    String outputText = getCLIOutput(grammar+"\n"+invalidInput+"\n"+quit+"\n");
    assertEquals(displayGrammar+testSentence+useSameGrammar+invalidStatement+displayGrammar+exit, outputText);
  }

  @Test
  void generateSentenceValidDoAgainInput()
      throws EmptyDirectoryException{
    String outputText = getCLIOutput(grammar+"\n"+yes+"\n"+no+"\n"+quit+"\n");
    assertEquals(displayGrammar+testSentence+useSameGrammar+testSentence+useSameGrammar+displayGrammar+exit, outputText);
  }


  String getCLIOutput(String input) throws EmptyDirectoryException {
    InputStream stdin = System.in;
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(byteArrayOutputStream);
    PrintStream stdout = System.out;
    System.setOut(ps);
    ui = new UserInterface(System.in, System.out);
    ui.loadGrammars(new File(testDirectory));
    ui.generateSentence();
    System.setIn(stdin);
    System.setOut(stdout);
    return byteArrayOutputStream.toString();
  }





}