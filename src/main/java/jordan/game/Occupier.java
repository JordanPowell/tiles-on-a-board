package jordan.game;

public class Occupier {
    public static final Occupier EMPTY_OCCUPIER = new Occupier("-");
    private final String printCharacter;

    public Occupier(final String printCharacter) {
        this.printCharacter = printCharacter;
    }

    public String getPrintCharacter() {
        return printCharacter;
    }
}
