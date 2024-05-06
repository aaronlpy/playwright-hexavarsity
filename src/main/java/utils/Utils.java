package utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {


    public static String setCurrentTimestamp() throws Exception {
        try {
            LocalDateTime localDate = LocalDateTime.now();
            DateTimeFormatter currentFormat = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
            return localDate.format(currentFormat);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public static String setFIleName(String name) throws Exception {
        try {
            if (name.contains("(") || name.contains(")")) {
                name = name.replace("(", "").replace(")", "");
            }

            return String.format("%s_%s", name, setCurrentTimestamp());

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public static void takeScreenshot(Page page, String fileName) throws Exception {
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(
                String.format("img/%s_%s.jpg", fileName, setCurrentTimestamp()))));
    }

    public static void takeLocatorScreenshot(Locator locator, String fileName) throws Exception {
        locator.screenshot(new Locator.ScreenshotOptions().setPath(
                Paths.get(
                        String.format("img/%s_%s.jpg", fileName, setCurrentTimestamp()))
        ));
    }

    public static void saveVideo(Page page, String name) throws Exception {
        page.video().saveAs(Paths.get("videos/" + Utils.setFIleName(name + ".webm")));
        System.out.println(page.video().path());
    }

    public static String setReportFolder() throws Exception {
        return "Report_" + setCurrentTimestamp();
    }

    public static void deleteFile(String filePath) throws Exception {
        try{
            File f = new File(filePath);
            if (f.exists())
                f.delete();

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
