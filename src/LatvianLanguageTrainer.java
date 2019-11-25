import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Stream;

public class LatvianLanguageTrainer {
    private Map<String, String> latv2Rus;
    private Object[] keys;
    private Object[] values;
    private String theWord;
    private String[] arrayOfTranslations;
    private String trueTranslation;
    public final static int numberOfTranslations = 4;


    public LatvianLanguageTrainer(int numberOfTranslations) {
        latv2Rus = new HashMap<>();
        arrayOfTranslations = new String[numberOfTranslations];
    }

    protected void loadVocabularyFromClassLoader() {
        // check if file exists and available for reading
        // stream through the file, fill the Map
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream vocabularyStr = loader.getResourceAsStream("vocabulary.txt");
        if (vocabularyStr == null)
            System.out.println("File with vocabulary not found");
        try (Stream<String> stream = new BufferedReader(new InputStreamReader(vocabularyStr)).lines()) {
            stream.forEach(line -> fillLat2RusMap(line));
        } catch (Exception e) {
            e.printStackTrace();
        }
        keys = latv2Rus.keySet().toArray();
        values = latv2Rus.values().toArray();
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

    public String[] getArrayOfTranslations() {
        return arrayOfTranslations;
    }

    public String getTrueTranslation() {
        return trueTranslation;
    }

    public void challenge() {
        Random generator = new Random();
        Object randomKey = keys[generator.nextInt(keys.length)];
        theWord = randomKey.toString();
        trueTranslation = latv2Rus.get(randomKey.toString());

        // create array for random translations
        Set<String> options = new HashSet<>();
        options.add(trueTranslation);
        while (options.size() < numberOfTranslations) {
            options.add(values[generator.nextInt(values.length)].toString());
        }
        arrayOfTranslations = options.toArray(new String[options.size()]);


    }

    public String getTranslation(int i) {
        return arrayOfTranslations[i];
    }
}
