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

## Użyte sensory / źródła danych (min. 3)
1. **GPS / Lokalizacja**  
   - pobieranie aktualnej pozycji (szer./dł. geograficzna) oraz dokładności (± metry)

2. **Akcelerometr**  
   - wyznaczanie „ruchu” jako peak przyspieszenia (w projekcie: peak/linear peak)

3. **Mikrofon**  
   - pomiar poziomu hałasu (wartość w skali 0–100 na podstawie RMS z AudioRecord)

---

## Funkcjonalności a wymagania minimalne
- ✅ Akwizycja danych z min. 3 źródeł: GPS + akcelerometr + mikrofon
- ✅ Reprezentacja danych: lista + statystyki (dashboard) + wykres
- ✅ Min. 2 ekrany: Dashboard + Szczegóły (Navigation Compose)
- ✅ Type-safe routes: @Serializable routes + composable<Route>()
- ✅ Trwałość danych: Room (lokalna baza)
- ✅ Interakcja: pomiar, przeglądanie, usuwanie i reset danych
- ✅ Obsługa runtime permissions: lokalizacja i mikrofon

---

## Zrzuty ekranu (wstaw swoje screeny)
> Zrób screeny w emulatorze/telefonie i wrzuć je do folderu `docs/screens/` (utwórz go), a następnie podmień linki poniżej.

### 1) Ekran Dashboard – lista + statystyki + wykres
**Co ma być widoczne:**
- karta „Statystyki”
- wykres hałasu z osiami
- lista pomiarów

**Wstaw tutaj:**
![Dashboard](docs/screens/01_dashboard.png)

### 2) Ekran Szczegóły pomiaru
**Co ma być widoczne:**
- data/czas
- GPS (lat/lon) i dokładność
- ruch peak
- hałas
- przycisk „Usuń pomiar”

**Wstaw tutaj:**
![Details](docs/screens/02_details.png)

### 3) Dialog potwierdzający usunięcie pomiaru
**Co ma być widoczne:**
- okno dialogowe „Usunąć pomiar?”

**Wstaw tutaj:**
![Delete dialog](docs/screens/03_delete_dialog.png)

### 4) Ekran uprawnień (PermissionGate)
**Co ma być widoczne:**
- informacja o wymaganych uprawnieniach
- przycisk „Nadaj uprawnienia”

**Wstaw tutaj:**
![Permissions](docs/screens/04_permissions.png)

---

## Instrukcja uruchomienia

### Wymagania
- Android Studio (zalecane: najnowsza wersja)
- Android SDK
- Telefon z Androidem lub emulator (AVD)
- Minimalny SDK projektu: **minSdk 34**

### Uruchomienie w Android Studio
1. Otwórz projekt w Android Studio (`File -> Open` i wybierz folder projektu).
2. Poczekaj na `Gradle Sync`.
3. Uruchom na emulatorze lub telefonie:
   - emulator: wybierz urządzenie AVD i kliknij `Run (▶)`
   - telefon: włącz „Debugowanie USB”, podłącz kabel, zaakceptuj debugowanie i kliknij `Run (▶)`
4. Przy pierwszym uruchomieniu nadaj uprawnienia:
   - Lokalizacja (GPS) – „Zezwól podczas używania”
   - Mikrofon – „Zezwól”

### Testowanie sensorów na emulatorze
- **Akcelerometr:** w `Extended controls -> Virtual sensors` użyj trybu **Move** (samo Rotate może dawać stałe ~9.81).
- **GPS:** w `Extended controls -> Location` ustaw punkt lub trasę (route/GPX).  
  Uwaga: w zależności od obrazu emulatora zachowanie GPS może się różnić.

---

## Budowanie pliku APK do testów

### APK debug (najszybsze – do testów)
1. Android Studio → `Build` → `Build Bundle(s) / APK(s)` → `Build APK(s)`
2. Po zakończeniu kliknij w komunikat „locate” lub znajdź plik tutaj:
   - `app/build/outputs/apk/debug/app-debug.apk`

### APK release (opcjonalnie)
1. Android Studio → `Build` → `Generate Signed Bundle / APK`
2. Wybierz `APK` → dalej → utwórz/wybierz keystore → `release`
3. Plik pojawi się zwykle w:
   - `app/build/outputs/apk/release/app-release.apk`

---

## Uprawnienia
Aplikacja wymaga:
- `android.permission.ACCESS_FINE_LOCATION`
- `android.permission.RECORD_AUDIO`

Wszystkie są obsługiwane jako runtime permissions.
