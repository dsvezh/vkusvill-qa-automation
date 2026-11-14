package ru.vkusvill.tests.web.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class SearchPage {

    private final ElementsCollection productNames =
            $$("#section_lvl2 .js-product-v-tizer__title-text");
    private final SelenideElement emptyMessage = $(".VV23_SearchPageEmptyBlock__HeaderCol._text");

    @Step("Проверить, что в выдаче есть товар по запросу «{query}»")
    public SearchPage shouldHaveProductMatching(String query) {
        findProduct(query).shouldBe(visible);
        return this;
    }

    @Step("Проверить сообщение об отсутствии товаров и пустую выдачу для запроса «{query}»")
    public SearchPage shouldHaveNoResultsFor(String expectedMessage, String query) {
        emptyMessage.shouldBe(visible).shouldHave(text(expectedMessage), text(query));
        productNames.shouldBe(empty);
        return this;
    }

    @Step("Открыть товар из выдачи по запросу «{query}»")
    public String openProductMatching(String query) {
        SelenideElement product = findProduct(query).shouldBe(visible);
        String name = product.getText();
        product.click();
        return name;
    }

    private SelenideElement findProduct(String query) {
        return productNames.findBy(text(query));
    }
}
