package spelling;

import java.util.*;
import java.util.stream.Collectors;

/**
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 *
 * @author You
 */
public class AutoCompleteDictionaryTrie implements Dictionary, AutoComplete {

    private TrieNode root;
    private int size;


    public AutoCompleteDictionaryTrie() {
        root = new TrieNode();
    }


    /**
     * Insert a word into the trie.
     * For the basic part of the assignment (part 2), you should ignore the word's case.
     * That is, you should convert the string to all lower case as you insert it.
     */
    public boolean addWord(String word) {
        // Implement this method.
        if (isWord(word)) {
            return false;
        }
        if (word != null) {
            String w = word.toLowerCase();
            TrieNode current = root;
            int length = w.length();
            for (int i = 0; i < length; i++) {
                char c = w.charAt(i);
                if (!current.getValidNextCharacters().contains(c)) {
                    current = current.insert(c);
                } else {
                    current = current.getChild(c);
                }
                if (current.getText().equals(w) && !current.endsWord()) {
                    current.setEndsWord(true);
                }
            }
        }
        return true;
    }

    private int size(TrieNode node, int acc) {
        if (node == null) {
            return acc;
        }
        int result = acc;
        if (node.endsWord()) {
            result += 1;
        }
        for (Character c : node.getValidNextCharacters()) {
            TrieNode child = node.getChild(c);
            result += size(child, 0);
        }
        return result;
    }

    /**
     * Return the number of words in the dictionary.  This is NOT necessarily the same
     * as the number of TrieNodes in the trie.
     */
    public int size() {
        return size(root, 0);
    }

    private TrieNode find(String s) {
        // Implement this method
        if (s == null || root == null) {
            return null;
        }
        String w = s.toLowerCase();
        TrieNode current = root;
        for (int i = 0; i < w.length(); i++) {
            char c = w.charAt(i);
            if (current != null && current.getValidNextCharacters().contains(c)) {
                current = current.getChild(c);
            }
        }
        return current != null && current.getText().equalsIgnoreCase(w) ? current : null;
    }

    /**
     * Returns whether the string is a word in the trie
     */
    @Override
    public boolean isWord(String s) {
        // Implement this method
        TrieNode found = find(s);
        return found != null && found.endsWord();
    }

    /**
     * * Returns up to the n "best" predictions, including the word itself,
     * in terms of length
     * If this string is not in the trie, it returns null.
     *
     * @param prefix         The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to n best predictions
     */
    @Override
    public List<String> predictCompletions(String prefix, int numCompletions) {
        // TODO: Implement this method
        // This method should implement the following algorithm:
        // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
        //    empty list
        List<String> result = new ArrayList<>();
        TrieNode stemNode = find(prefix);
        if (stemNode == null) {
            return result;
        }
        // 2. Once the stem is found, perform a breadth first search to generate completions
        //    using the following algorithm:
        //    Create a queue (LinkedList) and add the node that completes the stem to the back
        //       of the list.
        LinkedList<TrieNode> q = new LinkedList<>();
        q.add(stemNode);
        // Create a list of completions to return (initially empty)
        // While the queue is not empty and you don't have enough completions:
        while (!q.isEmpty() && result.size() < numCompletions) {
            TrieNode current = q.poll();
            // remove the first Node from the queue
            // If it is a word, add it to the completions list
            if (current.endsWord()) {
                result.add(current.getText());
            }
            // Add all of its child nodes to the back of the queue
            q.addAll(current.getValidNextCharacters().stream().map(current::getChild).collect(Collectors.toList()));
        }
        // Return the list of completions
        return result;
    }

    // For debugging
    public void printTree() {
        printNode(root);
    }

    /**
     * Do a pre-order traversal from this node down
     */
    public void printNode(TrieNode curr) {
        if (curr == null)
            return;

        System.out.println(curr.getText());

        TrieNode next = null;
        for (Character c : curr.getValidNextCharacters()) {
            next = curr.getChild(c);
            printNode(next);
        }
    }


}