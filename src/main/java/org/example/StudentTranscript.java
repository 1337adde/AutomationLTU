package org.example;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.TimeoutException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.File;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.sun.jna.platform.win32.WinNT.EVENTLOG_ERROR_TYPE;
import static com.sun.jna.platform.win32.WinNT.EVENTLOG_INFORMATION_TYPE;


public class StudentTranscript {
    public static void find(){

        switchTo().window(0); // Back to ltu.se


        try { // Navigates to transcript creation page (Ladok) from ltu.se


            String kursbevisURL = $x("//*[contains(text(), 'kursbevis')]").getAttribute("href");
            open(kursbevisURL);
            $x("//*[contains(text(), 'Ansökan')]").click();
            $x("//*[contains(text(), 'Inloggning via')]").click();

            // clicks away cookies if prompted
            if($x("//*[contains(text(), 'Only use necessary')]").is(visible)){
                $x("//*[contains(text(), 'Only use necessary')]").click();
            }

            $x("//*[@id=\"searchinput\"]").sendKeys("lulea");
            $x("//*[contains(text(), 'Lulea University of Technology')]").shouldBe(visible).click(); // Search sometimes takes more than 4 seconds.

            // Depending on screen size the menu list might hide inside hamburger menu , if target element is not visible click on menu.
            if(!$x("//*[contains(text(), 'Transcripts and certificates')]").is(visible)){
                $x("//button[@aria-label='Menu']").click();
            }


            try{ // Specifies type of transcript and creates it

                $x("//*[contains(text(), 'Transcripts and certificates')]").click();
                $("[title='Create']").click();
                $x("//*[@id=\"intygstyp\"]").click();
                $x("//*[contains(text(), 'Certificate of Registration')]").click();
                $x("//*[contains(text(), 'All registrations arranged by programme')]").click();
                // Creates the pdf
                $x("//*[@id=\"main\"]/div/ladok-skapa-intyg/ladok-card/div/div/ladok-card-body/div[3]/div/form/div[3]/div/ladok-skapa-intyg-knapprad/div/button[1]").click();


                try{ // downloads the pdf

                    String downloadUrl = $(By.xpath("//a[@title='Open PDF-document in a new window'][1]")).getAttribute("href"); // if more than one created PDF, selects the first (latest) in the list
                    File transcript = new File("target//downloads//Transcript2023.pdf");
                    download(downloadUrl).renameTo(transcript);
                }
                catch(ElementNotFound | Exception e){
                    String exceptionMessage = "Transcript download failed. Stacktrace:" + e.getMessage();
                    EventLogger.log(exceptionMessage, EVENTLOG_ERROR_TYPE);
                }

            }
            catch(ElementNotFound | Exception e){
                String exceptionMessage = "Transcript creation failed. Stacktrace:" + e.getMessage();
                EventLogger.log(exceptionMessage, EVENTLOG_ERROR_TYPE);
            }

        }
        catch(ElementNotFound | Exception e){
        String exceptionMessage = "Navigation to Ladok failed." + "Stacktrace: " + e.getMessage();
        EventLogger.log(exceptionMessage, EVENTLOG_ERROR_TYPE);
        }


    }

}
