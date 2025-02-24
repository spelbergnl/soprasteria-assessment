package nl.spelberg.soprasteria.assessment;

import java.util.List;

public interface WordFrequencyAnalyzer {

    /**
     * CalculateHighestFrequency should return the highest frequency in the text (several
     * words might have this frequency).
     *
     * @param text the text to analyze
     * @return the highest frequency in the text
     */
    int calculateHighestFrequency(String text);

    /**
     * CalculateFrequencyForWord should return the frequency of the specified word.
     *
     * @param text the text to analyze
     * @param word the word to count
     * @return the frequency of the specified word
     */
    int calculateFrequencyForWord(String text, String word);

    /**
     * CalculateMostFrequentNWords should return a list of the most frequent „n‟ words in
     * the input text, all the words returned in lower case. If several words have the same
     * frequency, this method should return them in ascendant alphabetical order (for input
     * text “The sun shines over the lake” and n = 3, it should return the list {(“the”, 2),
     * (“lake”, 1), (“over”, 1) }
     *
     * @param text the text to analyze
     * @param n    the number of words to return
     * @return a list of the most frequent „n‟ words
     */
    List<WordFrequency> calculateMostFrequentNWords(String text, int n);
}