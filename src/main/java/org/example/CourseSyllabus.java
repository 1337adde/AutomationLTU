package org.example;

import com.codeborne.selenide.*;
import com.codeborne.selenide.ex.ElementNotFound;
import static com.codeborne.selenide.Selenide.*;

import java.io.File;

import static com.sun.jna.platform.win32.WinNT.EVENTLOG_ERROR_TYPE;
import static com.sun.jna.platform.win32.WinNT.EVENTLOG_INFORMATION_TYPE;
public class CourseSyllabus {
    public static void find() {

        switchTo().window(0); // Back to ltu.se

        try {
            $x("//*[contains(text(), 'Kurs och program')]").click();
            $x("//*[contains(text(), 'Kurskatalog - programstudenter')]").click(); // Opens new tab automatically

            // Get window index of latest tab opened, and switch to it
            int newTabIndex = WebDriverRunner.getWebDriver().getWindowHandles().toArray().length - 1;
            switchTo().window(newTabIndex);

            $x("//*[@id='fritext']").setValue("test av it").pressEnter();
            $x("//*[@id=\"maincontent\"]/div[2]/div[2]/div/h5/a").click(); // test av it search result
            $x("//*[@id=\"maincontent\"]/article/div[1]/section/div[4]/ul/li[2]/a/span[2]").click(); // varen 2023
            $x("//*[@id=\"maincontent\"]/article/div[1]/section/div[8]/div/a").click(); // kursplan

            try {
                // Get the link of the element and download the pdf
                String downloadUrl = $("#utbkatForm div:nth-child(4) a").getAttribute("href");
                File syllabus = new File("target//downloads//Syllabus2023.pdf");
                download(downloadUrl).renameTo(syllabus);
                EventLogger.log(syllabus + " downloaded successfully.", EVENTLOG_INFORMATION_TYPE);
            }
            catch(ElementNotFound | Exception e){
                String exceptionMessage = "Syllabus download failed. Stacktrace: " + e.getMessage();
                EventLogger.log(exceptionMessage, EVENTLOG_ERROR_TYPE);
            }
        }
        catch(ElementNotFound | Exception e){
            String exceptionMessage = "Navigation to syllabus failed. Stacktrace: " + e.getMessage();
            EventLogger.log(exceptionMessage, EVENTLOG_ERROR_TYPE);
            }
        }






}

