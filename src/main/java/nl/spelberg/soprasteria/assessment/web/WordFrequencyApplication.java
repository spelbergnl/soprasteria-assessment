package nl.spelberg.soprasteria.assessment.web;

import nl.spelberg.soprasteria.assessment.DefaultWordFrequencyAnalyzer;
import nl.spelberg.soprasteria.assessment.WordFrequencyAnalyzer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WordFrequencyApplication {
    public static void main(String[] args) {
        SpringApplication.run(WordFrequencyApplication.class, args);
    }

    @Bean
    public WordFrequencyAnalyzer wordFrequencyAnalyzer() {
        return new DefaultWordFrequencyAnalyzer();
    }

}
