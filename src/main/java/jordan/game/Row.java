package jordan.game;

import java.util.List;

public class Row {

    private final List<Tile> tiles;

    public Row(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public List<Tile> getTiles() {
        return tiles;
    }
}
