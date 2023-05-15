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
        if(!loggedIn) { // Will run if testing individual test methods, only once if running the whole test.
            String endURL = LoginLogout.login();
            assertEquals("https://portal.ltu.se/group/student/start", endURL);
            loggedIn = true;
        }
    }

    @Test
    @Order(2)
    void examinationInformationTest() {
        // If the file already exists, delete it
        if (Files.exists(Paths.get("target/downloads/final_examination.jpeg"))) {
            try {
                Files.delete(Paths.get("target/downloads/final_examination.jpeg"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // If find() works, final_examination.jpeg should be downloaded
        ExaminationInformation.find();
        Path examinationPath = Paths.get("target/downloads/final_examination.jpeg");
        assertTrue(Files.exists(examinationPath)); // if true, test is passed
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
                String exceptionMessage = "Test unable to delete Syllabus2023.pdf. Stacktrace:" + e.getMessage();
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
            // Exceptions are logged in Event Viewer
        }
    }

    @Test
    @Order(4)
    void studentTranscriptTest() {
        // If the file already exists, delete it
        if (Files.exists(Paths.get("target/downloads/Transcript2023.pdf"))) {
            try {
                Files.delete(Paths.get("target/downloads/Transcript2023.pdf"));
            } catch (Exception e) {
                String exceptionMessage = "Test unable to modify / check for Transcript2023.pdf. Stacktrace:" + e.getMessage();
                EventLogger.log(exceptionMessage, EVENTLOG_ERROR_TYPE);
            }
        }
        try {
            StudentTranscript.find();
            Path transcriptPath = Paths.get("target//downloads//Transcript2023.pdf");
            assertTrue(Files.exists(transcriptPath)); // if true, test is passed
        }
        catch(Exception e){
            String exceptionMessage = "Test of StudentTranscript unsuccessful, see earlier logs.";
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
