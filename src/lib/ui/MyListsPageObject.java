package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class MyListsPageObject extends MainPageObject {
    private static final String
            FOLDER_BY_NAME_TPL = "//*[@resource-id='org.wikipedia:id/item_title'][@text='{FOLDER_NAME}']",
            ARTICLE_BY_TITLE_TPL = "//*[@text='{TITLE}']";

    public MyListsPageObject(AppiumDriver driver) {
        super(driver);
    }

    /* TEMPlATES METHODS */
    public static String getFolderXpathByName(String nameOfFolder) {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", nameOfFolder);
    }

    public static String getSavedArticleXpathByTitle(String articleTitle) {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", articleTitle);
    }
    /* TEMPlATES METHODS */

    public void openFolderByName(String nameOfFolder) {
        String folderNameXpath = getFolderXpathByName(nameOfFolder);

        this.waitForElementAndClick(
                By.xpath(folderNameXpath),
                "Cannot find folder by name " + nameOfFolder,
                5
        );
    }

    public void waitForArticleToAppearByTitle(String articleTitle) {
        String articleXpath = getSavedArticleXpathByTitle(articleTitle);

        this.waitForElementPresent(By.xpath(articleXpath), "Cannot find saved article by title " + articleTitle, 15);
    }

    public void waitForArticleToDisappearByTitle(String articleTitle) {
        String articleXpath = getSavedArticleXpathByTitle(articleTitle);

        this.waitForElementNotPresent(By.xpath(articleXpath), "Saved article still present with title " + articleTitle, 15);
    }

    public void swipeByArticleToDelete(String articleTitle) {
        String articleXpath = getSavedArticleXpathByTitle(articleTitle);

        this.waitForArticleToAppearByTitle(articleTitle);
        this.swipeElementToLeft(By.xpath(articleXpath), "Cannot find send article");
        this.waitForArticleToDisappearByTitle(articleTitle);
    }
}
