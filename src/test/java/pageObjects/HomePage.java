package pageObjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import core.ActionType;
import core.BrowserHandler;

public class HomePage extends BrowserHandler {

    private final Page page;

    public final Locator signUpLink;
    private final Locator productsLink;
    private final Locator logout;

    public HomePage(Page page) {
        this.page = page;
        signUpLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(" Signup / Login"));
        productsLink = page.locator("//a[text()=\" Products\"]");
        logout = page.locator("//a[text()=\" Logout\"]");
    }

    public void clickOnSignUp() throws Exception {
        try {

            PerformAction(ActionType.CLICK, page, signUpLink, null, false);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void clickOnProducts() {
        productsLink.click();
    }

    public void clickOnLogOut() {
        logout.click();
    }


}
