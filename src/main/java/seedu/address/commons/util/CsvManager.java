package seedu.address.commons.util;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.person.DietaryRequirements;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.person.StudentNumber;
import seedu.address.model.person.Year;
import seedu.address.model.tag.Tag;

/**
 * Handles import and export of Person data to and from CSV files.
 */
public class CsvManager {

    private static final String HEADER =
        "Name,Year,StudentNumber,Email,Phone,DietaryRequirements,Role,Tags";

    /**
     * Exports a list of persons to a CSV file.
     * If no path is provided, a new file is created automatically with a timestamped name.
     *
     * @param persons      the list of persons to export
     * @param optionalPath the optional file path (can be null or empty)
     * @return the Path of the exported CSV file
     * @throws IOException if writing fails
     */
    public static Path exportPersons(List<Person> persons, String optionalPath) throws IOException {
        Path filePath;

        // Determine file path
        if (optionalPath == null || optionalPath.trim().isEmpty()) {
            String defaultName = "members_export_"
                + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))
                + ".csv";
            filePath = Paths.get(defaultName);
        } else {
            filePath = Paths.get(optionalPath);
        }

        // Create file if missing
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }

        // Write CSV data
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            writer.write(HEADER);
            writer.newLine();

            for (Person p : persons) {
                String tags = p.getTags().stream()
                    .map(Tag::toString)
                    .collect(Collectors.joining(";"));

                writer.write(String.join(",",
                    safe(p.getName()),
                    safe(p.getYear()),
                    safe(p.getStudentNumber()),
                    safe(p.getEmail()),
                    safe(p.getPhone()),
                    safe(p.getDietaryRequirements()),
                    safe(p.getRole()),
                    tags
                ));
                writer.newLine();
            }
        }

        System.out.println("Export successful: " + filePath);

        // Try to auto-open file for user
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(filePath.toFile());
            }
        } catch (IOException e) {
            System.out.println("Export complete, but unable to open file automatically.");
        }

        return filePath;
    }

    /**
     * Imports Person data from a CSV file.
     * Handles missing fields gracefully by filling them with default placeholders.
     *
     * @param optionalPath the file path string (can be null or empty)
     * @return the list of imported Person objects
     * @throws IOException if reading fails or file not found
     */
    public static List<Person> importPersons(String optionalPath) throws IOException {
        Path filePath;

        if (optionalPath == null || optionalPath.trim().isEmpty()) {
            filePath = Paths.get("members_import.csv");
            if (!Files.exists(filePath)) {
                throw new FileNotFoundException(
                    "No import file specified and 'members_import.csv' not found.");
            }
        } else {
            filePath = Paths.get(optionalPath);
            if (!Files.exists(filePath)) {
                throw new FileNotFoundException("CSV file not found: " + filePath);
            }
        }

        List<Person> persons = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line = reader.readLine(); // skip header
            if (line == null) {
                throw new IOException("CSV file is empty.");
            }

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split(",", -1);

                // Graceful defaults, since requireAllNonNull
                String nameStr = getOrDefault(parts, 0, "Unknown");
                String yearStr = getOrDefault(parts, 1, "N/A");
                String studentNoStr = getOrDefault(parts, 2, "N/A");
                String emailStr = getOrDefault(parts, 3, "N/A");
                String phoneStr = getOrDefault(parts, 4, "00000000");
                String dietStr = getOrDefault(parts, 5, "None");
                String roleStr = getOrDefault(parts, 6, "Unassigned");
                String tagsStr = getOrDefault(parts, 7, "");

                try {
                    Person p = new Person(
                        new Name(nameStr),
                        new Year(yearStr),
                        new StudentNumber(studentNoStr),
                        new Email(emailStr),
                        new Phone(phoneStr),
                        new DietaryRequirements(dietStr),
                        new Role(roleStr),
                        parseTags(tagsStr)
                    );
                    persons.add(p);
                } catch (Exception e) {
                    System.out.println("Warning: Skipped malformed line: " + line);
                }
            }
        }

        System.out.println("Import complete: "
            + persons.size()
            + " members loaded from "
            + filePath);
        return persons;
    }

    // ===== Helper Methods =====

    private static String safe(Object obj) {
        return (obj == null) ? "" : obj.toString();
    }

    private static String getOrDefault(String[] arr, int index, String def) {
        if (index >= arr.length) {
            return def;
        }
        String value = arr[index].trim();
        return value.isEmpty() ? def : value;
    }

    private static Set<Tag> parseTags(String tagString) {
        if (tagString == null || tagString.isEmpty()) {
            return new HashSet<>();
        }
        return Arrays.stream(tagString.split(";"))
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .map(Tag::new)
            .collect(Collectors.toSet());
    }
}

