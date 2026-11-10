package ru.vkusvill.tests.web.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vkusvill.tests.web.pages.HomePage;
import ru.vkusvill.tests.web.pages.SearchPage;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.qameta.allure.SeverityLevel.NORMAL;

@Epic("ВкусВилл")
@Feature("Поиск товаров")
@Story("Поиск по названию")
class SearchPageTest extends TestBase {

    private final HomePage homePage = new HomePage();
    private final SearchPage searchPage = new SearchPage();

    @Test
    @DisplayName("Поиск находит товар по названию")
    @Owner("QA")
    @Severity(CRITICAL)
    void findsProductByName() {
        String query = "молоко";

        homePage.openHome()
                .search(query);
        searchPage.shouldHaveProductMatching(query);
    }

    @Test
    @DisplayName("Несуществующий товар даёт пустую выдачу")
    @Owner("QA")
    @Severity(NORMAL)
    void showsEmptySearchResults() {
        String query = "zxqv998xxyy";

        homePage.openHome()
                .search(query);
        searchPage.shouldHaveNoResultsFor(query);
    }
}
