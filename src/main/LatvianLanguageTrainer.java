import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.logging.*;
import java.util.stream.Stream;

public class LatvianLanguageTrainer {
    public static int numberOfTranslations;

    private Map<String, String> latv2Rus;
    private List<String> latv2RusKeys; // we will delete words (which user knows) from here
    private List<String> latv2RusValues; // we will pick translation variants from here
    private Queue<String> queueOfWrongAnswers;
    private String theWord;
    private List<String> listOfTranslations;
    private String trueTranslation;
    private boolean chooseFromQueueOfWrongAnswers = false;


    private static final Logger LOGGER = Logger.getLogger(LatvianLanguageTrainer.class.getName());


    public LatvianLanguageTrainer(int number) {
        LOGGER.setLevel(Level.INFO);
        numberOfTranslations = number;
        latv2Rus = new HashMap<>();
        listOfTranslations = new ArrayList<>();
        queueOfWrongAnswers = new LinkedList<>();
        latv2RusKeys = new LinkedList();
        latv2RusValues = new LinkedList();
        Handler consoleHandler = new ConsoleHandler();
        try {
            Handler fileHandler = new FileHandler("./latvianLanguageTrainer.log");
            //Assigning handlers to LOGGER object
            LOGGER.addHandler(consoleHandler);
            LOGGER.addHandler(fileHandler);

            //Setting levels to handlers and LOGGER
            consoleHandler.setLevel(Level.ALL);
            fileHandler.setLevel(Level.ALL);
            LOGGER.setLevel(Level.ALL);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    protected void loadVocabularyFromClassLoader(String vocabularyTxtString) {
        // check if file exists and available for reading
        // stream through the file, fill the Map
        ClassLoader loader = LatvianLanguageTrainer.class.getClassLoader();
        InputStream vocabularyStr = loader.getResourceAsStream(vocabularyTxtString);
        if (vocabularyStr == null) {
            LOGGER.info("File with vocabulary not found");
        }
        try (Stream<String> stream = new BufferedReader(new InputStreamReader(vocabularyStr)).lines()) {
            stream.forEach(line -> fillLat2RusMap(line));
        } catch (Exception e) {
            e.printStackTrace();
        }
        latv2RusKeys = new LinkedList<>(latv2Rus.keySet());
        latv2RusValues = new LinkedList<>(latv2Rus.values());
    }

    private void fillLat2RusMap(String line) {
        if (line.contains(";")) {
            String[] arrayFromStr = line.split("\\;");
            latv2Rus.put(arrayFromStr[0], arrayFromStr[1]);
        }
    }

    public String getTheWord() {
        return theWord;
    }

    public List<String> getListOfTranslations() {
        return listOfTranslations;
    }


    public String getTrueTranslation() {
        return trueTranslation;
    }

    public void challenge() {
        System.out.println();
        System.out.println("queueOfWrongAnswers.size() " + queueOfWrongAnswers.size());
        System.out.println("latv2RusKeys.size() " + latv2RusKeys.size());

        if (queueOfWrongAnswers.size() == 0 && latv2RusKeys.size() == 0) {
            theWord = "";
            return;
        }
        Random generatorKeys = new Random();
        if (queueOfWrongAnswers.size() != 0 && (chooseFromQueueOfWrongAnswers || latv2RusKeys.size() == 0)) {
            chooseFromQueueOfWrongAnswers = false;
            theWord = queueOfWrongAnswers.poll();
            System.out.println("word from queue");
        } else {
            theWord = latv2RusKeys.get(generatorKeys.nextInt(latv2RusKeys.size())).toString();
            System.out.println("word from list");
            if (queueOfWrongAnswers.size() > 0)
                chooseFromQueueOfWrongAnswers = true;
        }
        trueTranslation = latv2Rus.get(theWord);
        // create array for random translations
        Set<String> options = new HashSet<>();
        options.add(trueTranslation);
        while (options.size() < numberOfTranslations) {
            options.add(latv2RusValues.get(generatorKeys.nextInt(latv2RusValues.size())).toString());
        }
        listOfTranslations = new ArrayList<>(options);
    }

    public boolean translationIsRight(String usersChoiceOfTranslation) {
        if (this.getTrueTranslation().equals(usersChoiceOfTranslation)) {
            // delete word from structures
            latv2RusKeys.remove(this.theWord);
            return true;
        }
        queueOfWrongAnswers.add(this.theWord);
        return false;
    }

    public List<String> getLatv2RusKeys() {
        return latv2RusKeys;
    }

    public List<String> getLatv2RusValues() {
        return latv2RusValues;
    }
}
