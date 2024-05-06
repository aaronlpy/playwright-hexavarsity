package testCases.pomTests;

import com.microsoft.playwright.*;
import core.ActionType;
import core.BrowserFactory;
import core.BrowserHandler;
import core.BrowsersType;
import org.junit.jupiter.api.*;
import pageObjects.CheckOutPage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.ViewCartPage;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AutomationPracticePOMTest extends BrowserHandler {

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
    @Order(1)
    @Disabled
    public void LoginLogoutTest() throws Exception {
        HomePage homePage = new HomePage(page);
        LoginPage loginPage = new LoginPage(page);

        PerformAction(ActionType.NAVIGATE, page, null, "https://automationexercise.com/", false);
        homePage.clickOnSignUp();
        loginPage.fillForm("joe.doe27@email.com", "Password@1");
        context.storageState(new BrowserContext.StorageStateOptions().setPath(Paths.get("state.json")));

    }

    @Test
    @Order(2)
    @DisplayName("Selecting Products")
    public void selectProducts() throws Exception {
        PerformAction(ActionType.NAVIGATE, page, null, "https://automationexercise.com/products", false);
        PerformAction(ActionType.HOVER_CLICK, page, page.locator("a[data-product-id=\"2\"]"), "img[src$=\"/2\"]", false);
        assertThat(page.locator(".modal-content")).isVisible();
        PerformAction(ActionType.CLICK, page, page.locator("//button[text()=\"Continue Shopping\"]"), null, false);
        page.waitForTimeout(10000);
    }

    @Test
    @Order(3)
    @DisplayName("CheckOutProcess")
    public void proceedToCheckout() throws Exception {
        ViewCartPage viewCartPage = new ViewCartPage(page);
        CheckOutPage checkOutPage = new CheckOutPage(page);
        PerformAction(ActionType.NAVIGATE, page, null, "https://automationexercise.com/view_cart", false);
        viewCartPage.clickOnProceedToCheckout();
        page.waitForTimeout(5000);
        PerformAction(ActionType.SCROLL_JS, page, null, "0,700", false);
        PerformAction(ActionType.CLICK, page, checkOutPage.placeOrder, null, false);
        page.waitForTimeout(5000);
    }

    @Test
    @Order(4)
    @DisplayName("Add payment")
    public void addPaymentInformation() throws Exception {
        playwright.selectors().setTestIdAttribute("data-qa");
        PerformAction(ActionType.NAVIGATE, page, null, "https://automationexercise.com/payment", false);
        PerformAction(ActionType.FILL, page, page.getByTestId("name-on-card"), "Joe Doe", false);
        PerformAction(ActionType.FILL, page, page.getByTestId("card-number"), "123345747851", false);
        PerformAction(ActionType.FILL, page, page.getByTestId("cvc"), "311", false);
        PerformAction(ActionType.FILL, page, page.getByTestId("expiry-month"), "07", false);
        PerformAction(ActionType.FILL, page, page.getByTestId("expiry-year"), "2027", false);
        PerformAction(ActionType.PRESS, page, page.getByTestId("pay-button"), "Enter", false);
    }
}
