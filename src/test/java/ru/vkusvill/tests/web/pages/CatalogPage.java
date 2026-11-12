package ru.vkusvill.tests.web.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.Wait;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;

public class CatalogPage {

    private static final String REGULAR_PRICE_SELECTOR = ".Price._last";

    private final SelenideElement heading = $(".js-catalog-h1");
    private final ElementsCollection categoryLinks = $$(".VVCategCards2020__ItemTitle");
    private final ElementsCollection subcategoryLinks = $$(".VVCatalog2020Submenu__ItemTitle");
    private final ElementsCollection products = $$("#section_lvl2 .ProductCard");
    private final SelenideElement sortingDropdown = $(".js-vv25-sort-dropdown");
    private final SelenideElement sortingSelection = sortingDropdown.$(".js-dropdown-header");
    private final ElementsCollection sortingOptions = sortingDropdown.$$(".js-dropdown-option");
    private final ElementsCollection currentPrices = $$("#section_lvl2 .ProductCard__price .Price--label");

    @Step("Открыть категорию «{name}»")
    public CatalogPage openCategory(String name) {
        categoryLinks.findBy(exactText(name)).click();
        return this;
    }

    @Step("Открыть подкатегорию «{name}»")
    public CatalogPage openSubcategory(String name) {
        subcategoryLinks.findBy(exactText(name)).click();
        return this;
    }

    @Step("Проверить заголовок «{name}» и наличие товаров")
    public CatalogPage shouldShowProductsIn(String name) {
        heading.shouldBe(visible).shouldHave(exactText(name));
        products.filterBy(visible).shouldHave(sizeGreaterThan(0));
        return this;
    }

    @Step("Выбрать сортировку «{option}»")
    public CatalogPage selectSorting(String option) {
        WebElement firstProduct = products.first().shouldBe(visible).toWebElement();
        sortingSelection.click();
        sortingOptions.findBy(exactText(option)).click();
        sortingSelection.shouldHave(exactText(option));
        Wait().until(stalenessOf(firstProduct));
        return this;
    }

    @Step("Проверить, что обычные цены идут по возрастанию")
    public CatalogPage shouldHaveAscendingPrices() {
        List<BigDecimal> prices = catalogPrices();
        assertTrue(prices.size() >= 2, "Для проверки нужны как минимум две цены");
        assertHaveDifferentPrices(prices);
        assertEquals(prices.stream().sorted().toList(), prices, "Цены должны идти по возрастанию");
        return this;
    }

    @Step("Проверить, что обычные цены идут по убыванию")
    public CatalogPage shouldHaveDescendingPrices() {
        List<BigDecimal> prices = catalogPrices();
        assertTrue(prices.size() >= 2, "Для проверки нужны как минимум две цены");
        assertHaveDifferentPrices(prices);
        assertEquals(prices.stream().sorted(Comparator.reverseOrder()).toList(), prices,
                "Цены должны идти по убыванию");
        return this;
    }

    private void assertHaveDifferentPrices(List<BigDecimal> prices) {
        assertTrue(prices.stream().anyMatch(price -> price.compareTo(prices.get(0)) != 0),
                "Для проверки смены сортировки нужны как минимум две разные цены");
    }

    public List<BigDecimal> catalogPrices() {
        List<BigDecimal> prices = new ArrayList<>();
        for (SelenideElement price : currentPrices.filterBy(visible).shouldHave(sizeGreaterThan(1)).asFixedIterable()) {
            String priceText = price.getOwnText();
            SelenideElement regularPrice = price.parent().$(REGULAR_PRICE_SELECTOR);
            if (regularPrice.isDisplayed()) {
                priceText = regularPrice.getOwnText();
            }
            prices.add(parsePrice(priceText));
        }
        return prices;
    }

    private BigDecimal parsePrice(String value) {
        String amount = value.split("/", 2)[0];
        return new BigDecimal(amount.replaceAll("[\\s\\p{Z}]", "").replace(',', '.'));
    }
}
