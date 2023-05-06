package org.example;

import com.codeborne.selenide.ex.TimeoutException;

import java.util.NoSuchElementException;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.sun.jna.platform.win32.WinNT.EVENTLOG_ERROR_TYPE;
import static com.sun.jna.platform.win32.WinNT.EVENTLOG_INFORMATION_TYPE;

public class ExaminationInformation {
    public static void find() throws Exception {

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

           // Takes a screnshot when the web page is fully loaded
           $x("/html/body/table[2]/tbody/tr/td/a[1]").shouldBe(visible);
           Screenshot.take("final_examination");

           //Log the examination date
           if ($x("/html/body/table[2]/tbody/tr/td/a[1]").exists()) {
               String examDate = $x("/html/body/table[1]/tbody/tr[2]/td/table[2]/tbody/tr[7]/td[3]").getText();
               String logMessage = "Date of examination is " + examDate + ".";
               EventLogger.log(logMessage, EVENTLOG_INFORMATION_TYPE);
            }
       } catch (Throwable t) {
           String logMessage = "Examination could not be found.";
           EventLogger.log(logMessage, EVENTLOG_ERROR_TYPE);
       }
    }
}
