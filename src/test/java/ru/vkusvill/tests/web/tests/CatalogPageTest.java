package ru.vkusvill.tests.web.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vkusvill.tests.web.pages.CatalogPage;
import ru.vkusvill.tests.web.pages.HomePage;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.qameta.allure.SeverityLevel.NORMAL;

@Epic("ВкусВилл")
@Feature("Каталог")
@Story("Просмотр ассортимента")
class CatalogPageTest extends TestBase {

    private static final String DAIRY = "Молочные продукты, яйцо";
    private static final String COTTAGE_CHEESE = "Творог";
    private final HomePage homePage = new HomePage();
    private final CatalogPage catalogPage = new CatalogPage();

    @Test
    @DisplayName("Категория каталога показывает товары")
    @Owner("QA")
    @Severity(CRITICAL)
    void opensCategoryWithProducts() {
        homePage.openHome()
                .openCatalog();
        catalogPage.openCategory(DAIRY)
                .shouldShowProductsIn(DAIRY);
    }

    @Test
    @DisplayName("Подкатегория каталога показывает товары")
    @Owner("QA")
    @Severity(NORMAL)
    void opensSubcategoryWithProducts() {
        homePage.openHome()
                .openCatalog();
        catalogPage.openCategory(DAIRY)
                .openSubcategory(COTTAGE_CHEESE)
                .shouldShowProductsIn(COTTAGE_CHEESE);
    }

    @Test
    @DisplayName("Товары сортируются сначала по убыванию, затем по возрастанию цены")
    @Owner("QA")
    @Severity(NORMAL)
    void sortsProductsByDescendingThenAscendingPrice() {
        homePage.openHome()
                .openCatalog();
        catalogPage.openCategory(DAIRY)
                .selectSorting("Сначала дороже")
                .shouldHaveDescendingPrices()
                .selectSorting("Сначала дешевле")
                .shouldHaveAscendingPrices();
    }


}
