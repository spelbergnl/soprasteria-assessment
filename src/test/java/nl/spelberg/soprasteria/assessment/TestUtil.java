package nl.spelberg.soprasteria.assessment;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class TestUtil {
    public static String readTestFile() {
        try (InputStream resourceAsStream = TestUtil.class.getResourceAsStream("/testdata-chatgpt.txt")) {
            if (resourceAsStream == null) {
                throw new RuntimeException("resource '/testdata-chatgpt.txt' not found");
            }
            byte[] bytes = resourceAsStream.readAllBytes();
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
