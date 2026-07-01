# Zoo-Projekt

## Aufgabe 1.2 – Wahl der Datenstruktur

Für die Speicherung der Tiere in einem Gehege wurde `LinkedHashSet` verwendet.

Begründung:

- Ein `Set` verhindert doppelte Einträge automatisch. Dadurch kann ein Tier nicht mehrfach im selben Gehege vorkommen.
- `LinkedHashSet` erhält zusätzlich die Einfügereihenfolge der Elemente. Dadurch werden die Tiere in der Reihenfolge ausgegeben, in der sie hinzugefügt wurden.

---

# Aufgabe 1.4 – Wahl der Rückgabetypen

- `List<T>` wird verwendet, wenn mehrere Tiere oder Gehege zurückgegeben werden.
- `boolean` wird für `add()` und `remove()` verwendet, um anzugeben, ob die Operation erfolgreich war.
- `Map<Class<?>, Long>` wird verwendet, um die Anzahl der Tiere je Tierart zu speichern.
- `Enclosure<? extends Animal>` wird bei der Suche nach Gehegen verwendet, da unterschiedliche Gehegetypen zurückgegeben werden können.

---

# Aufgabe 3 – Reflexion

## Generics

Generics sorgen dafür, dass nur passende Tierarten in die entsprechenden Gehege eingefügt werden können. Dadurch werden Fehler bereits zur Compile-Zeit erkannt.

Beispiel:

Ein `Aquarium<Fish>` akzeptiert ausschließlich Fische. Ein Versuch, eine `Snake` oder einen `Lion` hinzuzufügen, führt bereits beim Kompilieren zu einem Fehler.

---

## Logging

Ein Logger ist sinnvoller als `System.out.println()`, weil verschiedene Log-Level verwendet werden können. Dadurch lassen sich Ausgaben gezielt ein- oder ausschalten und wichtige Ereignisse besser unterscheiden.

Verwendung der Log-Level:

- **INFO:** Aufruf öffentlicher Methoden.
- **FINE:** Informationen über den erfolgreichen Ablauf (z. B. Anzahl der Tiere).
- **WARNING:** Gesuchte Tiere oder Gehege wurden nicht gefunden.
- **SEVERE:** Schwerwiegende Fehler oder Inkonsistenzen, z. B. wenn versucht wird, ein `null`-Gehege hinzuzufügen.

---

## Streams

Streams erleichtern das Formulieren von Abfragen erheblich. Mit `filter()`, `map()` und `flatMap()` können Tiere einfach gefiltert, umgewandelt und aus mehreren Gehegen zusammengeführt werden, ohne explizite Schleifen schreiben zu müssen.

Bei längeren Stream-Ketten kann die Lesbarkeit jedoch etwas schlechter sein als bei klassischen Schleifen.