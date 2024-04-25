package testCases.demoqa;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import java.util.List;

public class DemoQAValidationsTest {

    static Playwright playwright;
    static Browser browser;

    BrowserContext context;
    Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setArgs(List.of("--start-maximized"))
        );

    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        context.close();
    }


    @Test
    void googleSearch(){
        page.navigate("https://www.google.com");
    }




}
