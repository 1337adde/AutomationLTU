package org.example;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ExaminationInformationTest {
    @Test
    void pathExists() {
        ExaminationInformation.find();
        Path examinationPath = Paths.get("target/downloads/final_examination.jpeg");
        assertTrue(Files.exists(examinationPath));
    }

}