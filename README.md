# ShopService

Ein einfaches Java-Projekt zur Übung von objektorientierter Programmierung, Repositories, Interfaces, Exceptions, Streams, Tests und Lagerverwaltung.

## Funktionen

- Produkte verwalten
- Bestellungen erstellen und verwalten
- Bestellungen mit `List` oder `Map` speichern
- Automatische Order-ID-Generierung mit UUID
- Order-Status verwalten (`PROCESSING`, `IN_DELIVERY`, `COMPLETED`)
- Bestellungen nach Status filtern
- Älteste Bestellung pro Status ermitteln
- Erstellungszeitpunkt einer Bestellung mit `Instant` speichern
- Produkte mit `Optional<Product>` suchen
- Eigene `ProductNotFoundException` verwenden, wenn ein Produkt nicht existiert
- Preise und Produktmengen verwalten
- Gesamtpreis einer Bestellung berechnen
- Lagerbestand verwalten
- Lagerbestand bei einer Bestellung automatisch reduzieren
- Prüfen, ob genügend Produkte auf Lager sind
- Produktmenge in einer Bestellung ändern
- Wareneingang und Warenausgang verwalten

## Architektur

Das Projekt verwendet verschiedene Repositories und Services:

- `ProductRepo` verwaltet die Produkte.
- `OrderRepo` definiert die Methoden für die Verwaltung von Bestellungen.
- `OrderListRepo` und `OrderMapRepo` sind verschiedene Implementierungen von `OrderRepo`.
- `ShopService` enthält die Geschäftslogik.
- `IdService` abstrahiert die Generierung von Order-IDs.
- Lombok `@RequiredArgsConstructor` wird für Constructor Injection verwendet.

## Technologien

- Java 25
- Maven
- JUnit 5
- AssertJ
- Lombok
- Java Streams
- GitHub Actions

## Tests

Das Projekt enthält Tests für:

- Product Repository
- Order Repositories
- Bestellungen
- ShopService
- Exceptions
- Order-Status
- Timestamps
- Lagerverwaltung

Alle Tests können mit Maven ausgeführt werden:

```bash
mvn test
```

## Continuous Integration

Das Projekt verwendet GitHub Actions für Continuous Integration.

Bei jedem Push auf den `main`-Branch und bei jedem Pull Request werden die Maven-Tests automatisch ausgeführt.