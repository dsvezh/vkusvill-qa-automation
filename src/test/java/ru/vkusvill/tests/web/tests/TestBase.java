package ru.vkusvill.tests.web.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.logging.Level;

abstract class TestBase {

    @BeforeAll
    static void configureBrowser() {
        Configuration.baseUrl = System.getProperty("baseUrl", "https://vkusvill.ru");
        Configuration.browserSize = System.getProperty("browserSize", "1920x1080");
        Configuration.headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        Configuration.pageLoadStrategy = "eager";
        Configuration.timeout = 10_000;

        LoggingPreferences logging = new LoggingPreferences();
        logging.enable(LogType.BROWSER, Level.ALL);
        if ("chrome".equals(Configuration.browser)) {
            Configuration.browserCapabilities.setCapability("goog:loggingPrefs", logging);
        }
    }

    @AfterEach
    void attachBrowserArtifactsAndClose() {
        if (!WebDriverRunner.hasWebDriverStarted()) {
            return;
        }

        try {
            attachScreenshot();
            attachBrowserLogs();
        } finally {
            Selenide.closeWebDriver();
        }
    }

    private void attachScreenshot() {
        try {
            byte[] screenshot = Selenide.screenshot(OutputType.BYTES);
            Allure.addAttachment("Скриншот страницы", "image/png", new ByteArrayInputStream(screenshot), ".png");
        } catch (WebDriverException exception) {
            Allure.addAttachment("Ошибка создания скриншота", exception.toString());
        }
    }

    private void attachBrowserLogs() {
        try {
            List<String> logs = Selenide.getWebDriverLogs(LogType.BROWSER, Level.ALL);
            String contents = logs.isEmpty() ? "Консоль браузера пуста" : String.join("\n", logs);
            Allure.addAttachment("Логи браузера", "text/plain", contents);
        } catch (UnsupportedOperationException | WebDriverException exception) {
            Allure.addAttachment("Ошибка получения логов браузера", exception.toString());
        }
    }
}
