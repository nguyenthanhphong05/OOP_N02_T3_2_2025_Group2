package utils;

import java.time.LocalDateTime;

public class DateParser {

    /**
     * Parse flexible user date input and return LocalDateTime at 12:00.
     * Supports formats: "2-3-25", "2 3 25", "2/3/25", "02 03 2025", etc.
     * @param input user input string
     * @return LocalDateTime or null if invalid
     */
    public static LocalDateTime parseFlexibleDate(String input) {
        // Replace all non-digit with space, then split
        String cleaned = input.trim().replaceAll("[^\\d]", " ");
        String[] parts = cleaned.split("\\s+");
        int day, month, year;
        try {
            day = Integer.parseInt(parts[0]);
            month = Integer.parseInt(parts[1]);
            year = Integer.parseInt(parts[2]);
            if (year < 100) year += 2000;
        } catch (Exception e) {
            System.out.println("Invalid date format! Use DD MM YYYY, separators \"-\", \"/\", or space.");
            return null;
        }
        try {
            return LocalDateTime.of(year, month, day, 12, 0);
        } catch (Exception e) {
            System.out.println("Invalid date values!");
            return null;
        }
    }
}
