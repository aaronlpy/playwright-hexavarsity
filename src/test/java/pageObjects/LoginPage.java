package pageObjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import core.ActionType;
import core.BrowserHandler;

public class LoginPage extends BrowserHandler {

    private final Page page;
    private final Locator emailInput;
    private final Locator emailPassword;
    private final Locator loginBtn;

    public LoginPage(Page page) {
        this.page = page;
        emailInput = page.getByTestId("login-email");
        emailPassword = page.getByTestId("login-password");
        loginBtn = page.getByTestId("login-button");
    }

    public void fillForm(String email, String password) throws Exception {
        try {
            PerformAction(ActionType.FILL, page, emailInput, email, false);
            PerformAction(ActionType.FILL, page, emailPassword, password, false);
            PerformAction(ActionType.PRESS, page, loginBtn, "Enter", false);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
