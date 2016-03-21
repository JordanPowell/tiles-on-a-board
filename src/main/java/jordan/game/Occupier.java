package jordan.game;

public class Occupier {
    public static final Occupier EMPTY_OCCUPIER = new Occupier('-');
    private final char printCharacter;

    public Occupier(final char printCharacter) {
        this.printCharacter = printCharacter;
    }

    public char getPrintCharacter() {
        return printCharacter;
    }
}
