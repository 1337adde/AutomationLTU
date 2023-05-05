package org.example;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class ExaminationInformation {
    public static void find() throws Exception {

        // Find & click the link to Kronox
        $x("//*[contains(text(), 'Tentamen')]").click();
        $x("//*[contains(text(), ' Tentamensschema')]").click();

        switchTo().window(1);

        // Enter search term and click search button
        $x("//*[@id=\"enkel_sokfalt\"]").sendKeys("test av");
        $x("//*[@id=\"enkel_sokknapp\"]").click();

        $x("//*[contains(text(), 'I0015N-VT23-47000')]").click();

        switchTo().window(2);

        // Add screenshot function here
        $x("/html/body/table[2]/tbody/tr/td/a[1]").shouldBe(visible);
        Screenshot.take("Examination Information");
    }
}
