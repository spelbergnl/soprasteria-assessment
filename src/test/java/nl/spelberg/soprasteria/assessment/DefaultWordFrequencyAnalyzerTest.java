package nl.spelberg.soprasteria.assessment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

class DefaultWordFrequencyAnalyzerTest {

    private WordFrequencyAnalyzer analyzer;

    @BeforeEach
    void setUp() {
        analyzer = new DefaultWordFrequencyAnalyzer();
    }

    @ParameterizedTest
    @CsvSource({
            ",0",
            "Hi,1",
            "Hi There,1",
            "Hi Hi There,2",
            "Hi There There,2",
            "Hi Hi There There,2",
            "Hi There Hi There,2",
            "Hi There How are you? You are you is it not?,3"
    })
    void calculateHighestFrequency(String text, int expected) {
        assertThat(analyzer.calculateHighestFrequency(text)).isEqualTo(expected);
    }

    @Test
    void calculateFrequencyForWord() {
        fail();
    }

    @Test
    void calculateMostFrequentNWords() {
        fail();
    }
}