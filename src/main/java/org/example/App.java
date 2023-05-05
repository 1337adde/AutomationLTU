package org.example;


import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.DownloadOptions;
import com.codeborne.selenide.FileDownloadMode;
import com.codeborne.selenide.impl.DownloadFileWithHttpRequest;

import java.io.File;

public class App
{
    public static void main( String[] args ) throws Exception {

        Configuration.fileDownload = FileDownloadMode.FOLDER; // Don't seem to make a difference
        Configuration.reportsFolder = "build/downloads"; // Don't seem to make a difference

        LoginLogout.login();
        ExaminationInformation.find();
        CourseSyllabus.find();
        LoginLogout.logout();
    }
}
