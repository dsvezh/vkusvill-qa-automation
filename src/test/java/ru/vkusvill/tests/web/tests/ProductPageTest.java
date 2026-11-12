package ru.vkusvill.tests.web.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vkusvill.tests.web.pages.HomePage;
import ru.vkusvill.tests.web.pages.ProductPage;
import ru.vkusvill.tests.web.pages.SearchPage;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.qameta.allure.SeverityLevel.NORMAL;

@Epic("ВкусВилл")
@Feature("Карточка товара")
@Story("Просмотр информации о товаре")
@DisplayName("Карточка товара")
class ProductPageTest extends TestBase {

    private final HomePage homePage = new HomePage();
    private final SearchPage searchPage = new SearchPage();
    private final ProductPage productPage = new ProductPage();

    @Test
    @DisplayName("Из выдачи открывается карточка выбранного товара")
    @Owner("QA")
    @Severity(CRITICAL)
    void opensSelectedProductFromSearch() {
        String query = "молоко";

        homePage.openHome()
                .search(query);
        String selectedName = searchPage.openProductMatching(query);
        productPage.shouldHaveName(selectedName);
    }

    @Test
    @DisplayName("Карточка товара показывает состав и пищевую ценность")
    @Owner("QA")
    @Severity(NORMAL)
    void showsCompositionAndNutrition() {
        productPage.openProduct("/goods/moloko-2-36296/")
                .shouldShowComposition()
                .shouldShowNutrition();
    }
}
