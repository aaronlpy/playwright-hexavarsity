package testCases.automationPractice;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginUser {

    public static void main(String[] args) {
        try(Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(false)
            );

            String userName = "Joe Doe";

            Page page = browser.newPage();

            // set data-qa as id
            playwright.selectors().setTestIdAttribute("data-qa");

            page.navigate("https://automationexercise.com/");
            Locator signUpLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(" Signup / Login"));
            signUpLink.click();

            // wait for the new page
            page.waitForURL("**/login");
            page.waitForLoadState();
            Locator loginEmail = page.getByTestId("login-email");
            loginEmail.fill("joe.doe27@email.com");
            loginEmail.screenshot(new Locator.ScreenshotOptions().setPath(Paths.get("img/fillInputEmail.png")));
            page.getByTestId("login-password").fill("Password@1");
            page.getByTestId("login-button").press("Enter");
            //page.waitForTimeout(5000);

            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("img/loginUser.png")));

            Locator loggedUser = page.locator("//b[text()=\"" + userName + "\"]");
            assertThat(loggedUser).containsText(userName);
            page.locator("//a[text()=\" Logout\"]").click();
            System.out.println(String.format("User %s was logged out successfully", userName));
            page.waitForTimeout(3000);

        }
    }

}
