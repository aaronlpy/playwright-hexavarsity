package core;

import com.microsoft.playwright.Download;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import utils.Utils;

import java.nio.file.Paths;

public class BrowserHandler {


    public void PerformAction(ActionType actionType, Page page, Locator locator, String value, boolean takeScreenshot) throws Exception {
        try {

            switch (actionType) {
                case NAVIGATE -> Navigate(page, value, takeScreenshot);
                case CLICK -> Click(page, locator, takeScreenshot);
                case DOUBLE_CLICK -> DblClick(page, locator, takeScreenshot);
                case FILL -> Fill(page, locator, value, takeScreenshot);
                case SCROLL_JS -> scrollJS(page, value);
                case PRESS -> Press(page, locator, value, takeScreenshot);
                case HOVER_CLICK -> HoverAndClick(page, locator, value, takeScreenshot);
                case DRAG_DROP -> DragAndDrop(page, locator, value, takeScreenshot);
                case GET_INNER_TEXT -> GetInnerText(page, locator, takeScreenshot);
                case DOWNLOAD_FILE -> DownloadFile(page, locator);

            }

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private void Navigate(Page page, String value, Boolean takeScreenshot) throws Exception {
        try {
            page.navigate(value);
            if (takeScreenshot)
                Utils.takeScreenshot(page, "navigate");


        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private void Click(Page page, Locator element, Boolean takeScreenshot) throws Exception {
        try {

            if (takeScreenshot)
                Utils.takeScreenshot(page, "clickOnElement" + element.textContent().replace(" ", "_"));

            element.click();


        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private void DblClick(Page page, Locator element, Boolean takeScreenshot) throws Exception {
        try {

            if (takeScreenshot)
                Utils.takeScreenshot(page, "clickOnElement" + element.textContent().replace(" ", "_"));


            element.dblclick();


        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private void Fill(Page page, Locator element, String value, Boolean takeScreenshot) throws Exception {
        try {
            element.fill(value);

            if (takeScreenshot)
                Utils.takeScreenshot(page, "TypeOnElement");

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private void Press(Page page, Locator element, String value, boolean takeScreenshot) throws Exception {
        try {
            if (takeScreenshot)
                Utils.takeScreenshot(page, "PressOnElement");
            element.press(value);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    private void scrollJS(Page page, String value) throws Exception {
        try {

            if (!value.contains(","))
                throw new Error("Is not a valid input value");

            String[] opts = value.split(",");
            page.evaluate("window.scrollBy(" + opts[0] + "," + opts[1] + ")");

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private void HoverAndClick(Page page, Locator locator, String selector, boolean takeScreenshot) throws Exception {
        try {

            page.waitForSelector(selector).hover();
            locator.first().click();

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private void DragAndDrop(Page page, Locator locator, String target, boolean takeScreenshot) throws Exception {
        try {

            locator.dragTo(page.locator(target));

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private String GetInnerText(Page page, Locator element, Boolean takeScreenshot) throws Exception {
        try {

            if (takeScreenshot)
                Utils.takeScreenshot(page, "TypeOnElement");

            return element.innerText();

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private void DownloadFile(Page page, Locator locator) throws Exception {
        try{
            Download download = page.waitForDownload(locator::click);
            download.saveAs(Paths.get("downloads/" + download.suggestedFilename()));
            System.out.println("File was successfully downloaded with name: " + download.suggestedFilename());
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }


    }


}
