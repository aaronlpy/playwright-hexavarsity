package core;

import com.microsoft.playwright.*;
import utils.Config;
import utils.Utils;

import java.awt.*;
import java.nio.file.Paths;
import java.util.List;

public class BrowserFactory {

    static String currentReportFolder;

    public static Browser createBrowser(Playwright playwright, BrowsersType browserType, boolean isHeadless) throws Exception {
        try {

            Browser browser = null;

            BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
            launchOptions
                    .setHeadless(isHeadless)
                    .setArgs(List.of("--start-maximized"));

            if (browserType.name().equalsIgnoreCase("edge"))
                launchOptions.setChannel("msedge");


            switch (browserType) {
                case CHROME, EDGE -> browser = playwright.chromium().launch(launchOptions);

                case FIREFOX -> browser = playwright.firefox().launch(new BrowserType.LaunchOptions()
                        .setHeadless(isHeadless));

                default -> throw new IllegalArgumentException("Unsupported browser type: " + browserType.name());
            }

            return browser;

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public static BrowserContext createBrowserContext(Browser browser) throws Exception {
        try {


            Browser.NewContextOptions contextOptions = new Browser.NewContextOptions();

            currentReportFolder = "Reports/Report_" + Utils.setCurrentTimestamp();


            if (Boolean.parseBoolean(Config.getProperty("video")))
                contextOptions.setRecordVideoDir(Paths.get(currentReportFolder + "/videos/"));

            if (browser.browserType().name().equalsIgnoreCase("firefox")) {
                Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                contextOptions.setViewportSize(dimension.width, dimension.height);
            } else
                contextOptions.setViewportSize(null);

            if (Boolean.parseBoolean(Config.getProperty("useStoreSession"))) {
                contextOptions.setStorageStatePath(Paths.get("state.json"));
            }

            return browser.newContext(contextOptions);


        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public static void startTraceRecording(BrowserContext context) throws Exception {
        try {

            if (Boolean.parseBoolean(Config.getProperty("traceRecording"))) {
                context.tracing().start(
                        new Tracing.StartOptions()
                                .setSnapshots(true)
                                .setScreenshots(true)
                                .setSources(true)
                );
            }


        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public static void stopTraceRecording(BrowserContext context) throws Exception {
        if (Boolean.parseBoolean(Config.getProperty("traceRecording"))) {
            context.tracing().stop(new Tracing.StopOptions()
                    .setPath(Paths.get( currentReportFolder + "/Tracing/Trace_" + Utils.setCurrentTimestamp() + ".zip"))
            );
        }
    }

    public static void saveVideoExecution(Page page,String name) throws Exception {
        if (Boolean.parseBoolean(Config.getProperty("video"))) {
            page.video().saveAs(Paths.get(currentReportFolder + "/videos/" + name + ".mp4"));
            Utils.deleteFile(page.video().path().toString());
        }
    }


}
