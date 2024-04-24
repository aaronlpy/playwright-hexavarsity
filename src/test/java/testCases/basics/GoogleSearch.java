package testCases.basics;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class GoogleSearch {


    public static void main(String[] args) {

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            Page page = browser.newPage();

            page.navigate("https://www.google.com");
            Locator searchInput = page.getByRole(AriaRole.COMBOBOX,new Page.GetByRoleOptions().setName("Buscar"));
            System.out.printf("Current page title: %s%n", page.title());

            // wait unitl page is complete loaded
            page.waitForLoadState();
            searchInput.fill("Hexaware");
            searchInput.press("Enter");
            page.waitForURL("**/search?**");

            // click on first search element
            String expectedTitle = "Hexaware Technologies - IT Consulting and Services";
            page.getByText(expectedTitle).click();
            page.waitForLoadState();
            System.out.printf("Current page title: %s%n", page.title());
            assertThat(page).hasTitle(expectedTitle);

        }
    }

}
