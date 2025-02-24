package nl.spelberg.soprasteria.assessment.web;

import java.util.List;
import nl.spelberg.soprasteria.assessment.WordFrequency;
import nl.spelberg.soprasteria.assessment.WordFrequencyAnalyzer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        value = "word",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class WordFrequencyController implements WordFrequencyAnalyzer {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final WordFrequencyAnalyzer wordFrequencyAnalyzer;

    public WordFrequencyController(WordFrequencyAnalyzer wordFrequencyAnalyzer) {
        this.wordFrequencyAnalyzer = wordFrequencyAnalyzer;
    }

    @PostMapping("calculate/highestFrequency")
    @Override
    public int calculateHighestFrequency(@RequestBody String text) {
        logger.debug("calculateHighestFrequency({})", text);
        return wordFrequencyAnalyzer.calculateHighestFrequency(text);
    }

    @PostMapping("calculate/frequencyForWord")
    @Override
    public int calculateFrequencyForWord(@RequestBody String text, @RequestParam("word") String word) {
        logger.debug("calculateFrequencyForWord({}, {})", text, word);
        return wordFrequencyAnalyzer.calculateFrequencyForWord(text, word);
    }

    @PostMapping("calculate/mostFrequentNWords")
    @Override
    public List<WordFrequency> calculateMostFrequentNWords(@RequestBody String text, @RequestParam(value = "n") int n) {
        logger.debug("calculateMostFrequentNWords({}, {})", text, n);
        return wordFrequencyAnalyzer.calculateMostFrequentNWords(text, n);
    }
}
