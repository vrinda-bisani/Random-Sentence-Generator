import static org.junit.jupiter.api.Assertions.assertThrows;

import Generator.CommandLineParser;
import Generator.DirectoryDoesNotExistException;
import Generator.EmptyDirectoryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommandLineParserTest {
  String basePath;
  String testDirectory;
  String testEmptyDirectory;
  String noSuchDirectory;
  CommandLineParser commandLineParser;

  @BeforeEach
  void setUp() {
    basePath = System.getProperty("user.dir") + "/src/test/resources/";
    testDirectory = "TestDirectory";
    testEmptyDirectory = "EmptyTestDirectory";
    noSuchDirectory = "NoSuchDirectory";
    commandLineParser =new CommandLineParser();
  }

  @Test
  void directoryDoesNotExistTest() {
    assertThrows(DirectoryDoesNotExistException.class, () ->  commandLineParser.start(noSuchDirectory));
  }

  @Test
  void loadGrammarsEmptyDirectory() {
    assertThrows(EmptyDirectoryException.class, () ->  commandLineParser.start(basePath + testEmptyDirectory));
  }

}