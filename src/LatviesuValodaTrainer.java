import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Stream;

public class LatviesuValodaTrainer {
    private Map<String, String> latv2Rus;
    private Object[] keys;
    private Object[] values;
    private String theWord;
    private String translation1;
    private String translation2;
    private String translation3;
    private String translation4;
    private String trueTranslation;

    public LatviesuValodaTrainer(InputStream vocabularyStr) {
        latv2Rus = new HashMap<>();
        // check if file exists and available for reading
        // stream through the file, fill the Map
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

    public String getTranslation1() {
        return translation1;
    }

    public String getTranslation2() {
        return translation2;
    }

    public String getTranslation3() {
        return translation3;
    }

    public String getTranslation4() {
        return translation4;
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
        while (options.size() < 4) {
            options.add(values[generator.nextInt(values.length)].toString());
        }
        Object[] arrayOfOptions = options.toArray();

        translation1 = arrayOfOptions[0].toString();
        translation2 = arrayOfOptions[1].toString();
        translation3 = arrayOfOptions[2].toString();
        translation4 = arrayOfOptions[3].toString();

    }
}
