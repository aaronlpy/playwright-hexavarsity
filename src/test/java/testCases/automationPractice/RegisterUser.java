package testCases.automationPractice;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class RegisterUser {
    public static void main(String[] args) {

        try(Playwright playwright = Playwright.create()){
            Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(false)
            );

            Page page = browser.newPage();

            // set data-qa as id
            playwright.selectors().setTestIdAttribute("data-qa");

            page.navigate("https://automationexercise.com/");
            Locator signUpLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(" Signup / Login"));
            signUpLink.click();

            // wait for the new page
            page.waitForURL("**/login");

            // fill username and password
            page.getByTestId("signup-name").fill("Joe Doe");
            page.getByTestId("signup-email").fill("joe.doe22@email.com");
            page.locator("//button[@type=\"submit\" and text()= \"Signup\"]").click();

            // wait for signup page
            page.waitForURL("**/signup");

            // check label
            Locator userTitle = page.locator("id=uniform-id_gender1");
            if (!userTitle.isChecked())
                userTitle.check();
            // type new password
            page.getByTestId("password").fill("Password@1");

            // Select DOB
            page.selectOption("#days", "8");
            page.selectOption("#months", "July");
            page.selectOption("#years", "1994");

            // check newsletter and special offers
            /*Locator isNewsletterChecked = page.getByLabel("Sign up for our newsletter!");
            if (isNewsletterChecked.isChecked())
                isNewsletterChecked.check();

            page.getByLabel("Receive special offers from our partners!").check();*/

            page.getByLabel("Sign up for our newsletter!").check();
            page.getByLabel("Receive special offers from our partners!").check();


            page.getByTestId("first_name").fill("Joe");
            page.getByTestId("last_name").fill("Doe");
            page.locator("#address1").fill("Some Random Address");
            page.selectOption("#country", "United States");
            page.locator("#state").fill( "Texas");
            page.locator("#city").fill( "Colony");
            page.mouse().move(0, 1000);
            page.getByTestId("zipcode").fill("75056");
            page.getByTestId("mobile_number").fill("7871563692");
            page.waitForTimeout(5000);
            page.locator("id=create-account").click();

            page.waitForURL("**/account_created");

            Locator successMsg = page.getByTestId("account-created");
            assertThat(successMsg).containsText("Account Created");
            page.pause();

        }

    }

}
