package org.example;

import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.*;

class ExaminationInformationTest {
    @Test // Test if the file is downloaded
    void pathExists() {
        // If the file already exists, delete it
        if (Files.exists(Paths.get("target/downloads/final_examination.jpeg"))) {
            try {
                Files.delete(Paths.get("target/downloads/final_examination.jpeg"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // If find() works, final_examination.jpeg should be downloaded
        LoginLogout.login(); // Web Driver is started in login()
        ExaminationInformation.find();
        Path examinationPath = Paths.get("target/downloads/final_examination.jpeg");
        assertTrue(Files.exists(examinationPath)); // if true, test is passed
    }
}