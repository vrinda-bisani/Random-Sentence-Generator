# Random Sentence Generator
The “Random Sentence Generator” is a handy and marvelous piece of technology to create random sentences from a pattern known as a grammar. 
A grammar is a template that describes the various combinations of words that can be used to form valid sentences.
### What is a grammar ?
- A grammar is just a set of rules for some language, be it English, a programming language, or an
  invented language. For now, we will introduce to you a particular kind of grammar called a
  Context Free Generator.Grammar (CFG). Here is an example of a simple grammar:
```html
{
  "grammarTitle": "Poem Generator",
  "grammarDesc": "A grammar that generates poems. ",
  "start":[
    "The <object> <verb> tonight."
  ],
  "object": [
    "waves",
    "big yellow flowers",
    "slugs"
  ],
  "verb": [
    "sigh <adverb>",
    "portend like <object>",
    "die <adverb>"
  ],
  "adverb": [
    "warily",
    "grumpily"
  ]
}
```

## Interesting randomly generated sentence
```html
"The big yellow flowers sigh warily tonight."
"The slugs portend like waves tonight."
"May an army of slavering wolverines find your buttocks suddenly delectable."
"With the force of Thor's belch, may the hosts of Hades retch in the oil refinery you call home."
```

## How to run this program in CLI / Intellij terminal
### Run program with Intellij UI
- If you are using intellij, go to menu ```Run > Edit configurations``` menu setting.
- A dialog box will appear as shown below.
- Now add arguments to the ```Program arguments``` input under build and run section.
![img.png](run-configuration.png)

### Run program with gradle run task
- Open terminal/cli and navigate to root project folder "Assignment3"
- Run the gradle run task with arguments ```gradle --console=plain run --args='<directory-path>'``` and program will start running successfully.
>Note : directory path of grammar json files should be provided in args

For reference, I have already added three grammar json files in src/main/resources directory.
```shell
gradle --console=plain run --args='src/main/resources'
```

Result after running above-mentioned command in Intellij terminal:
```html
Generator.SentenceGenerator% gradle --console=plain run --args='src/main/resources'

> Configure project :
all done!

> Task :compileJava
> Task :processResources
> Task :classes

> Task :run
Loading grammars...
The following grammars are available:
1. Insult Generator
2. Poem Generator
3. Term Paper Generator

Note: If you want to generate a random sentence, you can enter any number written before grammar name or press 'q' to quit from program.
Which would you like to use? (q to quit)
1
May a swarm of petrified, biting pit-fiends dance upon your heinie.

Would you like another? (enter 'y' for yes or 'n' for no)
y
You are so unkempt that even a cretin would not want to caress you.

Would you like another? (enter 'y' for yes or 'n' for no)
n
The following grammars are available:
1. Insult Generator
2. Poem Generator
3. Term Paper Generator

Note: If you want to generate a random sentence, you can enter any number written before grammar name or press 'q' to quit from program.
Which would you like to use? (q to quit)
q
Exiting...

Deprecated Gradle features were used in this build, making it incompatible with Gradle 8.0.

You can use '--warning-mode all' to show the individual deprecation warnings and determine if they come from your own scripts or plugins.

See https://docs.gradle.org/7.5.1/userguide/command_line_interface.html#sec:command_line_warnings

BUILD SUCCESSFUL in 26s
3 actionable tasks: 3 executed
```
#### Changes made in build.gradle
There are some changes in build.gradle file.
1. Using the application plugin
   - The Application plugin facilitates creating an executable JVM application
   - changes made in build.gradle
    ```
    plugin {
    ....
        // https://docs.gradle.org/current/userguide/application_plugin.html
        id 'application'
    }
    ```

2. Configuring the application main class
   - To run the application by executing the run task (type: JavaExec). 
   - This will compile the main source set, and launch a new JVM with its classes (along with all runtime dependencies) as the classpath and using the specified main class.
   - changes made in build.gradle
     ```
     application {
         mainClassName 'Generator.Main'
     }
     ```
   - the command line arguments can be passed with --args.
   - For example, if you want to launch the application with command line arguments src/main/resources, you can use gradle run --args="src/main/resources"

3. As mentioned in [gradle doc](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html)
    - By default, inputStream is set to an empty Stream
    - changes made in build.gradle
    ```
    run {
        standardInput = System.in
    }
    ```

## Entry point of this program
Entry point of this program is [Generator.Main.java](src/main/java/Generator.Main.java)

## High-level description about classes and key methods
1. `Generator.Main.java`
   - This class is an entry point of this program.
     - This class contains `main()` method. 
       - To run this program successfully, you need to provide directory path of grammar files in args of main() method.

2. `Generator.CommandLineParser.java`
   - This class is used to parse argument provided in main method and start the program.
   - This class contains `start(String directoryPath)` method which is responsible for initiating the program. 
     - If directoryPath provided in parameter of method is invalid, then throws an exception `Generator.DirectoryDoesNotExistException`

3. `Generator.UserInterface.java`
   - This class is used for user interaction with program and process user input to generate random sentence
   - This class contains two key methods: loadGrammars(File grammarDirectory) and generateSentence()
   - `loadGrammars(File grammarDirectory)`
     - This method is responsible for loading grammar json files from provided grammarDirectory file
     - This method takes one input of valid grammarDirectory file, represented as File
     - This method throws Generator.EmptyDirectoryException in case of grammarDirectory path provided does not have any grammar json files.
   - `generateSentence()`
     - This method generates random sentence according to input provided by user.
     
4. `Generator.Grammar.java`
   - This class represents the grammar json file with information like grammar title, grammar desc, and grammar definitions and its one or more productions.
   - Constructor of Generator.Grammar takes three parameters, `String grammarTitle, String grammarDesc, Map<String, List<String>> grammarDefinition`
   - `String getGrammarTitle()`
     - This method returns grammar title of grammar, represented as String
   - `String getGrammarDesc()`
     - This method returns grammar description of grammar, represented as String
   - `List<String> getProductions(String element)`
     - This method provides list of productions of given non-terminal element from grammarDefinition
   - There are three overrides methods also implemented
     - boolean equals(Object obj), int hashcode(), String toString()
     
5. `Generator.SentenceGenerator.java`
   - This class used generate random sentence according to grammar provided in constructor
   - Constructor of Generator.SentenceGenerator takes two parameters, `Generator.Grammar grammar, Long randomSeed`
   - `Generator.Grammar getGrammar()`
     - This method returns grammar provided for Generator.SentenceGenerator
   - `public String generateRandomSentence()`
     - This method generates random sentence according to start provided in grammar
   - There are three override methods as well
     - boolean equals(Object obj), int hashcode, String toString()

6. `Generator.JSONFileReader.java`
   - This class is used to read grammar json file at given path
   - `Map<String, JsonElement> readFile(String jsonFileName)`
     - This method is responsible for reading json file and returns a map of String as Terminal/Non-Terminal and value as JsonElement

7. `Generator.JSONFileParser.java`
   - This class parses grammar json map and return grammar instance of Generator.Grammar class
   - Constructor of Generator.JSONFileParser takes one argument, `String jsonFileName` and validates if json file exists and can be read
     - If json file provided is invalid, throws exception `Generator.InvalidJSONFileException`
   - `Generator.Grammar processJsonFile()`
     - This method creates instance of Generator.JSONFileReader and call method readFile of Generator.JSONFileReader
     - This method return object of Generator.Grammar

## Assumptions made about the nature of program
1. While running program, program will take one argument, which is the name of a folder/directory, where all grammars in that directory are loaded/read in.
2. The grammar model will be provided in a JSON file
   - The JSON file will specifically have 2 keys:
     - “start” which will contain a list of non-terminals to start with
     - “grammarTitle” which is a user-readable name for the grammar.
   - The file will optionally include:
     - a “grammarDesc” key, where the value is a user-friendly description of the grammar.
   - All other keys will be a non-terminal, with the values being an array of strings with a production for that non-terminal.
   - We are assuming that the grammar files are syntactically correct (i.e. have a start and grammarTitle definition,
      have the correct punctuation and format as described above, don't have some sort of endless recursive cycle in the expansion, etc.).

## Correctness of the program
1. While testing generateRandomSentence() method of Generator.SentenceGenerator class
   - To ensure that program is doing what we expect it to do, we are providing seed as second parameter to the constructor of Generator.SentenceGenerator.
2. If user is not providing valid directory for grammar files, program will throw Generator.DirectoryDoesNotExistException.
3. If user is not providing any json files in directory, program will throw Generator.EmptyDirectoryException.
4. If user doesn't provide invalid input to generating random sentence or to continue
   - program will show `invalid choice` text and will ask again for correct input.
5. While expanding a non-terminal, if a non-terminal is used but not defined, program will throw Generator.MissingDefinitionException.