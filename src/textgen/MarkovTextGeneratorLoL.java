package textgen;

import javax.smartcardio.TerminalFactory;
import java.util.*;
import java.util.regex.Pattern;

/**
 * An implementation of the MTG interface that uses a list of lists.
 *
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

    // The list of words with their next words
    private List<ListNode> wordList;

    // The starting "word"
    private String starter;

    // The random number generator
    private Random rnGenerator;

    public MarkovTextGeneratorLoL(Random generator) {
        wordList = new LinkedList<>();
        starter = "";
        rnGenerator = generator;
    }


    /**
     * Train the generator by adding the sourceText
     */
    @Override
    public void train(String sourceText) {
        // set starter to be an empty String ""
        starter = "";
        // for each word w in the source text
        // check to see if "starter" is already a node in the list
        if (sourceText == null || sourceText.trim().isEmpty()) {
            return;
        }
        List<String> words = Arrays.asList(sourceText.split("\\s+"));
        ListNode current = null;
        for (String w : words) {
            current = findWordNode(w);
            if (current != null) {
                // if "starter" is a node in the list
                // add w as a nextWord to the "starter" node
                current.addNextWord(w);
            } else {
                // else
                // add a node to the list with "starter" as the node's word
                // add w as a nextWord to the "starter" node
                current = new ListNode(starter);
                current.addNextWord(w);
                wordList.add(current);
            }
            // set starter = w
            starter = w;
        }
        // add "" to be a next word for the last word in the source text.
        if (current != null) {
            current.addNextWord("");
        }
    }

    /**
     * Generate the number of words requested.
     */
    @Override
    public String generateText(int numWords) {
        // set "output" to be ""
        String output = "";
        int count = 0;
        // set "starter" to be an empty String ""
        starter = "";
        // while you need more words
        while (count < numWords) {
            //  find the "node" corresponding to "starter" in the list
            ListNode node = findWordNode(starter);
            //  if that node does not exist
            //      find the "node" corresponding to the empty String ""
            if (node == null) {
                node = findWordNode("");
            }
            //  select a random word from the "wordList" for "node"
            String next = node != null? node.getRandomNextWord(rnGenerator): "";
            //  add the random word to the "output"
            output += (" " + next);
            //  set "starter" to the random word
            starter = next;
            //  increment number of words added to the list
            count++;
        }
        return output.trim();
    }


    // Can be helpful for debugging
    @Override
    public String toString() {
        String toReturn = "";
        for (ListNode n : wordList) {
            toReturn += n.toString();
        }
        return toReturn;
    }

    /**
     * Retrain the generator from scratch on the source text
     */
    @Override
    public void retrain(String sourceText) {
        wordList = new LinkedList<>();
        train(sourceText);
    }

    // TODO: Add any private helper methods you need here.
    private ListNode findWordNode(String word) {
        for (ListNode listNode : wordList) {
            if (listNode.getWord().equals(word)) {
                return listNode;
            }
        }
        return null;
    }


    /**
     * This is a minimal set of tests.  Note that it can be difficult
     * to test methods/classes with randomized behavior.
     *
     * @param args
     */
    public static void main(String[] args) {
        // feed the generator a fixed random value for repeatable behavior
        MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
        String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
        System.out.println(textString);
        gen.train(textString);
        System.out.println(gen);
        System.out.println(gen.generateText(20));
        String textString2 = "You say yes, I say no, " +
                "You say stop, and I say go, go, go, " +
                "Oh no. You say goodbye and I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello. " +
                "I say high, you say low, " +
                "You say why, and I say I don't know. " +
                "Oh no. " +
                "You say goodbye and I say hello, hello, hello. " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello. " +
                "Why, why, why, why, why, why, " +
                "Do you say goodbye. " +
                "Oh no. " +
                "You say goodbye and I say hello, hello, hello. " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello. " +
                "You say yes, I say no, " +
                "You say stop and I say go, go, go. " +
                "Oh, oh no. " +
                "You say goodbye and I say hello, hello, hello. " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello, hello, hello,";
        System.out.println(textString2);
        gen.retrain(textString2);
        System.out.println(gen);
        System.out.println(gen.generateText(20));
    }

}

/**
 * Links a word to the next words in the list
 * You should use this class in your implementation.
 */
class ListNode {
    // The word that is linking to the next words
    private String word;

    // The next words that could follow it
    private List<String> nextWords;

    ListNode(String word) {
        this.word = word;
        nextWords = new LinkedList<>();
    }

    public String getWord() {
        return word;
    }

    public void addNextWord(String nextWord) {
        nextWords.add(nextWord);
    }

    public String getRandomNextWord(Random generator) {
        // The random number generator should be passed from
        // the MarkovTextGeneratorLoL class
        int index = generator.nextInt(nextWords.size());
        return nextWords.get(index);
    }

    public String toString() {
        String toReturn = word + ": ";
        for (String s : nextWords) {
            toReturn += s + "->";
        }
        toReturn += "\n";
        return toReturn;
    }

}


