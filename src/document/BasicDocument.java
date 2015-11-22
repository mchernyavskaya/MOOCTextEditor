package document;

/**
 * A naive implementation of the Document abstract class.
 *
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class BasicDocument extends Document {
    private final String RX_SENTENCE_SEPARATOR = "[\\.!\\?]+";
    private final String RX_WORDS_SEPARATOR = "[^a-zA-Z]+";

    /**
     * Create a new BasicDocument object
     *
     * @param text The full text of the Document.
     */
    public BasicDocument(String text) {
        super(text);
    }


    /**
     * Get the number of words in the document.
     * "Words" are defined as contiguous strings of alphabetic characters
     * i.e. any upper or lower case characters a-z or A-Z
     *
     * @return The number of words in the document.
     */
    @Override
    public int getNumWords() {
        String text = prepareText();
        return text != null && text.length() > 0 ? text.split(RX_WORDS_SEPARATOR).length : 0;
    }

    /**
     * Get the number of sentences in the document.
     * Sentences are defined as contiguous strings of characters ending in an
     * end of sentence punctuation (. ! or ?) or the last contiguous set of
     * characters in the document, even if they don't end with a punctuation mark.
     *
     * @return The number of sentences in the document.
     */
    @Override
    public int getNumSentences() {
        String text = prepareText();
        return text != null && text.length() > 0 ? text.split(RX_SENTENCE_SEPARATOR).length : 0;
    }

    /**
     * Get the number of syllables in the document.
     * Words are defined as above.  Syllables are defined as:
     * a contiguous sequence of vowels, except for an "e" at the
     * end of a word if the word has another set of contiguous vowels,
     * makes up one syllable.   y is considered a vowel.
     *
     * @return The number of syllables in the document.
     */
    @Override
    public int getNumSyllables() {
        //TODO: Implement this method.  See the Module 1 support videos
        // if you need help.
        String[] words = prepareText().split(RX_WORDS_SEPARATOR);
        // aeiouy
        int count = 0;
        for (String word : words) {
            int numSyllables = countSyllables(word);
            count += numSyllables;
        }
        return count;
    }

    protected String prepareText() {
        String text = getText();
        return text != null ? text.replaceAll("\\d+", " ").toLowerCase() : null;
    }


    /* The main method for testing this class.
     * You are encouraged to add your own tests.  */
    public static void main(String[] args) {
        testCase(new BasicDocument("This is a test.  How many???  "
                        + "Senteeeeeeeeeences are here... there should be 5!  Right?"),
                16, 13, 5);
        testCase(new BasicDocument(""), 0, 0, 0);
        testCase(new BasicDocument("sentence, with, lots, of, commas.!  "
                + "(And some poaren)).  The output is: 7.5."), 15, 11, 4);
        testCase(new BasicDocument("many???  Senteeeeeeeeeences are"), 6, 3, 2);
        testCase(new BasicDocument("This is a sentence."), 5, 4, 1);
        testCase(new BasicDocument("These are some sentences. These are some sentences. These are some sentences."),
                18, 12, 3);
        testCase(new BasicDocument("This is test number threeeeeeeeeeeeeeeeeeeeeeeeee. I can do that with oooooooooooooooooooooother letters tooooooooooo"),
                16, 13, 2);
        testCase(new BasicDocument("test????? sentence,??????? probably!,        good"),
                7, 4, 4);
        testCase(new BasicDocument("i, challenge, you, to, 2.5, battle, of!? wits"),
                8, 7, 3);
        testCase(new BasicDocument("segue"), 2, 1, 1);

        testCase(new BasicDocument("double"), 1, 1, 1);
    }

}

