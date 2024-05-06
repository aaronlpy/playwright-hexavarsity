package pageObjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import core.ActionType;
import core.BrowserHandler;

public class ViewCartPage extends BrowserHandler {

    private final Page page;
    public final Locator checkOutLink;

    public ViewCartPage(Page page) {
        this.page = page;
        checkOutLink = page.locator("//a[text()=\"Proceed To Checkout\"]");
    }

    public void clickOnProceedToCheckout() throws Exception {
        PerformAction(ActionType.CLICK, page, checkOutLink, null, false);
    }

}
