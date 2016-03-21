package jordan.game;

public class Tile {
    private final Occupier occupier;
    private final int x;
    private final int y;

    public Tile(final Occupier occupier, final int x, final int y) {
        this.occupier = occupier;
        this.x = x;
        this.y = y;
    }

    public Occupier getOccupier() {
        return occupier;
    }
}
