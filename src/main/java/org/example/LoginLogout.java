package org.example;

import com.codeborne.selenide.Configuration;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

import static com.sun.jna.platform.win32.WinNT.EVENTLOG_ERROR_TYPE;
import static com.sun.jna.platform.win32.WinNT.EVENTLOG_INFORMATION_TYPE;

public class LoginLogout {
    static String endURL = "";
        public static String login() {
            // Set the path to the WebDriver executable (ChromeDriver)
            WebDriverManager.chromedriver().setup();
            Configuration.browser = "chrome";
            Configuration.holdBrowserOpen = true;
            Configuration.reportsFolder = "/target/downloads";
            Configuration.downloadsFolder = "/target/downloads";
            Configuration.timeout = 5000; // Makes selenide wait for elements if they don't appear immediately.

            // Creates the downloads folder if it does not already exist (Had a bug where the folder wasn't included in the git repository, fixed now but decided to keep the code just in case.)
            Path downloadsFolder = Paths.get("target/downloads");
            if(!Files.exists(downloadsFolder)) {
                try {
                    Files.createDirectory(downloadsFolder);
                    String logMessage = "Downloads folder created.";
                    EventLogger.log(logMessage, EVENTLOG_INFORMATION_TYPE);
                } catch (IOException e) {
                    String logMessage = "Unable to create downloads folder. Requested files may not be downloaded.";
                    EventLogger.log(logMessage, EVENTLOG_ERROR_TYPE);
                }
            }

            // Navigates to the login page
            open("https://www.ltu.se");
            $x("//button[@id='CybotCookiebotDialogBodyButtonDecline']").click(); // Accept cookies
            $x("//*[@id=\"main-nav\"]/div[3]/div/a[1]").shouldBe(visible).click(); // Student
            $x("//*[@id=\"maincontent\"]/div[1]/div/div[1]/div/div/div[3]/a").shouldBe(visible).click(); // Logga in

            // Login process
            try {
                // Creates a new object from an existing .JSON-file containing LTU credentials
                File jsonFile = new File("c:\\temp\\ltu.json");
                // Reads the JSON-file and writes the values to variables
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(jsonFile);
                String email = jsonNode.get("ltuCredentials").get("email").asText();
                String password = jsonNode.get("ltuCredentials").get("password").asText();

                // Puts down credentials and logs in
                $x("//*[@id=\"username\"]").sendKeys(email);
                $x("//input[@id='password']").sendKeys(password);
                $x("//input[@name='submit']").click();

                if($x("//*[contains(text(), 'Canvas och schema')]").exists()) { // Canvas och schema is only accessible to someone already logged in.
                    String logMessage = "User " + email + " logged in successfully.";
                    EventLogger.log(logMessage, EVENTLOG_INFORMATION_TYPE);
                    endURL = getWebDriver().getCurrentUrl();
                } else {
                        String logMessage = "Unable to login. Make sure credentials are correct.";
                        EventLogger.log(logMessage, EVENTLOG_ERROR_TYPE);
                        endURL = getWebDriver().getCurrentUrl();
                }

            } catch (Exception e) {
                String logMessage = "Unable to login. Make sure credentials are accessible. Stacktrace: " + e.getMessage();
                EventLogger.log(logMessage, EVENTLOG_ERROR_TYPE);
            }
            return endURL;
        }

        public static String logout() {
            try {
                open("https://portal.ltu.se/");
                //switchTo().window(0);
                $x("//*[@id=\"_145_userAvatar\"]/a").shouldBe(visible).click(); // User name
                $x("//li[contains(@class, 'sign-out')]").shouldBe(visible).click(); // Logout
                String logMessage = "User logged out successfully.";
                EventLogger.log(logMessage, EVENTLOG_INFORMATION_TYPE);
                return endURL = getWebDriver().getCurrentUrl();
            } catch (Exception e) {
                String logMessage = "Unable to logout. Stacktrace: " + e.getMessage();
                EventLogger.log(logMessage, EVENTLOG_ERROR_TYPE);
                return endURL = getWebDriver().getCurrentUrl();
            }
        }

}
