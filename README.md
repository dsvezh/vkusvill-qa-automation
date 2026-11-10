# vkusvill-qa-automation

Заготовка проекта автотестов на Java 17, Gradle и JUnit 5.

Для запуска нужен JDK 17. Gradle Wrapper скачает Gradle и зависимости при первом запуске.

### Windows PowerShell

```powershell
.\gradlew.bat test
```

### macOS и Linux

```sh
sh ./gradlew test
```

Добавляйте автотесты в `src/test/java/ru/vkusvill/qa`. Отчёт появится в `build/reports/tests/test/index.html`.
