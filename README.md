# Laby z dr. Norbertem Kozłowskim (polecam)
# Made by Jan Napieralski

## Lab1
Celem zajęć i utworzenie dwóch klas - `HelloWorld` oraz `Application` oraz opakowanie ich w archiwum JAR.

- Klasa `HelloWorld` powinna posiadać prostą metodę wyświetlającą na ekranie imię i nazwisko autora.
- Klasa `Application` stanowi punkt wejścia do aplikacji. Powinna ona zainicjalizować klasę `HelloWorld` oraz wywołać wcześniej utworzoną metodę.
- Klasy powinny być umieszczone w dedykowanym pakiecie (np. `com.pwr`).
- Aplikacja powinna spakowana w postaci archiwum JAR i być wykonywalna

## Lab2
Aplikacja dotyczy stworzenia systemu **maksymalizującego efektywność firm wytwarzających oprogramowanie IT**.

Firmy typu Software House realizują szereg projektów dla swoich klientów posiadając do dyspozycji pracowników o różnych kwalifikacjach. Algorytm powinien przypisać ludzi do odpowiednich projektów, aby zminimalizować braki ludzkie.
### Przykładowy plik wejściowy
```
PROJECTS
P1: JAVA JAVA QA PM
P2: PYTHON QA PM
P3: JS ANGULAR QA PM
P4: JAVA ANGULAR QA
P5: RUST

STAFF
R1: JAVA
R2: ANGULAR QA
R3: PYTHON QA
R4: JAVA PYTHON
R5: PM
R6: ANGULAR JS
R7: PM
R8: JAVA PYTHON
R9: QA
R10: BLOCKCHAIN PM QA
```

Wynikiem działania algorytmu jest przypisanie odpowiednich ludzi do projektu zgodnie z następującymi założeniami:

1. `QA` i `PM` mogą pracować jednocześnie na maksymalnie dwóch projektach (jeśli to ich jedyna cecha).
2. Pozostałe stanowiska pracują tylko na jednym projekcie
3. Jedna osoba może pełnić podwójną rolę w danym projekcie (np. `QA` i `PM`)

Algorytm powinien definiować odpowiednią funkcję dopasowania (miara efektywności danej kombinacji) a następnie podejmować odpowiednie akcje w celu jej optymalizacji.


## Lab3
**Feedback pracowników**

Jest to komponent pozwalający na zbieranie subiektywnych informacji nt. działań pracowników.

Funkcjonalności:

- dodanie opinii o pracowniku (data, id pracownika, rodzaj [pozytywna/negatywna], waga, komentarz),
- anulowanie opinii o pracowniku (na podstawie numeru id),
- analiza trendu  otrzymanych opinii dla danego pracownika (np trend tygodniowy, miesięczny, kwartalny) uwzględniający rodzaj opinii i wagę.
### Przykład
Pracownik X przez kilka dni z rzędu ma lekceważące nastawienie do swoich współpracowników. Manager dodaje tą informację do systemu, pomoże mu zachować obiektywne spojrzenie podczas późniejszej ewaluacji.

## Lab4 (nazwane demo1)
Celem projektu jest stworzenie aplikacji typu **Diet Builder** posiadających następujące funkcjonalności:

- użytkownik może dodawać, edytować i usuwać pojedyncze produkty definiując ich metadane, np.

```bash
Banan (100g):
- Węglowodany (g): 23,
- Tłuszcze (g): 0,
- Białko (g): 1,
- Kategoria: Owoce
```

- Użytkownik może tworzyć, edytować i usuwać posiłki grupując określone produkty w sekcje (np. śniadanie, obiad, kolacja), np.

```bash
Nazwa: Lunch
Produkty:
	- Bułka (100g) x 2
  - Szynka (plaster) x 2
  - Masło (5g) x 4

Nazwa: Podwieczorek v3
Produkty:
  - Banan (100g) x 0.5
```

- Każdy posiłek powinen prezentować sumaryczną ilość przyjętych makroskładników oraz obliczać ilość kalorii (dynamicznie na podstawie makroskładników),
- Użytkownik ma możliwość wygenerowania listy zakupów dla określonych posiłków w formacie PDF. Produkty powinne być scalone oraz pogrupowane per kategoria (np. jeśli `banan` występował w kilku posiłkach, na liście zakupów powinien widnieć tylko jeden wpis w kategorii `Owoce`).

- ## Lab5
- Celem zajęć jest implementacja gry Game of Life zaproponowanej przez brytyjskiego matematyka Johna Conwaya.
- Wykorzystamy standardowy zestaw reguł ([Conway 23/3](https://pl.wikipedia.org/wiki/Gra_w_%C5%BCycie)):

1. Martwa komórka, która ma dokładnie 3 żywych sąsiadów, staje się żywa w następnej jednostce czasu (*rodzi się*)
2. Żywa komórka z 2 lub 3 żywymi sąsiadami pozostaje nadal żywa; przy innej liczbie sąsiadów umiera (*samotność* / *zatłoczenie*)

Poniżej przestawiono wymagania dotyczące implementacji projektu.

<aside>
💡 **Przetwarzanie równoległe**
Prosta, sekwencyjna implementacja *Game of Life* może być zrealizowana za pomocą trzech pętli (jedna dla jednostek czasu, dwie pozostałe do iteracji po planszy). Warto jednak zauważyć, że obliczenie nowego stan każdej komórki na planszy jest niezależną operacją i może być przeprowadzone w dowolnej kolejności. Nie oznacza to jednak, że przetwarzania każdej komórki powinno odbywać się o osobnym wątku - to zbyt duża optymalizacja.

</aside>

<aside>
💡 Obszar gry (mapa 2D) powinna być zamodelowana jako **torus** - każda komórka posiada dokładnie osiem sąsiadów - szczególnie komórki na krawędziach.

```
x  @  x  _  _  _  _
x  x  x  _  _  _  _
_  _  _  _  _  _  _
_  _  _  _  _  _  _
_  _  _  _  _  _  _
_  _  _  _  _  _  _
x  x  x  _  _  _  _
```

W tym przypadku komórki `X` są sąsiadami komórki `@`.

</aside>

### Format pliku wejściowego

Plik wejściowy zawiera linie tekstu ASCII w następującej konwencji:

- L1-L3 określają rozmiar planszy oraz liczbę iteracji
- L4 określa liczbę współrzędnych żywych komórek
- L5-L* określa współrzędne kolejnych żywych komórek (punkt `0,0` znajduje się w lewym górnym rogu)

```
30
30
100
5
29 1
28 2
27 0
27 1
27 2
```

Należy zaimplementować kilka plików wejściowych demonstrujących działanie aplikacji ([stałe struktury, oscylatory, statki kosmiczne](https://en.wikipedia.org/wiki/Conway's_Game_of_Life#Examples_of_patterns)). Aplikacja powinna walidować poprawność pliku konfiguracyjnego.

**Partycjonowanie**: Column-based

**Synchronizacja wątków:** mechanizm `CyclicBarrier`

Każdy wątek w programie jako argument powinien pobierać aktualnie badane pola (współrzędne i sąsiedzi). Jako wynik działania należy zaktualizować globalną współdzieloną zmienną ([link](https://www.baeldung.com/java-synchronized-collections)). W momencie synchronizacji wątków nowy stan mapy powinen zostać zrekonstruowany na jej podstawie.

## Lab67
### Kluczowe funkcjonalności

1. **Hybrydowa komunikacja sieciowa** - użycie zarówno komunikacji poprzez RMI jak i gniazd TCP/IP.
2. **Mechanika gry** - implementacja silnika zarządzającego mechaniką gry w kółko i krzyżyk

### Obie grupy

- Należy wykorzystać mechanizm RMI w celu nawiązania połączenia między dwoma graczami oraz obsługę mechaniki gry (np. parowanie graczy, inicjalizowanie stanu gry, wymiana ruchów, walidacja I/O).
- Mechanika gry powinna być zaimplementowana w aplikacji serwera.
- Należy przemyśleć argumenty pobierane przez aplikacje JAR serwer/klient.
- Serwer powinen obsługiwać więcej niż jednego gracza w tym samym momencie (np. poprzez realizację gier w dedykowanych pokojach lub tworzenie tokenów poszczególnych sesji wymienianych przez graczy).
- Serwer powinen obliczać statystyki graczy w danej sesji - ilość wygranych, remisów oraz porażek.
- Aplikacja serwera powinna logować w konsoli kluczowe dane (parowanie użytkowników, błędy, koniec gry, itp).

Implementacja “trybu obserwatora”. Polega on na tym, że niezależny klient może podłączyć się do istniejącej sesji i mieć możliwość obserwacji trwającej rozgrywki. Tryb należy zaimplementować wykorzystując protokół TCP/IP.
