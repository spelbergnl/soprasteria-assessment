package nl.spelberg.soprasteria.assessment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultWordFrequencyAnalyzerTest {

    private DefaultWordFrequencyAnalyzer analyzer;

    @BeforeEach
    void setUp() {
        analyzer = new DefaultWordFrequencyAnalyzer();
    }

    @ParameterizedTest
    @CsvSource({
            "'',0",
            "Hi,1",
            "Hi There,1",
            "Hi Hi There,2",
            "Hi There There,2",
            "Hi Hi There There,2",
            "Hi There Hi There,2",
            "Hi There How are you? You are you is it not?,3"
    })
    void shouldCalculateHighestFrequency(String text, int expected) {
        assertThat(analyzer.calculateHighestFrequency(text)).isEqualTo(expected);
    }

    /**
     * NOTE: Generated using https://www.cursor.com/
     * With the command 'unittest for calculateFrequencyForWord'
     */
    @ParameterizedTest
    @CsvSource({
            "'',hi,0",
            "Hi,hi,1",
            "Hi There,hi,1",
            "Hi Hi There,hi,2",
            "Hi There There,hi,1",
            "Hi Hi There There,hi,2",
            "Hi There Hi There,hi,2",
            "Hi There How are you? You are you is it not?,hi,1",
            "Hi There How are you? You are you is it not?,you,3",
            "Hi There How are you? You are you is it not?,are,2",
            "Hi There How are you? You are you is it not?,there,1"
    })
    void shouldCalculateFrequencyForWord(String text, String word, int expected) {
        assertThat(analyzer.calculateFrequencyForWord(text, word)).isEqualTo(expected);
    }


    @ParameterizedTest(name = "[{index}] text=''{0}'', word=''{1}'', expected={2}")
    @CsvSource(delimiter = '|', value = {
            "                                              |word  |0",
            "Word WORD word WoRd                           |word  |4",
            "word;word/word\\word                          |word  |4",
            "word&word@word#word                           |word  |4",
            "word:word+word=word                           |word  |4",
            "word[word]word{word}                          |word  |4",
// Deze worden niet goed geparsed door JUnit
//            "word    word\tword\n\nword                    |word  |4",
//            "word\r\nword\t\tword                          |word  |3",
            "word...word!!!word???word                     |word  |4",
            "word's word's                                 |word  |2",
            "«word» \"word\" 'word'                        |word  |3",
            "word——word–word—word                          |word  |4",
            "word•word·word°word                           |word  |4",
            "word→word←word↑word                           |word  |4",
            "word\u0020word\u00A0word                      |word  |3",
            "The quick brown fox                           |word  |0",
            "word-word                                     |word  |2",
            "worldwide wordplay                            |word  |0",
            "Hello\u2028HELLO\u2029hello                   |hello |3"
    })
    void shouldCalculateWordFrequency(String text, String searchWord, int expectedFrequency) {
        assertThat(analyzer.calculateFrequencyForWord(text, searchWord)).as("Word frequency for '%s' in text '%s'",
                                                                            searchWord, text)
                .isEqualTo(expectedFrequency);
    }

    @ParameterizedTest
    @NullSource
    void shouldHandleNullText(String text) {
        assertThat(analyzer.calculateFrequencyForWord(text, "word")).as("Word frequency with null text").isZero();
    }

    @ParameterizedTest
    @NullSource
    void shouldHandleNullSearchWord(String searchWord) {
        assertThat(analyzer.calculateFrequencyForWord("some text", searchWord)).as(
                "Word frequency with null search word").isZero();
    }

    @Test
    void shouldHandleBothNullInputs() {
        assertThat(analyzer.calculateFrequencyForWord(null, null)).as("Word frequency with both inputs null").isZero();
    }

    private String readTestFile() throws IOException {
        return new String(getClass().getResourceAsStream("/testdata-chatgpt.txt").readAllBytes(),
                          StandardCharsets.UTF_8);
    }

    @Test
    void shouldCalculateMostFrequentNWordsFromFile() throws IOException {
        String text = readTestFile();

        List<WordFrequency> result = analyzer.calculateMostFrequentNWords(text, 5);

        List<WordFrequency> expected = List.of(new DefaultWordFrequencyAnalyzer.WordFrequencyRecord("een", 41),
                                               new DefaultWordFrequencyAnalyzer.WordFrequencyRecord("ik", 12),
                                               new DefaultWordFrequencyAnalyzer.WordFrequencyRecord("of", 10),
                                               new DefaultWordFrequencyAnalyzer.WordFrequencyRecord("is", 9),
                                               new DefaultWordFrequencyAnalyzer.WordFrequencyRecord("appel", 9));

        assertThat(result).hasSize(5).containsExactlyElementsOf(expected);
    }

    @Test
    void shouldCalculateMostFrequentThreeWordsFromFile() throws IOException {
        String text = readTestFile();

        List<WordFrequency> result = analyzer.calculateMostFrequentNWords(text, 3);

        List<WordFrequency> expected = List.of(new DefaultWordFrequencyAnalyzer.WordFrequencyRecord("een", 41),
                                               new DefaultWordFrequencyAnalyzer.WordFrequencyRecord("ik", 12),
                                               new DefaultWordFrequencyAnalyzer.WordFrequencyRecord("of", 10));

        assertThat(result).hasSize(3).containsExactlyElementsOf(expected);
    }

    @Test
    void shouldCalculateMostFrequentSingleWordFromFile() throws IOException {
        String text = readTestFile();

        List<WordFrequency> result = analyzer.calculateMostFrequentNWords(text, 1);

        List<WordFrequency> expected = List.of(new DefaultWordFrequencyAnalyzer.WordFrequencyRecord("een", 41));

        assertThat(result).hasSize(1).containsExactlyElementsOf(expected);
    }

    @Test
    void shouldCalculateMostFrequentTenWordsFromFile() throws IOException {
        String text = readTestFile();

        List<WordFrequency> result = analyzer.calculateMostFrequentNWords(text, 10);

        List<WordFrequency> expected = List.of(new DefaultWordFrequencyAnalyzer.WordFrequencyRecord("een", 41),
                                               new DefaultWordFrequencyAnalyzer.WordFrequencyRecord("ik", 12),
                                               new DefaultWordFrequencyAnalyzer.WordFrequencyRecord("of", 10),
                                               new DefaultWordFrequencyAnalyzer.WordFrequencyRecord("is", 9),
                                               new DefaultWordFrequencyAnalyzer.WordFrequencyRecord("appel", 9),
                                               new DefaultWordFrequencyAnalyzer.WordFrequencyRecord("mango", 8),
                                               new DefaultWordFrequencyAnalyzer.WordFrequencyRecord("kers", 8),
                                               new DefaultWordFrequencyAnalyzer.WordFrequencyRecord("banaan", 8),
                                               new DefaultWordFrequencyAnalyzer.WordFrequencyRecord("maar", 7),
                                               new DefaultWordFrequencyAnalyzer.WordFrequencyRecord("druif", 7));

        assertThat(result).hasSize(10).containsExactlyElementsOf(expected);
    }

    @Test
    void shouldHandleNullTextForMostFrequentNWords() {
        List<WordFrequency> result = analyzer.calculateMostFrequentNWords(null, 5);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldHandleNegativeNForMostFrequentNWords() {
        String text = "some sample text";
        List<WordFrequency> result = analyzer.calculateMostFrequentNWords(text, -1);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldHandleZeroNForMostFrequentNWords() {
        String text = "some sample text";
        List<WordFrequency> result = analyzer.calculateMostFrequentNWords(text, 0);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldCalculateHighestFrequencyFromFile() throws IOException {
        String text = readTestFile();

        int result = analyzer.calculateHighestFrequency(text);

        assertThat(result).isEqualTo(41); // "een" appears 41 times
    }

    @Test
    void shouldCalculateFrequencyForWordFromFile() throws IOException {
        String text = readTestFile();

        assertThat(analyzer.calculateFrequencyForWord(text, "een")).isEqualTo(41);
        assertThat(analyzer.calculateFrequencyForWord(text, "is")).isEqualTo(9);
        assertThat(analyzer.calculateFrequencyForWord(text, "maar")).isEqualTo(7);
        assertThat(analyzer.calculateFrequencyForWord(text, "ik")).isEqualTo(12);
        assertThat(analyzer.calculateFrequencyForWord(text, "appel")).isEqualTo(9);
        assertThat(analyzer.calculateFrequencyForWord(text, "nietbestaandwoord")).isZero();
    }
}