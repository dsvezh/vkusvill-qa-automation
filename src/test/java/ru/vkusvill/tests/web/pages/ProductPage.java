package ru.vkusvill.tests.web.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProductPage {

    private final SelenideElement heading = $("h1.Product__title");
    private final SelenideElement composition = $(".Product__composition ._sostav");
    private final ElementsCollection nutritionValues =
            $$(".VV23_DetailProdPageAccordion__EnergyWrap .VV23_DetailProdPageAccordion__EnergyValue");

    @Step("Проверить, что заголовок карточки совпадает с названием «{name}»")
    public ProductPage shouldHaveName(String name) {
        heading.shouldBe(visible).shouldHave(exactText(name));
        return this;
    }

    @Step("Проверить, что в карточке указан состав товара")
    public ProductPage shouldShowComposition() {
        composition.shouldBe(visible).shouldNotBe(empty);
        return this;
    }

    @Step("Проверить, что в карточке указана пищевая ценность товара")
    public ProductPage shouldShowNutrition() {
        nutritionValues.shouldHave(sizeGreaterThan(0));
        for (SelenideElement value : nutritionValues.asFixedIterable()) {
            value.shouldBe(visible).shouldNotBe(empty);
        }
        return this;
    }

}
