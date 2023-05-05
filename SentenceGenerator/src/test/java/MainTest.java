import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import Generator.DirectoryDoesNotExistException;
import Generator.InvalidInputException;
import Generator.Main;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MainTest {
  String[] args;
  String helpNote = "Note: If you want to generate a random sentence, you can enter any number written before grammar name or press 'q' to quit from program.";
  String displayGrammar = "The following grammars are available:\n"
      + "1. Test Generator\n\n"
      + helpNote
      + "\nWhich would you like to use? (q to quit)\n";
  String exit = "Exiting...\n";
  String testDirectory;

  @BeforeEach
  void setUp(){
    args = new String[]{};
    testDirectory = System.getProperty("user.dir") + "/src/test/resources/TestDirectory";
  }

  @Test
  void mainNoArgsTest(){
    assertThrows(InvalidInputException.class, ()-> Main.main(args));
  }

  @Test
  void mainArgsTest(){
    args = new String[]{"noSuchDirectory"};
    assertThrows(DirectoryDoesNotExistException.class, ()-> Main.main(args));
  }

  @Test
  void fromMain() {
    InputStream stdin = System.in;
    System.setIn(new ByteArrayInputStream(("q\n").getBytes()));
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(byteArrayOutputStream);
    PrintStream stdout = System.out;
    System.setOut(ps);
    Main.main(new String[]{testDirectory});
    System.setIn(stdin);
    System.setOut(stdout);
    String outputText = byteArrayOutputStream.toString();
    assertEquals("Loading grammars...\n" + displayGrammar+exit, outputText);
  }

}