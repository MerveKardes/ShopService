# ShopService

Ein einfaches Java-Projekt zur Übung von objektorientierter Programmierung, Repositories, Interfaces, Tests und Lagerverwaltung.

## Funktionen

- Produkte verwalten
- Bestellungen erstellen und verwalten
- Bestellungen mit `List` oder `Map` speichern
- Preise und Produktmengen verwalten
- Gesamtpreis einer Bestellung berechnen
- Lagerbestand verwalten
- Lagerbestand bei einer Bestellung automatisch reduzieren
- Prüfen, ob genügend Produkte auf Lager sind
- Produktmenge in einer Bestellung ändern
- Wareneingang und Warenausgang verwalten

## Technologien

- Java 25
- Maven
- JUnit 5
- AssertJ
- GitHub Actions

## Tests

Das Projekt enthält Tests für die Repositories, Bestellungen, den ShopService und die Lagerverwaltung.

Alle Tests können mit Maven ausgeführt werden:

```bash
mvn test
```

## Continuous Integration

Das Projekt verwendet GitHub Actions für Continuous Integration.

Bei jedem Push auf den `main`-Branch und bei jedem Pull Request werden die Maven-Tests automatisch ausgeführt.