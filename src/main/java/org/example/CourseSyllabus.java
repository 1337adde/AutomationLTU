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
        Configuration.fileDownload = FileDownloadMode.FOLDER; // Don't seem to make a difference
        Configuration.reportsFolder = "build/downloads"; // Don't seem to make a difference

       // $x("//*[@id=\"utbkatForm\"]/div[4]/a/div").click(); // download by clicking the link, below is non-working code based on the selenide download method

        File syllabus = new File("build/downloads/Syllabus2023.pdf"); //Funkar


        // lyckas inte lösa detta... har testat diverse sätt att få fram href från elementet men inte lyckats hittils.
        // ett tips är att använda debugger så man kan se värdet av downloadURL.
        // om man fyller i rätt url manuellt under debuggen skapar den en pdf med rätt namn i downloads så det ska fungera.
       String downloadUrl = $(By.xpath("//*[contains(text(), 'Kursplan antagna: Vår 2023, Läsperiod 4')]")).getAttribute("href");



       download(downloadUrl).renameTo(syllabus); // Funkar




    }
}
