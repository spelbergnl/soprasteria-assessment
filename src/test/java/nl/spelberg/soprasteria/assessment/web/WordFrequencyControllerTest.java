package nl.spelberg.soprasteria.assessment.web;

import java.util.List;
import nl.spelberg.soprasteria.assessment.DefaultWordFrequencyAnalyzer.WordFrequencyRecord;
import nl.spelberg.soprasteria.assessment.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WordFrequencyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        restTemplate.getRestTemplate().setInterceptors(List.of((request, body, execution) -> {
            request.getHeaders().set("Accept", MediaType.APPLICATION_JSON_VALUE);
            request.getHeaders().set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            return execution.execute(request, body);
        }));
    }

    @Test
    void postCalculateHighestFrequency() {
        assertThat(restTemplate.postForObject(
                "http://localhost:" + port + "/word/calculate/highestFrequency",
                "Hallo+wereld,+zeg+eens+hallo!",
                String.class)).isEqualTo("2");
    }

    @Test
    void postCalculateFrequencyForWord() {
        assertThat(restTemplate.postForObject(
                "http://localhost:" + port + "/word/calculate/frequencyForWord?word=haLLo",
                "Hallo+hallo+wereld,+zeg+eens+hallo!",
                String.class)).isEqualTo("3");
    }

    @Test
    void postCalculateMostFrequentNWords() {
        assertThat(restTemplate.postForObject(
                "http://localhost:" + port + "/word/calculate/mostFrequentNWords?n=12",
                TestUtil.readTestFile(),
                WordFrequencyRecord[].class)).isEqualTo(List.of(
                new WordFrequencyRecord("een", 41),
                new WordFrequencyRecord("ik", 12),
                new WordFrequencyRecord("of", 10),
                new WordFrequencyRecord("appel", 9),
                new WordFrequencyRecord("is", 9),
                new WordFrequencyRecord("banaan", 8),
                new WordFrequencyRecord("kers", 8),
                new WordFrequencyRecord("mango", 8),
                new WordFrequencyRecord("druif", 7),
                new WordFrequencyRecord("maar", 7),
                new WordFrequencyRecord("soms", 6),
                new WordFrequencyRecord("zijn", 6)).toArray());
    }

}