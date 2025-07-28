package utils;

import java.time.LocalDateTime;

public class DateParserTest {
    public static void main(String[] args) {
        String[] testInputs = {
            "2-3-25",
            "02 03 2025",
            "2/3/25",
            "2 3 25",
            "15-12-24",
            "31/01/2025",
            "invalid input",
            "32-13-25"
        };

        for (String input : testInputs) {
            LocalDateTime result = DateParser.parseFlexibleDate(input);
            System.out.printf("Input: %-15s => Parsed: %s%n", input, result);
        }
    }
}