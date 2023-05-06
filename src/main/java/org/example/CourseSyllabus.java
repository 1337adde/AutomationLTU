package org.example;

import com.codeborne.selenide.*;
import com.codeborne.selenide.ex.TimeoutException;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Set;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.sun.jna.platform.win32.WinNT.EVENTLOG_ERROR_TYPE;
import static com.sun.jna.platform.win32.WinNT.EVENTLOG_INFORMATION_TYPE;
public class CourseSyllabus {
    public static void find() throws Exception {

        switchTo().window(0); // Back to ltu.se
        $x("//*[contains(text(), 'Kurs och program')]").click();
        $x("//*[contains(text(), 'Kurskatalog - programstudenter')]").click();

        // Get window index of latest tab opened, and switch to it
        int newTabIndex = WebDriverRunner.getWebDriver().getWindowHandles().toArray().length - 1;
        switchTo().window(newTabIndex);

        $x("//*[@id='fritext']").sendKeys("test av it", Keys.ENTER);
        $x("//*[@id=\"maincontent\"]/div[2]/div[2]/div/h5/a").click(); // test av it search result
        $x("//*[@id=\"maincontent\"]/article/div[1]/section/div[4]/ul/li[2]/a/span[2]").click(); // varen 2023
        $x("//*[@id=\"maincontent\"]/article/div[1]/section/div[8]/div/a").click(); // kursplan

        // Get the link of the element and download the pdf
        String downloadUrl = $(By.xpath("//*[@id=\"utbkatForm\"]/div[4]/a")).getAttribute("href");
        File syllabus = new File("target//downloads//Syllabus2023.pdf");
        download(downloadUrl).renameTo(syllabus);




    }
}
