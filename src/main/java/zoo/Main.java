package zoo;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Logger rootLogger = Logger.getLogger("");
        rootLogger.setLevel(Level.FINE);

        for (var handler : rootLogger.getHandlers()) {
            handler.setLevel(Level.FINE);
        }

        Zoo zoo = new Zoo();

        Aquarium<Fish> aquarium = new Aquarium<>("Aquarium");
        aquarium.add(new Trout("Fritzi"));
        aquarium.add(new Salmon("Sami"));

        CatHouse<Cat> catHouse = new CatHouse<>("Katzenhaus");
        catHouse.add(new Lion("Leo"));
        catHouse.add(new Tiger("Tiga"));

        MammalHouse<Mammal> mammalHouse = new MammalHouse<>("Säugetierhaus");
        mammalHouse.add(new Gorilla("Gogo"));
        mammalHouse.add(new Mouse("Micky"));
        mammalHouse.add(new Beaver("Bibi"));

        Terrarium<Reptile> terrarium = new Terrarium<>("Terrarium");
        terrarium.add(new Snake("Sissi"));
        terrarium.add(new Turtle("Toni"));

        zoo.addEnclosure(aquarium);
        zoo.addEnclosure(catHouse);
        zoo.addEnclosure(mammalHouse);
        zoo.addEnclosure(terrarium);

        System.out.println(zoo.summary());
        System.out.println(zoo.getAllAnimals());
        System.out.println(zoo.getAllMammals());
        System.out.println(zoo.countAnimalsByType());
        System.out.println(zoo.getOvercrowdedEnclosures(2));
        System.out.println(zoo.findEnclosureByName("Nicht vorhanden"));
    }
}
