package com.corcentric.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReportUtility {

    public static void executeCMDCommands(String command){
        try {
            ProcessBuilder builder = new ProcessBuilder(
                    "cmd.exe", "/c", command);
            builder.redirectErrorStream(true);
            Process p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while (true) {
                line = r.readLine();
                if (line == null) {
                    break;
                }
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void clearOlderReports(String location) {
        executeCMDCommands("del /S /q /f "+location);
    }

    public static void createReportDirectories(String dirName){
        executeCMDCommands("mkdir "+dirName);
    }

    public static void generateAllureReport(String root){
        String os = System.getProperty("os.name").toLowerCase();
        if (os.startsWith("window")) {
           try {
               executeCMDCommands("cd "+root+" && allure generate --clean -o "+root+"\\allure-html-report");
               System.out.println("Allure report has been generated...");
               executeCMDCommands("cd "+root +"/allure-results && del /s *.json");
               System.out.println("cleanup old allure-results files");
               executeCMDCommands("cd "+root+"/allure-results && mkdir history");
               System.out.println("History Folder created");
               executeCMDCommands("cd "+root+"\\allure-html-report\\history && copy *.json "+root+"\\allure-results\\history");
               System.out.println("Restore allure report history data");
           }catch (Exception e){
               e.printStackTrace();
           }
        }
    }

    public static void copyReports(String source, String destination){
        executeCMDCommands("Xcopy "+source+" "+destination+" /E /H /C /I");
    }
}
