import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;

public class LatvianLanguageTrainerTest {

    @Test
    public void loadVocabularyFromClassLoaderFileNotExistsTest() {
        ClassLoader loader = LatvianLanguageTrainer.class.getClassLoader();
        InputStream vocabularyStr = loader.getResourceAsStream("no_file.txt");
        Assert.assertNull(vocabularyStr);
    }

    @Test
    public void loadVocabularyFromClassLoaderTest() {
        LatvianLanguageTrainer latvianLanguageTrainer = new LatvianLanguageTrainer(4);
        latvianLanguageTrainer.loadVocabularyFromClassLoader("vocabulary_test.txt");
        Assert.assertEquals(latvianLanguageTrainer.getLatv2RusKeys().size(), 5);
        Assert.assertEquals(latvianLanguageTrainer.getLatv2RusValues().size(), 5);

        String[] keys = {"﻿окно", "бабушка", "зеркало", "кровать", "покупатель"};
        Assert.assertArrayEquals(latvianLanguageTrainer.getLatv2RusKeys().toArray(), keys);

        String[] values = {"logs", "vecmāmiņa", "spogulis", "gulta", "pircējs"};
        Assert.assertArrayEquals(latvianLanguageTrainer.getLatv2RusValues().toArray(), values);

        latvianLanguageTrainer.challenge();
        String theWordForTest = latvianLanguageTrainer.getTheWord();
        String trueTranslation = latvianLanguageTrainer.getTrueTranslation();
        int indexOfTestTranslation = latvianLanguageTrainer.getLatv2RusKeys().indexOf(theWordForTest);
        int indexOfTrueTranslation = latvianLanguageTrainer.getLatv2RusValues().indexOf(trueTranslation);
        Assert.assertEquals(indexOfTestTranslation, indexOfTrueTranslation);


    }
}
