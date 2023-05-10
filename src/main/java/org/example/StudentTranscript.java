package org.example;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.ex.TimeoutException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.util.NoSuchElementException;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.sun.jna.platform.win32.WinNT.EVENTLOG_ERROR_TYPE;
import static com.sun.jna.platform.win32.WinNT.EVENTLOG_INFORMATION_TYPE;


public class StudentTranscript {
    public static void find() throws Exception{

        switchTo().window(0); // Back to ltu.se
        // $x("//*[contains(text(), 'Tentamen')]").click();
       // $x("(//*[contains(text(), 'Examen')])[3]").click();
        //     $x("//ul[@class='hasChildren ']//*[contains(text(), 'Examen')]").click();
        //     $x("//*[@id=\"yui_patched_v3_11_0_1_1683629444821_290\"]").click(); // clicks Examen drop down menu
        // $(By.xpath("//a[contains(@class, 'hasChildren') and contains(@class, 'active') and contains(@class, 'expand') and contains(text(), 'Examen')]")).click();

        switchTo().window(0); // Back to ltu.se
        String kursbevisURL = $x("//*[contains(text(), 'kursbevis')]").getAttribute("href");
        open(kursbevisURL);
        $x("//*[contains(text(), 'Ansökan')]").click();
        $x("//*[contains(text(), 'Inloggning via')]").click();
        $x("//*[@id=\"searchinput\"]").sendKeys("lulea");
        $x("//*[contains(text(), 'Lulea University of Technology')]").click();


        // Depending on screen size the hamburger menu
        if(!$x("//*[contains(text(), 'Transcripts and certificates')]").is(visible)){
            $x("//button[@aria-label='Menu']").click();
        }
    //    if($x("//button[@aria-label='Menu']").is()){
     //       $x("//button[@aria-label='Menu']").click();

        $x("//*[contains(text(), 'Transcripts and certificates')]").click();
        $("[title='Create']").click();
        $x("//*[@id=\"intygstyp\"]").click();
        $x("//*[contains(text(), 'Certificate of Registration')]").click();
        $x("//*[contains(text(), 'All registrations arranged by programme')]").click();
      //  $x("//*[@id=\"main\"]/div/ladok-skapa-intyg/ladok-card/div/div/ladok-card-body/div[3]/div/form/div[3]/div/ladok-skapa-intyg-knapprad/div/button[1]").click(); // Creates the pdf

        // downloads the pdf
     //   String downloadUrl = $(By.xpath("//a[@title='Open PDF-document in a new window'][1]")).getAttribute("href"); // if more than one created PDF, selects the first (latest) in the list
     //   File transcript = new File("target//downloads//Transcript2023.pdf");
      //  download(downloadUrl).renameTo(transcript);






       // $x("//*[@id=\"p_p_id_56_INSTANCE_6rMpXTVb7gkT_\"]/div/div/div[1]/p[3]/a").click(); // klick on ansökan


    }

}
