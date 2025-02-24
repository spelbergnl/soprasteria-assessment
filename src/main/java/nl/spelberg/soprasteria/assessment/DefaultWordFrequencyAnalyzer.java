package nl.spelberg.soprasteria.assessment;

import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DefaultWordFrequencyAnalyzer implements WordFrequencyAnalyzer {

    public static final Pattern WORD_PATTERN = Pattern.compile("[^a-zA-Z]");

    @Override
    public int calculateHighestFrequency(String text) {
        return countWordFrequencies(text).entrySet()
                .stream()
                .max(Entry.<String, Long>comparingByValue().thenComparing(Entry.comparingByKey()))
                .map(Entry::getValue)
                .orElse(0L)
                .intValue();
    }

    @Override
    public int calculateFrequencyForWord(String text, String word) {
        return 0;
    }

    @Override
    public List<WordFrequency> calculateMostFrequentNWords(String text, int n) {
        return List.of();
    }

    private static SortedMap<String, Long> countWordFrequencies(String text) {
        return Stream.of(WORD_PATTERN.split(text))
                .filter(Predicate.not(String::isBlank))
                .map(String::toLowerCase)
                .collect(Collectors.groupingBy(w -> w, TreeMap::new, Collectors.counting()));
    }
}
