package testCases.pomTests;

import com.microsoft.playwright.*;
import core.ActionType;
import core.BrowserFactory;
import core.BrowserHandler;
import core.BrowsersType;
import org.junit.jupiter.api.*;

import java.nio.file.Paths;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AutomationDemoQATest extends BrowserHandler {

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    Page page;

    @BeforeAll
    public static void setupBrowser() throws Exception {
        playwright = Playwright.create();
        browser = BrowserFactory.createBrowser(playwright, BrowsersType.CHROME, false);
        playwright.selectors().setTestIdAttribute("data-qa");

    }

    @AfterAll
    public static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    public void createContext() throws Exception {
        context = BrowserFactory.createBrowserContext(browser);
        BrowserFactory.startTraceRecording(context);
        page = context.newPage();
    }

    @AfterEach
    public void closeContext(TestInfo testInfo) throws Exception {
        BrowserFactory.stopTraceRecording(context);
        context.close();
        BrowserFactory.saveVideoExecution(page, testInfo.getDisplayName());
    }

    @Test
    @DisplayName("DragAndDrop")
    void dragAndDrop() throws Exception {
        PerformAction(ActionType.NAVIGATE, page, null, "https://www.w3schools.com/html/html5_draganddrop.asp", false);
        page.waitForLoadState();
        PerformAction(ActionType.DRAG_DROP, page, page.locator("#drag1"), "#div2", false);
        page.waitForTimeout(5000);
    }

    @Test
    @DisplayName("Download files")
    void downloadFile() throws Exception {
        PerformAction(ActionType.NAVIGATE, page, null, "https://unsplash.com/photos/an-aerial-view-of-a-snow-covered-ski-slope-k9-mmis-8MU", false);
        page.waitForLoadState();
        PerformAction(ActionType.DOWNLOAD_FILE, page, page.locator("//a[text()=\"Download free\"]"), null, false);
    }

    @Test
    @DisplayName("Upload File")
    void uploadFile() throws Exception {
        PerformAction(ActionType.NAVIGATE, page, null, "https://www.w3schools.com/howto/howto_html_file_upload_button.asp", false);
        page.locator("#myFile").setInputFiles(Paths.get("downloads/sampleFile.jpeg"));
        page.waitForTimeout(5000);
    }

}
