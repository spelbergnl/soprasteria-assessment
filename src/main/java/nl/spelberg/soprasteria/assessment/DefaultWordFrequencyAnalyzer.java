package nl.spelberg.soprasteria.assessment;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DefaultWordFrequencyAnalyzer implements WordFrequencyAnalyzer {

    public static final Pattern WORD_PATTERN = Pattern.compile("[^a-zA-Z]");

    @Override
    public int calculateHighestFrequency(String text) {
        return wordFrequencyStream(text).max(WordFrequencyRecord.byFrequency())
                .map(WordFrequency::getFrequency)
                .orElse(0);
    }

    @Override
    public int calculateFrequencyForWord(String text, String word) {
        return 0;
    }

    @Override
    public List<WordFrequency> calculateMostFrequentNWords(String text, int n) {
        return List.of();
    }

    private static Stream<WordFrequency> wordFrequencyStream(String text) {
        return wordFrequencyStream(countWordFrequencies(text));
    }

    private static Stream<WordFrequency> wordFrequencyStream(Map<String, Long> wordFrequencies) {
        return wordFrequencies.entrySet()
                .stream()
                .map(e -> new WordFrequencyRecord(e.getKey(), e.getValue().intValue()));
    }

    private static Map<String, Long> countWordFrequencies(String text) {
        return Stream.of(WORD_PATTERN.split(text))
                .filter(Predicate.not(String::isBlank))
                .map(String::toLowerCase)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    record WordFrequencyRecord(String word, int frequency) implements WordFrequency {
        @Override
        public String getWord() {
            return word();
        }

        @Override
        public int getFrequency() {
            return frequency();
        }

        public static Comparator<WordFrequency> byFrequency() {
            return Comparator.comparing(WordFrequency::getFrequency).thenComparing(WordFrequency::getWord);
        }

        public static Comparator<WordFrequency> byWord() {
            return Comparator.comparing(WordFrequency::getWord);
        }
    }
}
