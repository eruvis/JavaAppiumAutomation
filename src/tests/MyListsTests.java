package tests;

import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {
    @Test
    public void testSaveFirstArticleToMyList() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForTitleElement();

        String articleTitle = ArticlePageObject.getArticleTitle();
        String nameOfFolder = "Learning programming";

        ArticlePageObject.addArticleToMyNewList(nameOfFolder);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListPageObject = new MyListsPageObject(driver);
        MyListPageObject.openFolderByName(nameOfFolder);
        MyListPageObject.swipeByArticleToDelete(articleTitle);
    }

    @Test
    public void testSaveTwoArticlesToMyList() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        String firstSearchRequest = "Java";
        SearchPageObject.typeSearchLine(firstSearchRequest);
        String firstArticleTitle = "Java (programming language)";
        SearchPageObject.clickByArticleWithSubstring(firstArticleTitle);

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForTitleElement();

        String nameOfFolder = "List for ex.5";
        ArticlePageObject.addArticleToMyNewList(nameOfFolder);
        ArticlePageObject.closeArticle();

        SearchPageObject.initSearchInput();
        String secondSearchRequest = "Appium";
        SearchPageObject.typeSearchLine(secondSearchRequest);
        String secondArticleTitle = "Appium";
        SearchPageObject.clickByArticleWithSubstring(secondArticleTitle);

        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.addArticleToMyExistingList(nameOfFolder);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListPageObject = new MyListsPageObject(driver);
        MyListPageObject.openFolderByName(nameOfFolder);

        MyListPageObject.swipeByArticleToDelete(firstArticleTitle);
        MyListPageObject.openArticleByName(secondArticleTitle);

        String articleTitle = ArticlePageObject.getArticleTitle();

        assertEquals(
                "We see unexpected title!",
                secondArticleTitle,
                articleTitle
        );
    }
}
