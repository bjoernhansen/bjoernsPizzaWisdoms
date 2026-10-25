# Wartungsupdate für Google Play (Android 16)

## Umfang

Basis ist `master`, Commit `65e5a3ec91b9f781a6912f094ad982d4a679d7d0`.
Die App richtet sich an Android 16 (API 36). Paketkennung
`com.blueflame.quoteoftheday`, `minSdk 21`, Versionsname `1.6`, Sprüche und
Funktionen bleiben erhalten. Der Versionscode steigt von 7 auf 8. Der bisher höchste Play-Versionscode 7
wurde vom App-Inhaber bestätigt.

Zum Bauen werden Android Gradle Plugin 8.10.1, Gradle 8.11.1 und JDK 17 oder 21
verwendet (Cloud: JDK 21). Der Java-Quellcode bleibt auf Java 8.
Android Studio Arctic Fox ist für diese Werkzeugversion zu alt; eine aktuelle
stabile Android-Studio-Version installieren und das Projekt darin öffnen.

## Bauen und prüfen

Im Projektverzeichnis, mit installiertem Android SDK Platform 36 und Build Tools
35.0.0, ausführen (Windows: `gradlew.bat` statt `bash gradlew`):

```sh
bash gradlew :app:assembleDebug :app:bundleRelease :app:testDebugUnitTest :app:lintDebug :app:assembleDebugAndroidTest
```

Das SDK wird über Android Studio eingerichtet. Sein Pfad steht lokal in der
ignorierten Datei `local.properties` (`sdk.dir=...`) oder in `ANDROID_HOME`.
Keine lokalen Pfade oder Passwörter einchecken.

Die vorhandenen JUnit-5-Tests werden jetzt tatsächlich ausgeführt. Ein zusätzlicher
Test prüft alle 25 Sprüche und den erneuten Durchlauf. Der Gerätetest prüft auch
Start und Bedienung des Weiter-Buttons.

Das Debug-APK auf einem Emulator oder separaten Testgerät verwenden; es ist
mit einem anderen Schlüssel als die Play-Version signiert. Den Update-Pfad mit
vorhandenen App-Daten später über den signierten internen Play-Test prüfen.

Auf einem angeschlossenen Testgerät oder laufenden Emulator:

```sh
bash gradlew :app:connectedDebugAndroidTest
```

Vor dem Release auf Android 16 und möglichst auf einer älteren Android-Version
Start, mehrfaches Weiterklicken, Hoch-/Querformat, helle/dunkle Darstellung und
Zurück/erneutes Öffnen prüfen. Systemleisten dürfen nichts verdecken.

## Android-16-Prüfung

- Edge-to-edge ist bei Ziel-API 36 auf Android 16 verbindlich. Das bestehende
  Layout fordert deshalb mit `fitsSystemWindows` die Systemleisten-Abstände an.
  Die tatsächliche Darstellung ist noch auf einem Android-16-Gerät zu prüfen.
- Die App hat eine einzige exportierte Launcher-Activity, keine eigenen
  Zurück-Handler und keine Orientierungssperre. Änderungen am Zurück-Verhalten
  oder an Einschränkungen großer Displays benötigen daher keinen Umbau.
- Keine Netzwerk-, Medien- oder Laufzeitberechtigungen, Hintergrunddienste,
  Alarme oder eigenen nativen Bibliotheken sind im Quellprojekt vorhanden.
  Die betreffenden Android-16-Verhaltensänderungen erfordern hier keine neue Logik.
- App-Daten werden durch dieses Update nicht migriert oder gelöscht.
  Android erhält sie beim regulären Update mit derselben Paketkennung und
  kompatibler Signatur. Nicht zum Testen die veröffentlichte App deinstallieren.

Referenzen: [Android-16-Verhaltensänderungen](https://developer.android.com/about/versions/16/behavior-changes-16),
[AGP 8.10](https://developer.android.com/build/releases/past-releases/agp-8-10-0-release-notes).
Die für dieses Konto geltende Frist und weitere Freigabeanforderungen in der
Play Console prüfen; eine Ziel-API-Änderung allein garantiert keine Zulassung.

## Cloud-Ergebnisse

- Debug-APK, Release-AAB und Gerätetest-APK erfolgreich gebaut.
- 2 Unit-Tests bestanden, keine fehlgeschlagenen oder übersprungenen Tests.
- Lint: 0 Fehler, 8 Warnungen (Bibliotheksupdates, ungenutzte Ressourcen,
  monochrome Icons und überflüssige SDK-Prüfungen in Ressourcen).
- Generiertes Release-Manifest: Paketkennung unverändert, minSdk 21,
  targetSdk 36, versionCode 8. AAB enthält keine nativen `.so`-Bibliotheken
  und ist unsigniert.
- Installation und Build mit den wiederverwendbaren Cloud-Schritten erfolgreich
  wiederholt.

Die Gerätetests wurden **nicht ausgeführt**: Der Cloud-Emulator ohne KVM erreicht
keinen stabilen Android-16-Systemstart. Im Android-Protokoll beendet der Watchdog
den Systemprozess (`WATCHDOG KILLING SYSTEM PROCESS`, Hauptthread über 63 Sekunden
blockiert); System-Apps melden anschließend `DeadSystemException`. Dies geschieht
vor der Installation der Pizza-App. Der Installationsversuch meldet
`device is still booting`. Dies ist eine Grenze der Laufzeitprüfung in dieser
Cloud, kein nachgewiesener Fehler der Pizza-App. Die Systemleisten und den
Weiter-Button vor dem Release auf einem Android-16-Gerät oder einem lokal
hardwarebeschleunigten Android-16-Emulator prüfen.

## Signiertes AAB erstellen

1. In der **Play Console → App-Bundle-Explorer** und in allen Test-/Produktions-
   Tracks den höchsten jemals hochgeladenen Versionscode prüfen. `versionCode`
   in `app/build.gradle` muss größer sein. **8 nur verwenden, wenn der höchste
   bisherige Wert höchstens 7 ist.** Ein bereits hochgeladener Code ist nicht
   wiederverwendbar. Der im Repository abgelegte alte Release-Build beweist
   nicht den aktuellen Stand der Play Console.
2. Bestehenden Keystore, Alias und Passwörter sicher bereithalten. Unter
   **App-Integrität → App-Signierung** prüfen, welcher Upload-Schlüssel erwartet
   wird. Den bisherigen passenden Schlüssel verwenden; keinen neuen Keystore
   erzeugen und keinen Schlüssel ersetzen. Bei fehlendem Schlüssel hier stoppen
   und den offiziellen Wiederherstellungsweg prüfen. Schlüssel und Passwörter
   weder in Git noch in diesen Chat hochladen.
3. In aktuellem Android Studio: **Build → Generate Signed Bundle / APK → Android
   App Bundle**, Modul `app`, vorhandenen Keystore und Alias auswählen,
   Passwörter lokal eingeben, Variante **release** wählen und bauen.
4. Das gerade erzeugte AAB aus dem im Assistenten angegebenen Ausgabeordner
   verwenden. Das von `bundleRelease` in der Cloud erstellte AAB ist **unsigniert**.
   Auch `app/release/app-release.aab` im Repository ist ein altes Artefakt und
   darf nicht versehentlich hochgeladen werden.

## In Google Play einreichen

1. Das signierte AAB zunächst in einen **internen Test-Release** laden. Google
   prüft unter anderem Paketkennung, Signatur, Ziel-API und Versionscode.
2. Über den internen Play-Test installieren bzw. die bisherige App aktualisieren
   und den obigen Funktionstest durchführen. Vorab-Testbericht und Hinweise in
   der Play Console prüfen. Notwendige Pflichtangaben vervollständigen.
3. Erst nach deiner ausdrücklichen Freigabe den geprüften Release für Produktion
   übernehmen und zur Überprüfung bzw. Veröffentlichung einreichen. Einen
   Test-Release anzulegen aktualisiert noch nicht die öffentliche App.

Diese Änderungen veröffentlichen selbst nichts bei Google Play.

## Kleine Bildschirme

Die bisherigen festen 300-dp-Abstände und die unbeschränkte Bildgröße haben auf
dem Nexus S (API 26) Bild und Button abgeschnitten. Das Layout verankert den
Button nun unten, lässt dem Text seine benötigte Höhe und skaliert das Bild
proportional in den verbleibenden Platz. Nach Aktualisierung des Branches auf
dem Nexus-S-Emulator erneut prüfen; der Android-16-Test bleibt zusätzlich nötig.
