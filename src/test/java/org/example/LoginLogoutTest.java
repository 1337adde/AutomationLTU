package org.example;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginLogoutTest {

    @Test
    void afterLoginURL() {
        String endURL = LoginLogout.login();
        assertEquals("https://portal.ltu.se/group/student/start", endURL);
    }

    @Test
    void afterLoginDownloadsFolderShouldExist() {
        // If the folder already exists, delete it
        if (Files.exists(Paths.get("target/downloads/"))) {
            try {
                Files.delete(Paths.get("target/downloads/"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        LoginLogout.login();
        Path examinationPath = Paths.get("target/downloads/");
        assertTrue(Files.exists(examinationPath)); // if true, test is passed
    }

    @Test
    void afterLogoutURL() {
        LoginLogout.login(); // Cannot log out without logging in first
        String endURL = LoginLogout.logout();
        assertEquals("https://weblogon.ltu.se/cas/logout", endURL);
    }
}