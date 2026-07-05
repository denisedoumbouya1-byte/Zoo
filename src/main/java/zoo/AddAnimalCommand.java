package zoo;

public class AddAnimalCommand<T extends Animal>
        implements Command<Enclosure<? super T>> {

    private final T animal;
    private boolean executed;

    public AddAnimalCommand(T animal) {
        this.animal = animal;
    }

    @Override
    public Result<ZooError, String> execute(Enclosure<? super T> target) {
        if (target.add(animal)) {
            executed = true;
            return Result.ok("Tier hinzugefügt: " + animal.name());
        }

        return Result.err(ZooError.ANIMAL_COULD_NOT_BE_ADDED);
    }

    @Override
    public Result<ZooError, String> undo(Enclosure<? super T> target) {
        if (!executed) {
            return Result.err(ZooError.COMMAND_NOT_EXECUTED);
        }

        if (target.remove(animal)) {
            executed = false;
            return Result.ok("Hinzufügen rückgängig gemacht: " + animal.name());
        }

        return Result.err(ZooError.ANIMAL_COULD_NOT_BE_REMOVED);
    }

    @Override
    public String description() {
        return "AddAnimalCommand für " + animal.name();
    }
}