package org.example;

public class App
{
    public static void main( String[] args ){

        LoginLogout.login();
        ExaminationInformation.find();
        CourseSyllabus.find();
        StudentTranscript.find();
        LoginLogout.logout();

    }
}
