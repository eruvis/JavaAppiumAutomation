package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {
    @Test
    public void testSaveFirstArticleToMyList() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();

        String articleTitle = ArticlePageObject.getArticleTitle();
        String nameOfFolder = "Learning programming";

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyNewList(nameOfFolder);

        } else {
            ArticlePageObject.addArticlesToMySave();
        }
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()) {
            MyListPageObject.openFolderByName(nameOfFolder);

        }

        MyListPageObject.swipeByArticleToDelete(articleTitle);
    }

    @Test
    public void testSaveTwoArticlesToMyList() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        String firstSearchRequest = "Java";
        SearchPageObject.typeSearchLine(firstSearchRequest);
        String firstArticleTitle = "Java (programming language)";
        SearchPageObject.clickByArticleWithSubstring(firstArticleTitle);

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
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

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
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
