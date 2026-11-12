# vkusvill-qa-automation

Веб-автотесты ВкусВилла на Java 17, JUnit 5, Selenide и Allure. Семь сценариев в пакете `ru.vkusvill.tests.web` проверяют поиск, каталог и карточку товара.

Для локального запуска нужны JDK 17 и Chrome. При первом запуске Gradle Wrapper загружает зависимости, а Selenium Manager подбирает ChromeDriver.

### Windows PowerShell

```powershell
.\gradlew.bat cleanTest test allureReport --clean
.\gradlew.bat cleanTest test --tests 'ru.vkusvill.tests.web.tests.SearchPageTest.findsProductByName'
```

### macOS и Linux

```sh
sh ./gradlew cleanTest test allureReport --clean
```

Allure сохраняет результаты в `build/allure-results`. Задача `cleanTest` очищает результаты предыдущего прогона. Готовый отчёт можно открыть в браузере из файла `build/reports/allure-report/allureReport/index.html`.
