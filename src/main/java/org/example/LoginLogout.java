package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jna.platform.win32.WinNT;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class LoginLogout {
        public static void login() {
            // Set the path to the WebDriver executable (e.g., ChromeDriver)
            WebDriverManager.chromedriver().setup();
            Configuration.browser = "chrome";
            Configuration.holdBrowserOpen = true;

            open("https://www.ltu.se");
            $x("//button[@id='CybotCookiebotDialogBodyButtonDecline']").click(); // Accept cookies
            $x("//*[@id=\"main-nav\"]/div[3]/div/a[1]").shouldBe(visible).click(); // Student
            $x("//*[@id=\"maincontent\"]/div[1]/div/div[1]/div/div/div[3]/a").shouldBe(visible).click(); // Logga in

            // Creates a new object from an existing .JSON-file containing LTU credentials
            File jsonFile = new File("c:\\temp\\ltu.json");

            try {
                // Reads the JSON-file and writes the values to variables
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(jsonFile);
                String email = jsonNode.get("ltuCredentials").get("email").asText();
                String password = jsonNode.get("ltuCredentials").get("password").asText();

                // Puts down credentials and logs in
                $x("//*[@id=\"username\"]").sendKeys(email);
                $x("//input[@id='password']").sendKeys(password);
                $x("//input[@name='submit']").click();

                // Logs successful login to Windows Event Viewer
                String logMessage = "User " + email + " logged in successfully.";
                //EventLogger.logUserLogin(logMessage, WinNT.EVENTLOG_SUCCESS);
                EventLogger.logUserLogin("Hejhejhej!", WinNT.EVENTLOG_SUCCESS);

            } catch (IOException e) {
                System.out.println("IOException, create a log for this!");
            }
        }

        public static void logout() {
            $x("//*[@id=\"_145_userAvatar\"]/a").shouldBe(visible).click(); // User name
            $x("//li[contains(@class, 'sign-out')]").shouldBe(visible).click(); // Logout

        }
}
