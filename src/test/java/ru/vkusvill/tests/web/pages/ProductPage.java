package ru.vkusvill.tests.web.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProductPage {

    private static final String NUTRITION_VALUE_SELECTOR = ".VV23_DetailProdPageAccordion__EnergyValue";

    private final SelenideElement heading = $("h1.Product__title");
    private final SelenideElement composition = $(".Product__composition ._sostav");
    private final ElementsCollection nutritionLabels =
            $$(".VV23_DetailProdPageAccordion__EnergyWrap .VV23_DetailProdPageAccordion__EnergyDesc");

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

    @Step("Проверить наличие показателей пищевой ценности и их значений: {expectedNutrients}")
    public ProductPage shouldShowNutrition(String... expectedNutrients) {
        for (String nutrient : expectedNutrients) {
            SelenideElement label = nutritionLabels.findBy(exactText(nutrient)).shouldBe(visible);
            SelenideElement value = label.parent().$(NUTRITION_VALUE_SELECTOR);
            value.shouldBe(visible).shouldNotBe(empty);
        }
        return this;
    }

}

