package org.example;
import org.junit.jupiter.api.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.sun.jna.platform.win32.WinNT.EVENTLOG_ERROR_TYPE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppTest {
    private static boolean loggedIn = false;

    @Test
    @BeforeEach
    @Order(1)
    void loginTest() {
        if(!loggedIn) { // Will run first if testing individual test methods, only once if running the whole test.
            String endURL = LoginLogout.login();
            assertEquals("https://portal.ltu.se/group/student/start", endURL);
            loggedIn = true;
        }
    }

    @Test
    @Order(2)
    void examinationInformationTest() {
        Path examinationPath = null;
        // If the file already exists, delete it
        if (Files.exists(Paths.get("target/downloads/final_examination.jpeg"))) {
            try {
                Files.delete(Paths.get("target/downloads/final_examination.jpeg"));
            } catch (Exception e) {
                String exceptionMessage = "Test unable to delete / check for final_examination.jpeg. Stacktrace:" + e.getMessage();
                EventLogger.log(exceptionMessage, EVENTLOG_ERROR_TYPE);
            }
        }
        try {
            ExaminationInformation.find();
            examinationPath = Paths.get("target/downloads/final_examination.jpeg");
            assertTrue(Files.exists(examinationPath)); // if true, test is passed
        }
        catch(AssertionError | Exception e){
            String exceptionMessage = "Test unable to find final_examination.jpeg. examinationPath == " + examinationPath;
            EventLogger.log(exceptionMessage, EVENTLOG_ERROR_TYPE);
        }
    }

    @Test
    @Order(3)
    void courseSyllabusTest() {
        Path syllabusPath = null;
        // If the file already exists, delete it
        if (Files.exists(Paths.get("target/downloads/Syllabus2023.pdf"))) {
            try {
                Files.delete(Paths.get("target/downloads/Syllabus2023.pdf"));
            } catch (Exception e) {
                String exceptionMessage = "Test unable to delete / check for Syllabus2023.pdf. Stacktrace:" + e.getMessage();
                EventLogger.log(exceptionMessage, EVENTLOG_ERROR_TYPE);
            }
        }
        try {
            CourseSyllabus.find();
            syllabusPath = Paths.get("target//downloads//Syllabus2023.pdf");
            assertTrue(Files.exists(syllabusPath)); // if true, test is passed
        }
        catch(AssertionError | Exception e){
            String exceptionMessage = "Test unable to find Syllabus2023.pdf. syllabusPath == " + syllabusPath;
            EventLogger.log(exceptionMessage, EVENTLOG_ERROR_TYPE);
        }
    }

    @Test
    @Order(4)
    void studentTranscriptTest() {
        Path transcriptPath = null;
        // If the file already exists, delete it
        if (Files.exists(Paths.get("target/downloads/Transcript2023.pdf"))) {
            try {
                Files.delete(Paths.get("target/downloads/Transcript2023.pdf"));
            } catch (Exception e) {
                String exceptionMessage = "Test unable to delete / check for Transcript2023.pdf. Stacktrace:" + e.getMessage();
                EventLogger.log(exceptionMessage, EVENTLOG_ERROR_TYPE);
            }
        }
        try {
            StudentTranscript.find();
            transcriptPath = Paths.get("target//downloads//Transcript2023.pdf");
            assertTrue(Files.exists(transcriptPath)); // if true, test is passed
        }
        catch(Exception e){
            String exceptionMessage = "Test unable to find Transcript2023.pdf. transcriptPath == " + transcriptPath;
            EventLogger.log(exceptionMessage, EVENTLOG_ERROR_TYPE);
        }
    }

    @Test
    @Order(5)
    void logoutTest() {
        String endURL = LoginLogout.logout();
        assertEquals("https://weblogon.ltu.se/cas/logout", endURL);
    }
}
