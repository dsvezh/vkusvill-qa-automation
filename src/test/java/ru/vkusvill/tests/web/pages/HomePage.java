package ru.vkusvill.tests.web.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class HomePage {

    private final SelenideElement searchInput = $("#js-vv21-search__search-input");
    private final SelenideElement catalogLink = $("#js-header-big-catalog-dmenu-shower");

    @Step("Открыть главную страницу")
    public HomePage openHome() {
        open("/");
        return this;
    }

    @Step("Выполнить поиск по запросу «{query}»")
    public HomePage search(String query) {
        searchInput.setValue(query).pressEnter();
        return this;
    }

    @Step("Открыть меню каталога")
    public HomePage openCatalog() {
        catalogLink.click();
        return this;
    }
}
