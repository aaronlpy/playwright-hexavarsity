package pageObjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CheckOutPage {

    private final Page page;
    public final Locator placeOrder;

    public CheckOutPage(Page page) {
        this.page = page;
        this.placeOrder = page.locator("a[href=\"/payment\"]");
    }
}
