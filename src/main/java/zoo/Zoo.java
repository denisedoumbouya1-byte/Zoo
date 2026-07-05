package zoo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.Optional;

public class Zoo {

    private static final Logger LOGGER = Logger.getLogger(Zoo.class.getName());

    private final List<Enclosure<? extends Animal>> enclosures = new ArrayList<>();

    // Blatt 08: Optional Suche über alle Gehege
    public Optional<Animal> findAnimalByName(String animalName) {
        return enclosures.stream()
                .map(enclosure -> enclosure.findAnimalByName(animalName)
                        .map(animal -> (Animal) animal))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }
    public void addEnclosure(Enclosure<? extends Animal> enclosure) {

        if (enclosure == null) {
            LOGGER.severe("Versuch, ein null-Gehege hinzuzufügen.");
            throw new IllegalArgumentException("Enclosure darf nicht null sein.");
        }

        LOGGER.info("addEnclosure aufgerufen: " + enclosure.getName());

        enclosures.add(enclosure);

        LOGGER.fine("Gehege hinzugefügt. Anzahl Gehege: " + enclosures.size());
    }

    public List<Enclosure<? extends Animal>> getEnclosures() {
        LOGGER.info("getEnclosures aufgerufen");
        return new ArrayList<>(enclosures);
    }

    public Enclosure<? extends Animal> findEnclosureByName(String name) {
        LOGGER.info("findEnclosureByName aufgerufen: " + name);

        Enclosure<? extends Animal> result = enclosures.stream()
                .filter(e -> e.getName().equals(name))
                .findFirst()
                .orElse(null);

        if (result == null) {
            LOGGER.warning("Gehege nicht gefunden: " + name);
        }

        return result;
    }

    public List<Animal> getAllAnimals() {
        LOGGER.info("getAllAnimals aufgerufen");

        List<Animal> animals = enclosures.stream()
                .flatMap(e -> e.getInhabitants().stream())
                .collect(Collectors.toList());

        LOGGER.fine("Anzahl Tiere im Zoo: " + animals.size());
        return animals;
    }

    public List<Mammal> getAllMammals() {
        LOGGER.info("getAllMammals aufgerufen");

        return getAllAnimals().stream()
                .filter(Mammal.class::isInstance)
                .map(Mammal.class::cast)
                .collect(Collectors.toList());
    }

    public List<Animal> getAnimalsByPredicate(Predicate<Animal> predicate) {
        LOGGER.info("getAnimalsByPredicate aufgerufen");

        return getAllAnimals().stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public Map<Class<?>, Long> countAnimalsByType() {
        LOGGER.info("countAnimalsByType aufgerufen");

        return getAllAnimals().stream()
                .collect(Collectors.groupingBy(
                        Animal::getClass,
                        Collectors.counting()
                ));
    }

    public List<Enclosure<? extends Animal>> getOvercrowdedEnclosures(int maxAnimals) {
        LOGGER.info("getOvercrowdedEnclosures aufgerufen mit Grenze: " + maxAnimals);

        return enclosures.stream()
                .filter(e -> e.getInhabitants().size() > maxAnimals)
                .collect(Collectors.toList());
    }

    public String summary() {
        LOGGER.info("summary aufgerufen");

        long animalCount = getAllAnimals().size();
        long mammals = getAllAnimals().stream()
                .filter(Mammal.class::isInstance)
                .count();

        long birds = getAllAnimals().stream()
                .filter(Bird.class::isInstance)
                .count();

        long fish = getAllAnimals().stream()
                .filter(Fish.class::isInstance)
                .count();

        long reptiles = getAllAnimals().stream()
                .filter(Reptile.class::isInstance)
                .count();

        String result =
                "Zoo mit "
                        + enclosures.size()
                        + " Gehegen und "
                        + animalCount
                        + " Tieren: "
                        + mammals + " Mammals, "
                        + birds + " Birds, "
                        + fish + " Fish, "
                        + reptiles + " Reptiles";

        LOGGER.fine(result);

        return result;
    }
}