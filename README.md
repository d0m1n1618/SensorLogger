# SensorLogger (Android / Jetpack Compose)

## Opis aplikacji
SensorLogger to prosta aplikacja mobilna na Androida służąca do wykonywania pomiarów z kilku źródeł danych urządzenia, zapisywania wyników lokalnie oraz prezentowania ich użytkownikowi w formie listy, statystyk i wykresu.

Aplikacja umożliwia:
- wykonanie pojedynczego pomiaru (GPS + ruch + hałas),
- przeglądanie wyników w postaci listy,
- wejście w szczegóły wybranego pomiaru,
- usunięcie wybranego pomiaru,
- reset (usunięcie wszystkich danych).

---

## Użyte sensory / źródła danych
1. **GPS / Lokalizacja**  
   - pobieranie aktualnej pozycji (szer./dł. geograficzna) oraz dokładności (± metry)

2. **Akcelerometr**  
   - wyznaczanie ruchu jako peak przyspieszenia (w projekcie: peak/linear peak)

3. **Mikrofon**  
   - pomiar poziomu hałasu (wartość w skali 0–100 na podstawie RMS z AudioRecord)

---

## Funkcjonalności
- ✅ Akwizycja danych z min. 3 źródeł: GPS + akcelerometr + mikrofon
- ✅ Reprezentacja danych: lista + statystyki (dashboard) + wykres
- ✅ Min. 2 ekrany: Dashboard + Szczegóły (Navigation Compose)
- ✅ Type-safe routes: @Serializable routes + composable<Route>()
- ✅ Trwałość danych: Room (lokalna baza)
- ✅ Interakcja: pomiar, przeglądanie, usuwanie i reset danych
- ✅ Obsługa runtime permissions: lokalizacja i mikrofon

---

### 1) Ekran Dashboard – lista + statystyki + wykres
- karta „Statystyki”
- wykres hałasu z osiami
- lista pomiarów
  
<img width="405" height="840" alt="sensor" src="https://github.com/user-attachments/assets/2e00cb6e-e114-4c6b-844a-fa1f56cdf33f" />


### 2) Ekran Szczegóły pomiaru
- data/czas
- GPS (lat/lon) i dokładność
- ruch peak
- hałas
- przycisk „Usuń pomiar”
- 
<img width="405" height="832" alt="sensor2" src="https://github.com/user-attachments/assets/e5a92e99-6fe6-4c47-a66c-bce820800f88" />

---

## Instrukcja uruchomienia

### Wymagania
- Android Studio
- Android SDK
- Telefon z Androidem lub emulator (AVD)
- Minimalny SDK projektu: **minSdk 34**

### Uruchomienie w Android Studio
1. Otwórz projekt w Android Studio (`File -> Open` i wybierz folder projektu).
2. Poczekaj na `Gradle Sync`.
3. Uruchom na emulatorze lub telefonie:
   - emulator: wybierz urządzenie AVD i kliknij Run
   - telefon: włącz „Debugowanie USB”, podłącz kabel, zaakceptuj debugowanie i kliknij Run
4. Przy pierwszym uruchomieniu nadaj uprawnienia:
   - Lokalizacja (GPS) – „Zezwól podczas używania”
   - Mikrofon – „Zezwól”

### Testowanie sensorów na emulatorze
- **Akcelerometr:** w `Extended controls -> Virtual sensors` użyj trybu **Move** (samo Rotate może dawać stałe ~9.81).
- **GPS:** w `Extended controls -> Location` ustaw punkt lub trasę (route/GPX).

## Uprawnienia
Aplikacja wymaga:
- `android.permission.ACCESS_FINE_LOCATION`
- `android.permission.RECORD_AUDIO`

Wszystkie są obsługiwane jako runtime permissions.

Gotowy plik APK do testów znajduje się w zakładce Releases (SensorLogger v1.0).
