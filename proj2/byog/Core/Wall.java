package byog.Core;

import byog.TileEngine.*;
import java.util.ArrayList;

class Wall {

    private MapGen WORLD;

    Wall(MapGen world) {

        WORLD = world;
    }

    /* place wall on all nothing tiles next to a floor tile. */
    void placeWall() {
        for (int i = 1; i < WORLD.WIDTH - 1; i += 1) {
            for (int j = 1; j < WORLD.HEIGHT - 1; j += 1) {
                if (WORLD.MAP[i][j] == Tileset.NOTHING) {
                    changeTile(i, j);
                }
            }
        }
        for (Position P : WORLD.WALLS) {
            WORLD.POSITIONS.remove(P);
        }
    }

    /* Change if any surrounding tiles is floor. */
    private boolean checkTile(int x, int y) {
        return WORLD.MAP[x][y] == Tileset.FLOOR;
    }

    /* Change tile to wall if it is nothing and add it to wall list. */
    private void changeTile(int x, int y) {
        if (checkTile(x - 1, y) || checkTile(x + 1, y) || checkTile(x, y - 1) || checkTile(x, y + 1)) {
            WORLD.MAP[x][y] = Tileset.WALL;
            for (Position P : WORLD.POSITIONS) {
                if (P.equals(new Position(x, y))) {
                    WORLD.WALLS.add(P);
                }
            }
        }
    }
}
