package nl.spelberg.soprasteria.assessment;

import java.io.IOException;
import java.util.List;
import nl.spelberg.soprasteria.assessment.DefaultWordFrequencyAnalyzer.WordFrequencyRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultWordFrequencyAnalyzerTest {

    private DefaultWordFrequencyAnalyzer analyzer;

    @BeforeEach
    void setUp() {
        analyzer = new DefaultWordFrequencyAnalyzer();
    }

    @ParameterizedTest
    @CsvSource(
            {
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

    @Test
    void shouldCalculateHighestFrequencyFromFile() throws IOException {
        String text = TestUtil.readTestFile();
        int result = analyzer.calculateHighestFrequency(text);
        assertThat(result).isEqualTo(41); // "een" appears 41 times
    }


    /**
     * NOTE: Generated using https://www.cursor.com/
     * With the command 'unittest for calculateFrequencyForWord'
     */
    @ParameterizedTest
    @CsvSource(
            {
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
    @CsvSource(
            delimiter = '|',
            value = {
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
        assertThat(analyzer.calculateFrequencyForWord(text, searchWord)).as(
                "Word frequency for '%s' in text '%s'",
                searchWord,
                text).isEqualTo(expectedFrequency);
    }

    @Test
    void shouldCalculateFrequencyForWordFromFile() throws IOException {
        String text = TestUtil.readTestFile();

        assertThat(analyzer.calculateFrequencyForWord(text, "een")).isEqualTo(41);
        assertThat(analyzer.calculateFrequencyForWord(text, "is")).isEqualTo(9);
        assertThat(analyzer.calculateFrequencyForWord(text, "maar")).isEqualTo(7);
        assertThat(analyzer.calculateFrequencyForWord(text, "ik")).isEqualTo(12);
        assertThat(analyzer.calculateFrequencyForWord(text, "appel")).isEqualTo(9);
        assertThat(analyzer.calculateFrequencyForWord(text, "nietbestaandwoord")).isZero();
    }

    @Test
    void shouldHandleNullsCalculateFrequencyForWord() {
        assertThat(analyzer.calculateFrequencyForWord(null, "word")).as("Word frequency with null text").isZero();
        assertThat(analyzer.calculateFrequencyForWord("some text", null)).as("Word frequency with null search word")
                .isZero();
        assertThat(analyzer.calculateFrequencyForWord(null, null)).as("Word frequency with both inputs null").isZero();
    }

    @Test
    void shouldCalculateMostFrequentFiveWordsFromFile() throws IOException {
        String text = TestUtil.readTestFile();

        List<WordFrequency> result = analyzer.calculateMostFrequentNWords(text, 5);

        List<WordFrequency> expected = List.of(
                wordFreq("een", 41),
                wordFreq("ik", 12),
                wordFreq("of", 10),
                wordFreq("appel", 9),
                wordFreq("is", 9));

        assertThat(result).hasSize(5).containsExactlyElementsOf(expected);
    }

    @Test
    void shouldCalculateMostFrequentThreeWordsFromFile() throws IOException {
        String text = TestUtil.readTestFile();

        List<WordFrequency> result = analyzer.calculateMostFrequentNWords(text, 3);

        List<WordFrequency> expected = List.of(wordFreq("een", 41), wordFreq("ik", 12), wordFreq("of", 10));
        assertThat(result).hasSize(3).containsExactlyElementsOf(expected);
    }

    @Test
    void shouldCalculateMostFrequentSingleWordFromFile() throws IOException {
        String text = TestUtil.readTestFile();

        List<WordFrequency> result = analyzer.calculateMostFrequentNWords(text, 1);

        List<WordFrequency> expected = List.of(wordFreq("een", 41));

        assertThat(result).hasSize(1).containsExactlyElementsOf(expected);
    }

    @Test
    void shouldCalculateMostFrequentTenWordsFromFile() throws IOException {
        String text = TestUtil.readTestFile();

        List<WordFrequency> result = analyzer.calculateMostFrequentNWords(text, 10);

        List<WordFrequency> expected = List.of(
                wordFreq("een", 41),
                wordFreq("ik", 12),
                wordFreq("of", 10),
                wordFreq("appel", 9),
                wordFreq("is", 9),
                wordFreq("banaan", 8),
                wordFreq("kers", 8),
                wordFreq("mango", 8),
                wordFreq("druif", 7),
                wordFreq("maar", 7));

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
    void shouldMostFrequentNWords() {
        List<WordFrequency> result = analyzer.calculateMostFrequentNWords("Hallo+hallo+wereld,+zeg+eens+hallo!", 5);
        assertThat(result).isEqualTo(List.of(
                wordFreq("hallo", 3),
                wordFreq("eens", 1),
                wordFreq("wereld", 1),
                wordFreq("zeg", 1)));
    }

    private static WordFrequency wordFreq(String word, int frequency) {
        return new WordFrequencyRecord(word, frequency);
    }

}