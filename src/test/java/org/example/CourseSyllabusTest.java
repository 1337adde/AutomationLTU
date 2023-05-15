package org.example;

import com.codeborne.selenide.ex.ElementNotFound;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.sun.jna.platform.win32.WinNT.EVENTLOG_ERROR_TYPE;
import static org.junit.jupiter.api.Assertions.*;

class CourseSyllabusTest {

    @Test
    void find() {
        // If the file already exists, delete it
        if (Files.exists(Paths.get("target/downloads/Syllabus2023.pdf"))) {
            try {
                Files.delete(Paths.get("target/downloads/Syllabus2023.pdf"));
            } catch (Exception e) {
                String exceptionMessage = "Test unable to modify / check for Syllabus2023.pdf. Stacktrace:" + e.getMessage();
                EventLogger.log(exceptionMessage, EVENTLOG_ERROR_TYPE);
            }
        }
        LoginLogout.login(); // Web Driver and login needed
        try {
            CourseSyllabus.find();
            Path syllabusPath = Paths.get("target//downloads//Syllabus.pdf");
            assertTrue(Files.exists(syllabusPath)); // if true, test is passed
        }
        catch(ElementNotFound | Exception e){
            e.printStackTrace();
            // Exceptions are logged in Event Viewer
        }

    }
}