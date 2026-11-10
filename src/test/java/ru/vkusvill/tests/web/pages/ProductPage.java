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
import static com.codeborne.selenide.Selenide.open;

public class ProductPage {

    private final SelenideElement heading = $("h1.Product__title");
    private final ElementsCollection accordionButtons = $$(".js-vv23-detail-prod-page-accordion__toggler");
    private final SelenideElement composition = $(".Product__composition ._sostav");
    private final SelenideElement nutrition = $(".VV23_DetailProdPageAccordion__EnergyWrap");
    private final ElementsCollection nutritionValues = nutrition.$$(".VV23_DetailProdPageAccordion__EnergyValue");

    @Step("Открыть карточку товара по адресу «{path}»")
    public ProductPage openProduct(String path) {
        open(path);
        return this;
    }

    @Step("Проверить, что заголовок карточки совпадает с названием «{name}»")
    public ProductPage shouldHaveName(String name) {
        heading.shouldBe(visible).shouldHave(exactText(name));
        return this;
    }

    @Step("Проверить, что в карточке указан состав товара")
    public ProductPage shouldShowComposition() {
        expandIfHidden("Состав", composition);
        composition.shouldBe(visible).shouldNotBe(empty);
        return this;
    }

    @Step("Проверить, что в карточке указана пищевая ценность товара")
    public ProductPage shouldShowNutrition() {
        expandIfHidden("О товаре", nutrition);
        nutritionValues.filterBy(visible).filterBy(empty.negate()).shouldHave(sizeGreaterThan(0));
        return this;
    }

    private void expandIfHidden(String name, SelenideElement content) {
        if (!content.isDisplayed()) {
            accordionButtons.findBy(exactText(name)).click();
        }
    }
}
