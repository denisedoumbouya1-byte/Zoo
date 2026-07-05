package zoo;

public class RemoveAnimalCommand<T extends Animal>
        implements Command<Enclosure<? super T>> {

    private final T animal;
    private boolean executed;

    public RemoveAnimalCommand(T animal) {
        this.animal = animal;
    }

    @Override
    public Result<ZooError, String> execute(Enclosure<? super T> target) {
        if (target.remove(animal)) {
            executed = true;
            return Result.ok("Tier entfernt: " + animal.name());
        }

        return Result.err(ZooError.ANIMAL_COULD_NOT_BE_REMOVED);
    }

    @Override
    public Result<ZooError, String> undo(Enclosure<? super T> target) {
        if (!executed) {
            return Result.err(ZooError.COMMAND_NOT_EXECUTED);
        }

        if (target.add(animal)) {
            executed = false;
            return Result.ok("Entfernen rückgängig gemacht: " + animal.name());
        }

        return Result.err(ZooError.ANIMAL_COULD_NOT_BE_ADDED);
    }

    @Override
    public String description() {
        return "RemoveAnimalCommand für " + animal.name();
    }
}