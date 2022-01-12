package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SearchPageObject extends MainPageObject {
    private static final String
            SEARCH_INIT_ELEMENT = "org.wikipedia:id/search_container",
            SEARCH_INPUT = "//*[contains(@text, 'Search…')]",
            SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
            SEARCH_RESULT_BY_TWO_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']" +
                    "//*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='{FIRST_SUBSTRING}']" +
                    "/following-sibling::android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_description' and @text='{SECOND_SUBSTRING}']",
            SEARCH_RESULT_ELEMENT = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']",
            SEARCH_EMPTY_RESULT_ELEMENT = "//*[@text='No results found']";

    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    /* TEMPlATES METHODS */
    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getResultSearchElementByTitleAndDescription(String firstSubstring, String secondSubstring) {
        return SEARCH_RESULT_BY_TWO_SUBSTRING_TPL
                .replace("{FIRST_SUBSTRING}", firstSubstring)
                .replace("{SECOND_SUBSTRING}", secondSubstring);
    }
    /* TEMPlATES METHODS */

    public void initSearchInput() {
        this.waitForElementPresent(By.id(SEARCH_INIT_ELEMENT), "Cannot find search init element");
        this.waitForElementAndClick(By.id(SEARCH_INIT_ELEMENT), "Cannot find and click search init element", 5);
    }

    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(By.id(SEARCH_CANCEL_BUTTON), "Cannot find search cancel button", 5);
    }

    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(By.id(SEARCH_CANCEL_BUTTON), "Search cancel button is still present!", 5);
    }

    public void clickCancelSearch() {
        this.waitForElementAndClick(By.id(SEARCH_CANCEL_BUTTON), "Cannot find and click search cancel button", 5);
    }

    public void typeSearchLine(String searchLine) {
        this.waitForElementAndSendKeys(
                By.xpath(SEARCH_INPUT), searchLine, "Cannot find and type into search input", 5);
    }

    public void waitForSearchResult(String substring) {
        String searchResultXpath = getResultSearchElement(substring);
        this.waitForElementPresent(By.xpath(searchResultXpath), "Cannot find search result with substring" + substring, 15);
    }

    public void clickByArticleWithSubstring(String substring) {
        String searchResultXpath = getResultSearchElement(substring);
        this.waitForElementAndClick(By.xpath(searchResultXpath), "Cannot find and click search result with substring" + substring, 10);
    }

    public void waitForElementByTitleAndDescription(String title, String description) {
        String searchResultXpath = getResultSearchElementByTitleAndDescription(title, description);
        this.waitForElementPresent(
                By.xpath(searchResultXpath),
                "Cannot find search result with title \"" + title + "\" and description \"" + description + '\"',
                15
        );
    }

    public int getAmountOfFoundArticles() {
        this.waitForElementPresent(
                By.xpath(SEARCH_RESULT_ELEMENT),
                "Cannot find anything by the request ",
                15
        );

        return this.getAmountOfElements(By.xpath(SEARCH_RESULT_ELEMENT));
    }

    public void waitForEmptyResultsLabel() {
        this.waitForElementPresent(By.xpath(SEARCH_EMPTY_RESULT_ELEMENT), "Cannot find empty result label by the request ", 15);

    }

    public void assertThereIsNoResultOfSearch() {
        this.waitForElementNotPresent(By.xpath(SEARCH_RESULT_ELEMENT), "We supposed not to find any result!", 5);
    }

    public void assertTherePageOfSearchIsClose() {
        this.waitForElementNotPresent(By.id("org.wikipedia:id/fragment_search_results"), "Search results are not missing", 5);
    }
}
