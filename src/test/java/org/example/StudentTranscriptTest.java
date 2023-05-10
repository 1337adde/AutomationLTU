package org.example;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class StudentTranscriptTest {

    @Test
    void downloadTranscriptTest() {
        // If the file already exists, delete it
        if (Files.exists(Paths.get("target/downloads/Transcript2023.pdf"))) {
            try {
                Files.delete(Paths.get("target/downloads/Transcript2023.pdf"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        LoginLogout.login(); // Web Driver and login needed
        try {
            StudentTranscript.find();
            Path transcriptPath = Paths.get("target//downloads//Transcript2023.pdf");
            assertTrue(Files.exists(transcriptPath)); // if true, test is passed
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
}