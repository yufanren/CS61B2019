package byog.Core;

import java.util.ArrayList;
/* a Room object with the lower left tile of the room, width, height and all tiles in the room. */

public class Room {
    //public TETile[][] WORLD;

    private Position llCorner;

    private int WIDTH;

    private int HEIGHT;

    ArrayList<Position> TILES = new ArrayList();

    private MapGen MAP;

    boolean OVERLAP;

    int hallWay;

    protected boolean connectToMain;

    /* Remove room from map. */
    void destroyRoom() {
        MAP.POSITIONS.addAll(TILES);
    }

    Room(MapGen map, Position corner, int width, int height) {
        MAP = map;

        llCorner = corner;

        WIDTH = width;

        HEIGHT = height;

        OVERLAP = getTiles();
    }
    /* Get random tile from this room. */
    Position randomTile() {

        return TILES.get(RandomUtils.uniform(MAP.RANDOM, 0, TILES.size()));
    }

    /* Compare rooms according to their llCorner. */
    boolean minRoom(Room b) {
        int aValue = this.llCorner.Px + this.llCorner.Py;
        int bValue = b.llCorner.Px + b.llCorner.Py;
        if (aValue < bValue) {
            return true;
        }
        return false;
    }

    /* Takes the tile objects from the MAP and store in room object. If a tile can not be found in
    * map, set OVERLAP to true. */
    private boolean getTiles() {
        ArrayList<Position> tileTemp = new ArrayList();
        for (int i = llCorner.Px; i < llCorner.Px + WIDTH; i += 1) {
            for (int j = llCorner.Py; j < llCorner.Py + HEIGHT; j += 1) {
                boolean found = false;
                for (Position P : MAP.POSITIONS) {
                    if ((P.equals(new Position(i, j)))) {
                        tileTemp.add(P);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    return true;
                }
            }
        }
        TILES = tileTemp;
        for (Position P : TILES) {
            MAP.POSITIONS.remove(P);
        }
        return false;
    }

}
