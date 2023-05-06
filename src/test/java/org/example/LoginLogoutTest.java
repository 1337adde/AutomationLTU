package org.example;

import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginLogoutTest {

    @Test
    void afterLoginURL() {
        String endURL = LoginLogout.login();
        assertEquals("https://portal.ltu.se/group/student/start", endURL);
    }

    @Test
    void afterLogoutURL() {
        LoginLogout.login(); // Cannot log out without logging in first
        String endURL = LoginLogout.logout();
        assertEquals("https://weblogon.ltu.se/cas/logout", endURL);
    }
}