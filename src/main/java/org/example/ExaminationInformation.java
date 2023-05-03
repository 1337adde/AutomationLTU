package org.example;

import com.codeborne.selenide.SelenideDriver;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.*;
import org.openqa.selenium.*;

public class ExaminationInformation {
    public static void find() {


        // Find & click the link to Kronox
        $x("//*[contains(text(), 'Tentamen')]").click();
        $x("//*[contains(text(), ' Tentamensschema')]").click();

        switchTo().window(1);

        // Enter search term and click search button
        $x("//*[@id=\"enkel_sokfalt\"]").sendKeys("test av");
        $x("//*[@id=\"enkel_sokknapp\"]").click();

        $x("//*[contains(text(), 'I0015N-VT23-47000')]").click();

        switchTo().window(2);

        Screenshot.take("Examinations");

    }
}
