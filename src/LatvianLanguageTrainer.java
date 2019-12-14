import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Stream;

public class LatvianLanguageTrainer {
    private Map<String, String> latv2Rus;
    private List<String> latv2RusKeys; // we will delete words (which user knows) from here
    private List<String> latv2RusValues; // we will pick translation variants from here
    private Queue<String> queueOfWrongAnswers;
    private String theWord;
    private List<String> listOfTranslations;
    private String trueTranslation;
    public final static int numberOfTranslations = 4;
    public boolean chooseFromQueueOfWrongAnswers = false;


    public LatvianLanguageTrainer() {
        latv2Rus = new HashMap<>();
        listOfTranslations = new ArrayList<>();
        queueOfWrongAnswers = new LinkedList<>();
        latv2RusKeys = new LinkedList();
        latv2RusValues = new LinkedList();
    }


    protected void loadVocabularyFromClassLoader() {
        // check if file exists and available for reading
        // stream through the file, fill the Map
        ClassLoader loader = LatvianLanguageTrainer.class.getClassLoader();
        InputStream vocabularyStr = loader.getResourceAsStream("vocabulary.txt");
        if (vocabularyStr == null)
            System.out.println("File with vocabulary not found");
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
        Random generator = new Random();
        Object randomKey = latv2RusKeys.get(generator.nextInt(latv2RusKeys.size()));

        if (queueOfWrongAnswers.size() != 0 && chooseFromQueueOfWrongAnswers) {
            chooseFromQueueOfWrongAnswers = false;
            theWord = queueOfWrongAnswers.poll();
        } else {
            chooseFromQueueOfWrongAnswers = true;
            theWord = randomKey.toString();
        }
        trueTranslation = latv2Rus.get(theWord);
        // create array for random translations
        Set<String> options = new HashSet<>();
        options.add(trueTranslation);
        while (options.size() < numberOfTranslations) {
            options.add(latv2RusValues.get(generator.nextInt(latv2RusValues.size())).toString());
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

}
