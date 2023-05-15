package org.example;

import com.codeborne.selenide.ex.ElementNotFound;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.switchTo;

import static com.sun.jna.platform.win32.WinNT.EVENTLOG_ERROR_TYPE;
import static com.sun.jna.platform.win32.WinNT.EVENTLOG_INFORMATION_TYPE;

public class ExaminationInformation {
    public static void find() {

        // Find & click the link to Kronox
        $x("//*[contains(text(), 'Tentamen')]").click();
        $x("//*[contains(text(), ' Tentamensschema')]").click();

        switchTo().window(1);

        // Enter search term and click search button
        $x("//*[@id=\"enkel_sokfalt\"]").sendKeys("test av");
        $x("//*[@id=\"enkel_sokknapp\"]").click();

       try {
           $x("//*[contains(text(), 'I0015N-VT23-47000')]").shouldBe(visible).click();

           switchTo().window(2);

           // Takes a screenshot when the web page is fully loaded
           $x("/html/body/table[2]/tbody/tr/td/a[1]").shouldBe(visible);
           Screenshot.take("final_examination");
           EventLogger.log("Screenshot of exam date taken successfully.", EVENTLOG_INFORMATION_TYPE);

           //Log the examination date
           if ($x("/html/body/table[2]/tbody/tr/td/a[1]").exists()) {
               String examDate = $x("/html/body/table[1]/tbody/tr[2]/td/table[2]/tbody/tr[7]/td[3]").getText();
               String logMessage = "Date of examination is " + examDate + ".";
               EventLogger.log(logMessage, EVENTLOG_INFORMATION_TYPE);
            }
       } catch (ElementNotFound | Exception e) {
           String logMessage = "Examination  date could not be found. Stacktrace: " + e.getMessage();
           EventLogger.log(logMessage, EVENTLOG_ERROR_TYPE);
       }
    }
}
