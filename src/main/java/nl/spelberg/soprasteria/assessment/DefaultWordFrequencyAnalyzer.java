package nl.spelberg.soprasteria.assessment;

import java.util.List;

public class DefaultWordFrequencyAnalyzer implements WordFrequencyAnalyzer {
    @Override
    public int calculateHighestFrequency(String text) {
        return 0;
    }

    @Override
    public int calculateFrequencyForWord(String text, String word) {
        return 0;
    }

    @Override
    public List<WordFrequency> calculateMostFrequentNWords(String text, int n) {
        return List.of();
    }
}
